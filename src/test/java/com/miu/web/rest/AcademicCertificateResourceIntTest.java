package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.AcademicCertificate;
import com.miu.repository.AcademicCertificateRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AcademicCertificateResource REST controller.
 *
 * @see AcademicCertificateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class AcademicCertificateResourceIntTest {

    private static final String DEFAULT_MD_5_KEY = "AAAAAAAAAA";
    private static final String UPDATED_MD_5_KEY = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ACADEMIC_CERTIFICATE_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ACADEMIC_CERTIFICATE_1 = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ACADEMIC_CERTIFICATE_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ACADEMIC_CERTIFICATE_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ACADEMIC_CERTIFICATE_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ACADEMIC_CERTIFICATE_2 = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ACADEMIC_CERTIFICATE_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ACADEMIC_CERTIFICATE_2_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ACADEMIC_CERTIFICATE_3 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ACADEMIC_CERTIFICATE_3 = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ACADEMIC_CERTIFICATE_3_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ACADEMIC_CERTIFICATE_3_CONTENT_TYPE = "image/png";

    @Inject
    private AcademicCertificateRepository academicCertificateRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAcademicCertificateMockMvc;

    private AcademicCertificate academicCertificate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AcademicCertificateResource academicCertificateResource = new AcademicCertificateResource();
        ReflectionTestUtils.setField(academicCertificateResource, "academicCertificateRepository", academicCertificateRepository);
        this.restAcademicCertificateMockMvc = MockMvcBuilders.standaloneSetup(academicCertificateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcademicCertificate createEntity(EntityManager em) {
        AcademicCertificate academicCertificate = new AcademicCertificate()
                .md5Key(DEFAULT_MD_5_KEY)
                .academicCertificate1(DEFAULT_ACADEMIC_CERTIFICATE_1)
                .academicCertificate1ContentType(DEFAULT_ACADEMIC_CERTIFICATE_1_CONTENT_TYPE)
                .academicCertificate2(DEFAULT_ACADEMIC_CERTIFICATE_2)
                .academicCertificate2ContentType(DEFAULT_ACADEMIC_CERTIFICATE_2_CONTENT_TYPE)
                .academicCertificate3(DEFAULT_ACADEMIC_CERTIFICATE_3)
                .academicCertificate3ContentType(DEFAULT_ACADEMIC_CERTIFICATE_3_CONTENT_TYPE);
        return academicCertificate;
    }

    @Before
    public void initTest() {
        academicCertificate = createEntity(em);
    }

    @Test
    @Transactional
    public void createAcademicCertificate() throws Exception {
        int databaseSizeBeforeCreate = academicCertificateRepository.findAll().size();

        // Create the AcademicCertificate

        restAcademicCertificateMockMvc.perform(post("/api/academic-certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicCertificate)))
            .andExpect(status().isCreated());

        // Validate the AcademicCertificate in the database
        List<AcademicCertificate> academicCertificateList = academicCertificateRepository.findAll();
        assertThat(academicCertificateList).hasSize(databaseSizeBeforeCreate + 1);
        AcademicCertificate testAcademicCertificate = academicCertificateList.get(academicCertificateList.size() - 1);
        assertThat(testAcademicCertificate.getMd5Key()).isEqualTo(DEFAULT_MD_5_KEY);
        assertThat(testAcademicCertificate.getAcademicCertificate1()).isEqualTo(DEFAULT_ACADEMIC_CERTIFICATE_1);
        assertThat(testAcademicCertificate.getAcademicCertificate1ContentType()).isEqualTo(DEFAULT_ACADEMIC_CERTIFICATE_1_CONTENT_TYPE);
        assertThat(testAcademicCertificate.getAcademicCertificate2()).isEqualTo(DEFAULT_ACADEMIC_CERTIFICATE_2);
        assertThat(testAcademicCertificate.getAcademicCertificate2ContentType()).isEqualTo(DEFAULT_ACADEMIC_CERTIFICATE_2_CONTENT_TYPE);
        assertThat(testAcademicCertificate.getAcademicCertificate3()).isEqualTo(DEFAULT_ACADEMIC_CERTIFICATE_3);
        assertThat(testAcademicCertificate.getAcademicCertificate3ContentType()).isEqualTo(DEFAULT_ACADEMIC_CERTIFICATE_3_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createAcademicCertificateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = academicCertificateRepository.findAll().size();

        // Create the AcademicCertificate with an existing ID
        AcademicCertificate existingAcademicCertificate = new AcademicCertificate();
        existingAcademicCertificate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcademicCertificateMockMvc.perform(post("/api/academic-certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAcademicCertificate)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AcademicCertificate> academicCertificateList = academicCertificateRepository.findAll();
        assertThat(academicCertificateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAcademicCertificates() throws Exception {
        // Initialize the database
        academicCertificateRepository.saveAndFlush(academicCertificate);

        // Get all the academicCertificateList
        restAcademicCertificateMockMvc.perform(get("/api/academic-certificates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academicCertificate.getId().intValue())))
            .andExpect(jsonPath("$.[*].md5Key").value(hasItem(DEFAULT_MD_5_KEY.toString())))
            .andExpect(jsonPath("$.[*].academicCertificate1ContentType").value(hasItem(DEFAULT_ACADEMIC_CERTIFICATE_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].academicCertificate1").value(hasItem(Base64Utils.encodeToString(DEFAULT_ACADEMIC_CERTIFICATE_1))))
            .andExpect(jsonPath("$.[*].academicCertificate2ContentType").value(hasItem(DEFAULT_ACADEMIC_CERTIFICATE_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].academicCertificate2").value(hasItem(Base64Utils.encodeToString(DEFAULT_ACADEMIC_CERTIFICATE_2))))
            .andExpect(jsonPath("$.[*].academicCertificate3ContentType").value(hasItem(DEFAULT_ACADEMIC_CERTIFICATE_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].academicCertificate3").value(hasItem(Base64Utils.encodeToString(DEFAULT_ACADEMIC_CERTIFICATE_3))));
    }

    @Test
    @Transactional
    public void getAcademicCertificate() throws Exception {
        // Initialize the database
        academicCertificateRepository.saveAndFlush(academicCertificate);

        // Get the academicCertificate
        restAcademicCertificateMockMvc.perform(get("/api/academic-certificates/{id}", academicCertificate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(academicCertificate.getId().intValue()))
            .andExpect(jsonPath("$.md5Key").value(DEFAULT_MD_5_KEY.toString()))
            .andExpect(jsonPath("$.academicCertificate1ContentType").value(DEFAULT_ACADEMIC_CERTIFICATE_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.academicCertificate1").value(Base64Utils.encodeToString(DEFAULT_ACADEMIC_CERTIFICATE_1)))
            .andExpect(jsonPath("$.academicCertificate2ContentType").value(DEFAULT_ACADEMIC_CERTIFICATE_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.academicCertificate2").value(Base64Utils.encodeToString(DEFAULT_ACADEMIC_CERTIFICATE_2)))
            .andExpect(jsonPath("$.academicCertificate3ContentType").value(DEFAULT_ACADEMIC_CERTIFICATE_3_CONTENT_TYPE))
            .andExpect(jsonPath("$.academicCertificate3").value(Base64Utils.encodeToString(DEFAULT_ACADEMIC_CERTIFICATE_3)));
    }

    @Test
    @Transactional
    public void getNonExistingAcademicCertificate() throws Exception {
        // Get the academicCertificate
        restAcademicCertificateMockMvc.perform(get("/api/academic-certificates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcademicCertificate() throws Exception {
        // Initialize the database
        academicCertificateRepository.saveAndFlush(academicCertificate);
        int databaseSizeBeforeUpdate = academicCertificateRepository.findAll().size();

        // Update the academicCertificate
        AcademicCertificate updatedAcademicCertificate = academicCertificateRepository.findOne(academicCertificate.getId());
        updatedAcademicCertificate
                .md5Key(UPDATED_MD_5_KEY)
                .academicCertificate1(UPDATED_ACADEMIC_CERTIFICATE_1)
                .academicCertificate1ContentType(UPDATED_ACADEMIC_CERTIFICATE_1_CONTENT_TYPE)
                .academicCertificate2(UPDATED_ACADEMIC_CERTIFICATE_2)
                .academicCertificate2ContentType(UPDATED_ACADEMIC_CERTIFICATE_2_CONTENT_TYPE)
                .academicCertificate3(UPDATED_ACADEMIC_CERTIFICATE_3)
                .academicCertificate3ContentType(UPDATED_ACADEMIC_CERTIFICATE_3_CONTENT_TYPE);

        restAcademicCertificateMockMvc.perform(put("/api/academic-certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAcademicCertificate)))
            .andExpect(status().isOk());

        // Validate the AcademicCertificate in the database
        List<AcademicCertificate> academicCertificateList = academicCertificateRepository.findAll();
        assertThat(academicCertificateList).hasSize(databaseSizeBeforeUpdate);
        AcademicCertificate testAcademicCertificate = academicCertificateList.get(academicCertificateList.size() - 1);
        assertThat(testAcademicCertificate.getMd5Key()).isEqualTo(UPDATED_MD_5_KEY);
        assertThat(testAcademicCertificate.getAcademicCertificate1()).isEqualTo(UPDATED_ACADEMIC_CERTIFICATE_1);
        assertThat(testAcademicCertificate.getAcademicCertificate1ContentType()).isEqualTo(UPDATED_ACADEMIC_CERTIFICATE_1_CONTENT_TYPE);
        assertThat(testAcademicCertificate.getAcademicCertificate2()).isEqualTo(UPDATED_ACADEMIC_CERTIFICATE_2);
        assertThat(testAcademicCertificate.getAcademicCertificate2ContentType()).isEqualTo(UPDATED_ACADEMIC_CERTIFICATE_2_CONTENT_TYPE);
        assertThat(testAcademicCertificate.getAcademicCertificate3()).isEqualTo(UPDATED_ACADEMIC_CERTIFICATE_3);
        assertThat(testAcademicCertificate.getAcademicCertificate3ContentType()).isEqualTo(UPDATED_ACADEMIC_CERTIFICATE_3_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAcademicCertificate() throws Exception {
        int databaseSizeBeforeUpdate = academicCertificateRepository.findAll().size();

        // Create the AcademicCertificate

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAcademicCertificateMockMvc.perform(put("/api/academic-certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicCertificate)))
            .andExpect(status().isCreated());

        // Validate the AcademicCertificate in the database
        List<AcademicCertificate> academicCertificateList = academicCertificateRepository.findAll();
        assertThat(academicCertificateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAcademicCertificate() throws Exception {
        // Initialize the database
        academicCertificateRepository.saveAndFlush(academicCertificate);
        int databaseSizeBeforeDelete = academicCertificateRepository.findAll().size();

        // Get the academicCertificate
        restAcademicCertificateMockMvc.perform(delete("/api/academic-certificates/{id}", academicCertificate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AcademicCertificate> academicCertificateList = academicCertificateRepository.findAll();
        assertThat(academicCertificateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
