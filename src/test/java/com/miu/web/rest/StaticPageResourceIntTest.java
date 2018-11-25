package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.StaticPage;
import com.miu.domain.StaticPageType;
import com.miu.repository.StaticPageRepository;
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
 * Test class for the StaticPageResource REST controller.
 *
 * @see StaticPageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class StaticPageResourceIntTest {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private StaticPageRepository staticPageRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStaticPageMockMvc;

    private StaticPage staticPage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StaticPageResource staticPageResource = new StaticPageResource(staticPageRepository);
        this.restStaticPageMockMvc = MockMvcBuilders.standaloneSetup(staticPageResource)
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
    public static StaticPage createEntity(EntityManager em) {
        StaticPage staticPage = new StaticPage()
            .content(DEFAULT_CONTENT);
        // Add required entity
        StaticPageType staticPageType = StaticPageTypeResourceIntTest.createEntity(em);
        em.persist(staticPageType);
        em.flush();
        staticPage.setStaticPageType(staticPageType);
        return staticPage;
    }

    @Before
    public void initTest() {
        staticPage = createEntity(em);
    }

    @Test
    @Transactional
    public void createStaticPage() throws Exception {
        int databaseSizeBeforeCreate = staticPageRepository.findAll().size();

        // Create the StaticPage
        restStaticPageMockMvc.perform(post("/api/static-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staticPage)))
            .andExpect(status().isCreated());

        // Validate the StaticPage in the database
        List<StaticPage> staticPageList = staticPageRepository.findAll();
        assertThat(staticPageList).hasSize(databaseSizeBeforeCreate + 1);
        StaticPage testStaticPage = staticPageList.get(staticPageList.size() - 1);
        assertThat(testStaticPage.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void createStaticPageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = staticPageRepository.findAll().size();

        // Create the StaticPage with an existing ID
        staticPage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaticPageMockMvc.perform(post("/api/static-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staticPage)))
            .andExpect(status().isBadRequest());

        // Validate the StaticPage in the database
        List<StaticPage> staticPageList = staticPageRepository.findAll();
        assertThat(staticPageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = staticPageRepository.findAll().size();
        // set the field null
        staticPage.setContent(null);

        // Create the StaticPage, which fails.

        restStaticPageMockMvc.perform(post("/api/static-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staticPage)))
            .andExpect(status().isBadRequest());

        List<StaticPage> staticPageList = staticPageRepository.findAll();
        assertThat(staticPageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStaticPages() throws Exception {
        // Initialize the database
        staticPageRepository.saveAndFlush(staticPage);

        // Get all the staticPageList
        restStaticPageMockMvc.perform(get("/api/static-pages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staticPage.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
    }
    
    @Test
    @Transactional
    public void getStaticPage() throws Exception {
        // Initialize the database
        staticPageRepository.saveAndFlush(staticPage);

        // Get the staticPage
        restStaticPageMockMvc.perform(get("/api/static-pages/{id}", staticPage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(staticPage.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStaticPage() throws Exception {
        // Get the staticPage
        restStaticPageMockMvc.perform(get("/api/static-pages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStaticPage() throws Exception {
        // Initialize the database
        staticPageRepository.saveAndFlush(staticPage);

        int databaseSizeBeforeUpdate = staticPageRepository.findAll().size();

        // Update the staticPage
        StaticPage updatedStaticPage = staticPageRepository.findById(staticPage.getId()).get();
        // Disconnect from session so that the updates on updatedStaticPage are not directly saved in db
        em.detach(updatedStaticPage);
        updatedStaticPage
            .content(UPDATED_CONTENT);

        restStaticPageMockMvc.perform(put("/api/static-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStaticPage)))
            .andExpect(status().isOk());

        // Validate the StaticPage in the database
        List<StaticPage> staticPageList = staticPageRepository.findAll();
        assertThat(staticPageList).hasSize(databaseSizeBeforeUpdate);
        StaticPage testStaticPage = staticPageList.get(staticPageList.size() - 1);
        assertThat(testStaticPage.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingStaticPage() throws Exception {
        int databaseSizeBeforeUpdate = staticPageRepository.findAll().size();

        // Create the StaticPage

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaticPageMockMvc.perform(put("/api/static-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staticPage)))
            .andExpect(status().isBadRequest());

        // Validate the StaticPage in the database
        List<StaticPage> staticPageList = staticPageRepository.findAll();
        assertThat(staticPageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStaticPage() throws Exception {
        // Initialize the database
        staticPageRepository.saveAndFlush(staticPage);

        int databaseSizeBeforeDelete = staticPageRepository.findAll().size();

        // Get the staticPage
        restStaticPageMockMvc.perform(delete("/api/static-pages/{id}", staticPage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StaticPage> staticPageList = staticPageRepository.findAll();
        assertThat(staticPageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StaticPage.class);
        StaticPage staticPage1 = new StaticPage();
        staticPage1.setId(1L);
        StaticPage staticPage2 = new StaticPage();
        staticPage2.setId(staticPage1.getId());
        assertThat(staticPage1).isEqualTo(staticPage2);
        staticPage2.setId(2L);
        assertThat(staticPage1).isNotEqualTo(staticPage2);
        staticPage1.setId(null);
        assertThat(staticPage1).isNotEqualTo(staticPage2);
    }
}
