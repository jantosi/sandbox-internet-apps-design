package pl.lodz.p.ftims.pai.web.rest;

import pl.lodz.p.ftims.pai.Application;
import pl.lodz.p.ftims.pai.domain.TransporterInfo;
import pl.lodz.p.ftims.pai.repository.TransporterInfoRepository;

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

import pl.lodz.p.ftims.pai.domain.enumeration.TransporterType;

/**
 * Test class for the TransporterInfoResource REST controller.
 *
 * @see TransporterInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TransporterInfoResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));



private static final TransporterType DEFAULT_TYPE = TransporterType.VAN;
    private static final TransporterType UPDATED_TYPE = TransporterType.CAR;
    private static final String DEFAULT_MODEL = "AAAAA";
    private static final String UPDATED_MODEL = "BBBBB";
    private static final String DEFAULT_MAKE = "AAAAA";
    private static final String UPDATED_MAKE = "BBBBB";

    private static final ZonedDateTime DEFAULT_YEAR = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_YEAR = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_YEAR_STR = dateTimeFormatter.format(DEFAULT_YEAR);

    @Inject
    private TransporterInfoRepository transporterInfoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTransporterInfoMockMvc;

    private TransporterInfo transporterInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TransporterInfoResource transporterInfoResource = new TransporterInfoResource();
        ReflectionTestUtils.setField(transporterInfoResource, "transporterInfoRepository", transporterInfoRepository);
        this.restTransporterInfoMockMvc = MockMvcBuilders.standaloneSetup(transporterInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        transporterInfo = new TransporterInfo();
        transporterInfo.setType(DEFAULT_TYPE);
        transporterInfo.setModel(DEFAULT_MODEL);
        transporterInfo.setMake(DEFAULT_MAKE);
        transporterInfo.setYear(DEFAULT_YEAR);
    }

    @Test
    @Transactional
    public void createTransporterInfo() throws Exception {
        int databaseSizeBeforeCreate = transporterInfoRepository.findAll().size();

        // Create the TransporterInfo

        restTransporterInfoMockMvc.perform(post("/api/transporterInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(transporterInfo)))
                .andExpect(status().isCreated());

        // Validate the TransporterInfo in the database
        List<TransporterInfo> transporterInfos = transporterInfoRepository.findAll();
        assertThat(transporterInfos).hasSize(databaseSizeBeforeCreate + 1);
        TransporterInfo testTransporterInfo = transporterInfos.get(transporterInfos.size() - 1);
        assertThat(testTransporterInfo.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTransporterInfo.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testTransporterInfo.getMake()).isEqualTo(DEFAULT_MAKE);
        assertThat(testTransporterInfo.getYear()).isEqualTo(DEFAULT_YEAR);
    }

    @Test
    @Transactional
    public void getAllTransporterInfos() throws Exception {
        // Initialize the database
        transporterInfoRepository.saveAndFlush(transporterInfo);

        // Get all the transporterInfos
        restTransporterInfoMockMvc.perform(get("/api/transporterInfos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(transporterInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
                .andExpect(jsonPath("$.[*].make").value(hasItem(DEFAULT_MAKE.toString())))
                .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR_STR)));
    }

    @Test
    @Transactional
    public void getTransporterInfo() throws Exception {
        // Initialize the database
        transporterInfoRepository.saveAndFlush(transporterInfo);

        // Get the transporterInfo
        restTransporterInfoMockMvc.perform(get("/api/transporterInfos/{id}", transporterInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(transporterInfo.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.make").value(DEFAULT_MAKE.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR_STR));
    }

    @Test
    @Transactional
    public void getNonExistingTransporterInfo() throws Exception {
        // Get the transporterInfo
        restTransporterInfoMockMvc.perform(get("/api/transporterInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransporterInfo() throws Exception {
        // Initialize the database
        transporterInfoRepository.saveAndFlush(transporterInfo);

		int databaseSizeBeforeUpdate = transporterInfoRepository.findAll().size();

        // Update the transporterInfo
        transporterInfo.setType(UPDATED_TYPE);
        transporterInfo.setModel(UPDATED_MODEL);
        transporterInfo.setMake(UPDATED_MAKE);
        transporterInfo.setYear(UPDATED_YEAR);

        restTransporterInfoMockMvc.perform(put("/api/transporterInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(transporterInfo)))
                .andExpect(status().isOk());

        // Validate the TransporterInfo in the database
        List<TransporterInfo> transporterInfos = transporterInfoRepository.findAll();
        assertThat(transporterInfos).hasSize(databaseSizeBeforeUpdate);
        TransporterInfo testTransporterInfo = transporterInfos.get(transporterInfos.size() - 1);
        assertThat(testTransporterInfo.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTransporterInfo.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testTransporterInfo.getMake()).isEqualTo(UPDATED_MAKE);
        assertThat(testTransporterInfo.getYear()).isEqualTo(UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void deleteTransporterInfo() throws Exception {
        // Initialize the database
        transporterInfoRepository.saveAndFlush(transporterInfo);

		int databaseSizeBeforeDelete = transporterInfoRepository.findAll().size();

        // Get the transporterInfo
        restTransporterInfoMockMvc.perform(delete("/api/transporterInfos/{id}", transporterInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TransporterInfo> transporterInfos = transporterInfoRepository.findAll();
        assertThat(transporterInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
