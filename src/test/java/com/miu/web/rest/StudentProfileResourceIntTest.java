package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.StudentProfile;
import com.miu.domain.Salutation;
import com.miu.domain.Gender;
import com.miu.domain.User;
import com.miu.domain.User;
import com.miu.repository.StudentProfileRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.miu.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StudentProfileResource REST controller.
 *
 * @see StudentProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class StudentProfileResourceIntTest {

    private static final String DEFAULT_STUDENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_STUDENT_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_APPLICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPLICATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_COMMENCEMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMMENCEMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_COMPLETION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMPLETION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MAILING_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_MAILING_ADDRESS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PROFILE_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PROFILE_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PROFILE_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PROFILE_PHOTO_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_EXTENDED_COMPLETION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXTENDED_COMPLETION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStudentProfileMockMvc;

    private StudentProfile studentProfile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentProfileResource studentProfileResource = new StudentProfileResource(studentProfileRepository);
        this.restStudentProfileMockMvc = MockMvcBuilders.standaloneSetup(studentProfileResource)
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
    public static StudentProfile createEntity(EntityManager em) {
        StudentProfile studentProfile = new StudentProfile()
            .studentId(DEFAULT_STUDENT_ID)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .phone(DEFAULT_PHONE)
            .applicationDate(DEFAULT_APPLICATION_DATE)
            .commencementDate(DEFAULT_COMMENCEMENT_DATE)
            .completionDate(DEFAULT_COMPLETION_DATE)
            .mailingAddress(DEFAULT_MAILING_ADDRESS)
            .profilePhoto(DEFAULT_PROFILE_PHOTO)
            .profilePhotoContentType(DEFAULT_PROFILE_PHOTO_CONTENT_TYPE)
            .extendedCompletionDate(DEFAULT_EXTENDED_COMPLETION_DATE);
        // Add required entity
        Salutation salutation = SalutationResourceIntTest.createEntity(em);
        em.persist(salutation);
        em.flush();
        studentProfile.setSalutation(salutation);
        // Add required entity
        Gender gender = GenderResourceIntTest.createEntity(em);
        em.persist(gender);
        em.flush();
        studentProfile.setGender(gender);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        studentProfile.setSupervisor(user);
        // Add required entity
        studentProfile.setUser(user);
        return studentProfile;
    }

    @Before
    public void initTest() {
        studentProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentProfile() throws Exception {
        int databaseSizeBeforeCreate = studentProfileRepository.findAll().size();

        // Create the StudentProfile
        restStudentProfileMockMvc.perform(post("/api/student-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentProfile)))
            .andExpect(status().isCreated());

        // Validate the StudentProfile in the database
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeCreate + 1);
        StudentProfile testStudentProfile = studentProfileList.get(studentProfileList.size() - 1);
        assertThat(testStudentProfile.getStudentId()).isEqualTo(DEFAULT_STUDENT_ID);
        assertThat(testStudentProfile.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testStudentProfile.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testStudentProfile.getApplicationDate()).isEqualTo(DEFAULT_APPLICATION_DATE);
        assertThat(testStudentProfile.getCommencementDate()).isEqualTo(DEFAULT_COMMENCEMENT_DATE);
        assertThat(testStudentProfile.getCompletionDate()).isEqualTo(DEFAULT_COMPLETION_DATE);
        assertThat(testStudentProfile.getMailingAddress()).isEqualTo(DEFAULT_MAILING_ADDRESS);
        assertThat(testStudentProfile.getProfilePhoto()).isEqualTo(DEFAULT_PROFILE_PHOTO);
        assertThat(testStudentProfile.getProfilePhotoContentType()).isEqualTo(DEFAULT_PROFILE_PHOTO_CONTENT_TYPE);
        assertThat(testStudentProfile.getExtendedCompletionDate()).isEqualTo(DEFAULT_EXTENDED_COMPLETION_DATE);
    }

    @Test
    @Transactional
    public void createStudentProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentProfileRepository.findAll().size();

        // Create the StudentProfile with an existing ID
        studentProfile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentProfileMockMvc.perform(post("/api/student-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentProfile)))
            .andExpect(status().isBadRequest());

        // Validate the StudentProfile in the database
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStudentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentProfileRepository.findAll().size();
        // set the field null
        studentProfile.setStudentId(null);

        // Create the StudentProfile, which fails.

        restStudentProfileMockMvc.perform(post("/api/student-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentProfile)))
            .andExpect(status().isBadRequest());

        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentProfileRepository.findAll().size();
        // set the field null
        studentProfile.setPhone(null);

        // Create the StudentProfile, which fails.

        restStudentProfileMockMvc.perform(post("/api/student-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentProfile)))
            .andExpect(status().isBadRequest());

        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApplicationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentProfileRepository.findAll().size();
        // set the field null
        studentProfile.setApplicationDate(null);

        // Create the StudentProfile, which fails.

        restStudentProfileMockMvc.perform(post("/api/student-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentProfile)))
            .andExpect(status().isBadRequest());

        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommencementDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentProfileRepository.findAll().size();
        // set the field null
        studentProfile.setCommencementDate(null);

        // Create the StudentProfile, which fails.

        restStudentProfileMockMvc.perform(post("/api/student-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentProfile)))
            .andExpect(status().isBadRequest());

        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudentProfiles() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList
        restStudentProfileMockMvc.perform(get("/api/student-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].studentId").value(hasItem(DEFAULT_STUDENT_ID.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].applicationDate").value(hasItem(DEFAULT_APPLICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].commencementDate").value(hasItem(DEFAULT_COMMENCEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].completionDate").value(hasItem(DEFAULT_COMPLETION_DATE.toString())))
            .andExpect(jsonPath("$.[*].mailingAddress").value(hasItem(DEFAULT_MAILING_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].profilePhotoContentType").value(hasItem(DEFAULT_PROFILE_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].profilePhoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_PROFILE_PHOTO))))
            .andExpect(jsonPath("$.[*].extendedCompletionDate").value(hasItem(DEFAULT_EXTENDED_COMPLETION_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getStudentProfile() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get the studentProfile
        restStudentProfileMockMvc.perform(get("/api/student-profiles/{id}", studentProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentProfile.getId().intValue()))
            .andExpect(jsonPath("$.studentId").value(DEFAULT_STUDENT_ID.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.applicationDate").value(DEFAULT_APPLICATION_DATE.toString()))
            .andExpect(jsonPath("$.commencementDate").value(DEFAULT_COMMENCEMENT_DATE.toString()))
            .andExpect(jsonPath("$.completionDate").value(DEFAULT_COMPLETION_DATE.toString()))
            .andExpect(jsonPath("$.mailingAddress").value(DEFAULT_MAILING_ADDRESS.toString()))
            .andExpect(jsonPath("$.profilePhotoContentType").value(DEFAULT_PROFILE_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.profilePhoto").value(Base64Utils.encodeToString(DEFAULT_PROFILE_PHOTO)))
            .andExpect(jsonPath("$.extendedCompletionDate").value(DEFAULT_EXTENDED_COMPLETION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentProfile() throws Exception {
        // Get the studentProfile
        restStudentProfileMockMvc.perform(get("/api/student-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentProfile() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        int databaseSizeBeforeUpdate = studentProfileRepository.findAll().size();

        // Update the studentProfile
        StudentProfile updatedStudentProfile = studentProfileRepository.findById(studentProfile.getId()).get();
        // Disconnect from session so that the updates on updatedStudentProfile are not directly saved in db
        em.detach(updatedStudentProfile);
        updatedStudentProfile
            .studentId(UPDATED_STUDENT_ID)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .phone(UPDATED_PHONE)
            .applicationDate(UPDATED_APPLICATION_DATE)
            .commencementDate(UPDATED_COMMENCEMENT_DATE)
            .completionDate(UPDATED_COMPLETION_DATE)
            .mailingAddress(UPDATED_MAILING_ADDRESS)
            .profilePhoto(UPDATED_PROFILE_PHOTO)
            .profilePhotoContentType(UPDATED_PROFILE_PHOTO_CONTENT_TYPE)
            .extendedCompletionDate(UPDATED_EXTENDED_COMPLETION_DATE);

        restStudentProfileMockMvc.perform(put("/api/student-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudentProfile)))
            .andExpect(status().isOk());

        // Validate the StudentProfile in the database
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeUpdate);
        StudentProfile testStudentProfile = studentProfileList.get(studentProfileList.size() - 1);
        assertThat(testStudentProfile.getStudentId()).isEqualTo(UPDATED_STUDENT_ID);
        assertThat(testStudentProfile.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testStudentProfile.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testStudentProfile.getApplicationDate()).isEqualTo(UPDATED_APPLICATION_DATE);
        assertThat(testStudentProfile.getCommencementDate()).isEqualTo(UPDATED_COMMENCEMENT_DATE);
        assertThat(testStudentProfile.getCompletionDate()).isEqualTo(UPDATED_COMPLETION_DATE);
        assertThat(testStudentProfile.getMailingAddress()).isEqualTo(UPDATED_MAILING_ADDRESS);
        assertThat(testStudentProfile.getProfilePhoto()).isEqualTo(UPDATED_PROFILE_PHOTO);
        assertThat(testStudentProfile.getProfilePhotoContentType()).isEqualTo(UPDATED_PROFILE_PHOTO_CONTENT_TYPE);
        assertThat(testStudentProfile.getExtendedCompletionDate()).isEqualTo(UPDATED_EXTENDED_COMPLETION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentProfile() throws Exception {
        int databaseSizeBeforeUpdate = studentProfileRepository.findAll().size();

        // Create the StudentProfile

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentProfileMockMvc.perform(put("/api/student-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentProfile)))
            .andExpect(status().isBadRequest());

        // Validate the StudentProfile in the database
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudentProfile() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        int databaseSizeBeforeDelete = studentProfileRepository.findAll().size();

        // Get the studentProfile
        restStudentProfileMockMvc.perform(delete("/api/student-profiles/{id}", studentProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentProfile.class);
        StudentProfile studentProfile1 = new StudentProfile();
        studentProfile1.setId(1L);
        StudentProfile studentProfile2 = new StudentProfile();
        studentProfile2.setId(studentProfile1.getId());
        assertThat(studentProfile1).isEqualTo(studentProfile2);
        studentProfile2.setId(2L);
        assertThat(studentProfile1).isNotEqualTo(studentProfile2);
        studentProfile1.setId(null);
        assertThat(studentProfile1).isNotEqualTo(studentProfile2);
    }
}
