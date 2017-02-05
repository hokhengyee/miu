package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.LecturerProfile;
import com.miu.domain.User;
import com.miu.repository.LecturerProfileRepository;

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
import org.springframework.util.Base64Utils;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LecturerProfileResource REST controller.
 *
 * @see LecturerProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class LecturerProfileResourceIntTest {

    private static final String DEFAULT_OTHER_TITLES = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_TITLES = "BBBBBBBBBB";

    private static final Long DEFAULT_AGE = 1L;
    private static final Long UPDATED_AGE = 2L;

    private static final String DEFAULT_ORDINATION = "AAAAAAAAAA";
    private static final String UPDATED_ORDINATION = "BBBBBBBBBB";

    private static final String DEFAULT_ACADEMIC_HISTORY = "AAAAAAAAAA";
    private static final String UPDATED_ACADEMIC_HISTORY = "BBBBBBBBBB";

    private static final String DEFAULT_PROFESSIONAL_HISTORY = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSIONAL_HISTORY = "BBBBBBBBBB";

    private static final String DEFAULT_PAST_AND_CURRENT_MINISTRY = "AAAAAAAAAA";
    private static final String UPDATED_PAST_AND_CURRENT_MINISTRY = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLICATIONS = "AAAAAAAAAA";
    private static final String UPDATED_PUBLICATIONS = "BBBBBBBBBB";

    private static final String DEFAULT_FAMILY_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_FAMILY_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PROFILE_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PROFILE_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PROFILE_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PROFILE_PHOTO_CONTENT_TYPE = "image/png";

    @Inject
    private LecturerProfileRepository lecturerProfileRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLecturerProfileMockMvc;

    private LecturerProfile lecturerProfile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LecturerProfileResource lecturerProfileResource = new LecturerProfileResource();
        ReflectionTestUtils.setField(lecturerProfileResource, "lecturerProfileRepository", lecturerProfileRepository);
        this.restLecturerProfileMockMvc = MockMvcBuilders.standaloneSetup(lecturerProfileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LecturerProfile createEntity(EntityManager em) {
        LecturerProfile lecturerProfile = new LecturerProfile()
                .otherTitles(DEFAULT_OTHER_TITLES)
                .age(DEFAULT_AGE)
                .ordination(DEFAULT_ORDINATION)
                .academicHistory(DEFAULT_ACADEMIC_HISTORY)
                .professionalHistory(DEFAULT_PROFESSIONAL_HISTORY)
                .pastAndCurrentMinistry(DEFAULT_PAST_AND_CURRENT_MINISTRY)
                .publications(DEFAULT_PUBLICATIONS)
                .familyDetails(DEFAULT_FAMILY_DETAILS)
                .reference(DEFAULT_REFERENCE)
                .profilePhoto(DEFAULT_PROFILE_PHOTO)
                .profilePhotoContentType(DEFAULT_PROFILE_PHOTO_CONTENT_TYPE);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        lecturerProfile.setUser(user);
        return lecturerProfile;
    }

    @Before
    public void initTest() {
        lecturerProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createLecturerProfile() throws Exception {
        int databaseSizeBeforeCreate = lecturerProfileRepository.findAll().size();

        // Create the LecturerProfile

        restLecturerProfileMockMvc.perform(post("/api/lecturer-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturerProfile)))
            .andExpect(status().isCreated());

        // Validate the LecturerProfile in the database
        List<LecturerProfile> lecturerProfileList = lecturerProfileRepository.findAll();
        assertThat(lecturerProfileList).hasSize(databaseSizeBeforeCreate + 1);
        LecturerProfile testLecturerProfile = lecturerProfileList.get(lecturerProfileList.size() - 1);
        assertThat(testLecturerProfile.getOtherTitles()).isEqualTo(DEFAULT_OTHER_TITLES);
        assertThat(testLecturerProfile.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testLecturerProfile.getOrdination()).isEqualTo(DEFAULT_ORDINATION);
        assertThat(testLecturerProfile.getAcademicHistory()).isEqualTo(DEFAULT_ACADEMIC_HISTORY);
        assertThat(testLecturerProfile.getProfessionalHistory()).isEqualTo(DEFAULT_PROFESSIONAL_HISTORY);
        assertThat(testLecturerProfile.getPastAndCurrentMinistry()).isEqualTo(DEFAULT_PAST_AND_CURRENT_MINISTRY);
        assertThat(testLecturerProfile.getPublications()).isEqualTo(DEFAULT_PUBLICATIONS);
        assertThat(testLecturerProfile.getFamilyDetails()).isEqualTo(DEFAULT_FAMILY_DETAILS);
        assertThat(testLecturerProfile.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testLecturerProfile.getProfilePhoto()).isEqualTo(DEFAULT_PROFILE_PHOTO);
        assertThat(testLecturerProfile.getProfilePhotoContentType()).isEqualTo(DEFAULT_PROFILE_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createLecturerProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lecturerProfileRepository.findAll().size();

        // Create the LecturerProfile with an existing ID
        LecturerProfile existingLecturerProfile = new LecturerProfile();
        existingLecturerProfile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLecturerProfileMockMvc.perform(post("/api/lecturer-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLecturerProfile)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LecturerProfile> lecturerProfileList = lecturerProfileRepository.findAll();
        assertThat(lecturerProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLecturerProfiles() throws Exception {
        // Initialize the database
        lecturerProfileRepository.saveAndFlush(lecturerProfile);

        // Get all the lecturerProfileList
        restLecturerProfileMockMvc.perform(get("/api/lecturer-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lecturerProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].otherTitles").value(hasItem(DEFAULT_OTHER_TITLES.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.intValue())))
            .andExpect(jsonPath("$.[*].ordination").value(hasItem(DEFAULT_ORDINATION.toString())))
            .andExpect(jsonPath("$.[*].academicHistory").value(hasItem(DEFAULT_ACADEMIC_HISTORY.toString())))
            .andExpect(jsonPath("$.[*].professionalHistory").value(hasItem(DEFAULT_PROFESSIONAL_HISTORY.toString())))
            .andExpect(jsonPath("$.[*].pastAndCurrentMinistry").value(hasItem(DEFAULT_PAST_AND_CURRENT_MINISTRY.toString())))
            .andExpect(jsonPath("$.[*].publications").value(hasItem(DEFAULT_PUBLICATIONS.toString())))
            .andExpect(jsonPath("$.[*].familyDetails").value(hasItem(DEFAULT_FAMILY_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].profilePhotoContentType").value(hasItem(DEFAULT_PROFILE_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].profilePhoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_PROFILE_PHOTO))));
    }

    @Test
    @Transactional
    public void getLecturerProfile() throws Exception {
        // Initialize the database
        lecturerProfileRepository.saveAndFlush(lecturerProfile);

        // Get the lecturerProfile
        restLecturerProfileMockMvc.perform(get("/api/lecturer-profiles/{id}", lecturerProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lecturerProfile.getId().intValue()))
            .andExpect(jsonPath("$.otherTitles").value(DEFAULT_OTHER_TITLES.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE.intValue()))
            .andExpect(jsonPath("$.ordination").value(DEFAULT_ORDINATION.toString()))
            .andExpect(jsonPath("$.academicHistory").value(DEFAULT_ACADEMIC_HISTORY.toString()))
            .andExpect(jsonPath("$.professionalHistory").value(DEFAULT_PROFESSIONAL_HISTORY.toString()))
            .andExpect(jsonPath("$.pastAndCurrentMinistry").value(DEFAULT_PAST_AND_CURRENT_MINISTRY.toString()))
            .andExpect(jsonPath("$.publications").value(DEFAULT_PUBLICATIONS.toString()))
            .andExpect(jsonPath("$.familyDetails").value(DEFAULT_FAMILY_DETAILS.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.profilePhotoContentType").value(DEFAULT_PROFILE_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.profilePhoto").value(Base64Utils.encodeToString(DEFAULT_PROFILE_PHOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingLecturerProfile() throws Exception {
        // Get the lecturerProfile
        restLecturerProfileMockMvc.perform(get("/api/lecturer-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLecturerProfile() throws Exception {
        // Initialize the database
        lecturerProfileRepository.saveAndFlush(lecturerProfile);
        int databaseSizeBeforeUpdate = lecturerProfileRepository.findAll().size();

        // Update the lecturerProfile
        LecturerProfile updatedLecturerProfile = lecturerProfileRepository.findOne(lecturerProfile.getId());
        updatedLecturerProfile
                .otherTitles(UPDATED_OTHER_TITLES)
                .age(UPDATED_AGE)
                .ordination(UPDATED_ORDINATION)
                .academicHistory(UPDATED_ACADEMIC_HISTORY)
                .professionalHistory(UPDATED_PROFESSIONAL_HISTORY)
                .pastAndCurrentMinistry(UPDATED_PAST_AND_CURRENT_MINISTRY)
                .publications(UPDATED_PUBLICATIONS)
                .familyDetails(UPDATED_FAMILY_DETAILS)
                .reference(UPDATED_REFERENCE)
                .profilePhoto(UPDATED_PROFILE_PHOTO)
                .profilePhotoContentType(UPDATED_PROFILE_PHOTO_CONTENT_TYPE);

        restLecturerProfileMockMvc.perform(put("/api/lecturer-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLecturerProfile)))
            .andExpect(status().isOk());

        // Validate the LecturerProfile in the database
        List<LecturerProfile> lecturerProfileList = lecturerProfileRepository.findAll();
        assertThat(lecturerProfileList).hasSize(databaseSizeBeforeUpdate);
        LecturerProfile testLecturerProfile = lecturerProfileList.get(lecturerProfileList.size() - 1);
        assertThat(testLecturerProfile.getOtherTitles()).isEqualTo(UPDATED_OTHER_TITLES);
        assertThat(testLecturerProfile.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testLecturerProfile.getOrdination()).isEqualTo(UPDATED_ORDINATION);
        assertThat(testLecturerProfile.getAcademicHistory()).isEqualTo(UPDATED_ACADEMIC_HISTORY);
        assertThat(testLecturerProfile.getProfessionalHistory()).isEqualTo(UPDATED_PROFESSIONAL_HISTORY);
        assertThat(testLecturerProfile.getPastAndCurrentMinistry()).isEqualTo(UPDATED_PAST_AND_CURRENT_MINISTRY);
        assertThat(testLecturerProfile.getPublications()).isEqualTo(UPDATED_PUBLICATIONS);
        assertThat(testLecturerProfile.getFamilyDetails()).isEqualTo(UPDATED_FAMILY_DETAILS);
        assertThat(testLecturerProfile.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testLecturerProfile.getProfilePhoto()).isEqualTo(UPDATED_PROFILE_PHOTO);
        assertThat(testLecturerProfile.getProfilePhotoContentType()).isEqualTo(UPDATED_PROFILE_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingLecturerProfile() throws Exception {
        int databaseSizeBeforeUpdate = lecturerProfileRepository.findAll().size();

        // Create the LecturerProfile

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLecturerProfileMockMvc.perform(put("/api/lecturer-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturerProfile)))
            .andExpect(status().isCreated());

        // Validate the LecturerProfile in the database
        List<LecturerProfile> lecturerProfileList = lecturerProfileRepository.findAll();
        assertThat(lecturerProfileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLecturerProfile() throws Exception {
        // Initialize the database
        lecturerProfileRepository.saveAndFlush(lecturerProfile);
        int databaseSizeBeforeDelete = lecturerProfileRepository.findAll().size();

        // Get the lecturerProfile
        restLecturerProfileMockMvc.perform(delete("/api/lecturer-profiles/{id}", lecturerProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LecturerProfile> lecturerProfileList = lecturerProfileRepository.findAll();
        assertThat(lecturerProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
