package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.StudentModuleResult;
import com.miu.domain.User;
import com.miu.domain.Module;
import com.miu.repository.StudentModuleResultRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StudentModuleResultResource REST controller.
 *
 * @see StudentModuleResultResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class StudentModuleResultResourceIntTest {

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_GRADED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_GRADED = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_RESULT_ORDER = 1L;
    private static final Long UPDATED_RESULT_ORDER = 2L;

    @Inject
    private StudentModuleResultRepository studentModuleResultRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restStudentModuleResultMockMvc;

    private StudentModuleResult studentModuleResult;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StudentModuleResultResource studentModuleResultResource = new StudentModuleResultResource();
        ReflectionTestUtils.setField(studentModuleResultResource, "studentModuleResultRepository", studentModuleResultRepository);
        this.restStudentModuleResultMockMvc = MockMvcBuilders.standaloneSetup(studentModuleResultResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentModuleResult createEntity(EntityManager em) {
        StudentModuleResult studentModuleResult = new StudentModuleResult()
                .result(DEFAULT_RESULT)
                .dateGraded(DEFAULT_DATE_GRADED)
                .resultOrder(DEFAULT_RESULT_ORDER);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        studentModuleResult.setUser(user);
        // Add required entity
        Module module = ModuleResourceIntTest.createEntity(em);
        em.persist(module);
        em.flush();
        studentModuleResult.setModule(module);
        return studentModuleResult;
    }

    @Before
    public void initTest() {
        studentModuleResult = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentModuleResult() throws Exception {
        int databaseSizeBeforeCreate = studentModuleResultRepository.findAll().size();

        // Create the StudentModuleResult

        restStudentModuleResultMockMvc.perform(post("/api/student-module-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleResult)))
            .andExpect(status().isCreated());

        // Validate the StudentModuleResult in the database
        List<StudentModuleResult> studentModuleResultList = studentModuleResultRepository.findAll();
        assertThat(studentModuleResultList).hasSize(databaseSizeBeforeCreate + 1);
        StudentModuleResult testStudentModuleResult = studentModuleResultList.get(studentModuleResultList.size() - 1);
        assertThat(testStudentModuleResult.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testStudentModuleResult.getDateGraded()).isEqualTo(DEFAULT_DATE_GRADED);
        assertThat(testStudentModuleResult.getResultOrder()).isEqualTo(DEFAULT_RESULT_ORDER);
    }

    @Test
    @Transactional
    public void createStudentModuleResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentModuleResultRepository.findAll().size();

        // Create the StudentModuleResult with an existing ID
        StudentModuleResult existingStudentModuleResult = new StudentModuleResult();
        existingStudentModuleResult.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentModuleResultMockMvc.perform(post("/api/student-module-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingStudentModuleResult)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<StudentModuleResult> studentModuleResultList = studentModuleResultRepository.findAll();
        assertThat(studentModuleResultList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStudentModuleResults() throws Exception {
        // Initialize the database
        studentModuleResultRepository.saveAndFlush(studentModuleResult);

        // Get all the studentModuleResultList
        restStudentModuleResultMockMvc.perform(get("/api/student-module-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentModuleResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.toString())))
            .andExpect(jsonPath("$.[*].dateGraded").value(hasItem(DEFAULT_DATE_GRADED.toString())))
            .andExpect(jsonPath("$.[*].resultOrder").value(hasItem(DEFAULT_RESULT_ORDER.intValue())));
    }

    @Test
    @Transactional
    public void getStudentModuleResult() throws Exception {
        // Initialize the database
        studentModuleResultRepository.saveAndFlush(studentModuleResult);

        // Get the studentModuleResult
        restStudentModuleResultMockMvc.perform(get("/api/student-module-results/{id}", studentModuleResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentModuleResult.getId().intValue()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.toString()))
            .andExpect(jsonPath("$.dateGraded").value(DEFAULT_DATE_GRADED.toString()))
            .andExpect(jsonPath("$.resultOrder").value(DEFAULT_RESULT_ORDER.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentModuleResult() throws Exception {
        // Get the studentModuleResult
        restStudentModuleResultMockMvc.perform(get("/api/student-module-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentModuleResult() throws Exception {
        // Initialize the database
        studentModuleResultRepository.saveAndFlush(studentModuleResult);
        int databaseSizeBeforeUpdate = studentModuleResultRepository.findAll().size();

        // Update the studentModuleResult
        StudentModuleResult updatedStudentModuleResult = studentModuleResultRepository.findOne(studentModuleResult.getId());
        updatedStudentModuleResult
                .result(UPDATED_RESULT)
                .dateGraded(UPDATED_DATE_GRADED)
                .resultOrder(UPDATED_RESULT_ORDER);

        restStudentModuleResultMockMvc.perform(put("/api/student-module-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudentModuleResult)))
            .andExpect(status().isOk());

        // Validate the StudentModuleResult in the database
        List<StudentModuleResult> studentModuleResultList = studentModuleResultRepository.findAll();
        assertThat(studentModuleResultList).hasSize(databaseSizeBeforeUpdate);
        StudentModuleResult testStudentModuleResult = studentModuleResultList.get(studentModuleResultList.size() - 1);
        assertThat(testStudentModuleResult.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testStudentModuleResult.getDateGraded()).isEqualTo(UPDATED_DATE_GRADED);
        assertThat(testStudentModuleResult.getResultOrder()).isEqualTo(UPDATED_RESULT_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentModuleResult() throws Exception {
        int databaseSizeBeforeUpdate = studentModuleResultRepository.findAll().size();

        // Create the StudentModuleResult

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStudentModuleResultMockMvc.perform(put("/api/student-module-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleResult)))
            .andExpect(status().isCreated());

        // Validate the StudentModuleResult in the database
        List<StudentModuleResult> studentModuleResultList = studentModuleResultRepository.findAll();
        assertThat(studentModuleResultList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStudentModuleResult() throws Exception {
        // Initialize the database
        studentModuleResultRepository.saveAndFlush(studentModuleResult);
        int databaseSizeBeforeDelete = studentModuleResultRepository.findAll().size();

        // Get the studentModuleResult
        restStudentModuleResultMockMvc.perform(delete("/api/student-module-results/{id}", studentModuleResult.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentModuleResult> studentModuleResultList = studentModuleResultRepository.findAll();
        assertThat(studentModuleResultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
