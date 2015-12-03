package pl.lodz.p.ftims.pai.web.rest;

import pl.lodz.p.ftims.pai.Application;
import pl.lodz.p.ftims.pai.domain.Transporter;
import pl.lodz.p.ftims.pai.repository.TransporterRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TransporterResource REST controller.
 *
 * @see TransporterResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TransporterResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_PURCHASE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_PURCHASE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_PURCHASE_TIME_STR = dateTimeFormatter.format(DEFAULT_PURCHASE_TIME);

    private static final ZonedDateTime DEFAULT_WITHDRAWAL_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_WITHDRAWAL_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_WITHDRAWAL_TIME_STR = dateTimeFormatter.format(DEFAULT_WITHDRAWAL_TIME);
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private TransporterRepository transporterRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTransporterMockMvc;

    private Transporter transporter;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TransporterResource transporterResource = new TransporterResource();
        ReflectionTestUtils.setField(transporterResource, "transporterRepository", transporterRepository);
        this.restTransporterMockMvc = MockMvcBuilders.standaloneSetup(transporterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        transporter = new Transporter();
        transporter.setPurchaseTime(DEFAULT_PURCHASE_TIME);
        transporter.setWithdrawalTime(DEFAULT_WITHDRAWAL_TIME);
        transporter.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTransporter() throws Exception {
        int databaseSizeBeforeCreate = transporterRepository.findAll().size();

        // Create the Transporter

        restTransporterMockMvc.perform(post("/api/transporters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(transporter)))
                .andExpect(status().isCreated());

        // Validate the Transporter in the database
        List<Transporter> transporters = transporterRepository.findAll();
        assertThat(transporters).hasSize(databaseSizeBeforeCreate + 1);
        Transporter testTransporter = transporters.get(transporters.size() - 1);
        assertThat(testTransporter.getPurchaseTime()).isEqualTo(DEFAULT_PURCHASE_TIME);
        assertThat(testTransporter.getWithdrawalTime()).isEqualTo(DEFAULT_WITHDRAWAL_TIME);
        assertThat(testTransporter.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllTransporters() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporters
        restTransporterMockMvc.perform(get("/api/transporters"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(transporter.getId().intValue())))
                .andExpect(jsonPath("$.[*].purchaseTime").value(hasItem(DEFAULT_PURCHASE_TIME_STR)))
                .andExpect(jsonPath("$.[*].withdrawalTime").value(hasItem(DEFAULT_WITHDRAWAL_TIME_STR)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTransporter() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get the transporter
        restTransporterMockMvc.perform(get("/api/transporters/{id}", transporter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(transporter.getId().intValue()))
            .andExpect(jsonPath("$.purchaseTime").value(DEFAULT_PURCHASE_TIME_STR))
            .andExpect(jsonPath("$.withdrawalTime").value(DEFAULT_WITHDRAWAL_TIME_STR))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransporter() throws Exception {
        // Get the transporter
        restTransporterMockMvc.perform(get("/api/transporters/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransporter() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

		int databaseSizeBeforeUpdate = transporterRepository.findAll().size();

        // Update the transporter
        transporter.setPurchaseTime(UPDATED_PURCHASE_TIME);
        transporter.setWithdrawalTime(UPDATED_WITHDRAWAL_TIME);
        transporter.setName(UPDATED_NAME);

        restTransporterMockMvc.perform(put("/api/transporters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(transporter)))
                .andExpect(status().isOk());

        // Validate the Transporter in the database
        List<Transporter> transporters = transporterRepository.findAll();
        assertThat(transporters).hasSize(databaseSizeBeforeUpdate);
        Transporter testTransporter = transporters.get(transporters.size() - 1);
        assertThat(testTransporter.getPurchaseTime()).isEqualTo(UPDATED_PURCHASE_TIME);
        assertThat(testTransporter.getWithdrawalTime()).isEqualTo(UPDATED_WITHDRAWAL_TIME);
        assertThat(testTransporter.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteTransporter() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

		int databaseSizeBeforeDelete = transporterRepository.findAll().size();

        // Get the transporter
        restTransporterMockMvc.perform(delete("/api/transporters/{id}", transporter.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Transporter> transporters = transporterRepository.findAll();
        assertThat(transporters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
