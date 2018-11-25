package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.PageViewLog;
import com.miu.repository.PageViewLogRepository;

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
 * Test class for the PageViewLogResource REST controller.
 *
 * @see PageViewLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class PageViewLogResourceIntTest {

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_VIEWS = 1L;
    private static final Long UPDATED_VIEWS = 2L;

    @Inject
    private PageViewLogRepository pageViewLogRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPageViewLogMockMvc;

    private PageViewLog pageViewLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PageViewLogResource pageViewLogResource = new PageViewLogResource();
        ReflectionTestUtils.setField(pageViewLogResource, "pageViewLogRepository", pageViewLogRepository);
        this.restPageViewLogMockMvc = MockMvcBuilders.standaloneSetup(pageViewLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageViewLog createEntity(EntityManager em) {
        PageViewLog pageViewLog = new PageViewLog()
                .createdDate(DEFAULT_CREATED_DATE)
                .views(DEFAULT_VIEWS);
        return pageViewLog;
    }

    @Before
    public void initTest() {
        pageViewLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createPageViewLog() throws Exception {
        int databaseSizeBeforeCreate = pageViewLogRepository.findAll().size();

        // Create the PageViewLog

        restPageViewLogMockMvc.perform(post("/api/page-view-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pageViewLog)))
            .andExpect(status().isCreated());

        // Validate the PageViewLog in the database
        List<PageViewLog> pageViewLogList = pageViewLogRepository.findAll();
        assertThat(pageViewLogList).hasSize(databaseSizeBeforeCreate + 1);
        PageViewLog testPageViewLog = pageViewLogList.get(pageViewLogList.size() - 1);
        assertThat(testPageViewLog.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPageViewLog.getViews()).isEqualTo(DEFAULT_VIEWS);
    }

    @Test
    @Transactional
    public void createPageViewLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pageViewLogRepository.findAll().size();

        // Create the PageViewLog with an existing ID
        PageViewLog existingPageViewLog = new PageViewLog();
        existingPageViewLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPageViewLogMockMvc.perform(post("/api/page-view-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPageViewLog)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PageViewLog> pageViewLogList = pageViewLogRepository.findAll();
        assertThat(pageViewLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPageViewLogs() throws Exception {
        // Initialize the database
        pageViewLogRepository.saveAndFlush(pageViewLog);

        // Get all the pageViewLogList
        restPageViewLogMockMvc.perform(get("/api/page-view-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageViewLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].views").value(hasItem(DEFAULT_VIEWS.intValue())));
    }

    @Test
    @Transactional
    public void getPageViewLog() throws Exception {
        // Initialize the database
        pageViewLogRepository.saveAndFlush(pageViewLog);

        // Get the pageViewLog
        restPageViewLogMockMvc.perform(get("/api/page-view-logs/{id}", pageViewLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pageViewLog.getId().intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.views").value(DEFAULT_VIEWS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPageViewLog() throws Exception {
        // Get the pageViewLog
        restPageViewLogMockMvc.perform(get("/api/page-view-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePageViewLog() throws Exception {
        // Initialize the database
        pageViewLogRepository.saveAndFlush(pageViewLog);
        int databaseSizeBeforeUpdate = pageViewLogRepository.findAll().size();

        // Update the pageViewLog
        PageViewLog updatedPageViewLog = pageViewLogRepository.findOne(pageViewLog.getId());
        updatedPageViewLog
                .createdDate(UPDATED_CREATED_DATE)
                .views(UPDATED_VIEWS);

        restPageViewLogMockMvc.perform(put("/api/page-view-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPageViewLog)))
            .andExpect(status().isOk());

        // Validate the PageViewLog in the database
        List<PageViewLog> pageViewLogList = pageViewLogRepository.findAll();
        assertThat(pageViewLogList).hasSize(databaseSizeBeforeUpdate);
        PageViewLog testPageViewLog = pageViewLogList.get(pageViewLogList.size() - 1);
        assertThat(testPageViewLog.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPageViewLog.getViews()).isEqualTo(UPDATED_VIEWS);
    }

    @Test
    @Transactional
    public void updateNonExistingPageViewLog() throws Exception {
        int databaseSizeBeforeUpdate = pageViewLogRepository.findAll().size();

        // Create the PageViewLog

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPageViewLogMockMvc.perform(put("/api/page-view-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pageViewLog)))
            .andExpect(status().isCreated());

        // Validate the PageViewLog in the database
        List<PageViewLog> pageViewLogList = pageViewLogRepository.findAll();
        assertThat(pageViewLogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePageViewLog() throws Exception {
        // Initialize the database
        pageViewLogRepository.saveAndFlush(pageViewLog);
        int databaseSizeBeforeDelete = pageViewLogRepository.findAll().size();

        // Get the pageViewLog
        restPageViewLogMockMvc.perform(delete("/api/page-view-logs/{id}", pageViewLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PageViewLog> pageViewLogList = pageViewLogRepository.findAll();
        assertThat(pageViewLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
