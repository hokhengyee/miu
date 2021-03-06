package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.Course;
import com.miu.repository.CourseRepository;

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
 * Test class for the CourseResource REST controller.
 *
 * @see CourseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class CourseResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_COURSE_ORDER = 1L;
    private static final Long UPDATED_COURSE_ORDER = 2L;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Long DEFAULT_CREDIT_HOURS = 1L;
    private static final Long UPDATED_CREDIT_HOURS = 2L;

    private static final String DEFAULT_APPLICATION_FEE = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_FEE = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTRATION_FEE = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATION_FEE = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_FEE = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_FEE = "BBBBBBBBBB";

    @Inject
    private CourseRepository courseRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCourseMockMvc;

    private Course course;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CourseResource courseResource = new CourseResource();
        ReflectionTestUtils.setField(courseResource, "courseRepository", courseRepository);
        this.restCourseMockMvc = MockMvcBuilders.standaloneSetup(courseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Course createEntity(EntityManager em) {
        Course course = new Course()
                .description(DEFAULT_DESCRIPTION)
                .courseOrder(DEFAULT_COURSE_ORDER)
                .title(DEFAULT_TITLE)
                .creditHours(DEFAULT_CREDIT_HOURS)
                .applicationFee(DEFAULT_APPLICATION_FEE)
                .registrationFee(DEFAULT_REGISTRATION_FEE)
                .courseFee(DEFAULT_COURSE_FEE);
        return course;
    }

    @Before
    public void initTest() {
        course = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourse() throws Exception {
        int databaseSizeBeforeCreate = courseRepository.findAll().size();

        // Create the Course

        restCourseMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(course)))
            .andExpect(status().isCreated());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeCreate + 1);
        Course testCourse = courseList.get(courseList.size() - 1);
        assertThat(testCourse.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCourse.getCourseOrder()).isEqualTo(DEFAULT_COURSE_ORDER);
        assertThat(testCourse.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCourse.getCreditHours()).isEqualTo(DEFAULT_CREDIT_HOURS);
        assertThat(testCourse.getApplicationFee()).isEqualTo(DEFAULT_APPLICATION_FEE);
        assertThat(testCourse.getRegistrationFee()).isEqualTo(DEFAULT_REGISTRATION_FEE);
        assertThat(testCourse.getCourseFee()).isEqualTo(DEFAULT_COURSE_FEE);
    }

    @Test
    @Transactional
    public void createCourseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courseRepository.findAll().size();

        // Create the Course with an existing ID
        Course existingCourse = new Course();
        existingCourse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCourse)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseRepository.findAll().size();
        // set the field null
        course.setTitle(null);

        // Create the Course, which fails.

        restCourseMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(course)))
            .andExpect(status().isBadRequest());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditHoursIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseRepository.findAll().size();
        // set the field null
        course.setCreditHours(null);

        // Create the Course, which fails.

        restCourseMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(course)))
            .andExpect(status().isBadRequest());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApplicationFeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseRepository.findAll().size();
        // set the field null
        course.setApplicationFee(null);

        // Create the Course, which fails.

        restCourseMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(course)))
            .andExpect(status().isBadRequest());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegistrationFeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseRepository.findAll().size();
        // set the field null
        course.setRegistrationFee(null);

        // Create the Course, which fails.

        restCourseMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(course)))
            .andExpect(status().isBadRequest());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCourseFeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseRepository.findAll().size();
        // set the field null
        course.setCourseFee(null);

        // Create the Course, which fails.

        restCourseMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(course)))
            .andExpect(status().isBadRequest());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCourses() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList
        restCourseMockMvc.perform(get("/api/courses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(course.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].courseOrder").value(hasItem(DEFAULT_COURSE_ORDER.intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].creditHours").value(hasItem(DEFAULT_CREDIT_HOURS.intValue())))
            .andExpect(jsonPath("$.[*].applicationFee").value(hasItem(DEFAULT_APPLICATION_FEE.toString())))
            .andExpect(jsonPath("$.[*].registrationFee").value(hasItem(DEFAULT_REGISTRATION_FEE.toString())))
            .andExpect(jsonPath("$.[*].courseFee").value(hasItem(DEFAULT_COURSE_FEE.toString())));
    }

    @Test
    @Transactional
    public void getCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get the course
        restCourseMockMvc.perform(get("/api/courses/{id}", course.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(course.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.courseOrder").value(DEFAULT_COURSE_ORDER.intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.creditHours").value(DEFAULT_CREDIT_HOURS.intValue()))
            .andExpect(jsonPath("$.applicationFee").value(DEFAULT_APPLICATION_FEE.toString()))
            .andExpect(jsonPath("$.registrationFee").value(DEFAULT_REGISTRATION_FEE.toString()))
            .andExpect(jsonPath("$.courseFee").value(DEFAULT_COURSE_FEE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCourse() throws Exception {
        // Get the course
        restCourseMockMvc.perform(get("/api/courses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);
        int databaseSizeBeforeUpdate = courseRepository.findAll().size();

        // Update the course
        Course updatedCourse = courseRepository.findOne(course.getId());
        updatedCourse
                .description(UPDATED_DESCRIPTION)
                .courseOrder(UPDATED_COURSE_ORDER)
                .title(UPDATED_TITLE)
                .creditHours(UPDATED_CREDIT_HOURS)
                .applicationFee(UPDATED_APPLICATION_FEE)
                .registrationFee(UPDATED_REGISTRATION_FEE)
                .courseFee(UPDATED_COURSE_FEE);

        restCourseMockMvc.perform(put("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCourse)))
            .andExpect(status().isOk());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
        Course testCourse = courseList.get(courseList.size() - 1);
        assertThat(testCourse.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCourse.getCourseOrder()).isEqualTo(UPDATED_COURSE_ORDER);
        assertThat(testCourse.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCourse.getCreditHours()).isEqualTo(UPDATED_CREDIT_HOURS);
        assertThat(testCourse.getApplicationFee()).isEqualTo(UPDATED_APPLICATION_FEE);
        assertThat(testCourse.getRegistrationFee()).isEqualTo(UPDATED_REGISTRATION_FEE);
        assertThat(testCourse.getCourseFee()).isEqualTo(UPDATED_COURSE_FEE);
    }

    @Test
    @Transactional
    public void updateNonExistingCourse() throws Exception {
        int databaseSizeBeforeUpdate = courseRepository.findAll().size();

        // Create the Course

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCourseMockMvc.perform(put("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(course)))
            .andExpect(status().isCreated());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);
        int databaseSizeBeforeDelete = courseRepository.findAll().size();

        // Get the course
        restCourseMockMvc.perform(delete("/api/courses/{id}", course.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
