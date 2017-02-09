package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.ExternalOnlineResource;
import com.miu.repository.ExternalOnlineResourceRepository;

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
 * Test class for the ExternalOnlineResourceResource REST controller.
 *
 * @see ExternalOnlineResourceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class ExternalOnlineResourceResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE_LINK = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private ExternalOnlineResourceRepository externalOnlineResourceRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restExternalOnlineResourceMockMvc;

    private ExternalOnlineResource externalOnlineResource;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExternalOnlineResourceResource externalOnlineResourceResource = new ExternalOnlineResourceResource();
        ReflectionTestUtils.setField(externalOnlineResourceResource, "externalOnlineResourceRepository", externalOnlineResourceRepository);
        this.restExternalOnlineResourceMockMvc = MockMvcBuilders.standaloneSetup(externalOnlineResourceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExternalOnlineResource createEntity(EntityManager em) {
        ExternalOnlineResource externalOnlineResource = new ExternalOnlineResource()
                .title(DEFAULT_TITLE)
                .websiteLink(DEFAULT_WEBSITE_LINK)
                .description(DEFAULT_DESCRIPTION);
        return externalOnlineResource;
    }

    @Before
    public void initTest() {
        externalOnlineResource = createEntity(em);
    }

    @Test
    @Transactional
    public void createExternalOnlineResource() throws Exception {
        int databaseSizeBeforeCreate = externalOnlineResourceRepository.findAll().size();

        // Create the ExternalOnlineResource

        restExternalOnlineResourceMockMvc.perform(post("/api/external-online-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalOnlineResource)))
            .andExpect(status().isCreated());

        // Validate the ExternalOnlineResource in the database
        List<ExternalOnlineResource> externalOnlineResourceList = externalOnlineResourceRepository.findAll();
        assertThat(externalOnlineResourceList).hasSize(databaseSizeBeforeCreate + 1);
        ExternalOnlineResource testExternalOnlineResource = externalOnlineResourceList.get(externalOnlineResourceList.size() - 1);
        assertThat(testExternalOnlineResource.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testExternalOnlineResource.getWebsiteLink()).isEqualTo(DEFAULT_WEBSITE_LINK);
        assertThat(testExternalOnlineResource.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createExternalOnlineResourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = externalOnlineResourceRepository.findAll().size();

        // Create the ExternalOnlineResource with an existing ID
        ExternalOnlineResource existingExternalOnlineResource = new ExternalOnlineResource();
        existingExternalOnlineResource.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExternalOnlineResourceMockMvc.perform(post("/api/external-online-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingExternalOnlineResource)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ExternalOnlineResource> externalOnlineResourceList = externalOnlineResourceRepository.findAll();
        assertThat(externalOnlineResourceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = externalOnlineResourceRepository.findAll().size();
        // set the field null
        externalOnlineResource.setTitle(null);

        // Create the ExternalOnlineResource, which fails.

        restExternalOnlineResourceMockMvc.perform(post("/api/external-online-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalOnlineResource)))
            .andExpect(status().isBadRequest());

        List<ExternalOnlineResource> externalOnlineResourceList = externalOnlineResourceRepository.findAll();
        assertThat(externalOnlineResourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExternalOnlineResources() throws Exception {
        // Initialize the database
        externalOnlineResourceRepository.saveAndFlush(externalOnlineResource);

        // Get all the externalOnlineResourceList
        restExternalOnlineResourceMockMvc.perform(get("/api/external-online-resources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(externalOnlineResource.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].websiteLink").value(hasItem(DEFAULT_WEBSITE_LINK.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getExternalOnlineResource() throws Exception {
        // Initialize the database
        externalOnlineResourceRepository.saveAndFlush(externalOnlineResource);

        // Get the externalOnlineResource
        restExternalOnlineResourceMockMvc.perform(get("/api/external-online-resources/{id}", externalOnlineResource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(externalOnlineResource.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.websiteLink").value(DEFAULT_WEBSITE_LINK.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExternalOnlineResource() throws Exception {
        // Get the externalOnlineResource
        restExternalOnlineResourceMockMvc.perform(get("/api/external-online-resources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExternalOnlineResource() throws Exception {
        // Initialize the database
        externalOnlineResourceRepository.saveAndFlush(externalOnlineResource);
        int databaseSizeBeforeUpdate = externalOnlineResourceRepository.findAll().size();

        // Update the externalOnlineResource
        ExternalOnlineResource updatedExternalOnlineResource = externalOnlineResourceRepository.findOne(externalOnlineResource.getId());
        updatedExternalOnlineResource
                .title(UPDATED_TITLE)
                .websiteLink(UPDATED_WEBSITE_LINK)
                .description(UPDATED_DESCRIPTION);

        restExternalOnlineResourceMockMvc.perform(put("/api/external-online-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExternalOnlineResource)))
            .andExpect(status().isOk());

        // Validate the ExternalOnlineResource in the database
        List<ExternalOnlineResource> externalOnlineResourceList = externalOnlineResourceRepository.findAll();
        assertThat(externalOnlineResourceList).hasSize(databaseSizeBeforeUpdate);
        ExternalOnlineResource testExternalOnlineResource = externalOnlineResourceList.get(externalOnlineResourceList.size() - 1);
        assertThat(testExternalOnlineResource.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testExternalOnlineResource.getWebsiteLink()).isEqualTo(UPDATED_WEBSITE_LINK);
        assertThat(testExternalOnlineResource.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingExternalOnlineResource() throws Exception {
        int databaseSizeBeforeUpdate = externalOnlineResourceRepository.findAll().size();

        // Create the ExternalOnlineResource

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExternalOnlineResourceMockMvc.perform(put("/api/external-online-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalOnlineResource)))
            .andExpect(status().isCreated());

        // Validate the ExternalOnlineResource in the database
        List<ExternalOnlineResource> externalOnlineResourceList = externalOnlineResourceRepository.findAll();
        assertThat(externalOnlineResourceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExternalOnlineResource() throws Exception {
        // Initialize the database
        externalOnlineResourceRepository.saveAndFlush(externalOnlineResource);
        int databaseSizeBeforeDelete = externalOnlineResourceRepository.findAll().size();

        // Get the externalOnlineResource
        restExternalOnlineResourceMockMvc.perform(delete("/api/external-online-resources/{id}", externalOnlineResource.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExternalOnlineResource> externalOnlineResourceList = externalOnlineResourceRepository.findAll();
        assertThat(externalOnlineResourceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
