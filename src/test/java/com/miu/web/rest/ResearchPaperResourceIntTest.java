package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.ResearchPaper;
import com.miu.domain.Course;
import com.miu.repository.ResearchPaperRepository;
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
 * Test class for the ResearchPaperResource REST controller.
 *
 * @see ResearchPaperResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class ResearchPaperResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Long DEFAULT_SHOW_ORDER = 1L;
    private static final Long UPDATED_SHOW_ORDER = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ResearchPaperRepository researchPaperRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restResearchPaperMockMvc;

    private ResearchPaper researchPaper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResearchPaperResource researchPaperResource = new ResearchPaperResource(researchPaperRepository);
        this.restResearchPaperMockMvc = MockMvcBuilders.standaloneSetup(researchPaperResource)
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
    public static ResearchPaper createEntity(EntityManager em) {
        ResearchPaper researchPaper = new ResearchPaper()
            .code(DEFAULT_CODE)
            .title(DEFAULT_TITLE)
            .showOrder(DEFAULT_SHOW_ORDER)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        Course course = CourseResourceIntTest.createEntity(em);
        em.persist(course);
        em.flush();
        researchPaper.setCourse(course);
        return researchPaper;
    }

    @Before
    public void initTest() {
        researchPaper = createEntity(em);
    }

    @Test
    @Transactional
    public void createResearchPaper() throws Exception {
        int databaseSizeBeforeCreate = researchPaperRepository.findAll().size();

        // Create the ResearchPaper
        restResearchPaperMockMvc.perform(post("/api/research-papers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(researchPaper)))
            .andExpect(status().isCreated());

        // Validate the ResearchPaper in the database
        List<ResearchPaper> researchPaperList = researchPaperRepository.findAll();
        assertThat(researchPaperList).hasSize(databaseSizeBeforeCreate + 1);
        ResearchPaper testResearchPaper = researchPaperList.get(researchPaperList.size() - 1);
        assertThat(testResearchPaper.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testResearchPaper.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testResearchPaper.getShowOrder()).isEqualTo(DEFAULT_SHOW_ORDER);
        assertThat(testResearchPaper.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createResearchPaperWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = researchPaperRepository.findAll().size();

        // Create the ResearchPaper with an existing ID
        researchPaper.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResearchPaperMockMvc.perform(post("/api/research-papers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(researchPaper)))
            .andExpect(status().isBadRequest());

        // Validate the ResearchPaper in the database
        List<ResearchPaper> researchPaperList = researchPaperRepository.findAll();
        assertThat(researchPaperList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = researchPaperRepository.findAll().size();
        // set the field null
        researchPaper.setCode(null);

        // Create the ResearchPaper, which fails.

        restResearchPaperMockMvc.perform(post("/api/research-papers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(researchPaper)))
            .andExpect(status().isBadRequest());

        List<ResearchPaper> researchPaperList = researchPaperRepository.findAll();
        assertThat(researchPaperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = researchPaperRepository.findAll().size();
        // set the field null
        researchPaper.setTitle(null);

        // Create the ResearchPaper, which fails.

        restResearchPaperMockMvc.perform(post("/api/research-papers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(researchPaper)))
            .andExpect(status().isBadRequest());

        List<ResearchPaper> researchPaperList = researchPaperRepository.findAll();
        assertThat(researchPaperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResearchPapers() throws Exception {
        // Initialize the database
        researchPaperRepository.saveAndFlush(researchPaper);

        // Get all the researchPaperList
        restResearchPaperMockMvc.perform(get("/api/research-papers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(researchPaper.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getResearchPaper() throws Exception {
        // Initialize the database
        researchPaperRepository.saveAndFlush(researchPaper);

        // Get the researchPaper
        restResearchPaperMockMvc.perform(get("/api/research-papers/{id}", researchPaper.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(researchPaper.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.showOrder").value(DEFAULT_SHOW_ORDER.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResearchPaper() throws Exception {
        // Get the researchPaper
        restResearchPaperMockMvc.perform(get("/api/research-papers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResearchPaper() throws Exception {
        // Initialize the database
        researchPaperRepository.saveAndFlush(researchPaper);

        int databaseSizeBeforeUpdate = researchPaperRepository.findAll().size();

        // Update the researchPaper
        ResearchPaper updatedResearchPaper = researchPaperRepository.findById(researchPaper.getId()).get();
        // Disconnect from session so that the updates on updatedResearchPaper are not directly saved in db
        em.detach(updatedResearchPaper);
        updatedResearchPaper
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .showOrder(UPDATED_SHOW_ORDER)
            .description(UPDATED_DESCRIPTION);

        restResearchPaperMockMvc.perform(put("/api/research-papers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResearchPaper)))
            .andExpect(status().isOk());

        // Validate the ResearchPaper in the database
        List<ResearchPaper> researchPaperList = researchPaperRepository.findAll();
        assertThat(researchPaperList).hasSize(databaseSizeBeforeUpdate);
        ResearchPaper testResearchPaper = researchPaperList.get(researchPaperList.size() - 1);
        assertThat(testResearchPaper.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testResearchPaper.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testResearchPaper.getShowOrder()).isEqualTo(UPDATED_SHOW_ORDER);
        assertThat(testResearchPaper.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingResearchPaper() throws Exception {
        int databaseSizeBeforeUpdate = researchPaperRepository.findAll().size();

        // Create the ResearchPaper

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResearchPaperMockMvc.perform(put("/api/research-papers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(researchPaper)))
            .andExpect(status().isBadRequest());

        // Validate the ResearchPaper in the database
        List<ResearchPaper> researchPaperList = researchPaperRepository.findAll();
        assertThat(researchPaperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResearchPaper() throws Exception {
        // Initialize the database
        researchPaperRepository.saveAndFlush(researchPaper);

        int databaseSizeBeforeDelete = researchPaperRepository.findAll().size();

        // Get the researchPaper
        restResearchPaperMockMvc.perform(delete("/api/research-papers/{id}", researchPaper.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ResearchPaper> researchPaperList = researchPaperRepository.findAll();
        assertThat(researchPaperList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResearchPaper.class);
        ResearchPaper researchPaper1 = new ResearchPaper();
        researchPaper1.setId(1L);
        ResearchPaper researchPaper2 = new ResearchPaper();
        researchPaper2.setId(researchPaper1.getId());
        assertThat(researchPaper1).isEqualTo(researchPaper2);
        researchPaper2.setId(2L);
        assertThat(researchPaper1).isNotEqualTo(researchPaper2);
        researchPaper1.setId(null);
        assertThat(researchPaper1).isNotEqualTo(researchPaper2);
    }
}
