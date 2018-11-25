package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.RecordOfCertificate;
import com.miu.repository.RecordOfCertificateRepository;
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
 * Test class for the RecordOfCertificateResource REST controller.
 *
 * @see RecordOfCertificateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class RecordOfCertificateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEGREE = "AAAAAAAAAA";
    private static final String UPDATED_DEGREE = "BBBBBBBBBB";

    private static final String DEFAULT_STUDENT_NO = "AAAAAAAAAA";
    private static final String UPDATED_STUDENT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CERT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CERT_NUMBER = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CERT_SCAN_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CERT_SCAN_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CERT_SCAN_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CERT_SCAN_FILE_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_CERT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CERT_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RecordOfCertificateRepository recordOfCertificateRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRecordOfCertificateMockMvc;

    private RecordOfCertificate recordOfCertificate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecordOfCertificateResource recordOfCertificateResource = new RecordOfCertificateResource(recordOfCertificateRepository);
        this.restRecordOfCertificateMockMvc = MockMvcBuilders.standaloneSetup(recordOfCertificateResource)
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
    public static RecordOfCertificate createEntity(EntityManager em) {
        RecordOfCertificate recordOfCertificate = new RecordOfCertificate()
            .name(DEFAULT_NAME)
            .degree(DEFAULT_DEGREE)
            .studentNo(DEFAULT_STUDENT_NO)
            .certNumber(DEFAULT_CERT_NUMBER)
            .certScanFile(DEFAULT_CERT_SCAN_FILE)
            .certScanFileContentType(DEFAULT_CERT_SCAN_FILE_CONTENT_TYPE)
            .certDate(DEFAULT_CERT_DATE);
        return recordOfCertificate;
    }

    @Before
    public void initTest() {
        recordOfCertificate = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecordOfCertificate() throws Exception {
        int databaseSizeBeforeCreate = recordOfCertificateRepository.findAll().size();

        // Create the RecordOfCertificate
        restRecordOfCertificateMockMvc.perform(post("/api/record-of-certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordOfCertificate)))
            .andExpect(status().isCreated());

        // Validate the RecordOfCertificate in the database
        List<RecordOfCertificate> recordOfCertificateList = recordOfCertificateRepository.findAll();
        assertThat(recordOfCertificateList).hasSize(databaseSizeBeforeCreate + 1);
        RecordOfCertificate testRecordOfCertificate = recordOfCertificateList.get(recordOfCertificateList.size() - 1);
        assertThat(testRecordOfCertificate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRecordOfCertificate.getDegree()).isEqualTo(DEFAULT_DEGREE);
        assertThat(testRecordOfCertificate.getStudentNo()).isEqualTo(DEFAULT_STUDENT_NO);
        assertThat(testRecordOfCertificate.getCertNumber()).isEqualTo(DEFAULT_CERT_NUMBER);
        assertThat(testRecordOfCertificate.getCertScanFile()).isEqualTo(DEFAULT_CERT_SCAN_FILE);
        assertThat(testRecordOfCertificate.getCertScanFileContentType()).isEqualTo(DEFAULT_CERT_SCAN_FILE_CONTENT_TYPE);
        assertThat(testRecordOfCertificate.getCertDate()).isEqualTo(DEFAULT_CERT_DATE);
    }

    @Test
    @Transactional
    public void createRecordOfCertificateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recordOfCertificateRepository.findAll().size();

        // Create the RecordOfCertificate with an existing ID
        recordOfCertificate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecordOfCertificateMockMvc.perform(post("/api/record-of-certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordOfCertificate)))
            .andExpect(status().isBadRequest());

        // Validate the RecordOfCertificate in the database
        List<RecordOfCertificate> recordOfCertificateList = recordOfCertificateRepository.findAll();
        assertThat(recordOfCertificateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordOfCertificateRepository.findAll().size();
        // set the field null
        recordOfCertificate.setName(null);

        // Create the RecordOfCertificate, which fails.

        restRecordOfCertificateMockMvc.perform(post("/api/record-of-certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordOfCertificate)))
            .andExpect(status().isBadRequest());

        List<RecordOfCertificate> recordOfCertificateList = recordOfCertificateRepository.findAll();
        assertThat(recordOfCertificateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDegreeIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordOfCertificateRepository.findAll().size();
        // set the field null
        recordOfCertificate.setDegree(null);

        // Create the RecordOfCertificate, which fails.

        restRecordOfCertificateMockMvc.perform(post("/api/record-of-certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordOfCertificate)))
            .andExpect(status().isBadRequest());

        List<RecordOfCertificate> recordOfCertificateList = recordOfCertificateRepository.findAll();
        assertThat(recordOfCertificateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStudentNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordOfCertificateRepository.findAll().size();
        // set the field null
        recordOfCertificate.setStudentNo(null);

        // Create the RecordOfCertificate, which fails.

        restRecordOfCertificateMockMvc.perform(post("/api/record-of-certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordOfCertificate)))
            .andExpect(status().isBadRequest());

        List<RecordOfCertificate> recordOfCertificateList = recordOfCertificateRepository.findAll();
        assertThat(recordOfCertificateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCertNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordOfCertificateRepository.findAll().size();
        // set the field null
        recordOfCertificate.setCertNumber(null);

        // Create the RecordOfCertificate, which fails.

        restRecordOfCertificateMockMvc.perform(post("/api/record-of-certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordOfCertificate)))
            .andExpect(status().isBadRequest());

        List<RecordOfCertificate> recordOfCertificateList = recordOfCertificateRepository.findAll();
        assertThat(recordOfCertificateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecordOfCertificates() throws Exception {
        // Initialize the database
        recordOfCertificateRepository.saveAndFlush(recordOfCertificate);

        // Get all the recordOfCertificateList
        restRecordOfCertificateMockMvc.perform(get("/api/record-of-certificates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recordOfCertificate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE.toString())))
            .andExpect(jsonPath("$.[*].studentNo").value(hasItem(DEFAULT_STUDENT_NO.toString())))
            .andExpect(jsonPath("$.[*].certNumber").value(hasItem(DEFAULT_CERT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].certScanFileContentType").value(hasItem(DEFAULT_CERT_SCAN_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].certScanFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_CERT_SCAN_FILE))))
            .andExpect(jsonPath("$.[*].certDate").value(hasItem(DEFAULT_CERT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getRecordOfCertificate() throws Exception {
        // Initialize the database
        recordOfCertificateRepository.saveAndFlush(recordOfCertificate);

        // Get the recordOfCertificate
        restRecordOfCertificateMockMvc.perform(get("/api/record-of-certificates/{id}", recordOfCertificate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recordOfCertificate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.degree").value(DEFAULT_DEGREE.toString()))
            .andExpect(jsonPath("$.studentNo").value(DEFAULT_STUDENT_NO.toString()))
            .andExpect(jsonPath("$.certNumber").value(DEFAULT_CERT_NUMBER.toString()))
            .andExpect(jsonPath("$.certScanFileContentType").value(DEFAULT_CERT_SCAN_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.certScanFile").value(Base64Utils.encodeToString(DEFAULT_CERT_SCAN_FILE)))
            .andExpect(jsonPath("$.certDate").value(DEFAULT_CERT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRecordOfCertificate() throws Exception {
        // Get the recordOfCertificate
        restRecordOfCertificateMockMvc.perform(get("/api/record-of-certificates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecordOfCertificate() throws Exception {
        // Initialize the database
        recordOfCertificateRepository.saveAndFlush(recordOfCertificate);

        int databaseSizeBeforeUpdate = recordOfCertificateRepository.findAll().size();

        // Update the recordOfCertificate
        RecordOfCertificate updatedRecordOfCertificate = recordOfCertificateRepository.findById(recordOfCertificate.getId()).get();
        // Disconnect from session so that the updates on updatedRecordOfCertificate are not directly saved in db
        em.detach(updatedRecordOfCertificate);
        updatedRecordOfCertificate
            .name(UPDATED_NAME)
            .degree(UPDATED_DEGREE)
            .studentNo(UPDATED_STUDENT_NO)
            .certNumber(UPDATED_CERT_NUMBER)
            .certScanFile(UPDATED_CERT_SCAN_FILE)
            .certScanFileContentType(UPDATED_CERT_SCAN_FILE_CONTENT_TYPE)
            .certDate(UPDATED_CERT_DATE);

        restRecordOfCertificateMockMvc.perform(put("/api/record-of-certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecordOfCertificate)))
            .andExpect(status().isOk());

        // Validate the RecordOfCertificate in the database
        List<RecordOfCertificate> recordOfCertificateList = recordOfCertificateRepository.findAll();
        assertThat(recordOfCertificateList).hasSize(databaseSizeBeforeUpdate);
        RecordOfCertificate testRecordOfCertificate = recordOfCertificateList.get(recordOfCertificateList.size() - 1);
        assertThat(testRecordOfCertificate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRecordOfCertificate.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testRecordOfCertificate.getStudentNo()).isEqualTo(UPDATED_STUDENT_NO);
        assertThat(testRecordOfCertificate.getCertNumber()).isEqualTo(UPDATED_CERT_NUMBER);
        assertThat(testRecordOfCertificate.getCertScanFile()).isEqualTo(UPDATED_CERT_SCAN_FILE);
        assertThat(testRecordOfCertificate.getCertScanFileContentType()).isEqualTo(UPDATED_CERT_SCAN_FILE_CONTENT_TYPE);
        assertThat(testRecordOfCertificate.getCertDate()).isEqualTo(UPDATED_CERT_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingRecordOfCertificate() throws Exception {
        int databaseSizeBeforeUpdate = recordOfCertificateRepository.findAll().size();

        // Create the RecordOfCertificate

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecordOfCertificateMockMvc.perform(put("/api/record-of-certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordOfCertificate)))
            .andExpect(status().isBadRequest());

        // Validate the RecordOfCertificate in the database
        List<RecordOfCertificate> recordOfCertificateList = recordOfCertificateRepository.findAll();
        assertThat(recordOfCertificateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecordOfCertificate() throws Exception {
        // Initialize the database
        recordOfCertificateRepository.saveAndFlush(recordOfCertificate);

        int databaseSizeBeforeDelete = recordOfCertificateRepository.findAll().size();

        // Get the recordOfCertificate
        restRecordOfCertificateMockMvc.perform(delete("/api/record-of-certificates/{id}", recordOfCertificate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RecordOfCertificate> recordOfCertificateList = recordOfCertificateRepository.findAll();
        assertThat(recordOfCertificateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecordOfCertificate.class);
        RecordOfCertificate recordOfCertificate1 = new RecordOfCertificate();
        recordOfCertificate1.setId(1L);
        RecordOfCertificate recordOfCertificate2 = new RecordOfCertificate();
        recordOfCertificate2.setId(recordOfCertificate1.getId());
        assertThat(recordOfCertificate1).isEqualTo(recordOfCertificate2);
        recordOfCertificate2.setId(2L);
        assertThat(recordOfCertificate1).isNotEqualTo(recordOfCertificate2);
        recordOfCertificate1.setId(null);
        assertThat(recordOfCertificate1).isNotEqualTo(recordOfCertificate2);
    }
}
