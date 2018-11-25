package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.ForumRoomMessage;
import com.miu.repository.ForumRoomMessageRepository;
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
 * Test class for the ForumRoomMessageResource REST controller.
 *
 * @see ForumRoomMessageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class ForumRoomMessageResourceIntTest {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_MESSAGE_DATETIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MESSAGE_DATETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ForumRoomMessageRepository forumRoomMessageRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restForumRoomMessageMockMvc;

    private ForumRoomMessage forumRoomMessage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ForumRoomMessageResource forumRoomMessageResource = new ForumRoomMessageResource(forumRoomMessageRepository);
        this.restForumRoomMessageMockMvc = MockMvcBuilders.standaloneSetup(forumRoomMessageResource)
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
    public static ForumRoomMessage createEntity(EntityManager em) {
        ForumRoomMessage forumRoomMessage = new ForumRoomMessage()
            .message(DEFAULT_MESSAGE)
            .messageDatetime(DEFAULT_MESSAGE_DATETIME);
        return forumRoomMessage;
    }

    @Before
    public void initTest() {
        forumRoomMessage = createEntity(em);
    }

    @Test
    @Transactional
    public void createForumRoomMessage() throws Exception {
        int databaseSizeBeforeCreate = forumRoomMessageRepository.findAll().size();

        // Create the ForumRoomMessage
        restForumRoomMessageMockMvc.perform(post("/api/forum-room-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forumRoomMessage)))
            .andExpect(status().isCreated());

        // Validate the ForumRoomMessage in the database
        List<ForumRoomMessage> forumRoomMessageList = forumRoomMessageRepository.findAll();
        assertThat(forumRoomMessageList).hasSize(databaseSizeBeforeCreate + 1);
        ForumRoomMessage testForumRoomMessage = forumRoomMessageList.get(forumRoomMessageList.size() - 1);
        assertThat(testForumRoomMessage.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testForumRoomMessage.getMessageDatetime()).isEqualTo(DEFAULT_MESSAGE_DATETIME);
    }

    @Test
    @Transactional
    public void createForumRoomMessageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = forumRoomMessageRepository.findAll().size();

        // Create the ForumRoomMessage with an existing ID
        forumRoomMessage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restForumRoomMessageMockMvc.perform(post("/api/forum-room-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forumRoomMessage)))
            .andExpect(status().isBadRequest());

        // Validate the ForumRoomMessage in the database
        List<ForumRoomMessage> forumRoomMessageList = forumRoomMessageRepository.findAll();
        assertThat(forumRoomMessageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = forumRoomMessageRepository.findAll().size();
        // set the field null
        forumRoomMessage.setMessage(null);

        // Create the ForumRoomMessage, which fails.

        restForumRoomMessageMockMvc.perform(post("/api/forum-room-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forumRoomMessage)))
            .andExpect(status().isBadRequest());

        List<ForumRoomMessage> forumRoomMessageList = forumRoomMessageRepository.findAll();
        assertThat(forumRoomMessageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllForumRoomMessages() throws Exception {
        // Initialize the database
        forumRoomMessageRepository.saveAndFlush(forumRoomMessage);

        // Get all the forumRoomMessageList
        restForumRoomMessageMockMvc.perform(get("/api/forum-room-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(forumRoomMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].messageDatetime").value(hasItem(sameInstant(DEFAULT_MESSAGE_DATETIME))));
    }
    
    @Test
    @Transactional
    public void getForumRoomMessage() throws Exception {
        // Initialize the database
        forumRoomMessageRepository.saveAndFlush(forumRoomMessage);

        // Get the forumRoomMessage
        restForumRoomMessageMockMvc.perform(get("/api/forum-room-messages/{id}", forumRoomMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(forumRoomMessage.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.messageDatetime").value(sameInstant(DEFAULT_MESSAGE_DATETIME)));
    }

    @Test
    @Transactional
    public void getNonExistingForumRoomMessage() throws Exception {
        // Get the forumRoomMessage
        restForumRoomMessageMockMvc.perform(get("/api/forum-room-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateForumRoomMessage() throws Exception {
        // Initialize the database
        forumRoomMessageRepository.saveAndFlush(forumRoomMessage);

        int databaseSizeBeforeUpdate = forumRoomMessageRepository.findAll().size();

        // Update the forumRoomMessage
        ForumRoomMessage updatedForumRoomMessage = forumRoomMessageRepository.findById(forumRoomMessage.getId()).get();
        // Disconnect from session so that the updates on updatedForumRoomMessage are not directly saved in db
        em.detach(updatedForumRoomMessage);
        updatedForumRoomMessage
            .message(UPDATED_MESSAGE)
            .messageDatetime(UPDATED_MESSAGE_DATETIME);

        restForumRoomMessageMockMvc.perform(put("/api/forum-room-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedForumRoomMessage)))
            .andExpect(status().isOk());

        // Validate the ForumRoomMessage in the database
        List<ForumRoomMessage> forumRoomMessageList = forumRoomMessageRepository.findAll();
        assertThat(forumRoomMessageList).hasSize(databaseSizeBeforeUpdate);
        ForumRoomMessage testForumRoomMessage = forumRoomMessageList.get(forumRoomMessageList.size() - 1);
        assertThat(testForumRoomMessage.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testForumRoomMessage.getMessageDatetime()).isEqualTo(UPDATED_MESSAGE_DATETIME);
    }

    @Test
    @Transactional
    public void updateNonExistingForumRoomMessage() throws Exception {
        int databaseSizeBeforeUpdate = forumRoomMessageRepository.findAll().size();

        // Create the ForumRoomMessage

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restForumRoomMessageMockMvc.perform(put("/api/forum-room-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forumRoomMessage)))
            .andExpect(status().isBadRequest());

        // Validate the ForumRoomMessage in the database
        List<ForumRoomMessage> forumRoomMessageList = forumRoomMessageRepository.findAll();
        assertThat(forumRoomMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteForumRoomMessage() throws Exception {
        // Initialize the database
        forumRoomMessageRepository.saveAndFlush(forumRoomMessage);

        int databaseSizeBeforeDelete = forumRoomMessageRepository.findAll().size();

        // Get the forumRoomMessage
        restForumRoomMessageMockMvc.perform(delete("/api/forum-room-messages/{id}", forumRoomMessage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ForumRoomMessage> forumRoomMessageList = forumRoomMessageRepository.findAll();
        assertThat(forumRoomMessageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ForumRoomMessage.class);
        ForumRoomMessage forumRoomMessage1 = new ForumRoomMessage();
        forumRoomMessage1.setId(1L);
        ForumRoomMessage forumRoomMessage2 = new ForumRoomMessage();
        forumRoomMessage2.setId(forumRoomMessage1.getId());
        assertThat(forumRoomMessage1).isEqualTo(forumRoomMessage2);
        forumRoomMessage2.setId(2L);
        assertThat(forumRoomMessage1).isNotEqualTo(forumRoomMessage2);
        forumRoomMessage1.setId(null);
        assertThat(forumRoomMessage1).isNotEqualTo(forumRoomMessage2);
    }
}
