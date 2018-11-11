package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.StaticPageType;
import com.miu.repository.StaticPageTypeRepository;

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
 * Test class for the StaticPageTypeResource REST controller.
 *
 * @see StaticPageTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class StaticPageTypeResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    @Inject
    private StaticPageTypeRepository staticPageTypeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restStaticPageTypeMockMvc;

    private StaticPageType staticPageType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StaticPageTypeResource staticPageTypeResource = new StaticPageTypeResource();
        ReflectionTestUtils.setField(staticPageTypeResource, "staticPageTypeRepository", staticPageTypeRepository);
        this.restStaticPageTypeMockMvc = MockMvcBuilders.standaloneSetup(staticPageTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticPageType createEntity(EntityManager em) {
        StaticPageType staticPageType = new StaticPageType()
                .title(DEFAULT_TITLE);
        return staticPageType;
    }

    @Before
    public void initTest() {
        staticPageType = createEntity(em);
    }

    @Test
    @Transactional
    public void createStaticPageType() throws Exception {
        int databaseSizeBeforeCreate = staticPageTypeRepository.findAll().size();

        // Create the StaticPageType

        restStaticPageTypeMockMvc.perform(post("/api/static-page-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staticPageType)))
            .andExpect(status().isCreated());

        // Validate the StaticPageType in the database
        List<StaticPageType> staticPageTypeList = staticPageTypeRepository.findAll();
        assertThat(staticPageTypeList).hasSize(databaseSizeBeforeCreate + 1);
        StaticPageType testStaticPageType = staticPageTypeList.get(staticPageTypeList.size() - 1);
        assertThat(testStaticPageType.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void createStaticPageTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = staticPageTypeRepository.findAll().size();

        // Create the StaticPageType with an existing ID
        StaticPageType existingStaticPageType = new StaticPageType();
        existingStaticPageType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaticPageTypeMockMvc.perform(post("/api/static-page-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingStaticPageType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<StaticPageType> staticPageTypeList = staticPageTypeRepository.findAll();
        assertThat(staticPageTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = staticPageTypeRepository.findAll().size();
        // set the field null
        staticPageType.setTitle(null);

        // Create the StaticPageType, which fails.

        restStaticPageTypeMockMvc.perform(post("/api/static-page-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staticPageType)))
            .andExpect(status().isBadRequest());

        List<StaticPageType> staticPageTypeList = staticPageTypeRepository.findAll();
        assertThat(staticPageTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStaticPageTypes() throws Exception {
        // Initialize the database
        staticPageTypeRepository.saveAndFlush(staticPageType);

        // Get all the staticPageTypeList
        restStaticPageTypeMockMvc.perform(get("/api/static-page-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staticPageType.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())));
    }

    @Test
    @Transactional
    public void getStaticPageType() throws Exception {
        // Initialize the database
        staticPageTypeRepository.saveAndFlush(staticPageType);

        // Get the staticPageType
        restStaticPageTypeMockMvc.perform(get("/api/static-page-types/{id}", staticPageType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(staticPageType.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStaticPageType() throws Exception {
        // Get the staticPageType
        restStaticPageTypeMockMvc.perform(get("/api/static-page-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStaticPageType() throws Exception {
        // Initialize the database
        staticPageTypeRepository.saveAndFlush(staticPageType);
        int databaseSizeBeforeUpdate = staticPageTypeRepository.findAll().size();

        // Update the staticPageType
        StaticPageType updatedStaticPageType = staticPageTypeRepository.findOne(staticPageType.getId());
        updatedStaticPageType
                .title(UPDATED_TITLE);

        restStaticPageTypeMockMvc.perform(put("/api/static-page-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStaticPageType)))
            .andExpect(status().isOk());

        // Validate the StaticPageType in the database
        List<StaticPageType> staticPageTypeList = staticPageTypeRepository.findAll();
        assertThat(staticPageTypeList).hasSize(databaseSizeBeforeUpdate);
        StaticPageType testStaticPageType = staticPageTypeList.get(staticPageTypeList.size() - 1);
        assertThat(testStaticPageType.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void updateNonExistingStaticPageType() throws Exception {
        int databaseSizeBeforeUpdate = staticPageTypeRepository.findAll().size();

        // Create the StaticPageType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStaticPageTypeMockMvc.perform(put("/api/static-page-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staticPageType)))
            .andExpect(status().isCreated());

        // Validate the StaticPageType in the database
        List<StaticPageType> staticPageTypeList = staticPageTypeRepository.findAll();
        assertThat(staticPageTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStaticPageType() throws Exception {
        // Initialize the database
        staticPageTypeRepository.saveAndFlush(staticPageType);
        int databaseSizeBeforeDelete = staticPageTypeRepository.findAll().size();

        // Get the staticPageType
        restStaticPageTypeMockMvc.perform(delete("/api/static-page-types/{id}", staticPageType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StaticPageType> staticPageTypeList = staticPageTypeRepository.findAll();
        assertThat(staticPageTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
