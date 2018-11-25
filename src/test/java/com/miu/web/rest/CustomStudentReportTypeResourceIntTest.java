package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.CustomStudentReportType;
import com.miu.repository.CustomStudentReportTypeRepository;
import com.miu.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.miu.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CustomStudentReportTypeResource REST controller.
 *
 * @see CustomStudentReportTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class CustomStudentReportTypeResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    @Autowired
    private CustomStudentReportTypeRepository customStudentReportTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomStudentReportTypeMockMvc;

    private CustomStudentReportType customStudentReportType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomStudentReportTypeResource customStudentReportTypeResource = new CustomStudentReportTypeResource(customStudentReportTypeRepository);
        this.restCustomStudentReportTypeMockMvc = MockMvcBuilders.standaloneSetup(customStudentReportTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomStudentReportType createEntity(EntityManager em) {
        CustomStudentReportType customStudentReportType = new CustomStudentReportType()
            .label(DEFAULT_LABEL);
        return customStudentReportType;
    }

    @Before
    public void initTest() {
        customStudentReportType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomStudentReportType() throws Exception {
        int databaseSizeBeforeCreate = customStudentReportTypeRepository.findAll().size();

        // Create the CustomStudentReportType
        restCustomStudentReportTypeMockMvc.perform(post("/api/custom-student-report-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customStudentReportType)))
            .andExpect(status().isCreated());

        // Validate the CustomStudentReportType in the database
        List<CustomStudentReportType> customStudentReportTypeList = customStudentReportTypeRepository.findAll();
        assertThat(customStudentReportTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CustomStudentReportType testCustomStudentReportType = customStudentReportTypeList.get(customStudentReportTypeList.size() - 1);
        assertThat(testCustomStudentReportType.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createCustomStudentReportTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customStudentReportTypeRepository.findAll().size();

        // Create the CustomStudentReportType with an existing ID
        customStudentReportType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomStudentReportTypeMockMvc.perform(post("/api/custom-student-report-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customStudentReportType)))
            .andExpect(status().isBadRequest());

        // Validate the CustomStudentReportType in the database
        List<CustomStudentReportType> customStudentReportTypeList = customStudentReportTypeRepository.findAll();
        assertThat(customStudentReportTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = customStudentReportTypeRepository.findAll().size();
        // set the field null
        customStudentReportType.setLabel(null);

        // Create the CustomStudentReportType, which fails.

        restCustomStudentReportTypeMockMvc.perform(post("/api/custom-student-report-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customStudentReportType)))
            .andExpect(status().isBadRequest());

        List<CustomStudentReportType> customStudentReportTypeList = customStudentReportTypeRepository.findAll();
        assertThat(customStudentReportTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomStudentReportTypes() throws Exception {
        // Initialize the database
        customStudentReportTypeRepository.saveAndFlush(customStudentReportType);

        // Get all the customStudentReportTypeList
        restCustomStudentReportTypeMockMvc.perform(get("/api/custom-student-report-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customStudentReportType.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }
    
    @Test
    @Transactional
    public void getCustomStudentReportType() throws Exception {
        // Initialize the database
        customStudentReportTypeRepository.saveAndFlush(customStudentReportType);

        // Get the customStudentReportType
        restCustomStudentReportTypeMockMvc.perform(get("/api/custom-student-report-types/{id}", customStudentReportType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customStudentReportType.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomStudentReportType() throws Exception {
        // Get the customStudentReportType
        restCustomStudentReportTypeMockMvc.perform(get("/api/custom-student-report-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomStudentReportType() throws Exception {
        // Initialize the database
        customStudentReportTypeRepository.saveAndFlush(customStudentReportType);

        int databaseSizeBeforeUpdate = customStudentReportTypeRepository.findAll().size();

        // Update the customStudentReportType
        CustomStudentReportType updatedCustomStudentReportType = customStudentReportTypeRepository.findById(customStudentReportType.getId()).get();
        // Disconnect from session so that the updates on updatedCustomStudentReportType are not directly saved in db
        em.detach(updatedCustomStudentReportType);
        updatedCustomStudentReportType
            .label(UPDATED_LABEL);

        restCustomStudentReportTypeMockMvc.perform(put("/api/custom-student-report-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomStudentReportType)))
            .andExpect(status().isOk());

        // Validate the CustomStudentReportType in the database
        List<CustomStudentReportType> customStudentReportTypeList = customStudentReportTypeRepository.findAll();
        assertThat(customStudentReportTypeList).hasSize(databaseSizeBeforeUpdate);
        CustomStudentReportType testCustomStudentReportType = customStudentReportTypeList.get(customStudentReportTypeList.size() - 1);
        assertThat(testCustomStudentReportType.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomStudentReportType() throws Exception {
        int databaseSizeBeforeUpdate = customStudentReportTypeRepository.findAll().size();

        // Create the CustomStudentReportType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomStudentReportTypeMockMvc.perform(put("/api/custom-student-report-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customStudentReportType)))
            .andExpect(status().isBadRequest());

        // Validate the CustomStudentReportType in the database
        List<CustomStudentReportType> customStudentReportTypeList = customStudentReportTypeRepository.findAll();
        assertThat(customStudentReportTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomStudentReportType() throws Exception {
        // Initialize the database
        customStudentReportTypeRepository.saveAndFlush(customStudentReportType);

        int databaseSizeBeforeDelete = customStudentReportTypeRepository.findAll().size();

        // Get the customStudentReportType
        restCustomStudentReportTypeMockMvc.perform(delete("/api/custom-student-report-types/{id}", customStudentReportType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomStudentReportType> customStudentReportTypeList = customStudentReportTypeRepository.findAll();
        assertThat(customStudentReportTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomStudentReportType.class);
        CustomStudentReportType customStudentReportType1 = new CustomStudentReportType();
        customStudentReportType1.setId(1L);
        CustomStudentReportType customStudentReportType2 = new CustomStudentReportType();
        customStudentReportType2.setId(customStudentReportType1.getId());
        assertThat(customStudentReportType1).isEqualTo(customStudentReportType2);
        customStudentReportType2.setId(2L);
        assertThat(customStudentReportType1).isNotEqualTo(customStudentReportType2);
        customStudentReportType1.setId(null);
        assertThat(customStudentReportType1).isNotEqualTo(customStudentReportType2);
    }
}
