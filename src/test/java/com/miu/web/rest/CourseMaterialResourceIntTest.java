package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.CourseMaterial;
import com.miu.domain.Course;
import com.miu.repository.CourseMaterialRepository;

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
 * Test class for the CourseMaterialResource REST controller.
 *
 * @see CourseMaterialResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class CourseMaterialResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE_LINK = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE_LINK = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_CONTENT_TYPE = "image/png";

    private static final Long DEFAULT_DISPLAY_ORDER = 1L;
    private static final Long UPDATED_DISPLAY_ORDER = 2L;

    @Inject
    private CourseMaterialRepository courseMaterialRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCourseMaterialMockMvc;

    private CourseMaterial courseMaterial;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CourseMaterialResource courseMaterialResource = new CourseMaterialResource();
        ReflectionTestUtils.setField(courseMaterialResource, "courseMaterialRepository", courseMaterialRepository);
        this.restCourseMaterialMockMvc = MockMvcBuilders.standaloneSetup(courseMaterialResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseMaterial createEntity(EntityManager em) {
        CourseMaterial courseMaterial = new CourseMaterial()
                .title(DEFAULT_TITLE)
                .description(DEFAULT_DESCRIPTION)
                .websiteLink(DEFAULT_WEBSITE_LINK)
                .content(DEFAULT_CONTENT)
                .contentContentType(DEFAULT_CONTENT_CONTENT_TYPE)
                .displayOrder(DEFAULT_DISPLAY_ORDER);
        // Add required entity
        Course course = CourseResourceIntTest.createEntity(em);
        em.persist(course);
        em.flush();
        courseMaterial.setCourse(course);
        return courseMaterial;
    }

    @Before
    public void initTest() {
        courseMaterial = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourseMaterial() throws Exception {
        int databaseSizeBeforeCreate = courseMaterialRepository.findAll().size();

        // Create the CourseMaterial

        restCourseMaterialMockMvc.perform(post("/api/course-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseMaterial)))
            .andExpect(status().isCreated());

        // Validate the CourseMaterial in the database
        List<CourseMaterial> courseMaterialList = courseMaterialRepository.findAll();
        assertThat(courseMaterialList).hasSize(databaseSizeBeforeCreate + 1);
        CourseMaterial testCourseMaterial = courseMaterialList.get(courseMaterialList.size() - 1);
        assertThat(testCourseMaterial.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCourseMaterial.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCourseMaterial.getWebsiteLink()).isEqualTo(DEFAULT_WEBSITE_LINK);
        assertThat(testCourseMaterial.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testCourseMaterial.getContentContentType()).isEqualTo(DEFAULT_CONTENT_CONTENT_TYPE);
        assertThat(testCourseMaterial.getDisplayOrder()).isEqualTo(DEFAULT_DISPLAY_ORDER);
    }

    @Test
    @Transactional
    public void createCourseMaterialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courseMaterialRepository.findAll().size();

        // Create the CourseMaterial with an existing ID
        CourseMaterial existingCourseMaterial = new CourseMaterial();
        existingCourseMaterial.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseMaterialMockMvc.perform(post("/api/course-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCourseMaterial)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CourseMaterial> courseMaterialList = courseMaterialRepository.findAll();
        assertThat(courseMaterialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseMaterialRepository.findAll().size();
        // set the field null
        courseMaterial.setTitle(null);

        // Create the CourseMaterial, which fails.

        restCourseMaterialMockMvc.perform(post("/api/course-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseMaterial)))
            .andExpect(status().isBadRequest());

        List<CourseMaterial> courseMaterialList = courseMaterialRepository.findAll();
        assertThat(courseMaterialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCourseMaterials() throws Exception {
        // Initialize the database
        courseMaterialRepository.saveAndFlush(courseMaterial);

        // Get all the courseMaterialList
        restCourseMaterialMockMvc.perform(get("/api/course-materials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseMaterial.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].websiteLink").value(hasItem(DEFAULT_WEBSITE_LINK.toString())))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].displayOrder").value(hasItem(DEFAULT_DISPLAY_ORDER.intValue())));
    }

    @Test
    @Transactional
    public void getCourseMaterial() throws Exception {
        // Initialize the database
        courseMaterialRepository.saveAndFlush(courseMaterial);

        // Get the courseMaterial
        restCourseMaterialMockMvc.perform(get("/api/course-materials/{id}", courseMaterial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(courseMaterial.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.websiteLink").value(DEFAULT_WEBSITE_LINK.toString()))
            .andExpect(jsonPath("$.contentContentType").value(DEFAULT_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.content").value(Base64Utils.encodeToString(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.displayOrder").value(DEFAULT_DISPLAY_ORDER.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCourseMaterial() throws Exception {
        // Get the courseMaterial
        restCourseMaterialMockMvc.perform(get("/api/course-materials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourseMaterial() throws Exception {
        // Initialize the database
        courseMaterialRepository.saveAndFlush(courseMaterial);
        int databaseSizeBeforeUpdate = courseMaterialRepository.findAll().size();

        // Update the courseMaterial
        CourseMaterial updatedCourseMaterial = courseMaterialRepository.findOne(courseMaterial.getId());
        updatedCourseMaterial
                .title(UPDATED_TITLE)
                .description(UPDATED_DESCRIPTION)
                .websiteLink(UPDATED_WEBSITE_LINK)
                .content(UPDATED_CONTENT)
                .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
                .displayOrder(UPDATED_DISPLAY_ORDER);

        restCourseMaterialMockMvc.perform(put("/api/course-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCourseMaterial)))
            .andExpect(status().isOk());

        // Validate the CourseMaterial in the database
        List<CourseMaterial> courseMaterialList = courseMaterialRepository.findAll();
        assertThat(courseMaterialList).hasSize(databaseSizeBeforeUpdate);
        CourseMaterial testCourseMaterial = courseMaterialList.get(courseMaterialList.size() - 1);
        assertThat(testCourseMaterial.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCourseMaterial.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCourseMaterial.getWebsiteLink()).isEqualTo(UPDATED_WEBSITE_LINK);
        assertThat(testCourseMaterial.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testCourseMaterial.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
        assertThat(testCourseMaterial.getDisplayOrder()).isEqualTo(UPDATED_DISPLAY_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingCourseMaterial() throws Exception {
        int databaseSizeBeforeUpdate = courseMaterialRepository.findAll().size();

        // Create the CourseMaterial

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCourseMaterialMockMvc.perform(put("/api/course-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseMaterial)))
            .andExpect(status().isCreated());

        // Validate the CourseMaterial in the database
        List<CourseMaterial> courseMaterialList = courseMaterialRepository.findAll();
        assertThat(courseMaterialList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCourseMaterial() throws Exception {
        // Initialize the database
        courseMaterialRepository.saveAndFlush(courseMaterial);
        int databaseSizeBeforeDelete = courseMaterialRepository.findAll().size();

        // Get the courseMaterial
        restCourseMaterialMockMvc.perform(delete("/api/course-materials/{id}", courseMaterial.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CourseMaterial> courseMaterialList = courseMaterialRepository.findAll();
        assertThat(courseMaterialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
