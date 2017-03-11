package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.RegistrationAcademicDetails;
import com.miu.repository.RegistrationAcademicDetailsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RegistrationAcademicDetailsResource REST controller.
 *
 * @see RegistrationAcademicDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class RegistrationAcademicDetailsResourceIntTest {

    private static final String DEFAULT_NAME_OF_INSTITUTION_1 = "AAAAAAAAAA";
    private static final String UPDATED_NAME_OF_INSTITUTION_1 = "BBBBBBBBBB";

    private static final Long DEFAULT_YEAR_1 = 1900L;
    private static final Long UPDATED_YEAR_1 = 1901L;

    private static final String DEFAULT_GRADE_1 = "AAAAAAAAAA";
    private static final String UPDATED_GRADE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_OF_INSTITUTION_2 = "AAAAAAAAAA";
    private static final String UPDATED_NAME_OF_INSTITUTION_2 = "BBBBBBBBBB";

    private static final String DEFAULT_EXAM_PASSED_2 = "AAAAAAAAAA";
    private static final String UPDATED_EXAM_PASSED_2 = "BBBBBBBBBB";

    private static final Long DEFAULT_YEAR_2 = 1900L;
    private static final Long UPDATED_YEAR_2 = 1901L;

    private static final String DEFAULT_GRADE_2 = "AAAAAAAAAA";
    private static final String UPDATED_GRADE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_OF_INSTITUTION_3 = "AAAAAAAAAA";
    private static final String UPDATED_NAME_OF_INSTITUTION_3 = "BBBBBBBBBB";

    private static final String DEFAULT_EXAM_PASSED_3 = "AAAAAAAAAA";
    private static final String UPDATED_EXAM_PASSED_3 = "BBBBBBBBBB";

    private static final Long DEFAULT_YEAR_3 = 1900L;
    private static final Long UPDATED_YEAR_3 = 1901L;

    private static final String DEFAULT_GRADE_3 = "AAAAAAAAAA";
    private static final String UPDATED_GRADE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_OF_INSTITUTION_4 = "AAAAAAAAAA";
    private static final String UPDATED_NAME_OF_INSTITUTION_4 = "BBBBBBBBBB";

    private static final String DEFAULT_EXAM_PASSED_4 = "AAAAAAAAAA";
    private static final String UPDATED_EXAM_PASSED_4 = "BBBBBBBBBB";

    private static final Long DEFAULT_YEAR_4 = 1900L;
    private static final Long UPDATED_YEAR_4 = 1901L;

    private static final String DEFAULT_GRADE_4 = "AAAAAAAAAA";
    private static final String UPDATED_GRADE_4 = "BBBBBBBBBB";

    private static final String DEFAULT_EXAM_PASSED_1 = "AAAAAAAAAA";
    private static final String UPDATED_EXAM_PASSED_1 = "BBBBBBBBBB";

    private static final String DEFAULT_MD_5_KEY = "AAAAAAAAAA";
    private static final String UPDATED_MD_5_KEY = "BBBBBBBBBB";

    @Inject
    private RegistrationAcademicDetailsRepository registrationAcademicDetailsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRegistrationAcademicDetailsMockMvc;

    private RegistrationAcademicDetails registrationAcademicDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RegistrationAcademicDetailsResource registrationAcademicDetailsResource = new RegistrationAcademicDetailsResource();
        ReflectionTestUtils.setField(registrationAcademicDetailsResource, "registrationAcademicDetailsRepository", registrationAcademicDetailsRepository);
        this.restRegistrationAcademicDetailsMockMvc = MockMvcBuilders.standaloneSetup(registrationAcademicDetailsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistrationAcademicDetails createEntity(EntityManager em) {
        RegistrationAcademicDetails registrationAcademicDetails = new RegistrationAcademicDetails()
                .nameOfInstitution1(DEFAULT_NAME_OF_INSTITUTION_1)
                .year1(DEFAULT_YEAR_1)
                .grade1(DEFAULT_GRADE_1)
                .nameOfInstitution2(DEFAULT_NAME_OF_INSTITUTION_2)
                .examPassed2(DEFAULT_EXAM_PASSED_2)
                .year2(DEFAULT_YEAR_2)
                .grade2(DEFAULT_GRADE_2)
                .nameOfInstitution3(DEFAULT_NAME_OF_INSTITUTION_3)
                .examPassed3(DEFAULT_EXAM_PASSED_3)
                .year3(DEFAULT_YEAR_3)
                .grade3(DEFAULT_GRADE_3)
                .nameOfInstitution4(DEFAULT_NAME_OF_INSTITUTION_4)
                .examPassed4(DEFAULT_EXAM_PASSED_4)
                .year4(DEFAULT_YEAR_4)
                .grade4(DEFAULT_GRADE_4)
                .examPassed1(DEFAULT_EXAM_PASSED_1)
                .md5key(DEFAULT_MD_5_KEY);
        return registrationAcademicDetails;
    }

    @Before
    public void initTest() {
        registrationAcademicDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegistrationAcademicDetails() throws Exception {
        int databaseSizeBeforeCreate = registrationAcademicDetailsRepository.findAll().size();

        // Create the RegistrationAcademicDetails

        restRegistrationAcademicDetailsMockMvc.perform(post("/api/registration-academic-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registrationAcademicDetails)))
            .andExpect(status().isCreated());

        // Validate the RegistrationAcademicDetails in the database
        List<RegistrationAcademicDetails> registrationAcademicDetailsList = registrationAcademicDetailsRepository.findAll();
        assertThat(registrationAcademicDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        RegistrationAcademicDetails testRegistrationAcademicDetails = registrationAcademicDetailsList.get(registrationAcademicDetailsList.size() - 1);
        assertThat(testRegistrationAcademicDetails.getNameOfInstitution1()).isEqualTo(DEFAULT_NAME_OF_INSTITUTION_1);
        assertThat(testRegistrationAcademicDetails.getYear1()).isEqualTo(DEFAULT_YEAR_1);
        assertThat(testRegistrationAcademicDetails.getGrade1()).isEqualTo(DEFAULT_GRADE_1);
        assertThat(testRegistrationAcademicDetails.getNameOfInstitution2()).isEqualTo(DEFAULT_NAME_OF_INSTITUTION_2);
        assertThat(testRegistrationAcademicDetails.getExamPassed2()).isEqualTo(DEFAULT_EXAM_PASSED_2);
        assertThat(testRegistrationAcademicDetails.getYear2()).isEqualTo(DEFAULT_YEAR_2);
        assertThat(testRegistrationAcademicDetails.getGrade2()).isEqualTo(DEFAULT_GRADE_2);
        assertThat(testRegistrationAcademicDetails.getNameOfInstitution3()).isEqualTo(DEFAULT_NAME_OF_INSTITUTION_3);
        assertThat(testRegistrationAcademicDetails.getExamPassed3()).isEqualTo(DEFAULT_EXAM_PASSED_3);
        assertThat(testRegistrationAcademicDetails.getYear3()).isEqualTo(DEFAULT_YEAR_3);
        assertThat(testRegistrationAcademicDetails.getGrade3()).isEqualTo(DEFAULT_GRADE_3);
        assertThat(testRegistrationAcademicDetails.getNameOfInstitution4()).isEqualTo(DEFAULT_NAME_OF_INSTITUTION_4);
        assertThat(testRegistrationAcademicDetails.getExamPassed4()).isEqualTo(DEFAULT_EXAM_PASSED_4);
        assertThat(testRegistrationAcademicDetails.getYear4()).isEqualTo(DEFAULT_YEAR_4);
        assertThat(testRegistrationAcademicDetails.getGrade4()).isEqualTo(DEFAULT_GRADE_4);
        assertThat(testRegistrationAcademicDetails.getExamPassed1()).isEqualTo(DEFAULT_EXAM_PASSED_1);
        assertThat(testRegistrationAcademicDetails.getMd5key()).isEqualTo(DEFAULT_MD_5_KEY);
    }

    @Test
    @Transactional
    public void createRegistrationAcademicDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = registrationAcademicDetailsRepository.findAll().size();

        // Create the RegistrationAcademicDetails with an existing ID
        RegistrationAcademicDetails existingRegistrationAcademicDetails = new RegistrationAcademicDetails();
        existingRegistrationAcademicDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegistrationAcademicDetailsMockMvc.perform(post("/api/registration-academic-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingRegistrationAcademicDetails)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RegistrationAcademicDetails> registrationAcademicDetailsList = registrationAcademicDetailsRepository.findAll();
        assertThat(registrationAcademicDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameOfInstitution1IsRequired() throws Exception {
        int databaseSizeBeforeTest = registrationAcademicDetailsRepository.findAll().size();
        // set the field null
        registrationAcademicDetails.setNameOfInstitution1(null);

        // Create the RegistrationAcademicDetails, which fails.

        restRegistrationAcademicDetailsMockMvc.perform(post("/api/registration-academic-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registrationAcademicDetails)))
            .andExpect(status().isBadRequest());

        List<RegistrationAcademicDetails> registrationAcademicDetailsList = registrationAcademicDetailsRepository.findAll();
        assertThat(registrationAcademicDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYear1IsRequired() throws Exception {
        int databaseSizeBeforeTest = registrationAcademicDetailsRepository.findAll().size();
        // set the field null
        registrationAcademicDetails.setYear1(null);

        // Create the RegistrationAcademicDetails, which fails.

        restRegistrationAcademicDetailsMockMvc.perform(post("/api/registration-academic-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registrationAcademicDetails)))
            .andExpect(status().isBadRequest());

        List<RegistrationAcademicDetails> registrationAcademicDetailsList = registrationAcademicDetailsRepository.findAll();
        assertThat(registrationAcademicDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGrade1IsRequired() throws Exception {
        int databaseSizeBeforeTest = registrationAcademicDetailsRepository.findAll().size();
        // set the field null
        registrationAcademicDetails.setGrade1(null);

        // Create the RegistrationAcademicDetails, which fails.

        restRegistrationAcademicDetailsMockMvc.perform(post("/api/registration-academic-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registrationAcademicDetails)))
            .andExpect(status().isBadRequest());

        List<RegistrationAcademicDetails> registrationAcademicDetailsList = registrationAcademicDetailsRepository.findAll();
        assertThat(registrationAcademicDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExamPassed1IsRequired() throws Exception {
        int databaseSizeBeforeTest = registrationAcademicDetailsRepository.findAll().size();
        // set the field null
        registrationAcademicDetails.setExamPassed1(null);

        // Create the RegistrationAcademicDetails, which fails.

        restRegistrationAcademicDetailsMockMvc.perform(post("/api/registration-academic-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registrationAcademicDetails)))
            .andExpect(status().isBadRequest());

        List<RegistrationAcademicDetails> registrationAcademicDetailsList = registrationAcademicDetailsRepository.findAll();
        assertThat(registrationAcademicDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegistrationAcademicDetails() throws Exception {
        // Initialize the database
        registrationAcademicDetailsRepository.saveAndFlush(registrationAcademicDetails);

        // Get all the registrationAcademicDetailsList
        restRegistrationAcademicDetailsMockMvc.perform(get("/api/registration-academic-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registrationAcademicDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameOfInstitution1").value(hasItem(DEFAULT_NAME_OF_INSTITUTION_1.toString())))
            .andExpect(jsonPath("$.[*].year1").value(hasItem(DEFAULT_YEAR_1.intValue())))
            .andExpect(jsonPath("$.[*].grade1").value(hasItem(DEFAULT_GRADE_1.toString())))
            .andExpect(jsonPath("$.[*].nameOfInstitution2").value(hasItem(DEFAULT_NAME_OF_INSTITUTION_2.toString())))
            .andExpect(jsonPath("$.[*].examPassed2").value(hasItem(DEFAULT_EXAM_PASSED_2.toString())))
            .andExpect(jsonPath("$.[*].year2").value(hasItem(DEFAULT_YEAR_2.intValue())))
            .andExpect(jsonPath("$.[*].grade2").value(hasItem(DEFAULT_GRADE_2.toString())))
            .andExpect(jsonPath("$.[*].nameOfInstitution3").value(hasItem(DEFAULT_NAME_OF_INSTITUTION_3.toString())))
            .andExpect(jsonPath("$.[*].examPassed3").value(hasItem(DEFAULT_EXAM_PASSED_3.toString())))
            .andExpect(jsonPath("$.[*].year3").value(hasItem(DEFAULT_YEAR_3.intValue())))
            .andExpect(jsonPath("$.[*].grade3").value(hasItem(DEFAULT_GRADE_3.toString())))
            .andExpect(jsonPath("$.[*].nameOfInstitution4").value(hasItem(DEFAULT_NAME_OF_INSTITUTION_4.toString())))
            .andExpect(jsonPath("$.[*].examPassed4").value(hasItem(DEFAULT_EXAM_PASSED_4.toString())))
            .andExpect(jsonPath("$.[*].year4").value(hasItem(DEFAULT_YEAR_4.intValue())))
            .andExpect(jsonPath("$.[*].grade4").value(hasItem(DEFAULT_GRADE_4.toString())))
            .andExpect(jsonPath("$.[*].examPassed1").value(hasItem(DEFAULT_EXAM_PASSED_1.toString())))
            .andExpect(jsonPath("$.[*].md5key").value(hasItem(DEFAULT_MD_5_KEY.toString())));
    }

    @Test
    @Transactional
    public void getRegistrationAcademicDetails() throws Exception {
        // Initialize the database
        registrationAcademicDetailsRepository.saveAndFlush(registrationAcademicDetails);

        // Get the registrationAcademicDetails
        restRegistrationAcademicDetailsMockMvc.perform(get("/api/registration-academic-details/{id}", registrationAcademicDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(registrationAcademicDetails.getId().intValue()))
            .andExpect(jsonPath("$.nameOfInstitution1").value(DEFAULT_NAME_OF_INSTITUTION_1.toString()))
            .andExpect(jsonPath("$.year1").value(DEFAULT_YEAR_1.intValue()))
            .andExpect(jsonPath("$.grade1").value(DEFAULT_GRADE_1.toString()))
            .andExpect(jsonPath("$.nameOfInstitution2").value(DEFAULT_NAME_OF_INSTITUTION_2.toString()))
            .andExpect(jsonPath("$.examPassed2").value(DEFAULT_EXAM_PASSED_2.toString()))
            .andExpect(jsonPath("$.year2").value(DEFAULT_YEAR_2.intValue()))
            .andExpect(jsonPath("$.grade2").value(DEFAULT_GRADE_2.toString()))
            .andExpect(jsonPath("$.nameOfInstitution3").value(DEFAULT_NAME_OF_INSTITUTION_3.toString()))
            .andExpect(jsonPath("$.examPassed3").value(DEFAULT_EXAM_PASSED_3.toString()))
            .andExpect(jsonPath("$.year3").value(DEFAULT_YEAR_3.intValue()))
            .andExpect(jsonPath("$.grade3").value(DEFAULT_GRADE_3.toString()))
            .andExpect(jsonPath("$.nameOfInstitution4").value(DEFAULT_NAME_OF_INSTITUTION_4.toString()))
            .andExpect(jsonPath("$.examPassed4").value(DEFAULT_EXAM_PASSED_4.toString()))
            .andExpect(jsonPath("$.year4").value(DEFAULT_YEAR_4.intValue()))
            .andExpect(jsonPath("$.grade4").value(DEFAULT_GRADE_4.toString()))
            .andExpect(jsonPath("$.examPassed1").value(DEFAULT_EXAM_PASSED_1.toString()))
            .andExpect(jsonPath("$.md5key").value(DEFAULT_MD_5_KEY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRegistrationAcademicDetails() throws Exception {
        // Get the registrationAcademicDetails
        restRegistrationAcademicDetailsMockMvc.perform(get("/api/registration-academic-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegistrationAcademicDetails() throws Exception {
        // Initialize the database
        registrationAcademicDetailsRepository.saveAndFlush(registrationAcademicDetails);
        int databaseSizeBeforeUpdate = registrationAcademicDetailsRepository.findAll().size();

        // Update the registrationAcademicDetails
        RegistrationAcademicDetails updatedRegistrationAcademicDetails = registrationAcademicDetailsRepository.findOne(registrationAcademicDetails.getId());
        updatedRegistrationAcademicDetails
                .nameOfInstitution1(UPDATED_NAME_OF_INSTITUTION_1)
                .year1(UPDATED_YEAR_1)
                .grade1(UPDATED_GRADE_1)
                .nameOfInstitution2(UPDATED_NAME_OF_INSTITUTION_2)
                .examPassed2(UPDATED_EXAM_PASSED_2)
                .year2(UPDATED_YEAR_2)
                .grade2(UPDATED_GRADE_2)
                .nameOfInstitution3(UPDATED_NAME_OF_INSTITUTION_3)
                .examPassed3(UPDATED_EXAM_PASSED_3)
                .year3(UPDATED_YEAR_3)
                .grade3(UPDATED_GRADE_3)
                .nameOfInstitution4(UPDATED_NAME_OF_INSTITUTION_4)
                .examPassed4(UPDATED_EXAM_PASSED_4)
                .year4(UPDATED_YEAR_4)
                .grade4(UPDATED_GRADE_4)
                .examPassed1(UPDATED_EXAM_PASSED_1)
                .md5key(UPDATED_MD_5_KEY);

        restRegistrationAcademicDetailsMockMvc.perform(put("/api/registration-academic-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRegistrationAcademicDetails)))
            .andExpect(status().isOk());

        // Validate the RegistrationAcademicDetails in the database
        List<RegistrationAcademicDetails> registrationAcademicDetailsList = registrationAcademicDetailsRepository.findAll();
        assertThat(registrationAcademicDetailsList).hasSize(databaseSizeBeforeUpdate);
        RegistrationAcademicDetails testRegistrationAcademicDetails = registrationAcademicDetailsList.get(registrationAcademicDetailsList.size() - 1);
        assertThat(testRegistrationAcademicDetails.getNameOfInstitution1()).isEqualTo(UPDATED_NAME_OF_INSTITUTION_1);
        assertThat(testRegistrationAcademicDetails.getYear1()).isEqualTo(UPDATED_YEAR_1);
        assertThat(testRegistrationAcademicDetails.getGrade1()).isEqualTo(UPDATED_GRADE_1);
        assertThat(testRegistrationAcademicDetails.getNameOfInstitution2()).isEqualTo(UPDATED_NAME_OF_INSTITUTION_2);
        assertThat(testRegistrationAcademicDetails.getExamPassed2()).isEqualTo(UPDATED_EXAM_PASSED_2);
        assertThat(testRegistrationAcademicDetails.getYear2()).isEqualTo(UPDATED_YEAR_2);
        assertThat(testRegistrationAcademicDetails.getGrade2()).isEqualTo(UPDATED_GRADE_2);
        assertThat(testRegistrationAcademicDetails.getNameOfInstitution3()).isEqualTo(UPDATED_NAME_OF_INSTITUTION_3);
        assertThat(testRegistrationAcademicDetails.getExamPassed3()).isEqualTo(UPDATED_EXAM_PASSED_3);
        assertThat(testRegistrationAcademicDetails.getYear3()).isEqualTo(UPDATED_YEAR_3);
        assertThat(testRegistrationAcademicDetails.getGrade3()).isEqualTo(UPDATED_GRADE_3);
        assertThat(testRegistrationAcademicDetails.getNameOfInstitution4()).isEqualTo(UPDATED_NAME_OF_INSTITUTION_4);
        assertThat(testRegistrationAcademicDetails.getExamPassed4()).isEqualTo(UPDATED_EXAM_PASSED_4);
        assertThat(testRegistrationAcademicDetails.getYear4()).isEqualTo(UPDATED_YEAR_4);
        assertThat(testRegistrationAcademicDetails.getGrade4()).isEqualTo(UPDATED_GRADE_4);
        assertThat(testRegistrationAcademicDetails.getExamPassed1()).isEqualTo(UPDATED_EXAM_PASSED_1);
        assertThat(testRegistrationAcademicDetails.getMd5key()).isEqualTo(UPDATED_MD_5_KEY);
    }

    @Test
    @Transactional
    public void updateNonExistingRegistrationAcademicDetails() throws Exception {
        int databaseSizeBeforeUpdate = registrationAcademicDetailsRepository.findAll().size();

        // Create the RegistrationAcademicDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRegistrationAcademicDetailsMockMvc.perform(put("/api/registration-academic-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registrationAcademicDetails)))
            .andExpect(status().isCreated());

        // Validate the RegistrationAcademicDetails in the database
        List<RegistrationAcademicDetails> registrationAcademicDetailsList = registrationAcademicDetailsRepository.findAll();
        assertThat(registrationAcademicDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRegistrationAcademicDetails() throws Exception {
        // Initialize the database
        registrationAcademicDetailsRepository.saveAndFlush(registrationAcademicDetails);
        int databaseSizeBeforeDelete = registrationAcademicDetailsRepository.findAll().size();

        // Get the registrationAcademicDetails
        restRegistrationAcademicDetailsMockMvc.perform(delete("/api/registration-academic-details/{id}", registrationAcademicDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RegistrationAcademicDetails> registrationAcademicDetailsList = registrationAcademicDetailsRepository.findAll();
        assertThat(registrationAcademicDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
