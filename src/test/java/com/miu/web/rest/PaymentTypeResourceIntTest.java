package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.PaymentType;
import com.miu.repository.PaymentTypeRepository;

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
 * Test class for the PaymentTypeResource REST controller.
 *
 * @see PaymentTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class PaymentTypeResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    @Inject
    private PaymentTypeRepository paymentTypeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPaymentTypeMockMvc;

    private PaymentType paymentType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PaymentTypeResource paymentTypeResource = new PaymentTypeResource();
        ReflectionTestUtils.setField(paymentTypeResource, "paymentTypeRepository", paymentTypeRepository);
        this.restPaymentTypeMockMvc = MockMvcBuilders.standaloneSetup(paymentTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentType createEntity(EntityManager em) {
        PaymentType paymentType = new PaymentType()
                .title(DEFAULT_TITLE);
        return paymentType;
    }

    @Before
    public void initTest() {
        paymentType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentType() throws Exception {
        int databaseSizeBeforeCreate = paymentTypeRepository.findAll().size();

        // Create the PaymentType

        restPaymentTypeMockMvc.perform(post("/api/payment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentType)))
            .andExpect(status().isCreated());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentType testPaymentType = paymentTypeList.get(paymentTypeList.size() - 1);
        assertThat(testPaymentType.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void createPaymentTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentTypeRepository.findAll().size();

        // Create the PaymentType with an existing ID
        PaymentType existingPaymentType = new PaymentType();
        existingPaymentType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentTypeMockMvc.perform(post("/api/payment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPaymentType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentTypeRepository.findAll().size();
        // set the field null
        paymentType.setTitle(null);

        // Create the PaymentType, which fails.

        restPaymentTypeMockMvc.perform(post("/api/payment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentType)))
            .andExpect(status().isBadRequest());

        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaymentTypes() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        // Get all the paymentTypeList
        restPaymentTypeMockMvc.perform(get("/api/payment-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())));
    }

    @Test
    @Transactional
    public void getPaymentType() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        // Get the paymentType
        restPaymentTypeMockMvc.perform(get("/api/payment-types/{id}", paymentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentType.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentType() throws Exception {
        // Get the paymentType
        restPaymentTypeMockMvc.perform(get("/api/payment-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentType() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);
        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();

        // Update the paymentType
        PaymentType updatedPaymentType = paymentTypeRepository.findOne(paymentType.getId());
        updatedPaymentType
                .title(UPDATED_TITLE);

        restPaymentTypeMockMvc.perform(put("/api/payment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaymentType)))
            .andExpect(status().isOk());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate);
        PaymentType testPaymentType = paymentTypeList.get(paymentTypeList.size() - 1);
        assertThat(testPaymentType.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentType() throws Exception {
        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();

        // Create the PaymentType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPaymentTypeMockMvc.perform(put("/api/payment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentType)))
            .andExpect(status().isCreated());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePaymentType() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);
        int databaseSizeBeforeDelete = paymentTypeRepository.findAll().size();

        // Get the paymentType
        restPaymentTypeMockMvc.perform(delete("/api/payment-types/{id}", paymentType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
