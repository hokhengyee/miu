package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.CourseModule;
import com.miu.domain.Course;
import com.miu.domain.Module;
import com.miu.repository.CourseModuleRepository;

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
 * Test class for the CourseModuleResource REST controller.
 *
 * @see CourseModuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class CourseModuleResourceIntTest {

    private static final Long DEFAULT_DISPLAY_ORDER = 1L;
    private static final Long UPDATED_DISPLAY_ORDER = 2L;

    @Inject
    private CourseModuleRepository courseModuleRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCourseModuleMockMvc;

    private CourseModule courseModule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CourseModuleResource courseModuleResource = new CourseModuleResource();
        ReflectionTestUtils.setField(courseModuleResource, "courseModuleRepository", courseModuleRepository);
        this.restCourseModuleMockMvc = MockMvcBuilders.standaloneSetup(courseModuleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseModule createEntity(EntityManager em) {
        CourseModule courseModule = new CourseModule()
                .displayOrder(DEFAULT_DISPLAY_ORDER);
        // Add required entity
        Course course = CourseResourceIntTest.createEntity(em);
        em.persist(course);
        em.flush();
        courseModule.setCourse(course);
        // Add required entity
        Module module = ModuleResourceIntTest.createEntity(em);
        em.persist(module);
        em.flush();
        courseModule.setModule(module);
        return courseModule;
    }

    @Before
    public void initTest() {
        courseModule = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourseModule() throws Exception {
        int databaseSizeBeforeCreate = courseModuleRepository.findAll().size();

        // Create the CourseModule

        restCourseModuleMockMvc.perform(post("/api/course-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseModule)))
            .andExpect(status().isCreated());

        // Validate the CourseModule in the database
        List<CourseModule> courseModuleList = courseModuleRepository.findAll();
        assertThat(courseModuleList).hasSize(databaseSizeBeforeCreate + 1);
        CourseModule testCourseModule = courseModuleList.get(courseModuleList.size() - 1);
        assertThat(testCourseModule.getDisplayOrder()).isEqualTo(DEFAULT_DISPLAY_ORDER);
    }

    @Test
    @Transactional
    public void createCourseModuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courseModuleRepository.findAll().size();

        // Create the CourseModule with an existing ID
        CourseModule existingCourseModule = new CourseModule();
        existingCourseModule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseModuleMockMvc.perform(post("/api/course-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCourseModule)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CourseModule> courseModuleList = courseModuleRepository.findAll();
        assertThat(courseModuleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCourseModules() throws Exception {
        // Initialize the database
        courseModuleRepository.saveAndFlush(courseModule);

        // Get all the courseModuleList
        restCourseModuleMockMvc.perform(get("/api/course-modules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseModule.getId().intValue())))
            .andExpect(jsonPath("$.[*].displayOrder").value(hasItem(DEFAULT_DISPLAY_ORDER.intValue())));
    }

    @Test
    @Transactional
    public void getCourseModule() throws Exception {
        // Initialize the database
        courseModuleRepository.saveAndFlush(courseModule);

        // Get the courseModule
        restCourseModuleMockMvc.perform(get("/api/course-modules/{id}", courseModule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(courseModule.getId().intValue()))
            .andExpect(jsonPath("$.displayOrder").value(DEFAULT_DISPLAY_ORDER.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCourseModule() throws Exception {
        // Get the courseModule
        restCourseModuleMockMvc.perform(get("/api/course-modules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourseModule() throws Exception {
        // Initialize the database
        courseModuleRepository.saveAndFlush(courseModule);
        int databaseSizeBeforeUpdate = courseModuleRepository.findAll().size();

        // Update the courseModule
        CourseModule updatedCourseModule = courseModuleRepository.findOne(courseModule.getId());
        updatedCourseModule
                .displayOrder(UPDATED_DISPLAY_ORDER);

        restCourseModuleMockMvc.perform(put("/api/course-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCourseModule)))
            .andExpect(status().isOk());

        // Validate the CourseModule in the database
        List<CourseModule> courseModuleList = courseModuleRepository.findAll();
        assertThat(courseModuleList).hasSize(databaseSizeBeforeUpdate);
        CourseModule testCourseModule = courseModuleList.get(courseModuleList.size() - 1);
        assertThat(testCourseModule.getDisplayOrder()).isEqualTo(UPDATED_DISPLAY_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingCourseModule() throws Exception {
        int databaseSizeBeforeUpdate = courseModuleRepository.findAll().size();

        // Create the CourseModule

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCourseModuleMockMvc.perform(put("/api/course-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseModule)))
            .andExpect(status().isCreated());

        // Validate the CourseModule in the database
        List<CourseModule> courseModuleList = courseModuleRepository.findAll();
        assertThat(courseModuleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCourseModule() throws Exception {
        // Initialize the database
        courseModuleRepository.saveAndFlush(courseModule);
        int databaseSizeBeforeDelete = courseModuleRepository.findAll().size();

        // Get the courseModule
        restCourseModuleMockMvc.perform(delete("/api/course-modules/{id}", courseModule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CourseModule> courseModuleList = courseModuleRepository.findAll();
        assertThat(courseModuleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
