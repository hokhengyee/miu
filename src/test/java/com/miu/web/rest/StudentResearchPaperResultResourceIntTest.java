package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.StudentResearchPaperResult;
import com.miu.domain.ResearchPaper;
import com.miu.domain.User;
import com.miu.repository.StudentResearchPaperResultRepository;
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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.miu.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StudentResearchPaperResultResource REST controller.
 *
 * @see StudentResearchPaperResultResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class StudentResearchPaperResultResourceIntTest {

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_GRADED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_GRADED = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_RESULT_ORDER = 1L;
    private static final Long UPDATED_RESULT_ORDER = 2L;

    @Autowired
    private StudentResearchPaperResultRepository studentResearchPaperResultRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStudentResearchPaperResultMockMvc;

    private StudentResearchPaperResult studentResearchPaperResult;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentResearchPaperResultResource studentResearchPaperResultResource = new StudentResearchPaperResultResource(studentResearchPaperResultRepository);
        this.restStudentResearchPaperResultMockMvc = MockMvcBuilders.standaloneSetup(studentResearchPaperResultResource)
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
    public static StudentResearchPaperResult createEntity(EntityManager em) {
        StudentResearchPaperResult studentResearchPaperResult = new StudentResearchPaperResult()
            .result(DEFAULT_RESULT)
            .dateGraded(DEFAULT_DATE_GRADED)
            .resultOrder(DEFAULT_RESULT_ORDER);
        // Add required entity
        ResearchPaper researchPaper = ResearchPaperResourceIntTest.createEntity(em);
        em.persist(researchPaper);
        em.flush();
        studentResearchPaperResult.setResearchPaper(researchPaper);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        studentResearchPaperResult.setUser(user);
        return studentResearchPaperResult;
    }

    @Before
    public void initTest() {
        studentResearchPaperResult = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentResearchPaperResult() throws Exception {
        int databaseSizeBeforeCreate = studentResearchPaperResultRepository.findAll().size();

        // Create the StudentResearchPaperResult
        restStudentResearchPaperResultMockMvc.perform(post("/api/student-research-paper-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentResearchPaperResult)))
            .andExpect(status().isCreated());

        // Validate the StudentResearchPaperResult in the database
        List<StudentResearchPaperResult> studentResearchPaperResultList = studentResearchPaperResultRepository.findAll();
        assertThat(studentResearchPaperResultList).hasSize(databaseSizeBeforeCreate + 1);
        StudentResearchPaperResult testStudentResearchPaperResult = studentResearchPaperResultList.get(studentResearchPaperResultList.size() - 1);
        assertThat(testStudentResearchPaperResult.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testStudentResearchPaperResult.getDateGraded()).isEqualTo(DEFAULT_DATE_GRADED);
        assertThat(testStudentResearchPaperResult.getResultOrder()).isEqualTo(DEFAULT_RESULT_ORDER);
    }

    @Test
    @Transactional
    public void createStudentResearchPaperResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentResearchPaperResultRepository.findAll().size();

        // Create the StudentResearchPaperResult with an existing ID
        studentResearchPaperResult.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentResearchPaperResultMockMvc.perform(post("/api/student-research-paper-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentResearchPaperResult)))
            .andExpect(status().isBadRequest());

        // Validate the StudentResearchPaperResult in the database
        List<StudentResearchPaperResult> studentResearchPaperResultList = studentResearchPaperResultRepository.findAll();
        assertThat(studentResearchPaperResultList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStudentResearchPaperResults() throws Exception {
        // Initialize the database
        studentResearchPaperResultRepository.saveAndFlush(studentResearchPaperResult);

        // Get all the studentResearchPaperResultList
        restStudentResearchPaperResultMockMvc.perform(get("/api/student-research-paper-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentResearchPaperResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.toString())))
            .andExpect(jsonPath("$.[*].dateGraded").value(hasItem(DEFAULT_DATE_GRADED.toString())))
            .andExpect(jsonPath("$.[*].resultOrder").value(hasItem(DEFAULT_RESULT_ORDER.intValue())));
    }
    
    @Test
    @Transactional
    public void getStudentResearchPaperResult() throws Exception {
        // Initialize the database
        studentResearchPaperResultRepository.saveAndFlush(studentResearchPaperResult);

        // Get the studentResearchPaperResult
        restStudentResearchPaperResultMockMvc.perform(get("/api/student-research-paper-results/{id}", studentResearchPaperResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentResearchPaperResult.getId().intValue()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.toString()))
            .andExpect(jsonPath("$.dateGraded").value(DEFAULT_DATE_GRADED.toString()))
            .andExpect(jsonPath("$.resultOrder").value(DEFAULT_RESULT_ORDER.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentResearchPaperResult() throws Exception {
        // Get the studentResearchPaperResult
        restStudentResearchPaperResultMockMvc.perform(get("/api/student-research-paper-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentResearchPaperResult() throws Exception {
        // Initialize the database
        studentResearchPaperResultRepository.saveAndFlush(studentResearchPaperResult);

        int databaseSizeBeforeUpdate = studentResearchPaperResultRepository.findAll().size();

        // Update the studentResearchPaperResult
        StudentResearchPaperResult updatedStudentResearchPaperResult = studentResearchPaperResultRepository.findById(studentResearchPaperResult.getId()).get();
        // Disconnect from session so that the updates on updatedStudentResearchPaperResult are not directly saved in db
        em.detach(updatedStudentResearchPaperResult);
        updatedStudentResearchPaperResult
            .result(UPDATED_RESULT)
            .dateGraded(UPDATED_DATE_GRADED)
            .resultOrder(UPDATED_RESULT_ORDER);

        restStudentResearchPaperResultMockMvc.perform(put("/api/student-research-paper-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudentResearchPaperResult)))
            .andExpect(status().isOk());

        // Validate the StudentResearchPaperResult in the database
        List<StudentResearchPaperResult> studentResearchPaperResultList = studentResearchPaperResultRepository.findAll();
        assertThat(studentResearchPaperResultList).hasSize(databaseSizeBeforeUpdate);
        StudentResearchPaperResult testStudentResearchPaperResult = studentResearchPaperResultList.get(studentResearchPaperResultList.size() - 1);
        assertThat(testStudentResearchPaperResult.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testStudentResearchPaperResult.getDateGraded()).isEqualTo(UPDATED_DATE_GRADED);
        assertThat(testStudentResearchPaperResult.getResultOrder()).isEqualTo(UPDATED_RESULT_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentResearchPaperResult() throws Exception {
        int databaseSizeBeforeUpdate = studentResearchPaperResultRepository.findAll().size();

        // Create the StudentResearchPaperResult

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentResearchPaperResultMockMvc.perform(put("/api/student-research-paper-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentResearchPaperResult)))
            .andExpect(status().isBadRequest());

        // Validate the StudentResearchPaperResult in the database
        List<StudentResearchPaperResult> studentResearchPaperResultList = studentResearchPaperResultRepository.findAll();
        assertThat(studentResearchPaperResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudentResearchPaperResult() throws Exception {
        // Initialize the database
        studentResearchPaperResultRepository.saveAndFlush(studentResearchPaperResult);

        int databaseSizeBeforeDelete = studentResearchPaperResultRepository.findAll().size();

        // Get the studentResearchPaperResult
        restStudentResearchPaperResultMockMvc.perform(delete("/api/student-research-paper-results/{id}", studentResearchPaperResult.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentResearchPaperResult> studentResearchPaperResultList = studentResearchPaperResultRepository.findAll();
        assertThat(studentResearchPaperResultList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentResearchPaperResult.class);
        StudentResearchPaperResult studentResearchPaperResult1 = new StudentResearchPaperResult();
        studentResearchPaperResult1.setId(1L);
        StudentResearchPaperResult studentResearchPaperResult2 = new StudentResearchPaperResult();
        studentResearchPaperResult2.setId(studentResearchPaperResult1.getId());
        assertThat(studentResearchPaperResult1).isEqualTo(studentResearchPaperResult2);
        studentResearchPaperResult2.setId(2L);
        assertThat(studentResearchPaperResult1).isNotEqualTo(studentResearchPaperResult2);
        studentResearchPaperResult1.setId(null);
        assertThat(studentResearchPaperResult1).isNotEqualTo(studentResearchPaperResult2);
    }
}
