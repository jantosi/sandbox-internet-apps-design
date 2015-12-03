package pl.lodz.p.ftims.pai.web.rest;

import pl.lodz.p.ftims.pai.Application;
import pl.lodz.p.ftims.pai.domain.EmployeePersonalData;
import pl.lodz.p.ftims.pai.repository.EmployeePersonalDataRepository;

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
 * Test class for the EmployeePersonalDataResource REST controller.
 *
 * @see EmployeePersonalDataResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EmployeePersonalDataResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_DATE_STR = dateTimeFormatter.format(DEFAULT_START_DATE);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_DATE_STR = dateTimeFormatter.format(DEFAULT_END_DATE);
    private static final String DEFAULT_FIRST_NAME = "AAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBB";
    private static final String DEFAULT_LAST_NAME = "AAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";

    @Inject
    private EmployeePersonalDataRepository employeePersonalDataRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEmployeePersonalDataMockMvc;

    private EmployeePersonalData employeePersonalData;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmployeePersonalDataResource employeePersonalDataResource = new EmployeePersonalDataResource();
        ReflectionTestUtils.setField(employeePersonalDataResource, "employeePersonalDataRepository", employeePersonalDataRepository);
        this.restEmployeePersonalDataMockMvc = MockMvcBuilders.standaloneSetup(employeePersonalDataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        employeePersonalData = new EmployeePersonalData();
        employeePersonalData.setStartDate(DEFAULT_START_DATE);
        employeePersonalData.setEndDate(DEFAULT_END_DATE);
        employeePersonalData.setFirstName(DEFAULT_FIRST_NAME);
        employeePersonalData.setLastName(DEFAULT_LAST_NAME);
        employeePersonalData.setAddress(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createEmployeePersonalData() throws Exception {
        int databaseSizeBeforeCreate = employeePersonalDataRepository.findAll().size();

        // Create the EmployeePersonalData

        restEmployeePersonalDataMockMvc.perform(post("/api/employeePersonalDatas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeePersonalData)))
                .andExpect(status().isCreated());

        // Validate the EmployeePersonalData in the database
        List<EmployeePersonalData> employeePersonalDatas = employeePersonalDataRepository.findAll();
        assertThat(employeePersonalDatas).hasSize(databaseSizeBeforeCreate + 1);
        EmployeePersonalData testEmployeePersonalData = employeePersonalDatas.get(employeePersonalDatas.size() - 1);
        assertThat(testEmployeePersonalData.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testEmployeePersonalData.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testEmployeePersonalData.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testEmployeePersonalData.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testEmployeePersonalData.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllEmployeePersonalDatas() throws Exception {
        // Initialize the database
        employeePersonalDataRepository.saveAndFlush(employeePersonalData);

        // Get all the employeePersonalDatas
        restEmployeePersonalDataMockMvc.perform(get("/api/employeePersonalDatas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(employeePersonalData.getId().intValue())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE_STR)))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE_STR)))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())));
    }

    @Test
    @Transactional
    public void getEmployeePersonalData() throws Exception {
        // Initialize the database
        employeePersonalDataRepository.saveAndFlush(employeePersonalData);

        // Get the employeePersonalData
        restEmployeePersonalDataMockMvc.perform(get("/api/employeePersonalDatas/{id}", employeePersonalData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(employeePersonalData.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE_STR))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE_STR))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeePersonalData() throws Exception {
        // Get the employeePersonalData
        restEmployeePersonalDataMockMvc.perform(get("/api/employeePersonalDatas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeePersonalData() throws Exception {
        // Initialize the database
        employeePersonalDataRepository.saveAndFlush(employeePersonalData);

		int databaseSizeBeforeUpdate = employeePersonalDataRepository.findAll().size();

        // Update the employeePersonalData
        employeePersonalData.setStartDate(UPDATED_START_DATE);
        employeePersonalData.setEndDate(UPDATED_END_DATE);
        employeePersonalData.setFirstName(UPDATED_FIRST_NAME);
        employeePersonalData.setLastName(UPDATED_LAST_NAME);
        employeePersonalData.setAddress(UPDATED_ADDRESS);

        restEmployeePersonalDataMockMvc.perform(put("/api/employeePersonalDatas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeePersonalData)))
                .andExpect(status().isOk());

        // Validate the EmployeePersonalData in the database
        List<EmployeePersonalData> employeePersonalDatas = employeePersonalDataRepository.findAll();
        assertThat(employeePersonalDatas).hasSize(databaseSizeBeforeUpdate);
        EmployeePersonalData testEmployeePersonalData = employeePersonalDatas.get(employeePersonalDatas.size() - 1);
        assertThat(testEmployeePersonalData.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEmployeePersonalData.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEmployeePersonalData.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEmployeePersonalData.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEmployeePersonalData.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void deleteEmployeePersonalData() throws Exception {
        // Initialize the database
        employeePersonalDataRepository.saveAndFlush(employeePersonalData);

		int databaseSizeBeforeDelete = employeePersonalDataRepository.findAll().size();

        // Get the employeePersonalData
        restEmployeePersonalDataMockMvc.perform(delete("/api/employeePersonalDatas/{id}", employeePersonalData.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeePersonalData> employeePersonalDatas = employeePersonalDataRepository.findAll();
        assertThat(employeePersonalDatas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
