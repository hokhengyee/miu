package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.ForumRoom;
import com.miu.repository.ForumRoomRepository;

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
 * Test class for the ForumRoomResource REST controller.
 *
 * @see ForumRoomResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class ForumRoomResourceIntTest {

    private static final String DEFAULT_FORUM_ROOM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FORUM_ROOM_NAME = "BBBBBBBBBB";

    @Inject
    private ForumRoomRepository forumRoomRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restForumRoomMockMvc;

    private ForumRoom forumRoom;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ForumRoomResource forumRoomResource = new ForumRoomResource();
        ReflectionTestUtils.setField(forumRoomResource, "forumRoomRepository", forumRoomRepository);
        this.restForumRoomMockMvc = MockMvcBuilders.standaloneSetup(forumRoomResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ForumRoom createEntity(EntityManager em) {
        ForumRoom forumRoom = new ForumRoom()
                .forumRoomName(DEFAULT_FORUM_ROOM_NAME);
        return forumRoom;
    }

    @Before
    public void initTest() {
        forumRoom = createEntity(em);
    }

    @Test
    @Transactional
    public void createForumRoom() throws Exception {
        int databaseSizeBeforeCreate = forumRoomRepository.findAll().size();

        // Create the ForumRoom

        restForumRoomMockMvc.perform(post("/api/forum-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forumRoom)))
            .andExpect(status().isCreated());

        // Validate the ForumRoom in the database
        List<ForumRoom> forumRoomList = forumRoomRepository.findAll();
        assertThat(forumRoomList).hasSize(databaseSizeBeforeCreate + 1);
        ForumRoom testForumRoom = forumRoomList.get(forumRoomList.size() - 1);
        assertThat(testForumRoom.getForumRoomName()).isEqualTo(DEFAULT_FORUM_ROOM_NAME);
    }

    @Test
    @Transactional
    public void createForumRoomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = forumRoomRepository.findAll().size();

        // Create the ForumRoom with an existing ID
        ForumRoom existingForumRoom = new ForumRoom();
        existingForumRoom.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restForumRoomMockMvc.perform(post("/api/forum-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingForumRoom)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ForumRoom> forumRoomList = forumRoomRepository.findAll();
        assertThat(forumRoomList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkForumRoomNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = forumRoomRepository.findAll().size();
        // set the field null
        forumRoom.setForumRoomName(null);

        // Create the ForumRoom, which fails.

        restForumRoomMockMvc.perform(post("/api/forum-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forumRoom)))
            .andExpect(status().isBadRequest());

        List<ForumRoom> forumRoomList = forumRoomRepository.findAll();
        assertThat(forumRoomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllForumRooms() throws Exception {
        // Initialize the database
        forumRoomRepository.saveAndFlush(forumRoom);

        // Get all the forumRoomList
        restForumRoomMockMvc.perform(get("/api/forum-rooms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(forumRoom.getId().intValue())))
            .andExpect(jsonPath("$.[*].forumRoomName").value(hasItem(DEFAULT_FORUM_ROOM_NAME.toString())));
    }

    @Test
    @Transactional
    public void getForumRoom() throws Exception {
        // Initialize the database
        forumRoomRepository.saveAndFlush(forumRoom);

        // Get the forumRoom
        restForumRoomMockMvc.perform(get("/api/forum-rooms/{id}", forumRoom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(forumRoom.getId().intValue()))
            .andExpect(jsonPath("$.forumRoomName").value(DEFAULT_FORUM_ROOM_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingForumRoom() throws Exception {
        // Get the forumRoom
        restForumRoomMockMvc.perform(get("/api/forum-rooms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateForumRoom() throws Exception {
        // Initialize the database
        forumRoomRepository.saveAndFlush(forumRoom);
        int databaseSizeBeforeUpdate = forumRoomRepository.findAll().size();

        // Update the forumRoom
        ForumRoom updatedForumRoom = forumRoomRepository.findOne(forumRoom.getId());
        updatedForumRoom
                .forumRoomName(UPDATED_FORUM_ROOM_NAME);

        restForumRoomMockMvc.perform(put("/api/forum-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedForumRoom)))
            .andExpect(status().isOk());

        // Validate the ForumRoom in the database
        List<ForumRoom> forumRoomList = forumRoomRepository.findAll();
        assertThat(forumRoomList).hasSize(databaseSizeBeforeUpdate);
        ForumRoom testForumRoom = forumRoomList.get(forumRoomList.size() - 1);
        assertThat(testForumRoom.getForumRoomName()).isEqualTo(UPDATED_FORUM_ROOM_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingForumRoom() throws Exception {
        int databaseSizeBeforeUpdate = forumRoomRepository.findAll().size();

        // Create the ForumRoom

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restForumRoomMockMvc.perform(put("/api/forum-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forumRoom)))
            .andExpect(status().isCreated());

        // Validate the ForumRoom in the database
        List<ForumRoom> forumRoomList = forumRoomRepository.findAll();
        assertThat(forumRoomList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteForumRoom() throws Exception {
        // Initialize the database
        forumRoomRepository.saveAndFlush(forumRoom);
        int databaseSizeBeforeDelete = forumRoomRepository.findAll().size();

        // Get the forumRoom
        restForumRoomMockMvc.perform(delete("/api/forum-rooms/{id}", forumRoom.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ForumRoom> forumRoomList = forumRoomRepository.findAll();
        assertThat(forumRoomList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
