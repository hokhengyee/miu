package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.MinisterialWorkExperience;
import com.miu.repository.MinisterialWorkExperienceRepository;
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
 * Test class for the MinisterialWorkExperienceResource REST controller.
 *
 * @see MinisterialWorkExperienceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class MinisterialWorkExperienceResourceIntTest {

    private static final String DEFAULT_NAME_OF_MINISTRY_2 = "AAAAAAAAAA";
    private static final String UPDATED_NAME_OF_MINISTRY_2 = "BBBBBBBBBB";

    private static final String DEFAULT_AREA_OF_MINISTRY_2 = "AAAAAAAAAA";
    private static final String UPDATED_AREA_OF_MINISTRY_2 = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_OF_MINISTRY_3 = "AAAAAAAAAA";
    private static final String UPDATED_NAME_OF_MINISTRY_3 = "BBBBBBBBBB";

    private static final String DEFAULT_AREA_OF_MINISTRY_3 = "AAAAAAAAAA";
    private static final String UPDATED_AREA_OF_MINISTRY_3 = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_OF_MINISTRY_4 = "AAAAAAAAAA";
    private static final String UPDATED_NAME_OF_MINISTRY_4 = "BBBBBBBBBB";

    private static final String DEFAULT_AREA_OF_MINISTRY_4 = "AAAAAAAAAA";
    private static final String UPDATED_AREA_OF_MINISTRY_4 = "BBBBBBBBBB";

    private static final String DEFAULT_MD_5_KEY = "AAAAAAAAAA";
    private static final String UPDATED_MD_5_KEY = "BBBBBBBBBB";

    private static final Long DEFAULT_YEARS_2 = 1L;
    private static final Long UPDATED_YEARS_2 = 2L;

    private static final Long DEFAULT_YEARS_3 = 1L;
    private static final Long UPDATED_YEARS_3 = 2L;

    private static final Long DEFAULT_YEARS_4 = 1L;
    private static final Long UPDATED_YEARS_4 = 2L;

    private static final String DEFAULT_NAME_OF_MINISTRY_1 = "AAAAAAAAAA";
    private static final String UPDATED_NAME_OF_MINISTRY_1 = "BBBBBBBBBB";

    private static final String DEFAULT_AREA_OF_MINISTRY_1 = "AAAAAAAAAA";
    private static final String UPDATED_AREA_OF_MINISTRY_1 = "BBBBBBBBBB";

    private static final Long DEFAULT_YEARS_1 = 1L;
    private static final Long UPDATED_YEARS_1 = 2L;

    @Autowired
    private MinisterialWorkExperienceRepository ministerialWorkExperienceRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMinisterialWorkExperienceMockMvc;

    private MinisterialWorkExperience ministerialWorkExperience;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MinisterialWorkExperienceResource ministerialWorkExperienceResource = new MinisterialWorkExperienceResource(ministerialWorkExperienceRepository);
        this.restMinisterialWorkExperienceMockMvc = MockMvcBuilders.standaloneSetup(ministerialWorkExperienceResource)
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
    public static MinisterialWorkExperience createEntity(EntityManager em) {
        MinisterialWorkExperience ministerialWorkExperience = new MinisterialWorkExperience()
            .nameOfMinistry2(DEFAULT_NAME_OF_MINISTRY_2)
            .areaOfMinistry2(DEFAULT_AREA_OF_MINISTRY_2)
            .nameOfMinistry3(DEFAULT_NAME_OF_MINISTRY_3)
            .areaOfMinistry3(DEFAULT_AREA_OF_MINISTRY_3)
            .nameOfMinistry4(DEFAULT_NAME_OF_MINISTRY_4)
            .areaOfMinistry4(DEFAULT_AREA_OF_MINISTRY_4)
            .md5Key(DEFAULT_MD_5_KEY)
            .years2(DEFAULT_YEARS_2)
            .years3(DEFAULT_YEARS_3)
            .years4(DEFAULT_YEARS_4)
            .nameOfMinistry1(DEFAULT_NAME_OF_MINISTRY_1)
            .areaOfMinistry1(DEFAULT_AREA_OF_MINISTRY_1)
            .years1(DEFAULT_YEARS_1);
        return ministerialWorkExperience;
    }

    @Before
    public void initTest() {
        ministerialWorkExperience = createEntity(em);
    }

    @Test
    @Transactional
    public void createMinisterialWorkExperience() throws Exception {
        int databaseSizeBeforeCreate = ministerialWorkExperienceRepository.findAll().size();

        // Create the MinisterialWorkExperience
        restMinisterialWorkExperienceMockMvc.perform(post("/api/ministerial-work-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ministerialWorkExperience)))
            .andExpect(status().isCreated());

        // Validate the MinisterialWorkExperience in the database
        List<MinisterialWorkExperience> ministerialWorkExperienceList = ministerialWorkExperienceRepository.findAll();
        assertThat(ministerialWorkExperienceList).hasSize(databaseSizeBeforeCreate + 1);
        MinisterialWorkExperience testMinisterialWorkExperience = ministerialWorkExperienceList.get(ministerialWorkExperienceList.size() - 1);
        assertThat(testMinisterialWorkExperience.getNameOfMinistry2()).isEqualTo(DEFAULT_NAME_OF_MINISTRY_2);
        assertThat(testMinisterialWorkExperience.getAreaOfMinistry2()).isEqualTo(DEFAULT_AREA_OF_MINISTRY_2);
        assertThat(testMinisterialWorkExperience.getNameOfMinistry3()).isEqualTo(DEFAULT_NAME_OF_MINISTRY_3);
        assertThat(testMinisterialWorkExperience.getAreaOfMinistry3()).isEqualTo(DEFAULT_AREA_OF_MINISTRY_3);
        assertThat(testMinisterialWorkExperience.getNameOfMinistry4()).isEqualTo(DEFAULT_NAME_OF_MINISTRY_4);
        assertThat(testMinisterialWorkExperience.getAreaOfMinistry4()).isEqualTo(DEFAULT_AREA_OF_MINISTRY_4);
        assertThat(testMinisterialWorkExperience.getMd5Key()).isEqualTo(DEFAULT_MD_5_KEY);
        assertThat(testMinisterialWorkExperience.getYears2()).isEqualTo(DEFAULT_YEARS_2);
        assertThat(testMinisterialWorkExperience.getYears3()).isEqualTo(DEFAULT_YEARS_3);
        assertThat(testMinisterialWorkExperience.getYears4()).isEqualTo(DEFAULT_YEARS_4);
        assertThat(testMinisterialWorkExperience.getNameOfMinistry1()).isEqualTo(DEFAULT_NAME_OF_MINISTRY_1);
        assertThat(testMinisterialWorkExperience.getAreaOfMinistry1()).isEqualTo(DEFAULT_AREA_OF_MINISTRY_1);
        assertThat(testMinisterialWorkExperience.getYears1()).isEqualTo(DEFAULT_YEARS_1);
    }

    @Test
    @Transactional
    public void createMinisterialWorkExperienceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ministerialWorkExperienceRepository.findAll().size();

        // Create the MinisterialWorkExperience with an existing ID
        ministerialWorkExperience.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMinisterialWorkExperienceMockMvc.perform(post("/api/ministerial-work-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ministerialWorkExperience)))
            .andExpect(status().isBadRequest());

        // Validate the MinisterialWorkExperience in the database
        List<MinisterialWorkExperience> ministerialWorkExperienceList = ministerialWorkExperienceRepository.findAll();
        assertThat(ministerialWorkExperienceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMinisterialWorkExperiences() throws Exception {
        // Initialize the database
        ministerialWorkExperienceRepository.saveAndFlush(ministerialWorkExperience);

        // Get all the ministerialWorkExperienceList
        restMinisterialWorkExperienceMockMvc.perform(get("/api/ministerial-work-experiences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ministerialWorkExperience.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameOfMinistry2").value(hasItem(DEFAULT_NAME_OF_MINISTRY_2.toString())))
            .andExpect(jsonPath("$.[*].areaOfMinistry2").value(hasItem(DEFAULT_AREA_OF_MINISTRY_2.toString())))
            .andExpect(jsonPath("$.[*].nameOfMinistry3").value(hasItem(DEFAULT_NAME_OF_MINISTRY_3.toString())))
            .andExpect(jsonPath("$.[*].areaOfMinistry3").value(hasItem(DEFAULT_AREA_OF_MINISTRY_3.toString())))
            .andExpect(jsonPath("$.[*].nameOfMinistry4").value(hasItem(DEFAULT_NAME_OF_MINISTRY_4.toString())))
            .andExpect(jsonPath("$.[*].areaOfMinistry4").value(hasItem(DEFAULT_AREA_OF_MINISTRY_4.toString())))
            .andExpect(jsonPath("$.[*].md5Key").value(hasItem(DEFAULT_MD_5_KEY.toString())))
            .andExpect(jsonPath("$.[*].years2").value(hasItem(DEFAULT_YEARS_2.intValue())))
            .andExpect(jsonPath("$.[*].years3").value(hasItem(DEFAULT_YEARS_3.intValue())))
            .andExpect(jsonPath("$.[*].years4").value(hasItem(DEFAULT_YEARS_4.intValue())))
            .andExpect(jsonPath("$.[*].nameOfMinistry1").value(hasItem(DEFAULT_NAME_OF_MINISTRY_1.toString())))
            .andExpect(jsonPath("$.[*].areaOfMinistry1").value(hasItem(DEFAULT_AREA_OF_MINISTRY_1.toString())))
            .andExpect(jsonPath("$.[*].years1").value(hasItem(DEFAULT_YEARS_1.intValue())));
    }
    
    @Test
    @Transactional
    public void getMinisterialWorkExperience() throws Exception {
        // Initialize the database
        ministerialWorkExperienceRepository.saveAndFlush(ministerialWorkExperience);

        // Get the ministerialWorkExperience
        restMinisterialWorkExperienceMockMvc.perform(get("/api/ministerial-work-experiences/{id}", ministerialWorkExperience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ministerialWorkExperience.getId().intValue()))
            .andExpect(jsonPath("$.nameOfMinistry2").value(DEFAULT_NAME_OF_MINISTRY_2.toString()))
            .andExpect(jsonPath("$.areaOfMinistry2").value(DEFAULT_AREA_OF_MINISTRY_2.toString()))
            .andExpect(jsonPath("$.nameOfMinistry3").value(DEFAULT_NAME_OF_MINISTRY_3.toString()))
            .andExpect(jsonPath("$.areaOfMinistry3").value(DEFAULT_AREA_OF_MINISTRY_3.toString()))
            .andExpect(jsonPath("$.nameOfMinistry4").value(DEFAULT_NAME_OF_MINISTRY_4.toString()))
            .andExpect(jsonPath("$.areaOfMinistry4").value(DEFAULT_AREA_OF_MINISTRY_4.toString()))
            .andExpect(jsonPath("$.md5Key").value(DEFAULT_MD_5_KEY.toString()))
            .andExpect(jsonPath("$.years2").value(DEFAULT_YEARS_2.intValue()))
            .andExpect(jsonPath("$.years3").value(DEFAULT_YEARS_3.intValue()))
            .andExpect(jsonPath("$.years4").value(DEFAULT_YEARS_4.intValue()))
            .andExpect(jsonPath("$.nameOfMinistry1").value(DEFAULT_NAME_OF_MINISTRY_1.toString()))
            .andExpect(jsonPath("$.areaOfMinistry1").value(DEFAULT_AREA_OF_MINISTRY_1.toString()))
            .andExpect(jsonPath("$.years1").value(DEFAULT_YEARS_1.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMinisterialWorkExperience() throws Exception {
        // Get the ministerialWorkExperience
        restMinisterialWorkExperienceMockMvc.perform(get("/api/ministerial-work-experiences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMinisterialWorkExperience() throws Exception {
        // Initialize the database
        ministerialWorkExperienceRepository.saveAndFlush(ministerialWorkExperience);

        int databaseSizeBeforeUpdate = ministerialWorkExperienceRepository.findAll().size();

        // Update the ministerialWorkExperience
        MinisterialWorkExperience updatedMinisterialWorkExperience = ministerialWorkExperienceRepository.findById(ministerialWorkExperience.getId()).get();
        // Disconnect from session so that the updates on updatedMinisterialWorkExperience are not directly saved in db
        em.detach(updatedMinisterialWorkExperience);
        updatedMinisterialWorkExperience
            .nameOfMinistry2(UPDATED_NAME_OF_MINISTRY_2)
            .areaOfMinistry2(UPDATED_AREA_OF_MINISTRY_2)
            .nameOfMinistry3(UPDATED_NAME_OF_MINISTRY_3)
            .areaOfMinistry3(UPDATED_AREA_OF_MINISTRY_3)
            .nameOfMinistry4(UPDATED_NAME_OF_MINISTRY_4)
            .areaOfMinistry4(UPDATED_AREA_OF_MINISTRY_4)
            .md5Key(UPDATED_MD_5_KEY)
            .years2(UPDATED_YEARS_2)
            .years3(UPDATED_YEARS_3)
            .years4(UPDATED_YEARS_4)
            .nameOfMinistry1(UPDATED_NAME_OF_MINISTRY_1)
            .areaOfMinistry1(UPDATED_AREA_OF_MINISTRY_1)
            .years1(UPDATED_YEARS_1);

        restMinisterialWorkExperienceMockMvc.perform(put("/api/ministerial-work-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMinisterialWorkExperience)))
            .andExpect(status().isOk());

        // Validate the MinisterialWorkExperience in the database
        List<MinisterialWorkExperience> ministerialWorkExperienceList = ministerialWorkExperienceRepository.findAll();
        assertThat(ministerialWorkExperienceList).hasSize(databaseSizeBeforeUpdate);
        MinisterialWorkExperience testMinisterialWorkExperience = ministerialWorkExperienceList.get(ministerialWorkExperienceList.size() - 1);
        assertThat(testMinisterialWorkExperience.getNameOfMinistry2()).isEqualTo(UPDATED_NAME_OF_MINISTRY_2);
        assertThat(testMinisterialWorkExperience.getAreaOfMinistry2()).isEqualTo(UPDATED_AREA_OF_MINISTRY_2);
        assertThat(testMinisterialWorkExperience.getNameOfMinistry3()).isEqualTo(UPDATED_NAME_OF_MINISTRY_3);
        assertThat(testMinisterialWorkExperience.getAreaOfMinistry3()).isEqualTo(UPDATED_AREA_OF_MINISTRY_3);
        assertThat(testMinisterialWorkExperience.getNameOfMinistry4()).isEqualTo(UPDATED_NAME_OF_MINISTRY_4);
        assertThat(testMinisterialWorkExperience.getAreaOfMinistry4()).isEqualTo(UPDATED_AREA_OF_MINISTRY_4);
        assertThat(testMinisterialWorkExperience.getMd5Key()).isEqualTo(UPDATED_MD_5_KEY);
        assertThat(testMinisterialWorkExperience.getYears2()).isEqualTo(UPDATED_YEARS_2);
        assertThat(testMinisterialWorkExperience.getYears3()).isEqualTo(UPDATED_YEARS_3);
        assertThat(testMinisterialWorkExperience.getYears4()).isEqualTo(UPDATED_YEARS_4);
        assertThat(testMinisterialWorkExperience.getNameOfMinistry1()).isEqualTo(UPDATED_NAME_OF_MINISTRY_1);
        assertThat(testMinisterialWorkExperience.getAreaOfMinistry1()).isEqualTo(UPDATED_AREA_OF_MINISTRY_1);
        assertThat(testMinisterialWorkExperience.getYears1()).isEqualTo(UPDATED_YEARS_1);
    }

    @Test
    @Transactional
    public void updateNonExistingMinisterialWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = ministerialWorkExperienceRepository.findAll().size();

        // Create the MinisterialWorkExperience

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMinisterialWorkExperienceMockMvc.perform(put("/api/ministerial-work-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ministerialWorkExperience)))
            .andExpect(status().isBadRequest());

        // Validate the MinisterialWorkExperience in the database
        List<MinisterialWorkExperience> ministerialWorkExperienceList = ministerialWorkExperienceRepository.findAll();
        assertThat(ministerialWorkExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMinisterialWorkExperience() throws Exception {
        // Initialize the database
        ministerialWorkExperienceRepository.saveAndFlush(ministerialWorkExperience);

        int databaseSizeBeforeDelete = ministerialWorkExperienceRepository.findAll().size();

        // Get the ministerialWorkExperience
        restMinisterialWorkExperienceMockMvc.perform(delete("/api/ministerial-work-experiences/{id}", ministerialWorkExperience.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MinisterialWorkExperience> ministerialWorkExperienceList = ministerialWorkExperienceRepository.findAll();
        assertThat(ministerialWorkExperienceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MinisterialWorkExperience.class);
        MinisterialWorkExperience ministerialWorkExperience1 = new MinisterialWorkExperience();
        ministerialWorkExperience1.setId(1L);
        MinisterialWorkExperience ministerialWorkExperience2 = new MinisterialWorkExperience();
        ministerialWorkExperience2.setId(ministerialWorkExperience1.getId());
        assertThat(ministerialWorkExperience1).isEqualTo(ministerialWorkExperience2);
        ministerialWorkExperience2.setId(2L);
        assertThat(ministerialWorkExperience1).isNotEqualTo(ministerialWorkExperience2);
        ministerialWorkExperience1.setId(null);
        assertThat(ministerialWorkExperience1).isNotEqualTo(ministerialWorkExperience2);
    }
}
