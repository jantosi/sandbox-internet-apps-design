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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import pl.lodz.p.ftims.pai.domain.enumeration.TransporterType;

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



private static final TransporterType DEFAULT_TYPE = TransporterType.CAR;
    private static final TransporterType UPDATED_TYPE = TransporterType.TRUCK;
    private static final String DEFAULT_MODEL = "AAAAA";
    private static final String UPDATED_MODEL = "BBBBB";

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
        transporter.setType(DEFAULT_TYPE);
        transporter.setModel(DEFAULT_MODEL);
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
        assertThat(testTransporter.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTransporter.getModel()).isEqualTo(DEFAULT_MODEL);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transporterRepository.findAll().size();
        // set the field null
        transporter.setType(null);

        // Create the Transporter, which fails.

        restTransporterMockMvc.perform(post("/api/transporters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(transporter)))
                .andExpect(status().isBadRequest());

        List<Transporter> transporters = transporterRepository.findAll();
        assertThat(transporters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModelIsRequired() throws Exception {
        int databaseSizeBeforeTest = transporterRepository.findAll().size();
        // set the field null
        transporter.setModel(null);

        // Create the Transporter, which fails.

        restTransporterMockMvc.perform(post("/api/transporters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(transporter)))
                .andExpect(status().isBadRequest());

        List<Transporter> transporters = transporterRepository.findAll();
        assertThat(transporters).hasSize(databaseSizeBeforeTest);
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
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())));
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
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()));
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
        transporter.setType(UPDATED_TYPE);
        transporter.setModel(UPDATED_MODEL);

        restTransporterMockMvc.perform(put("/api/transporters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(transporter)))
                .andExpect(status().isOk());

        // Validate the Transporter in the database
        List<Transporter> transporters = transporterRepository.findAll();
        assertThat(transporters).hasSize(databaseSizeBeforeUpdate);
        Transporter testTransporter = transporters.get(transporters.size() - 1);
        assertThat(testTransporter.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTransporter.getModel()).isEqualTo(UPDATED_MODEL);
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
