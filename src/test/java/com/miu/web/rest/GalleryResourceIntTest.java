package com.miu.web.rest;

import com.miu.MiuApp;

import com.miu.domain.Gallery;
import com.miu.repository.GalleryRepository;

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
 * Test class for the GalleryResource REST controller.
 *
 * @see GalleryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiuApp.class)
public class GalleryResourceIntTest {

    private static final String DEFAULT_IMAGE_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_TITLE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_GALLERY_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_GALLERY_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_GALLERY_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_GALLERY_PHOTO_CONTENT_TYPE = "image/png";

    @Inject
    private GalleryRepository galleryRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restGalleryMockMvc;

    private Gallery gallery;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GalleryResource galleryResource = new GalleryResource();
        ReflectionTestUtils.setField(galleryResource, "galleryRepository", galleryRepository);
        this.restGalleryMockMvc = MockMvcBuilders.standaloneSetup(galleryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gallery createEntity(EntityManager em) {
        Gallery gallery = new Gallery()
                .imageTitle(DEFAULT_IMAGE_TITLE)
                .galleryPhoto(DEFAULT_GALLERY_PHOTO)
                .galleryPhotoContentType(DEFAULT_GALLERY_PHOTO_CONTENT_TYPE);
        return gallery;
    }

    @Before
    public void initTest() {
        gallery = createEntity(em);
    }

    @Test
    @Transactional
    public void createGallery() throws Exception {
        int databaseSizeBeforeCreate = galleryRepository.findAll().size();

        // Create the Gallery

        restGalleryMockMvc.perform(post("/api/galleries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gallery)))
            .andExpect(status().isCreated());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeCreate + 1);
        Gallery testGallery = galleryList.get(galleryList.size() - 1);
        assertThat(testGallery.getImageTitle()).isEqualTo(DEFAULT_IMAGE_TITLE);
        assertThat(testGallery.getGalleryPhoto()).isEqualTo(DEFAULT_GALLERY_PHOTO);
        assertThat(testGallery.getGalleryPhotoContentType()).isEqualTo(DEFAULT_GALLERY_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createGalleryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = galleryRepository.findAll().size();

        // Create the Gallery with an existing ID
        Gallery existingGallery = new Gallery();
        existingGallery.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGalleryMockMvc.perform(post("/api/galleries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingGallery)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkGalleryPhotoIsRequired() throws Exception {
        int databaseSizeBeforeTest = galleryRepository.findAll().size();
        // set the field null
        gallery.setGalleryPhoto(null);

        // Create the Gallery, which fails.

        restGalleryMockMvc.perform(post("/api/galleries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gallery)))
            .andExpect(status().isBadRequest());

        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGalleries() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList
        restGalleryMockMvc.perform(get("/api/galleries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gallery.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageTitle").value(hasItem(DEFAULT_IMAGE_TITLE.toString())))
            .andExpect(jsonPath("$.[*].galleryPhotoContentType").value(hasItem(DEFAULT_GALLERY_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].galleryPhoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_GALLERY_PHOTO))));
    }

    @Test
    @Transactional
    public void getGallery() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get the gallery
        restGalleryMockMvc.perform(get("/api/galleries/{id}", gallery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gallery.getId().intValue()))
            .andExpect(jsonPath("$.imageTitle").value(DEFAULT_IMAGE_TITLE.toString()))
            .andExpect(jsonPath("$.galleryPhotoContentType").value(DEFAULT_GALLERY_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.galleryPhoto").value(Base64Utils.encodeToString(DEFAULT_GALLERY_PHOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingGallery() throws Exception {
        // Get the gallery
        restGalleryMockMvc.perform(get("/api/galleries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGallery() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);
        int databaseSizeBeforeUpdate = galleryRepository.findAll().size();

        // Update the gallery
        Gallery updatedGallery = galleryRepository.findOne(gallery.getId());
        updatedGallery
                .imageTitle(UPDATED_IMAGE_TITLE)
                .galleryPhoto(UPDATED_GALLERY_PHOTO)
                .galleryPhotoContentType(UPDATED_GALLERY_PHOTO_CONTENT_TYPE);

        restGalleryMockMvc.perform(put("/api/galleries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGallery)))
            .andExpect(status().isOk());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeUpdate);
        Gallery testGallery = galleryList.get(galleryList.size() - 1);
        assertThat(testGallery.getImageTitle()).isEqualTo(UPDATED_IMAGE_TITLE);
        assertThat(testGallery.getGalleryPhoto()).isEqualTo(UPDATED_GALLERY_PHOTO);
        assertThat(testGallery.getGalleryPhotoContentType()).isEqualTo(UPDATED_GALLERY_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingGallery() throws Exception {
        int databaseSizeBeforeUpdate = galleryRepository.findAll().size();

        // Create the Gallery

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGalleryMockMvc.perform(put("/api/galleries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gallery)))
            .andExpect(status().isCreated());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGallery() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);
        int databaseSizeBeforeDelete = galleryRepository.findAll().size();

        // Get the gallery
        restGalleryMockMvc.perform(delete("/api/galleries/{id}", gallery.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
