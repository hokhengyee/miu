package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.OnlineApplication;
import com.miu.domain.Course;
import com.miu.repository.OnlineApplicationRepository;

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
import org.springframework.util.Base64Utils;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.miu.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OnlineApplicationResource REST controller.
 *
 * @see OnlineApplicationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class OnlineApplicationResourceIntTest {

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_POSTCODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTCODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_REGISTRATION_DATETIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REGISTRATION_DATETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final String DEFAULT_GIVEN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GIVEN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PROFILE_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PROFILE_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PROFILE_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PROFILE_PHOTO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ACADEMIC_CERTIFICATE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ACADEMIC_CERTIFICATE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ACADEMIC_CERTIFICATE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ACADEMIC_CERTIFICATE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_LETTER_OF_RECOMMENDATION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LETTER_OF_RECOMMENDATION = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_LETTER_OF_RECOMMENDATION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LETTER_OF_RECOMMENDATION_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PROFILE_DOCUMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PROFILE_DOCUMENT = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PROFILE_DOCUMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PROFILE_DOCUMENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_MD_5_KEY = "AAAAAAAAAA";
    private static final String UPDATED_MD_5_KEY = "BBBBBBBBBB";

    @Inject
    private OnlineApplicationRepository onlineApplicationRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restOnlineApplicationMockMvc;

    private OnlineApplication onlineApplication;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OnlineApplicationResource onlineApplicationResource = new OnlineApplicationResource();
        ReflectionTestUtils.setField(onlineApplicationResource, "onlineApplicationRepository", onlineApplicationRepository);
        this.restOnlineApplicationMockMvc = MockMvcBuilders.standaloneSetup(onlineApplicationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OnlineApplication createEntity(EntityManager em) {
        OnlineApplication onlineApplication = new OnlineApplication()
                .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
                .telephone(DEFAULT_TELEPHONE)
                .email(DEFAULT_EMAIL)
                .city(DEFAULT_CITY)
                .state(DEFAULT_STATE)
                .country(DEFAULT_COUNTRY)
                .postcode(DEFAULT_POSTCODE)
                .registrationDatetime(DEFAULT_REGISTRATION_DATETIME)
                .surname(DEFAULT_SURNAME)
                .givenName(DEFAULT_GIVEN_NAME)
                .address(DEFAULT_ADDRESS)
                .profilePhoto(DEFAULT_PROFILE_PHOTO)
                .profilePhotoContentType(DEFAULT_PROFILE_PHOTO_CONTENT_TYPE)
                .academicCertificate(DEFAULT_ACADEMIC_CERTIFICATE)
                .academicCertificateContentType(DEFAULT_ACADEMIC_CERTIFICATE_CONTENT_TYPE)
                .letterOfRecommendation(DEFAULT_LETTER_OF_RECOMMENDATION)
                .letterOfRecommendationContentType(DEFAULT_LETTER_OF_RECOMMENDATION_CONTENT_TYPE)
                .profileDocument(DEFAULT_PROFILE_DOCUMENT)
                .profileDocumentContentType(DEFAULT_PROFILE_DOCUMENT_CONTENT_TYPE)
                .md5key(DEFAULT_MD_5_KEY);
        // Add required entity
        Course course = CourseResourceIntTest.createEntity(em);
        em.persist(course);
        em.flush();
        onlineApplication.setCourse(course);
        return onlineApplication;
    }

    @Before
    public void initTest() {
        onlineApplication = createEntity(em);
    }

    @Test
    @Transactional
    public void createOnlineApplication() throws Exception {
        int databaseSizeBeforeCreate = onlineApplicationRepository.findAll().size();

        // Create the OnlineApplication

        restOnlineApplicationMockMvc.perform(post("/api/online-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(onlineApplication)))
            .andExpect(status().isCreated());

        // Validate the OnlineApplication in the database
        List<OnlineApplication> onlineApplicationList = onlineApplicationRepository.findAll();
        assertThat(onlineApplicationList).hasSize(databaseSizeBeforeCreate + 1);
        OnlineApplication testOnlineApplication = onlineApplicationList.get(onlineApplicationList.size() - 1);
        assertThat(testOnlineApplication.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testOnlineApplication.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testOnlineApplication.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOnlineApplication.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testOnlineApplication.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testOnlineApplication.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testOnlineApplication.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(testOnlineApplication.getRegistrationDatetime()).isEqualTo(DEFAULT_REGISTRATION_DATETIME);
        assertThat(testOnlineApplication.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testOnlineApplication.getGivenName()).isEqualTo(DEFAULT_GIVEN_NAME);
        assertThat(testOnlineApplication.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testOnlineApplication.getProfilePhoto()).isEqualTo(DEFAULT_PROFILE_PHOTO);
        assertThat(testOnlineApplication.getProfilePhotoContentType()).isEqualTo(DEFAULT_PROFILE_PHOTO_CONTENT_TYPE);
        assertThat(testOnlineApplication.getAcademicCertificate()).isEqualTo(DEFAULT_ACADEMIC_CERTIFICATE);
        assertThat(testOnlineApplication.getAcademicCertificateContentType()).isEqualTo(DEFAULT_ACADEMIC_CERTIFICATE_CONTENT_TYPE);
        assertThat(testOnlineApplication.getLetterOfRecommendation()).isEqualTo(DEFAULT_LETTER_OF_RECOMMENDATION);
        assertThat(testOnlineApplication.getLetterOfRecommendationContentType()).isEqualTo(DEFAULT_LETTER_OF_RECOMMENDATION_CONTENT_TYPE);
        assertThat(testOnlineApplication.getProfileDocument()).isEqualTo(DEFAULT_PROFILE_DOCUMENT);
        assertThat(testOnlineApplication.getProfileDocumentContentType()).isEqualTo(DEFAULT_PROFILE_DOCUMENT_CONTENT_TYPE);
        assertThat(testOnlineApplication.getMd5key()).isEqualTo(DEFAULT_MD_5_KEY);
    }

    @Test
    @Transactional
    public void createOnlineApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = onlineApplicationRepository.findAll().size();

        // Create the OnlineApplication with an existing ID
        OnlineApplication existingOnlineApplication = new OnlineApplication();
        existingOnlineApplication.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOnlineApplicationMockMvc.perform(post("/api/online-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingOnlineApplication)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<OnlineApplication> onlineApplicationList = onlineApplicationRepository.findAll();
        assertThat(onlineApplicationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = onlineApplicationRepository.findAll().size();
        // set the field null
        onlineApplication.setDateOfBirth(null);

        // Create the OnlineApplication, which fails.

        restOnlineApplicationMockMvc.perform(post("/api/online-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(onlineApplication)))
            .andExpect(status().isBadRequest());

        List<OnlineApplication> onlineApplicationList = onlineApplicationRepository.findAll();
        assertThat(onlineApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = onlineApplicationRepository.findAll().size();
        // set the field null
        onlineApplication.setTelephone(null);

        // Create the OnlineApplication, which fails.

        restOnlineApplicationMockMvc.perform(post("/api/online-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(onlineApplication)))
            .andExpect(status().isBadRequest());

        List<OnlineApplication> onlineApplicationList = onlineApplicationRepository.findAll();
        assertThat(onlineApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = onlineApplicationRepository.findAll().size();
        // set the field null
        onlineApplication.setEmail(null);

        // Create the OnlineApplication, which fails.

        restOnlineApplicationMockMvc.perform(post("/api/online-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(onlineApplication)))
            .andExpect(status().isBadRequest());

        List<OnlineApplication> onlineApplicationList = onlineApplicationRepository.findAll();
        assertThat(onlineApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = onlineApplicationRepository.findAll().size();
        // set the field null
        onlineApplication.setCity(null);

        // Create the OnlineApplication, which fails.

        restOnlineApplicationMockMvc.perform(post("/api/online-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(onlineApplication)))
            .andExpect(status().isBadRequest());

        List<OnlineApplication> onlineApplicationList = onlineApplicationRepository.findAll();
        assertThat(onlineApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = onlineApplicationRepository.findAll().size();
        // set the field null
        onlineApplication.setState(null);

        // Create the OnlineApplication, which fails.

        restOnlineApplicationMockMvc.perform(post("/api/online-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(onlineApplication)))
            .andExpect(status().isBadRequest());

        List<OnlineApplication> onlineApplicationList = onlineApplicationRepository.findAll();
        assertThat(onlineApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = onlineApplicationRepository.findAll().size();
        // set the field null
        onlineApplication.setCountry(null);

        // Create the OnlineApplication, which fails.

        restOnlineApplicationMockMvc.perform(post("/api/online-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(onlineApplication)))
            .andExpect(status().isBadRequest());

        List<OnlineApplication> onlineApplicationList = onlineApplicationRepository.findAll();
        assertThat(onlineApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = onlineApplicationRepository.findAll().size();
        // set the field null
        onlineApplication.setPostcode(null);

        // Create the OnlineApplication, which fails.

        restOnlineApplicationMockMvc.perform(post("/api/online-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(onlineApplication)))
            .andExpect(status().isBadRequest());

        List<OnlineApplication> onlineApplicationList = onlineApplicationRepository.findAll();
        assertThat(onlineApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = onlineApplicationRepository.findAll().size();
        // set the field null
        onlineApplication.setSurname(null);

        // Create the OnlineApplication, which fails.

        restOnlineApplicationMockMvc.perform(post("/api/online-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(onlineApplication)))
            .andExpect(status().isBadRequest());

        List<OnlineApplication> onlineApplicationList = onlineApplicationRepository.findAll();
        assertThat(onlineApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGivenNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = onlineApplicationRepository.findAll().size();
        // set the field null
        onlineApplication.setGivenName(null);

        // Create the OnlineApplication, which fails.

        restOnlineApplicationMockMvc.perform(post("/api/online-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(onlineApplication)))
            .andExpect(status().isBadRequest());

        List<OnlineApplication> onlineApplicationList = onlineApplicationRepository.findAll();
        assertThat(onlineApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = onlineApplicationRepository.findAll().size();
        // set the field null
        onlineApplication.setAddress(null);

        // Create the OnlineApplication, which fails.

        restOnlineApplicationMockMvc.perform(post("/api/online-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(onlineApplication)))
            .andExpect(status().isBadRequest());

        List<OnlineApplication> onlineApplicationList = onlineApplicationRepository.findAll();
        assertThat(onlineApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOnlineApplications() throws Exception {
        // Initialize the database
        onlineApplicationRepository.saveAndFlush(onlineApplication);

        // Get all the onlineApplicationList
        restOnlineApplicationMockMvc.perform(get("/api/online-applications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(onlineApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE.toString())))
            .andExpect(jsonPath("$.[*].registrationDatetime").value(hasItem(sameInstant(DEFAULT_REGISTRATION_DATETIME))))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].givenName").value(hasItem(DEFAULT_GIVEN_NAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].profilePhotoContentType").value(hasItem(DEFAULT_PROFILE_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].profilePhoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_PROFILE_PHOTO))))
            .andExpect(jsonPath("$.[*].academicCertificateContentType").value(hasItem(DEFAULT_ACADEMIC_CERTIFICATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].academicCertificate").value(hasItem(Base64Utils.encodeToString(DEFAULT_ACADEMIC_CERTIFICATE))))
            .andExpect(jsonPath("$.[*].letterOfRecommendationContentType").value(hasItem(DEFAULT_LETTER_OF_RECOMMENDATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].letterOfRecommendation").value(hasItem(Base64Utils.encodeToString(DEFAULT_LETTER_OF_RECOMMENDATION))))
            .andExpect(jsonPath("$.[*].profileDocumentContentType").value(hasItem(DEFAULT_PROFILE_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].profileDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_PROFILE_DOCUMENT))))
            .andExpect(jsonPath("$.[*].md5key").value(hasItem(DEFAULT_MD_5_KEY.toString())));
    }

    @Test
    @Transactional
    public void getOnlineApplication() throws Exception {
        // Initialize the database
        onlineApplicationRepository.saveAndFlush(onlineApplication);

        // Get the onlineApplication
        restOnlineApplicationMockMvc.perform(get("/api/online-applications/{id}", onlineApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(onlineApplication.getId().intValue()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE.toString()))
            .andExpect(jsonPath("$.registrationDatetime").value(sameInstant(DEFAULT_REGISTRATION_DATETIME)))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME.toString()))
            .andExpect(jsonPath("$.givenName").value(DEFAULT_GIVEN_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.profilePhotoContentType").value(DEFAULT_PROFILE_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.profilePhoto").value(Base64Utils.encodeToString(DEFAULT_PROFILE_PHOTO)))
            .andExpect(jsonPath("$.academicCertificateContentType").value(DEFAULT_ACADEMIC_CERTIFICATE_CONTENT_TYPE))
            .andExpect(jsonPath("$.academicCertificate").value(Base64Utils.encodeToString(DEFAULT_ACADEMIC_CERTIFICATE)))
            .andExpect(jsonPath("$.letterOfRecommendationContentType").value(DEFAULT_LETTER_OF_RECOMMENDATION_CONTENT_TYPE))
            .andExpect(jsonPath("$.letterOfRecommendation").value(Base64Utils.encodeToString(DEFAULT_LETTER_OF_RECOMMENDATION)))
            .andExpect(jsonPath("$.profileDocumentContentType").value(DEFAULT_PROFILE_DOCUMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.profileDocument").value(Base64Utils.encodeToString(DEFAULT_PROFILE_DOCUMENT)))
            .andExpect(jsonPath("$.md5key").value(DEFAULT_MD_5_KEY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOnlineApplication() throws Exception {
        // Get the onlineApplication
        restOnlineApplicationMockMvc.perform(get("/api/online-applications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOnlineApplication() throws Exception {
        // Initialize the database
        onlineApplicationRepository.saveAndFlush(onlineApplication);
        int databaseSizeBeforeUpdate = onlineApplicationRepository.findAll().size();

        // Update the onlineApplication
        OnlineApplication updatedOnlineApplication = onlineApplicationRepository.findOne(onlineApplication.getId());
        updatedOnlineApplication
                .dateOfBirth(UPDATED_DATE_OF_BIRTH)
                .telephone(UPDATED_TELEPHONE)
                .email(UPDATED_EMAIL)
                .city(UPDATED_CITY)
                .state(UPDATED_STATE)
                .country(UPDATED_COUNTRY)
                .postcode(UPDATED_POSTCODE)
                .registrationDatetime(UPDATED_REGISTRATION_DATETIME)
                .surname(UPDATED_SURNAME)
                .givenName(UPDATED_GIVEN_NAME)
                .address(UPDATED_ADDRESS)
                .profilePhoto(UPDATED_PROFILE_PHOTO)
                .profilePhotoContentType(UPDATED_PROFILE_PHOTO_CONTENT_TYPE)
                .academicCertificate(UPDATED_ACADEMIC_CERTIFICATE)
                .academicCertificateContentType(UPDATED_ACADEMIC_CERTIFICATE_CONTENT_TYPE)
                .letterOfRecommendation(UPDATED_LETTER_OF_RECOMMENDATION)
                .letterOfRecommendationContentType(UPDATED_LETTER_OF_RECOMMENDATION_CONTENT_TYPE)
                .profileDocument(UPDATED_PROFILE_DOCUMENT)
                .profileDocumentContentType(UPDATED_PROFILE_DOCUMENT_CONTENT_TYPE)
                .md5key(UPDATED_MD_5_KEY);

        restOnlineApplicationMockMvc.perform(put("/api/online-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOnlineApplication)))
            .andExpect(status().isOk());

        // Validate the OnlineApplication in the database
        List<OnlineApplication> onlineApplicationList = onlineApplicationRepository.findAll();
        assertThat(onlineApplicationList).hasSize(databaseSizeBeforeUpdate);
        OnlineApplication testOnlineApplication = onlineApplicationList.get(onlineApplicationList.size() - 1);
        assertThat(testOnlineApplication.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testOnlineApplication.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testOnlineApplication.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOnlineApplication.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testOnlineApplication.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testOnlineApplication.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testOnlineApplication.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testOnlineApplication.getRegistrationDatetime()).isEqualTo(UPDATED_REGISTRATION_DATETIME);
        assertThat(testOnlineApplication.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testOnlineApplication.getGivenName()).isEqualTo(UPDATED_GIVEN_NAME);
        assertThat(testOnlineApplication.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testOnlineApplication.getProfilePhoto()).isEqualTo(UPDATED_PROFILE_PHOTO);
        assertThat(testOnlineApplication.getProfilePhotoContentType()).isEqualTo(UPDATED_PROFILE_PHOTO_CONTENT_TYPE);
        assertThat(testOnlineApplication.getAcademicCertificate()).isEqualTo(UPDATED_ACADEMIC_CERTIFICATE);
        assertThat(testOnlineApplication.getAcademicCertificateContentType()).isEqualTo(UPDATED_ACADEMIC_CERTIFICATE_CONTENT_TYPE);
        assertThat(testOnlineApplication.getLetterOfRecommendation()).isEqualTo(UPDATED_LETTER_OF_RECOMMENDATION);
        assertThat(testOnlineApplication.getLetterOfRecommendationContentType()).isEqualTo(UPDATED_LETTER_OF_RECOMMENDATION_CONTENT_TYPE);
        assertThat(testOnlineApplication.getProfileDocument()).isEqualTo(UPDATED_PROFILE_DOCUMENT);
        assertThat(testOnlineApplication.getProfileDocumentContentType()).isEqualTo(UPDATED_PROFILE_DOCUMENT_CONTENT_TYPE);
        assertThat(testOnlineApplication.getMd5key()).isEqualTo(UPDATED_MD_5_KEY);
    }

    @Test
    @Transactional
    public void updateNonExistingOnlineApplication() throws Exception {
        int databaseSizeBeforeUpdate = onlineApplicationRepository.findAll().size();

        // Create the OnlineApplication

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOnlineApplicationMockMvc.perform(put("/api/online-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(onlineApplication)))
            .andExpect(status().isCreated());

        // Validate the OnlineApplication in the database
        List<OnlineApplication> onlineApplicationList = onlineApplicationRepository.findAll();
        assertThat(onlineApplicationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOnlineApplication() throws Exception {
        // Initialize the database
        onlineApplicationRepository.saveAndFlush(onlineApplication);
        int databaseSizeBeforeDelete = onlineApplicationRepository.findAll().size();

        // Get the onlineApplication
        restOnlineApplicationMockMvc.perform(delete("/api/online-applications/{id}", onlineApplication.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OnlineApplication> onlineApplicationList = onlineApplicationRepository.findAll();
        assertThat(onlineApplicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
