package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.CourseMaterialAccess;
import com.miu.domain.Course;
import com.miu.domain.CourseMaterial;
import com.miu.repository.CourseMaterialAccessRepository;

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
 * Test class for the CourseMaterialAccessResource REST controller.
 *
 * @see CourseMaterialAccessResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class CourseMaterialAccessResourceIntTest {

    private static final Long DEFAULT_DISPLAY_ORDER = 1L;
    private static final Long UPDATED_DISPLAY_ORDER = 2L;

    @Inject
    private CourseMaterialAccessRepository courseMaterialAccessRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCourseMaterialAccessMockMvc;

    private CourseMaterialAccess courseMaterialAccess;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CourseMaterialAccessResource courseMaterialAccessResource = new CourseMaterialAccessResource();
        ReflectionTestUtils.setField(courseMaterialAccessResource, "courseMaterialAccessRepository", courseMaterialAccessRepository);
        this.restCourseMaterialAccessMockMvc = MockMvcBuilders.standaloneSetup(courseMaterialAccessResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseMaterialAccess createEntity(EntityManager em) {
        CourseMaterialAccess courseMaterialAccess = new CourseMaterialAccess()
                .displayOrder(DEFAULT_DISPLAY_ORDER);
        // Add required entity
        Course course = CourseResourceIntTest.createEntity(em);
        em.persist(course);
        em.flush();
        courseMaterialAccess.setCourse(course);
        // Add required entity
        CourseMaterial courseMaterial = CourseMaterialResourceIntTest.createEntity(em);
        em.persist(courseMaterial);
        em.flush();
        courseMaterialAccess.setCourseMaterial(courseMaterial);
        return courseMaterialAccess;
    }

    @Before
    public void initTest() {
        courseMaterialAccess = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourseMaterialAccess() throws Exception {
        int databaseSizeBeforeCreate = courseMaterialAccessRepository.findAll().size();

        // Create the CourseMaterialAccess

        restCourseMaterialAccessMockMvc.perform(post("/api/course-material-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseMaterialAccess)))
            .andExpect(status().isCreated());

        // Validate the CourseMaterialAccess in the database
        List<CourseMaterialAccess> courseMaterialAccessList = courseMaterialAccessRepository.findAll();
        assertThat(courseMaterialAccessList).hasSize(databaseSizeBeforeCreate + 1);
        CourseMaterialAccess testCourseMaterialAccess = courseMaterialAccessList.get(courseMaterialAccessList.size() - 1);
        assertThat(testCourseMaterialAccess.getDisplayOrder()).isEqualTo(DEFAULT_DISPLAY_ORDER);
    }

    @Test
    @Transactional
    public void createCourseMaterialAccessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courseMaterialAccessRepository.findAll().size();

        // Create the CourseMaterialAccess with an existing ID
        CourseMaterialAccess existingCourseMaterialAccess = new CourseMaterialAccess();
        existingCourseMaterialAccess.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseMaterialAccessMockMvc.perform(post("/api/course-material-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCourseMaterialAccess)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CourseMaterialAccess> courseMaterialAccessList = courseMaterialAccessRepository.findAll();
        assertThat(courseMaterialAccessList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCourseMaterialAccesses() throws Exception {
        // Initialize the database
        courseMaterialAccessRepository.saveAndFlush(courseMaterialAccess);

        // Get all the courseMaterialAccessList
        restCourseMaterialAccessMockMvc.perform(get("/api/course-material-accesses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseMaterialAccess.getId().intValue())))
            .andExpect(jsonPath("$.[*].displayOrder").value(hasItem(DEFAULT_DISPLAY_ORDER.intValue())));
    }

    @Test
    @Transactional
    public void getCourseMaterialAccess() throws Exception {
        // Initialize the database
        courseMaterialAccessRepository.saveAndFlush(courseMaterialAccess);

        // Get the courseMaterialAccess
        restCourseMaterialAccessMockMvc.perform(get("/api/course-material-accesses/{id}", courseMaterialAccess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(courseMaterialAccess.getId().intValue()))
            .andExpect(jsonPath("$.displayOrder").value(DEFAULT_DISPLAY_ORDER.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCourseMaterialAccess() throws Exception {
        // Get the courseMaterialAccess
        restCourseMaterialAccessMockMvc.perform(get("/api/course-material-accesses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourseMaterialAccess() throws Exception {
        // Initialize the database
        courseMaterialAccessRepository.saveAndFlush(courseMaterialAccess);
        int databaseSizeBeforeUpdate = courseMaterialAccessRepository.findAll().size();

        // Update the courseMaterialAccess
        CourseMaterialAccess updatedCourseMaterialAccess = courseMaterialAccessRepository.findOne(courseMaterialAccess.getId());
        updatedCourseMaterialAccess
                .displayOrder(UPDATED_DISPLAY_ORDER);

        restCourseMaterialAccessMockMvc.perform(put("/api/course-material-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCourseMaterialAccess)))
            .andExpect(status().isOk());

        // Validate the CourseMaterialAccess in the database
        List<CourseMaterialAccess> courseMaterialAccessList = courseMaterialAccessRepository.findAll();
        assertThat(courseMaterialAccessList).hasSize(databaseSizeBeforeUpdate);
        CourseMaterialAccess testCourseMaterialAccess = courseMaterialAccessList.get(courseMaterialAccessList.size() - 1);
        assertThat(testCourseMaterialAccess.getDisplayOrder()).isEqualTo(UPDATED_DISPLAY_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingCourseMaterialAccess() throws Exception {
        int databaseSizeBeforeUpdate = courseMaterialAccessRepository.findAll().size();

        // Create the CourseMaterialAccess

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCourseMaterialAccessMockMvc.perform(put("/api/course-material-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseMaterialAccess)))
            .andExpect(status().isCreated());

        // Validate the CourseMaterialAccess in the database
        List<CourseMaterialAccess> courseMaterialAccessList = courseMaterialAccessRepository.findAll();
        assertThat(courseMaterialAccessList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCourseMaterialAccess() throws Exception {
        // Initialize the database
        courseMaterialAccessRepository.saveAndFlush(courseMaterialAccess);
        int databaseSizeBeforeDelete = courseMaterialAccessRepository.findAll().size();

        // Get the courseMaterialAccess
        restCourseMaterialAccessMockMvc.perform(delete("/api/course-material-accesses/{id}", courseMaterialAccess.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CourseMaterialAccess> courseMaterialAccessList = courseMaterialAccessRepository.findAll();
        assertThat(courseMaterialAccessList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
