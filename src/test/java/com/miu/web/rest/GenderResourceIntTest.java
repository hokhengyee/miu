package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.Gender;
import com.miu.repository.GenderRepository;

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
 * Test class for the GenderResource REST controller.
 *
 * @see GenderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class GenderResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    @Inject
    private GenderRepository genderRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restGenderMockMvc;

    private Gender gender;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GenderResource genderResource = new GenderResource();
        ReflectionTestUtils.setField(genderResource, "genderRepository", genderRepository);
        this.restGenderMockMvc = MockMvcBuilders.standaloneSetup(genderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gender createEntity(EntityManager em) {
        Gender gender = new Gender()
                .title(DEFAULT_TITLE);
        return gender;
    }

    @Before
    public void initTest() {
        gender = createEntity(em);
    }

    @Test
    @Transactional
    public void createGender() throws Exception {
        int databaseSizeBeforeCreate = genderRepository.findAll().size();

        // Create the Gender

        restGenderMockMvc.perform(post("/api/genders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gender)))
            .andExpect(status().isCreated());

        // Validate the Gender in the database
        List<Gender> genderList = genderRepository.findAll();
        assertThat(genderList).hasSize(databaseSizeBeforeCreate + 1);
        Gender testGender = genderList.get(genderList.size() - 1);
        assertThat(testGender.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void createGenderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = genderRepository.findAll().size();

        // Create the Gender with an existing ID
        Gender existingGender = new Gender();
        existingGender.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGenderMockMvc.perform(post("/api/genders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingGender)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Gender> genderList = genderRepository.findAll();
        assertThat(genderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = genderRepository.findAll().size();
        // set the field null
        gender.setTitle(null);

        // Create the Gender, which fails.

        restGenderMockMvc.perform(post("/api/genders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gender)))
            .andExpect(status().isBadRequest());

        List<Gender> genderList = genderRepository.findAll();
        assertThat(genderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGenders() throws Exception {
        // Initialize the database
        genderRepository.saveAndFlush(gender);

        // Get all the genderList
        restGenderMockMvc.perform(get("/api/genders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gender.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())));
    }

    @Test
    @Transactional
    public void getGender() throws Exception {
        // Initialize the database
        genderRepository.saveAndFlush(gender);

        // Get the gender
        restGenderMockMvc.perform(get("/api/genders/{id}", gender.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gender.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGender() throws Exception {
        // Get the gender
        restGenderMockMvc.perform(get("/api/genders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGender() throws Exception {
        // Initialize the database
        genderRepository.saveAndFlush(gender);
        int databaseSizeBeforeUpdate = genderRepository.findAll().size();

        // Update the gender
        Gender updatedGender = genderRepository.findOne(gender.getId());
        updatedGender
                .title(UPDATED_TITLE);

        restGenderMockMvc.perform(put("/api/genders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGender)))
            .andExpect(status().isOk());

        // Validate the Gender in the database
        List<Gender> genderList = genderRepository.findAll();
        assertThat(genderList).hasSize(databaseSizeBeforeUpdate);
        Gender testGender = genderList.get(genderList.size() - 1);
        assertThat(testGender.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void updateNonExistingGender() throws Exception {
        int databaseSizeBeforeUpdate = genderRepository.findAll().size();

        // Create the Gender

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGenderMockMvc.perform(put("/api/genders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gender)))
            .andExpect(status().isCreated());

        // Validate the Gender in the database
        List<Gender> genderList = genderRepository.findAll();
        assertThat(genderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGender() throws Exception {
        // Initialize the database
        genderRepository.saveAndFlush(gender);
        int databaseSizeBeforeDelete = genderRepository.findAll().size();

        // Get the gender
        restGenderMockMvc.perform(delete("/api/genders/{id}", gender.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Gender> genderList = genderRepository.findAll();
        assertThat(genderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
