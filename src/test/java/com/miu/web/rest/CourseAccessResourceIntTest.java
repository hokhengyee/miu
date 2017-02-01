package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.CourseAccess;
import com.miu.domain.User;
import com.miu.domain.Course;
import com.miu.repository.CourseAccessRepository;

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
 * Test class for the CourseAccessResource REST controller.
 *
 * @see CourseAccessResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class CourseAccessResourceIntTest {

    @Inject
    private CourseAccessRepository courseAccessRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCourseAccessMockMvc;

    private CourseAccess courseAccess;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CourseAccessResource courseAccessResource = new CourseAccessResource();
        ReflectionTestUtils.setField(courseAccessResource, "courseAccessRepository", courseAccessRepository);
        this.restCourseAccessMockMvc = MockMvcBuilders.standaloneSetup(courseAccessResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseAccess createEntity(EntityManager em) {
        CourseAccess courseAccess = new CourseAccess();
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        courseAccess.setUser(user);
        // Add required entity
        Course course = CourseResourceIntTest.createEntity(em);
        em.persist(course);
        em.flush();
        courseAccess.setCourse(course);
        return courseAccess;
    }

    @Before
    public void initTest() {
        courseAccess = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourseAccess() throws Exception {
        int databaseSizeBeforeCreate = courseAccessRepository.findAll().size();

        // Create the CourseAccess

        restCourseAccessMockMvc.perform(post("/api/course-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseAccess)))
            .andExpect(status().isCreated());

        // Validate the CourseAccess in the database
        List<CourseAccess> courseAccessList = courseAccessRepository.findAll();
        assertThat(courseAccessList).hasSize(databaseSizeBeforeCreate + 1);
        CourseAccess testCourseAccess = courseAccessList.get(courseAccessList.size() - 1);
    }

    @Test
    @Transactional
    public void createCourseAccessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courseAccessRepository.findAll().size();

        // Create the CourseAccess with an existing ID
        CourseAccess existingCourseAccess = new CourseAccess();
        existingCourseAccess.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseAccessMockMvc.perform(post("/api/course-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCourseAccess)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CourseAccess> courseAccessList = courseAccessRepository.findAll();
        assertThat(courseAccessList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCourseAccesses() throws Exception {
        // Initialize the database
        courseAccessRepository.saveAndFlush(courseAccess);

        // Get all the courseAccessList
        restCourseAccessMockMvc.perform(get("/api/course-accesses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseAccess.getId().intValue())));
    }

    @Test
    @Transactional
    public void getCourseAccess() throws Exception {
        // Initialize the database
        courseAccessRepository.saveAndFlush(courseAccess);

        // Get the courseAccess
        restCourseAccessMockMvc.perform(get("/api/course-accesses/{id}", courseAccess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(courseAccess.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCourseAccess() throws Exception {
        // Get the courseAccess
        restCourseAccessMockMvc.perform(get("/api/course-accesses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourseAccess() throws Exception {
        // Initialize the database
        courseAccessRepository.saveAndFlush(courseAccess);
        int databaseSizeBeforeUpdate = courseAccessRepository.findAll().size();

        // Update the courseAccess
        CourseAccess updatedCourseAccess = courseAccessRepository.findOne(courseAccess.getId());

        restCourseAccessMockMvc.perform(put("/api/course-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCourseAccess)))
            .andExpect(status().isOk());

        // Validate the CourseAccess in the database
        List<CourseAccess> courseAccessList = courseAccessRepository.findAll();
        assertThat(courseAccessList).hasSize(databaseSizeBeforeUpdate);
        CourseAccess testCourseAccess = courseAccessList.get(courseAccessList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCourseAccess() throws Exception {
        int databaseSizeBeforeUpdate = courseAccessRepository.findAll().size();

        // Create the CourseAccess

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCourseAccessMockMvc.perform(put("/api/course-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseAccess)))
            .andExpect(status().isCreated());

        // Validate the CourseAccess in the database
        List<CourseAccess> courseAccessList = courseAccessRepository.findAll();
        assertThat(courseAccessList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCourseAccess() throws Exception {
        // Initialize the database
        courseAccessRepository.saveAndFlush(courseAccess);
        int databaseSizeBeforeDelete = courseAccessRepository.findAll().size();

        // Get the courseAccess
        restCourseAccessMockMvc.perform(delete("/api/course-accesses/{id}", courseAccess.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CourseAccess> courseAccessList = courseAccessRepository.findAll();
        assertThat(courseAccessList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
