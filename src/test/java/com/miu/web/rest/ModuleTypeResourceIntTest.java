package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.ModuleType;
import com.miu.repository.ModuleTypeRepository;

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
 * Test class for the ModuleTypeResource REST controller.
 *
 * @see ModuleTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class ModuleTypeResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_MODULE_TYPE_ORDER = 1L;
    private static final Long UPDATED_MODULE_TYPE_ORDER = 2L;

    @Inject
    private ModuleTypeRepository moduleTypeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restModuleTypeMockMvc;

    private ModuleType moduleType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ModuleTypeResource moduleTypeResource = new ModuleTypeResource();
        ReflectionTestUtils.setField(moduleTypeResource, "moduleTypeRepository", moduleTypeRepository);
        this.restModuleTypeMockMvc = MockMvcBuilders.standaloneSetup(moduleTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModuleType createEntity(EntityManager em) {
        ModuleType moduleType = new ModuleType()
                .title(DEFAULT_TITLE)
                .description(DEFAULT_DESCRIPTION)
                .moduleTypeOrder(DEFAULT_MODULE_TYPE_ORDER);
        return moduleType;
    }

    @Before
    public void initTest() {
        moduleType = createEntity(em);
    }

    @Test
    @Transactional
    public void createModuleType() throws Exception {
        int databaseSizeBeforeCreate = moduleTypeRepository.findAll().size();

        // Create the ModuleType

        restModuleTypeMockMvc.perform(post("/api/module-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleType)))
            .andExpect(status().isCreated());

        // Validate the ModuleType in the database
        List<ModuleType> moduleTypeList = moduleTypeRepository.findAll();
        assertThat(moduleTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ModuleType testModuleType = moduleTypeList.get(moduleTypeList.size() - 1);
        assertThat(testModuleType.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testModuleType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testModuleType.getModuleTypeOrder()).isEqualTo(DEFAULT_MODULE_TYPE_ORDER);
    }

    @Test
    @Transactional
    public void createModuleTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moduleTypeRepository.findAll().size();

        // Create the ModuleType with an existing ID
        ModuleType existingModuleType = new ModuleType();
        existingModuleType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModuleTypeMockMvc.perform(post("/api/module-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingModuleType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ModuleType> moduleTypeList = moduleTypeRepository.findAll();
        assertThat(moduleTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = moduleTypeRepository.findAll().size();
        // set the field null
        moduleType.setTitle(null);

        // Create the ModuleType, which fails.

        restModuleTypeMockMvc.perform(post("/api/module-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleType)))
            .andExpect(status().isBadRequest());

        List<ModuleType> moduleTypeList = moduleTypeRepository.findAll();
        assertThat(moduleTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModuleTypeOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = moduleTypeRepository.findAll().size();
        // set the field null
        moduleType.setModuleTypeOrder(null);

        // Create the ModuleType, which fails.

        restModuleTypeMockMvc.perform(post("/api/module-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleType)))
            .andExpect(status().isBadRequest());

        List<ModuleType> moduleTypeList = moduleTypeRepository.findAll();
        assertThat(moduleTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllModuleTypes() throws Exception {
        // Initialize the database
        moduleTypeRepository.saveAndFlush(moduleType);

        // Get all the moduleTypeList
        restModuleTypeMockMvc.perform(get("/api/module-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moduleType.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].moduleTypeOrder").value(hasItem(DEFAULT_MODULE_TYPE_ORDER.intValue())));
    }

    @Test
    @Transactional
    public void getModuleType() throws Exception {
        // Initialize the database
        moduleTypeRepository.saveAndFlush(moduleType);

        // Get the moduleType
        restModuleTypeMockMvc.perform(get("/api/module-types/{id}", moduleType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(moduleType.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.moduleTypeOrder").value(DEFAULT_MODULE_TYPE_ORDER.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingModuleType() throws Exception {
        // Get the moduleType
        restModuleTypeMockMvc.perform(get("/api/module-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModuleType() throws Exception {
        // Initialize the database
        moduleTypeRepository.saveAndFlush(moduleType);
        int databaseSizeBeforeUpdate = moduleTypeRepository.findAll().size();

        // Update the moduleType
        ModuleType updatedModuleType = moduleTypeRepository.findOne(moduleType.getId());
        updatedModuleType
                .title(UPDATED_TITLE)
                .description(UPDATED_DESCRIPTION)
                .moduleTypeOrder(UPDATED_MODULE_TYPE_ORDER);

        restModuleTypeMockMvc.perform(put("/api/module-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedModuleType)))
            .andExpect(status().isOk());

        // Validate the ModuleType in the database
        List<ModuleType> moduleTypeList = moduleTypeRepository.findAll();
        assertThat(moduleTypeList).hasSize(databaseSizeBeforeUpdate);
        ModuleType testModuleType = moduleTypeList.get(moduleTypeList.size() - 1);
        assertThat(testModuleType.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testModuleType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testModuleType.getModuleTypeOrder()).isEqualTo(UPDATED_MODULE_TYPE_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingModuleType() throws Exception {
        int databaseSizeBeforeUpdate = moduleTypeRepository.findAll().size();

        // Create the ModuleType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restModuleTypeMockMvc.perform(put("/api/module-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleType)))
            .andExpect(status().isCreated());

        // Validate the ModuleType in the database
        List<ModuleType> moduleTypeList = moduleTypeRepository.findAll();
        assertThat(moduleTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteModuleType() throws Exception {
        // Initialize the database
        moduleTypeRepository.saveAndFlush(moduleType);
        int databaseSizeBeforeDelete = moduleTypeRepository.findAll().size();

        // Get the moduleType
        restModuleTypeMockMvc.perform(delete("/api/module-types/{id}", moduleType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ModuleType> moduleTypeList = moduleTypeRepository.findAll();
        assertThat(moduleTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
