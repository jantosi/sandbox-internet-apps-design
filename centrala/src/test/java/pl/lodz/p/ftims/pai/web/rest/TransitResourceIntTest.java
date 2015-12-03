package pl.lodz.p.ftims.pai.web.rest;

import pl.lodz.p.ftims.pai.Application;
import pl.lodz.p.ftims.pai.domain.Transit;
import pl.lodz.p.ftims.pai.repository.TransitRepository;

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

import pl.lodz.p.ftims.pai.domain.enumeration.TransitType;

/**
 * Test class for the TransitResource REST controller.
 *
 * @see TransitResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TransitResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));



private static final TransitType DEFAULT_TYPE = TransitType.EXPRESS;
    private static final TransitType UPDATED_TYPE = TransitType.NORMAL;

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_TIME_STR = dateTimeFormatter.format(DEFAULT_START_TIME);

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_TIME_STR = dateTimeFormatter.format(DEFAULT_END_TIME);

    @Inject
    private TransitRepository transitRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTransitMockMvc;

    private Transit transit;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TransitResource transitResource = new TransitResource();
        ReflectionTestUtils.setField(transitResource, "transitRepository", transitRepository);
        this.restTransitMockMvc = MockMvcBuilders.standaloneSetup(transitResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        transit = new Transit();
        transit.setType(DEFAULT_TYPE);
        transit.setStartTime(DEFAULT_START_TIME);
        transit.setEndTime(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    public void createTransit() throws Exception {
        int databaseSizeBeforeCreate = transitRepository.findAll().size();

        // Create the Transit

        restTransitMockMvc.perform(post("/api/transits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(transit)))
                .andExpect(status().isCreated());

        // Validate the Transit in the database
        List<Transit> transits = transitRepository.findAll();
        assertThat(transits).hasSize(databaseSizeBeforeCreate + 1);
        Transit testTransit = transits.get(transits.size() - 1);
        assertThat(testTransit.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTransit.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testTransit.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    public void getAllTransits() throws Exception {
        // Initialize the database
        transitRepository.saveAndFlush(transit);

        // Get all the transits
        restTransitMockMvc.perform(get("/api/transits"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(transit.getId().intValue())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME_STR)))
                .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME_STR)));
    }

    @Test
    @Transactional
    public void getTransit() throws Exception {
        // Initialize the database
        transitRepository.saveAndFlush(transit);

        // Get the transit
        restTransitMockMvc.perform(get("/api/transits/{id}", transit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(transit.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME_STR))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingTransit() throws Exception {
        // Get the transit
        restTransitMockMvc.perform(get("/api/transits/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransit() throws Exception {
        // Initialize the database
        transitRepository.saveAndFlush(transit);

		int databaseSizeBeforeUpdate = transitRepository.findAll().size();

        // Update the transit
        transit.setType(UPDATED_TYPE);
        transit.setStartTime(UPDATED_START_TIME);
        transit.setEndTime(UPDATED_END_TIME);

        restTransitMockMvc.perform(put("/api/transits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(transit)))
                .andExpect(status().isOk());

        // Validate the Transit in the database
        List<Transit> transits = transitRepository.findAll();
        assertThat(transits).hasSize(databaseSizeBeforeUpdate);
        Transit testTransit = transits.get(transits.size() - 1);
        assertThat(testTransit.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTransit.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testTransit.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void deleteTransit() throws Exception {
        // Initialize the database
        transitRepository.saveAndFlush(transit);

		int databaseSizeBeforeDelete = transitRepository.findAll().size();

        // Get the transit
        restTransitMockMvc.perform(delete("/api/transits/{id}", transit.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Transit> transits = transitRepository.findAll();
        assertThat(transits).hasSize(databaseSizeBeforeDelete - 1);
    }
}
