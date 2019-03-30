package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.NewsAndEvent;
import com.miu.repository.NewsAndEventRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.miu.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NewsAndEventResource REST controller.
 *
 * @see NewsAndEventResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class NewsAndEventResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE_LINK = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE_LINK = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_VENUE = "AAAAAAAAAA";
    private static final String UPDATED_VENUE = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_DETAIL = "BBBBBBBBBB";

    @Inject
    private NewsAndEventRepository newsAndEventRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restNewsAndEventMockMvc;

    private NewsAndEvent newsAndEvent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NewsAndEventResource newsAndEventResource = new NewsAndEventResource();
        ReflectionTestUtils.setField(newsAndEventResource, "newsAndEventRepository", newsAndEventRepository);
        this.restNewsAndEventMockMvc = MockMvcBuilders.standaloneSetup(newsAndEventResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NewsAndEvent createEntity(EntityManager em) {
        NewsAndEvent newsAndEvent = new NewsAndEvent()
                .title(DEFAULT_TITLE)
                .websiteLink(DEFAULT_WEBSITE_LINK)
                .startDT(DEFAULT_START_DT)
                .endDT(DEFAULT_END_DT)
                .venue(DEFAULT_VENUE)
                .eventDetail(DEFAULT_EVENT_DETAIL);
        return newsAndEvent;
    }

    @Before
    public void initTest() {
        newsAndEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createNewsAndEvent() throws Exception {
        int databaseSizeBeforeCreate = newsAndEventRepository.findAll().size();

        // Create the NewsAndEvent

        restNewsAndEventMockMvc.perform(post("/api/news-and-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newsAndEvent)))
            .andExpect(status().isCreated());

        // Validate the NewsAndEvent in the database
        List<NewsAndEvent> newsAndEventList = newsAndEventRepository.findAll();
        assertThat(newsAndEventList).hasSize(databaseSizeBeforeCreate + 1);
        NewsAndEvent testNewsAndEvent = newsAndEventList.get(newsAndEventList.size() - 1);
        assertThat(testNewsAndEvent.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testNewsAndEvent.getWebsiteLink()).isEqualTo(DEFAULT_WEBSITE_LINK);
        assertThat(testNewsAndEvent.getStartDT()).isEqualTo(DEFAULT_START_DT);
        assertThat(testNewsAndEvent.getEndDT()).isEqualTo(DEFAULT_END_DT);
        assertThat(testNewsAndEvent.getVenue()).isEqualTo(DEFAULT_VENUE);
        assertThat(testNewsAndEvent.getEventDetail()).isEqualTo(DEFAULT_EVENT_DETAIL);
    }

    @Test
    @Transactional
    public void createNewsAndEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = newsAndEventRepository.findAll().size();

        // Create the NewsAndEvent with an existing ID
        NewsAndEvent existingNewsAndEvent = new NewsAndEvent();
        existingNewsAndEvent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNewsAndEventMockMvc.perform(post("/api/news-and-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingNewsAndEvent)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<NewsAndEvent> newsAndEventList = newsAndEventRepository.findAll();
        assertThat(newsAndEventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsAndEventRepository.findAll().size();
        // set the field null
        newsAndEvent.setTitle(null);

        // Create the NewsAndEvent, which fails.

        restNewsAndEventMockMvc.perform(post("/api/news-and-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newsAndEvent)))
            .andExpect(status().isBadRequest());

        List<NewsAndEvent> newsAndEventList = newsAndEventRepository.findAll();
        assertThat(newsAndEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNewsAndEvents() throws Exception {
        // Initialize the database
        newsAndEventRepository.saveAndFlush(newsAndEvent);

        // Get all the newsAndEventList
        restNewsAndEventMockMvc.perform(get("/api/news-and-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(newsAndEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].websiteLink").value(hasItem(DEFAULT_WEBSITE_LINK.toString())))
            .andExpect(jsonPath("$.[*].startDT").value(hasItem(sameInstant(DEFAULT_START_DT))))
            .andExpect(jsonPath("$.[*].endDT").value(hasItem(sameInstant(DEFAULT_END_DT))))
            .andExpect(jsonPath("$.[*].venue").value(hasItem(DEFAULT_VENUE.toString())))
            .andExpect(jsonPath("$.[*].eventDetail").value(hasItem(DEFAULT_EVENT_DETAIL.toString())));
    }

    @Test
    @Transactional
    public void getNewsAndEvent() throws Exception {
        // Initialize the database
        newsAndEventRepository.saveAndFlush(newsAndEvent);

        // Get the newsAndEvent
        restNewsAndEventMockMvc.perform(get("/api/news-and-events/{id}", newsAndEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(newsAndEvent.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.websiteLink").value(DEFAULT_WEBSITE_LINK.toString()))
            .andExpect(jsonPath("$.startDT").value(sameInstant(DEFAULT_START_DT)))
            .andExpect(jsonPath("$.endDT").value(sameInstant(DEFAULT_END_DT)))
            .andExpect(jsonPath("$.venue").value(DEFAULT_VENUE.toString()))
            .andExpect(jsonPath("$.eventDetail").value(DEFAULT_EVENT_DETAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNewsAndEvent() throws Exception {
        // Get the newsAndEvent
        restNewsAndEventMockMvc.perform(get("/api/news-and-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNewsAndEvent() throws Exception {
        // Initialize the database
        newsAndEventRepository.saveAndFlush(newsAndEvent);
        int databaseSizeBeforeUpdate = newsAndEventRepository.findAll().size();

        // Update the newsAndEvent
        NewsAndEvent updatedNewsAndEvent = newsAndEventRepository.findOne(newsAndEvent.getId());
        updatedNewsAndEvent
                .title(UPDATED_TITLE)
                .websiteLink(UPDATED_WEBSITE_LINK)
                .startDT(UPDATED_START_DT)
                .endDT(UPDATED_END_DT)
                .venue(UPDATED_VENUE)
                .eventDetail(UPDATED_EVENT_DETAIL);

        restNewsAndEventMockMvc.perform(put("/api/news-and-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNewsAndEvent)))
            .andExpect(status().isOk());

        // Validate the NewsAndEvent in the database
        List<NewsAndEvent> newsAndEventList = newsAndEventRepository.findAll();
        assertThat(newsAndEventList).hasSize(databaseSizeBeforeUpdate);
        NewsAndEvent testNewsAndEvent = newsAndEventList.get(newsAndEventList.size() - 1);
        assertThat(testNewsAndEvent.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNewsAndEvent.getWebsiteLink()).isEqualTo(UPDATED_WEBSITE_LINK);
        assertThat(testNewsAndEvent.getStartDT()).isEqualTo(UPDATED_START_DT);
        assertThat(testNewsAndEvent.getEndDT()).isEqualTo(UPDATED_END_DT);
        assertThat(testNewsAndEvent.getVenue()).isEqualTo(UPDATED_VENUE);
        assertThat(testNewsAndEvent.getEventDetail()).isEqualTo(UPDATED_EVENT_DETAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingNewsAndEvent() throws Exception {
        int databaseSizeBeforeUpdate = newsAndEventRepository.findAll().size();

        // Create the NewsAndEvent

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNewsAndEventMockMvc.perform(put("/api/news-and-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newsAndEvent)))
            .andExpect(status().isCreated());

        // Validate the NewsAndEvent in the database
        List<NewsAndEvent> newsAndEventList = newsAndEventRepository.findAll();
        assertThat(newsAndEventList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNewsAndEvent() throws Exception {
        // Initialize the database
        newsAndEventRepository.saveAndFlush(newsAndEvent);
        int databaseSizeBeforeDelete = newsAndEventRepository.findAll().size();

        // Get the newsAndEvent
        restNewsAndEventMockMvc.perform(delete("/api/news-and-events/{id}", newsAndEvent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NewsAndEvent> newsAndEventList = newsAndEventRepository.findAll();
        assertThat(newsAndEventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
