package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.StudentOtherResult;
import com.miu.domain.CustomStudentReportType;
import com.miu.domain.User;
import com.miu.repository.StudentOtherResultRepository;

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
 * Test class for the StudentOtherResultResource REST controller.
 *
 * @see StudentOtherResultResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class StudentOtherResultResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_GRADED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_GRADED = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_RESULT_ORDER = 1L;
    private static final Long UPDATED_RESULT_ORDER = 2L;

    @Inject
    private StudentOtherResultRepository studentOtherResultRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restStudentOtherResultMockMvc;

    private StudentOtherResult studentOtherResult;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StudentOtherResultResource studentOtherResultResource = new StudentOtherResultResource();
        ReflectionTestUtils.setField(studentOtherResultResource, "studentOtherResultRepository", studentOtherResultRepository);
        this.restStudentOtherResultMockMvc = MockMvcBuilders.standaloneSetup(studentOtherResultResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentOtherResult createEntity(EntityManager em) {
        StudentOtherResult studentOtherResult = new StudentOtherResult()
                .code(DEFAULT_CODE)
                .title(DEFAULT_TITLE)
                .result(DEFAULT_RESULT)
                .dateGraded(DEFAULT_DATE_GRADED)
                .resultOrder(DEFAULT_RESULT_ORDER);
        // Add required entity
        CustomStudentReportType customStudentReportType = CustomStudentReportTypeResourceIntTest.createEntity(em);
        em.persist(customStudentReportType);
        em.flush();
        studentOtherResult.setCustomStudentReportType(customStudentReportType);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        studentOtherResult.setUser(user);
        return studentOtherResult;
    }

    @Before
    public void initTest() {
        studentOtherResult = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentOtherResult() throws Exception {
        int databaseSizeBeforeCreate = studentOtherResultRepository.findAll().size();

        // Create the StudentOtherResult

        restStudentOtherResultMockMvc.perform(post("/api/student-other-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentOtherResult)))
            .andExpect(status().isCreated());

        // Validate the StudentOtherResult in the database
        List<StudentOtherResult> studentOtherResultList = studentOtherResultRepository.findAll();
        assertThat(studentOtherResultList).hasSize(databaseSizeBeforeCreate + 1);
        StudentOtherResult testStudentOtherResult = studentOtherResultList.get(studentOtherResultList.size() - 1);
        assertThat(testStudentOtherResult.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testStudentOtherResult.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testStudentOtherResult.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testStudentOtherResult.getDateGraded()).isEqualTo(DEFAULT_DATE_GRADED);
        assertThat(testStudentOtherResult.getResultOrder()).isEqualTo(DEFAULT_RESULT_ORDER);
    }

    @Test
    @Transactional
    public void createStudentOtherResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentOtherResultRepository.findAll().size();

        // Create the StudentOtherResult with an existing ID
        StudentOtherResult existingStudentOtherResult = new StudentOtherResult();
        existingStudentOtherResult.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentOtherResultMockMvc.perform(post("/api/student-other-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingStudentOtherResult)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<StudentOtherResult> studentOtherResultList = studentOtherResultRepository.findAll();
        assertThat(studentOtherResultList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStudentOtherResults() throws Exception {
        // Initialize the database
        studentOtherResultRepository.saveAndFlush(studentOtherResult);

        // Get all the studentOtherResultList
        restStudentOtherResultMockMvc.perform(get("/api/student-other-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentOtherResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.toString())))
            .andExpect(jsonPath("$.[*].dateGraded").value(hasItem(DEFAULT_DATE_GRADED.toString())))
            .andExpect(jsonPath("$.[*].resultOrder").value(hasItem(DEFAULT_RESULT_ORDER.intValue())));
    }

    @Test
    @Transactional
    public void getStudentOtherResult() throws Exception {
        // Initialize the database
        studentOtherResultRepository.saveAndFlush(studentOtherResult);

        // Get the studentOtherResult
        restStudentOtherResultMockMvc.perform(get("/api/student-other-results/{id}", studentOtherResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentOtherResult.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.toString()))
            .andExpect(jsonPath("$.dateGraded").value(DEFAULT_DATE_GRADED.toString()))
            .andExpect(jsonPath("$.resultOrder").value(DEFAULT_RESULT_ORDER.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentOtherResult() throws Exception {
        // Get the studentOtherResult
        restStudentOtherResultMockMvc.perform(get("/api/student-other-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentOtherResult() throws Exception {
        // Initialize the database
        studentOtherResultRepository.saveAndFlush(studentOtherResult);
        int databaseSizeBeforeUpdate = studentOtherResultRepository.findAll().size();

        // Update the studentOtherResult
        StudentOtherResult updatedStudentOtherResult = studentOtherResultRepository.findOne(studentOtherResult.getId());
        updatedStudentOtherResult
                .code(UPDATED_CODE)
                .title(UPDATED_TITLE)
                .result(UPDATED_RESULT)
                .dateGraded(UPDATED_DATE_GRADED)
                .resultOrder(UPDATED_RESULT_ORDER);

        restStudentOtherResultMockMvc.perform(put("/api/student-other-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudentOtherResult)))
            .andExpect(status().isOk());

        // Validate the StudentOtherResult in the database
        List<StudentOtherResult> studentOtherResultList = studentOtherResultRepository.findAll();
        assertThat(studentOtherResultList).hasSize(databaseSizeBeforeUpdate);
        StudentOtherResult testStudentOtherResult = studentOtherResultList.get(studentOtherResultList.size() - 1);
        assertThat(testStudentOtherResult.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testStudentOtherResult.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testStudentOtherResult.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testStudentOtherResult.getDateGraded()).isEqualTo(UPDATED_DATE_GRADED);
        assertThat(testStudentOtherResult.getResultOrder()).isEqualTo(UPDATED_RESULT_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentOtherResult() throws Exception {
        int databaseSizeBeforeUpdate = studentOtherResultRepository.findAll().size();

        // Create the StudentOtherResult

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStudentOtherResultMockMvc.perform(put("/api/student-other-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentOtherResult)))
            .andExpect(status().isCreated());

        // Validate the StudentOtherResult in the database
        List<StudentOtherResult> studentOtherResultList = studentOtherResultRepository.findAll();
        assertThat(studentOtherResultList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStudentOtherResult() throws Exception {
        // Initialize the database
        studentOtherResultRepository.saveAndFlush(studentOtherResult);
        int databaseSizeBeforeDelete = studentOtherResultRepository.findAll().size();

        // Get the studentOtherResult
        restStudentOtherResultMockMvc.perform(delete("/api/student-other-results/{id}", studentOtherResult.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentOtherResult> studentOtherResultList = studentOtherResultRepository.findAll();
        assertThat(studentOtherResultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
