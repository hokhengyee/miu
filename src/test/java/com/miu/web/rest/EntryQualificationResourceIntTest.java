package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.EntryQualification;
import com.miu.domain.Course;
import com.miu.repository.EntryQualificationRepository;
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
 * Test class for the EntryQualificationResource REST controller.
 *
 * @see EntryQualificationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class EntryQualificationResourceIntTest {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final Long DEFAULT_DISPLAY_ORDER = 1L;
    private static final Long UPDATED_DISPLAY_ORDER = 2L;

    @Autowired
    private EntryQualificationRepository entryQualificationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEntryQualificationMockMvc;

    private EntryQualification entryQualification;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntryQualificationResource entryQualificationResource = new EntryQualificationResource(entryQualificationRepository);
        this.restEntryQualificationMockMvc = MockMvcBuilders.standaloneSetup(entryQualificationResource)
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
    public static EntryQualification createEntity(EntityManager em) {
        EntryQualification entryQualification = new EntryQualification()
            .content(DEFAULT_CONTENT)
            .displayOrder(DEFAULT_DISPLAY_ORDER);
        // Add required entity
        Course course = CourseResourceIntTest.createEntity(em);
        em.persist(course);
        em.flush();
        entryQualification.setCourse(course);
        return entryQualification;
    }

    @Before
    public void initTest() {
        entryQualification = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntryQualification() throws Exception {
        int databaseSizeBeforeCreate = entryQualificationRepository.findAll().size();

        // Create the EntryQualification
        restEntryQualificationMockMvc.perform(post("/api/entry-qualifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entryQualification)))
            .andExpect(status().isCreated());

        // Validate the EntryQualification in the database
        List<EntryQualification> entryQualificationList = entryQualificationRepository.findAll();
        assertThat(entryQualificationList).hasSize(databaseSizeBeforeCreate + 1);
        EntryQualification testEntryQualification = entryQualificationList.get(entryQualificationList.size() - 1);
        assertThat(testEntryQualification.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testEntryQualification.getDisplayOrder()).isEqualTo(DEFAULT_DISPLAY_ORDER);
    }

    @Test
    @Transactional
    public void createEntryQualificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entryQualificationRepository.findAll().size();

        // Create the EntryQualification with an existing ID
        entryQualification.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntryQualificationMockMvc.perform(post("/api/entry-qualifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entryQualification)))
            .andExpect(status().isBadRequest());

        // Validate the EntryQualification in the database
        List<EntryQualification> entryQualificationList = entryQualificationRepository.findAll();
        assertThat(entryQualificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = entryQualificationRepository.findAll().size();
        // set the field null
        entryQualification.setContent(null);

        // Create the EntryQualification, which fails.

        restEntryQualificationMockMvc.perform(post("/api/entry-qualifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entryQualification)))
            .andExpect(status().isBadRequest());

        List<EntryQualification> entryQualificationList = entryQualificationRepository.findAll();
        assertThat(entryQualificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEntryQualifications() throws Exception {
        // Initialize the database
        entryQualificationRepository.saveAndFlush(entryQualification);

        // Get all the entryQualificationList
        restEntryQualificationMockMvc.perform(get("/api/entry-qualifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entryQualification.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].displayOrder").value(hasItem(DEFAULT_DISPLAY_ORDER.intValue())));
    }
    
    @Test
    @Transactional
    public void getEntryQualification() throws Exception {
        // Initialize the database
        entryQualificationRepository.saveAndFlush(entryQualification);

        // Get the entryQualification
        restEntryQualificationMockMvc.perform(get("/api/entry-qualifications/{id}", entryQualification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entryQualification.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.displayOrder").value(DEFAULT_DISPLAY_ORDER.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEntryQualification() throws Exception {
        // Get the entryQualification
        restEntryQualificationMockMvc.perform(get("/api/entry-qualifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntryQualification() throws Exception {
        // Initialize the database
        entryQualificationRepository.saveAndFlush(entryQualification);

        int databaseSizeBeforeUpdate = entryQualificationRepository.findAll().size();

        // Update the entryQualification
        EntryQualification updatedEntryQualification = entryQualificationRepository.findById(entryQualification.getId()).get();
        // Disconnect from session so that the updates on updatedEntryQualification are not directly saved in db
        em.detach(updatedEntryQualification);
        updatedEntryQualification
            .content(UPDATED_CONTENT)
            .displayOrder(UPDATED_DISPLAY_ORDER);

        restEntryQualificationMockMvc.perform(put("/api/entry-qualifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntryQualification)))
            .andExpect(status().isOk());

        // Validate the EntryQualification in the database
        List<EntryQualification> entryQualificationList = entryQualificationRepository.findAll();
        assertThat(entryQualificationList).hasSize(databaseSizeBeforeUpdate);
        EntryQualification testEntryQualification = entryQualificationList.get(entryQualificationList.size() - 1);
        assertThat(testEntryQualification.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testEntryQualification.getDisplayOrder()).isEqualTo(UPDATED_DISPLAY_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingEntryQualification() throws Exception {
        int databaseSizeBeforeUpdate = entryQualificationRepository.findAll().size();

        // Create the EntryQualification

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntryQualificationMockMvc.perform(put("/api/entry-qualifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entryQualification)))
            .andExpect(status().isBadRequest());

        // Validate the EntryQualification in the database
        List<EntryQualification> entryQualificationList = entryQualificationRepository.findAll();
        assertThat(entryQualificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEntryQualification() throws Exception {
        // Initialize the database
        entryQualificationRepository.saveAndFlush(entryQualification);

        int databaseSizeBeforeDelete = entryQualificationRepository.findAll().size();

        // Get the entryQualification
        restEntryQualificationMockMvc.perform(delete("/api/entry-qualifications/{id}", entryQualification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EntryQualification> entryQualificationList = entryQualificationRepository.findAll();
        assertThat(entryQualificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntryQualification.class);
        EntryQualification entryQualification1 = new EntryQualification();
        entryQualification1.setId(1L);
        EntryQualification entryQualification2 = new EntryQualification();
        entryQualification2.setId(entryQualification1.getId());
        assertThat(entryQualification1).isEqualTo(entryQualification2);
        entryQualification2.setId(2L);
        assertThat(entryQualification1).isNotEqualTo(entryQualification2);
        entryQualification1.setId(null);
        assertThat(entryQualification1).isNotEqualTo(entryQualification2);
    }
}
