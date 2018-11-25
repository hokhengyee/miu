package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.Salutation;
import com.miu.repository.SalutationRepository;
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
 * Test class for the SalutationResource REST controller.
 *
 * @see SalutationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class SalutationResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Long DEFAULT_DISPLAY_ORDER = 1L;
    private static final Long UPDATED_DISPLAY_ORDER = 2L;

    @Autowired
    private SalutationRepository salutationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSalutationMockMvc;

    private Salutation salutation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SalutationResource salutationResource = new SalutationResource(salutationRepository);
        this.restSalutationMockMvc = MockMvcBuilders.standaloneSetup(salutationResource)
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
    public static Salutation createEntity(EntityManager em) {
        Salutation salutation = new Salutation()
            .title(DEFAULT_TITLE)
            .displayOrder(DEFAULT_DISPLAY_ORDER);
        return salutation;
    }

    @Before
    public void initTest() {
        salutation = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalutation() throws Exception {
        int databaseSizeBeforeCreate = salutationRepository.findAll().size();

        // Create the Salutation
        restSalutationMockMvc.perform(post("/api/salutations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salutation)))
            .andExpect(status().isCreated());

        // Validate the Salutation in the database
        List<Salutation> salutationList = salutationRepository.findAll();
        assertThat(salutationList).hasSize(databaseSizeBeforeCreate + 1);
        Salutation testSalutation = salutationList.get(salutationList.size() - 1);
        assertThat(testSalutation.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSalutation.getDisplayOrder()).isEqualTo(DEFAULT_DISPLAY_ORDER);
    }

    @Test
    @Transactional
    public void createSalutationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salutationRepository.findAll().size();

        // Create the Salutation with an existing ID
        salutation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalutationMockMvc.perform(post("/api/salutations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salutation)))
            .andExpect(status().isBadRequest());

        // Validate the Salutation in the database
        List<Salutation> salutationList = salutationRepository.findAll();
        assertThat(salutationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = salutationRepository.findAll().size();
        // set the field null
        salutation.setTitle(null);

        // Create the Salutation, which fails.

        restSalutationMockMvc.perform(post("/api/salutations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salutation)))
            .andExpect(status().isBadRequest());

        List<Salutation> salutationList = salutationRepository.findAll();
        assertThat(salutationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSalutations() throws Exception {
        // Initialize the database
        salutationRepository.saveAndFlush(salutation);

        // Get all the salutationList
        restSalutationMockMvc.perform(get("/api/salutations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salutation.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].displayOrder").value(hasItem(DEFAULT_DISPLAY_ORDER.intValue())));
    }
    
    @Test
    @Transactional
    public void getSalutation() throws Exception {
        // Initialize the database
        salutationRepository.saveAndFlush(salutation);

        // Get the salutation
        restSalutationMockMvc.perform(get("/api/salutations/{id}", salutation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salutation.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.displayOrder").value(DEFAULT_DISPLAY_ORDER.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSalutation() throws Exception {
        // Get the salutation
        restSalutationMockMvc.perform(get("/api/salutations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalutation() throws Exception {
        // Initialize the database
        salutationRepository.saveAndFlush(salutation);

        int databaseSizeBeforeUpdate = salutationRepository.findAll().size();

        // Update the salutation
        Salutation updatedSalutation = salutationRepository.findById(salutation.getId()).get();
        // Disconnect from session so that the updates on updatedSalutation are not directly saved in db
        em.detach(updatedSalutation);
        updatedSalutation
            .title(UPDATED_TITLE)
            .displayOrder(UPDATED_DISPLAY_ORDER);

        restSalutationMockMvc.perform(put("/api/salutations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSalutation)))
            .andExpect(status().isOk());

        // Validate the Salutation in the database
        List<Salutation> salutationList = salutationRepository.findAll();
        assertThat(salutationList).hasSize(databaseSizeBeforeUpdate);
        Salutation testSalutation = salutationList.get(salutationList.size() - 1);
        assertThat(testSalutation.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSalutation.getDisplayOrder()).isEqualTo(UPDATED_DISPLAY_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingSalutation() throws Exception {
        int databaseSizeBeforeUpdate = salutationRepository.findAll().size();

        // Create the Salutation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalutationMockMvc.perform(put("/api/salutations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salutation)))
            .andExpect(status().isBadRequest());

        // Validate the Salutation in the database
        List<Salutation> salutationList = salutationRepository.findAll();
        assertThat(salutationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSalutation() throws Exception {
        // Initialize the database
        salutationRepository.saveAndFlush(salutation);

        int databaseSizeBeforeDelete = salutationRepository.findAll().size();

        // Get the salutation
        restSalutationMockMvc.perform(delete("/api/salutations/{id}", salutation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Salutation> salutationList = salutationRepository.findAll();
        assertThat(salutationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Salutation.class);
        Salutation salutation1 = new Salutation();
        salutation1.setId(1L);
        Salutation salutation2 = new Salutation();
        salutation2.setId(salutation1.getId());
        assertThat(salutation1).isEqualTo(salutation2);
        salutation2.setId(2L);
        assertThat(salutation1).isNotEqualTo(salutation2);
        salutation1.setId(null);
        assertThat(salutation1).isNotEqualTo(salutation2);
    }
}
