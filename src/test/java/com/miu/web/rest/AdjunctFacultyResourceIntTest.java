package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.AdjunctFaculty;
import com.miu.domain.LecturerProfile;
import com.miu.repository.AdjunctFacultyRepository;

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
 * Test class for the AdjunctFacultyResource REST controller.
 *
 * @see AdjunctFacultyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class AdjunctFacultyResourceIntTest {

    private static final Long DEFAULT_SHOW_ORDER = 1L;
    private static final Long UPDATED_SHOW_ORDER = 2L;

    @Inject
    private AdjunctFacultyRepository adjunctFacultyRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAdjunctFacultyMockMvc;

    private AdjunctFaculty adjunctFaculty;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AdjunctFacultyResource adjunctFacultyResource = new AdjunctFacultyResource();
        ReflectionTestUtils.setField(adjunctFacultyResource, "adjunctFacultyRepository", adjunctFacultyRepository);
        this.restAdjunctFacultyMockMvc = MockMvcBuilders.standaloneSetup(adjunctFacultyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdjunctFaculty createEntity(EntityManager em) {
        AdjunctFaculty adjunctFaculty = new AdjunctFaculty()
                .showOrder(DEFAULT_SHOW_ORDER);
        // Add required entity
        LecturerProfile lecturerProfile = LecturerProfileResourceIntTest.createEntity(em);
        em.persist(lecturerProfile);
        em.flush();
        adjunctFaculty.setLecturerProfile(lecturerProfile);
        return adjunctFaculty;
    }

    @Before
    public void initTest() {
        adjunctFaculty = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdjunctFaculty() throws Exception {
        int databaseSizeBeforeCreate = adjunctFacultyRepository.findAll().size();

        // Create the AdjunctFaculty

        restAdjunctFacultyMockMvc.perform(post("/api/adjunct-faculties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adjunctFaculty)))
            .andExpect(status().isCreated());

        // Validate the AdjunctFaculty in the database
        List<AdjunctFaculty> adjunctFacultyList = adjunctFacultyRepository.findAll();
        assertThat(adjunctFacultyList).hasSize(databaseSizeBeforeCreate + 1);
        AdjunctFaculty testAdjunctFaculty = adjunctFacultyList.get(adjunctFacultyList.size() - 1);
        assertThat(testAdjunctFaculty.getShowOrder()).isEqualTo(DEFAULT_SHOW_ORDER);
    }

    @Test
    @Transactional
    public void createAdjunctFacultyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adjunctFacultyRepository.findAll().size();

        // Create the AdjunctFaculty with an existing ID
        AdjunctFaculty existingAdjunctFaculty = new AdjunctFaculty();
        existingAdjunctFaculty.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdjunctFacultyMockMvc.perform(post("/api/adjunct-faculties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAdjunctFaculty)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AdjunctFaculty> adjunctFacultyList = adjunctFacultyRepository.findAll();
        assertThat(adjunctFacultyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAdjunctFaculties() throws Exception {
        // Initialize the database
        adjunctFacultyRepository.saveAndFlush(adjunctFaculty);

        // Get all the adjunctFacultyList
        restAdjunctFacultyMockMvc.perform(get("/api/adjunct-faculties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adjunctFaculty.getId().intValue())))
            .andExpect(jsonPath("$.[*].showOrder").value(hasItem(DEFAULT_SHOW_ORDER.intValue())));
    }

    @Test
    @Transactional
    public void getAdjunctFaculty() throws Exception {
        // Initialize the database
        adjunctFacultyRepository.saveAndFlush(adjunctFaculty);

        // Get the adjunctFaculty
        restAdjunctFacultyMockMvc.perform(get("/api/adjunct-faculties/{id}", adjunctFaculty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adjunctFaculty.getId().intValue()))
            .andExpect(jsonPath("$.showOrder").value(DEFAULT_SHOW_ORDER.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAdjunctFaculty() throws Exception {
        // Get the adjunctFaculty
        restAdjunctFacultyMockMvc.perform(get("/api/adjunct-faculties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdjunctFaculty() throws Exception {
        // Initialize the database
        adjunctFacultyRepository.saveAndFlush(adjunctFaculty);
        int databaseSizeBeforeUpdate = adjunctFacultyRepository.findAll().size();

        // Update the adjunctFaculty
        AdjunctFaculty updatedAdjunctFaculty = adjunctFacultyRepository.findOne(adjunctFaculty.getId());
        updatedAdjunctFaculty
                .showOrder(UPDATED_SHOW_ORDER);

        restAdjunctFacultyMockMvc.perform(put("/api/adjunct-faculties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAdjunctFaculty)))
            .andExpect(status().isOk());

        // Validate the AdjunctFaculty in the database
        List<AdjunctFaculty> adjunctFacultyList = adjunctFacultyRepository.findAll();
        assertThat(adjunctFacultyList).hasSize(databaseSizeBeforeUpdate);
        AdjunctFaculty testAdjunctFaculty = adjunctFacultyList.get(adjunctFacultyList.size() - 1);
        assertThat(testAdjunctFaculty.getShowOrder()).isEqualTo(UPDATED_SHOW_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingAdjunctFaculty() throws Exception {
        int databaseSizeBeforeUpdate = adjunctFacultyRepository.findAll().size();

        // Create the AdjunctFaculty

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdjunctFacultyMockMvc.perform(put("/api/adjunct-faculties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adjunctFaculty)))
            .andExpect(status().isCreated());

        // Validate the AdjunctFaculty in the database
        List<AdjunctFaculty> adjunctFacultyList = adjunctFacultyRepository.findAll();
        assertThat(adjunctFacultyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAdjunctFaculty() throws Exception {
        // Initialize the database
        adjunctFacultyRepository.saveAndFlush(adjunctFaculty);
        int databaseSizeBeforeDelete = adjunctFacultyRepository.findAll().size();

        // Get the adjunctFaculty
        restAdjunctFacultyMockMvc.perform(delete("/api/adjunct-faculties/{id}", adjunctFaculty.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AdjunctFaculty> adjunctFacultyList = adjunctFacultyRepository.findAll();
        assertThat(adjunctFacultyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
