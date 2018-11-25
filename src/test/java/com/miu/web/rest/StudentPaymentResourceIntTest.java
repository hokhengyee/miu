package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.StudentPayment;
import com.miu.domain.User;
import com.miu.domain.Course;
import com.miu.domain.PaymentType;
import com.miu.repository.StudentPaymentRepository;
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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static com.miu.web.rest.TestUtil.sameInstant;
import static com.miu.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StudentPaymentResource REST controller.
 *
 * @see StudentPaymentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class StudentPaymentResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PAYMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_PAID = false;
    private static final Boolean UPDATED_PAID = true;

    @Autowired
    private StudentPaymentRepository studentPaymentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStudentPaymentMockMvc;

    private StudentPayment studentPayment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentPaymentResource studentPaymentResource = new StudentPaymentResource(studentPaymentRepository);
        this.restStudentPaymentMockMvc = MockMvcBuilders.standaloneSetup(studentPaymentResource)
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
    public static StudentPayment createEntity(EntityManager em) {
        StudentPayment studentPayment = new StudentPayment()
            .createdDate(DEFAULT_CREATED_DATE)
            .amount(DEFAULT_AMOUNT)
            .description(DEFAULT_DESCRIPTION)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .paid(DEFAULT_PAID);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        studentPayment.setUser(user);
        // Add required entity
        Course course = CourseResourceIntTest.createEntity(em);
        em.persist(course);
        em.flush();
        studentPayment.setCourse(course);
        // Add required entity
        PaymentType paymentType = PaymentTypeResourceIntTest.createEntity(em);
        em.persist(paymentType);
        em.flush();
        studentPayment.setPaymentType(paymentType);
        return studentPayment;
    }

    @Before
    public void initTest() {
        studentPayment = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentPayment() throws Exception {
        int databaseSizeBeforeCreate = studentPaymentRepository.findAll().size();

        // Create the StudentPayment
        restStudentPaymentMockMvc.perform(post("/api/student-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentPayment)))
            .andExpect(status().isCreated());

        // Validate the StudentPayment in the database
        List<StudentPayment> studentPaymentList = studentPaymentRepository.findAll();
        assertThat(studentPaymentList).hasSize(databaseSizeBeforeCreate + 1);
        StudentPayment testStudentPayment = studentPaymentList.get(studentPaymentList.size() - 1);
        assertThat(testStudentPayment.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testStudentPayment.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testStudentPayment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStudentPayment.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testStudentPayment.isPaid()).isEqualTo(DEFAULT_PAID);
    }

    @Test
    @Transactional
    public void createStudentPaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentPaymentRepository.findAll().size();

        // Create the StudentPayment with an existing ID
        studentPayment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentPaymentMockMvc.perform(post("/api/student-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentPayment)))
            .andExpect(status().isBadRequest());

        // Validate the StudentPayment in the database
        List<StudentPayment> studentPaymentList = studentPaymentRepository.findAll();
        assertThat(studentPaymentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentPaymentRepository.findAll().size();
        // set the field null
        studentPayment.setAmount(null);

        // Create the StudentPayment, which fails.

        restStudentPaymentMockMvc.perform(post("/api/student-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentPayment)))
            .andExpect(status().isBadRequest());

        List<StudentPayment> studentPaymentList = studentPaymentRepository.findAll();
        assertThat(studentPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudentPayments() throws Exception {
        // Initialize the database
        studentPaymentRepository.saveAndFlush(studentPayment);

        // Get all the studentPaymentList
        restStudentPaymentMockMvc.perform(get("/api/student-payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].paid").value(hasItem(DEFAULT_PAID.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getStudentPayment() throws Exception {
        // Initialize the database
        studentPaymentRepository.saveAndFlush(studentPayment);

        // Get the studentPayment
        restStudentPaymentMockMvc.perform(get("/api/student-payments/{id}", studentPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentPayment.getId().intValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.paid").value(DEFAULT_PAID.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentPayment() throws Exception {
        // Get the studentPayment
        restStudentPaymentMockMvc.perform(get("/api/student-payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentPayment() throws Exception {
        // Initialize the database
        studentPaymentRepository.saveAndFlush(studentPayment);

        int databaseSizeBeforeUpdate = studentPaymentRepository.findAll().size();

        // Update the studentPayment
        StudentPayment updatedStudentPayment = studentPaymentRepository.findById(studentPayment.getId()).get();
        // Disconnect from session so that the updates on updatedStudentPayment are not directly saved in db
        em.detach(updatedStudentPayment);
        updatedStudentPayment
            .createdDate(UPDATED_CREATED_DATE)
            .amount(UPDATED_AMOUNT)
            .description(UPDATED_DESCRIPTION)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .paid(UPDATED_PAID);

        restStudentPaymentMockMvc.perform(put("/api/student-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudentPayment)))
            .andExpect(status().isOk());

        // Validate the StudentPayment in the database
        List<StudentPayment> studentPaymentList = studentPaymentRepository.findAll();
        assertThat(studentPaymentList).hasSize(databaseSizeBeforeUpdate);
        StudentPayment testStudentPayment = studentPaymentList.get(studentPaymentList.size() - 1);
        assertThat(testStudentPayment.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testStudentPayment.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testStudentPayment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStudentPayment.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testStudentPayment.isPaid()).isEqualTo(UPDATED_PAID);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentPayment() throws Exception {
        int databaseSizeBeforeUpdate = studentPaymentRepository.findAll().size();

        // Create the StudentPayment

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentPaymentMockMvc.perform(put("/api/student-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentPayment)))
            .andExpect(status().isBadRequest());

        // Validate the StudentPayment in the database
        List<StudentPayment> studentPaymentList = studentPaymentRepository.findAll();
        assertThat(studentPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudentPayment() throws Exception {
        // Initialize the database
        studentPaymentRepository.saveAndFlush(studentPayment);

        int databaseSizeBeforeDelete = studentPaymentRepository.findAll().size();

        // Get the studentPayment
        restStudentPaymentMockMvc.perform(delete("/api/student-payments/{id}", studentPayment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentPayment> studentPaymentList = studentPaymentRepository.findAll();
        assertThat(studentPaymentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentPayment.class);
        StudentPayment studentPayment1 = new StudentPayment();
        studentPayment1.setId(1L);
        StudentPayment studentPayment2 = new StudentPayment();
        studentPayment2.setId(studentPayment1.getId());
        assertThat(studentPayment1).isEqualTo(studentPayment2);
        studentPayment2.setId(2L);
        assertThat(studentPayment1).isNotEqualTo(studentPayment2);
        studentPayment1.setId(null);
        assertThat(studentPayment1).isNotEqualTo(studentPayment2);
    }
}
