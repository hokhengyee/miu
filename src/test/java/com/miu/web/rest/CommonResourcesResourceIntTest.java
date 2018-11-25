package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.CommonResources;
import com.miu.repository.CommonResourcesRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;


import static com.miu.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CommonResourcesResource REST controller.
 *
 * @see CommonResourcesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class CommonResourcesResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_DISPLAY_ORDER = 1L;
    private static final Long UPDATED_DISPLAY_ORDER = 2L;

    private static final String DEFAULT_WEBSITE_LINK = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE_LINK = "BBBBBBBBBB";

    @Autowired
    private CommonResourcesRepository commonResourcesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommonResourcesMockMvc;

    private CommonResources commonResources;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommonResourcesResource commonResourcesResource = new CommonResourcesResource(commonResourcesRepository);
        this.restCommonResourcesMockMvc = MockMvcBuilders.standaloneSetup(commonResourcesResource)
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
    public static CommonResources createEntity(EntityManager em) {
        CommonResources commonResources = new CommonResources()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .contentContentType(DEFAULT_CONTENT_CONTENT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .displayOrder(DEFAULT_DISPLAY_ORDER)
            .websiteLink(DEFAULT_WEBSITE_LINK);
        return commonResources;
    }

    @Before
    public void initTest() {
        commonResources = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommonResources() throws Exception {
        int databaseSizeBeforeCreate = commonResourcesRepository.findAll().size();

        // Create the CommonResources
        restCommonResourcesMockMvc.perform(post("/api/common-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commonResources)))
            .andExpect(status().isCreated());

        // Validate the CommonResources in the database
        List<CommonResources> commonResourcesList = commonResourcesRepository.findAll();
        assertThat(commonResourcesList).hasSize(databaseSizeBeforeCreate + 1);
        CommonResources testCommonResources = commonResourcesList.get(commonResourcesList.size() - 1);
        assertThat(testCommonResources.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCommonResources.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testCommonResources.getContentContentType()).isEqualTo(DEFAULT_CONTENT_CONTENT_TYPE);
        assertThat(testCommonResources.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCommonResources.getDisplayOrder()).isEqualTo(DEFAULT_DISPLAY_ORDER);
        assertThat(testCommonResources.getWebsiteLink()).isEqualTo(DEFAULT_WEBSITE_LINK);
    }

    @Test
    @Transactional
    public void createCommonResourcesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commonResourcesRepository.findAll().size();

        // Create the CommonResources with an existing ID
        commonResources.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommonResourcesMockMvc.perform(post("/api/common-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commonResources)))
            .andExpect(status().isBadRequest());

        // Validate the CommonResources in the database
        List<CommonResources> commonResourcesList = commonResourcesRepository.findAll();
        assertThat(commonResourcesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = commonResourcesRepository.findAll().size();
        // set the field null
        commonResources.setTitle(null);

        // Create the CommonResources, which fails.

        restCommonResourcesMockMvc.perform(post("/api/common-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commonResources)))
            .andExpect(status().isBadRequest());

        List<CommonResources> commonResourcesList = commonResourcesRepository.findAll();
        assertThat(commonResourcesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommonResources() throws Exception {
        // Initialize the database
        commonResourcesRepository.saveAndFlush(commonResources);

        // Get all the commonResourcesList
        restCommonResourcesMockMvc.perform(get("/api/common-resources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commonResources.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].displayOrder").value(hasItem(DEFAULT_DISPLAY_ORDER.intValue())))
            .andExpect(jsonPath("$.[*].websiteLink").value(hasItem(DEFAULT_WEBSITE_LINK.toString())));
    }
    
    @Test
    @Transactional
    public void getCommonResources() throws Exception {
        // Initialize the database
        commonResourcesRepository.saveAndFlush(commonResources);

        // Get the commonResources
        restCommonResourcesMockMvc.perform(get("/api/common-resources/{id}", commonResources.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commonResources.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.contentContentType").value(DEFAULT_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.content").value(Base64Utils.encodeToString(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.displayOrder").value(DEFAULT_DISPLAY_ORDER.intValue()))
            .andExpect(jsonPath("$.websiteLink").value(DEFAULT_WEBSITE_LINK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommonResources() throws Exception {
        // Get the commonResources
        restCommonResourcesMockMvc.perform(get("/api/common-resources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommonResources() throws Exception {
        // Initialize the database
        commonResourcesRepository.saveAndFlush(commonResources);

        int databaseSizeBeforeUpdate = commonResourcesRepository.findAll().size();

        // Update the commonResources
        CommonResources updatedCommonResources = commonResourcesRepository.findById(commonResources.getId()).get();
        // Disconnect from session so that the updates on updatedCommonResources are not directly saved in db
        em.detach(updatedCommonResources);
        updatedCommonResources
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .displayOrder(UPDATED_DISPLAY_ORDER)
            .websiteLink(UPDATED_WEBSITE_LINK);

        restCommonResourcesMockMvc.perform(put("/api/common-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommonResources)))
            .andExpect(status().isOk());

        // Validate the CommonResources in the database
        List<CommonResources> commonResourcesList = commonResourcesRepository.findAll();
        assertThat(commonResourcesList).hasSize(databaseSizeBeforeUpdate);
        CommonResources testCommonResources = commonResourcesList.get(commonResourcesList.size() - 1);
        assertThat(testCommonResources.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCommonResources.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testCommonResources.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
        assertThat(testCommonResources.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCommonResources.getDisplayOrder()).isEqualTo(UPDATED_DISPLAY_ORDER);
        assertThat(testCommonResources.getWebsiteLink()).isEqualTo(UPDATED_WEBSITE_LINK);
    }

    @Test
    @Transactional
    public void updateNonExistingCommonResources() throws Exception {
        int databaseSizeBeforeUpdate = commonResourcesRepository.findAll().size();

        // Create the CommonResources

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommonResourcesMockMvc.perform(put("/api/common-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commonResources)))
            .andExpect(status().isBadRequest());

        // Validate the CommonResources in the database
        List<CommonResources> commonResourcesList = commonResourcesRepository.findAll();
        assertThat(commonResourcesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommonResources() throws Exception {
        // Initialize the database
        commonResourcesRepository.saveAndFlush(commonResources);

        int databaseSizeBeforeDelete = commonResourcesRepository.findAll().size();

        // Get the commonResources
        restCommonResourcesMockMvc.perform(delete("/api/common-resources/{id}", commonResources.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommonResources> commonResourcesList = commonResourcesRepository.findAll();
        assertThat(commonResourcesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonResources.class);
        CommonResources commonResources1 = new CommonResources();
        commonResources1.setId(1L);
        CommonResources commonResources2 = new CommonResources();
        commonResources2.setId(commonResources1.getId());
        assertThat(commonResources1).isEqualTo(commonResources2);
        commonResources2.setId(2L);
        assertThat(commonResources1).isNotEqualTo(commonResources2);
        commonResources1.setId(null);
        assertThat(commonResources1).isNotEqualTo(commonResources2);
    }
}
