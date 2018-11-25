package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.CourseMaterial;
import com.miu.repository.CourseMaterialRepository;
import com.miu.web.rest.errors.BadRequestAlertException;
import com.miu.web.rest.util.HeaderUtil;
import com.miu.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CourseMaterial.
 */
@RestController
@RequestMapping("/api")
public class CourseMaterialResource {

    private final Logger log = LoggerFactory.getLogger(CourseMaterialResource.class);

    private static final String ENTITY_NAME = "courseMaterial";

    private final CourseMaterialRepository courseMaterialRepository;

    public CourseMaterialResource(CourseMaterialRepository courseMaterialRepository) {
        this.courseMaterialRepository = courseMaterialRepository;
    }

    /**
     * POST  /course-materials : Create a new courseMaterial.
     *
     * @param courseMaterial the courseMaterial to create
     * @return the ResponseEntity with status 201 (Created) and with body the new courseMaterial, or with status 400 (Bad Request) if the courseMaterial has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/course-materials")
    @Timed
    public ResponseEntity<CourseMaterial> createCourseMaterial(@Valid @RequestBody CourseMaterial courseMaterial) throws URISyntaxException {
        log.debug("REST request to save CourseMaterial : {}", courseMaterial);
        if (courseMaterial.getId() != null) {
            throw new BadRequestAlertException("A new courseMaterial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseMaterial result = courseMaterialRepository.save(courseMaterial);
        return ResponseEntity.created(new URI("/api/course-materials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /course-materials : Updates an existing courseMaterial.
     *
     * @param courseMaterial the courseMaterial to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated courseMaterial,
     * or with status 400 (Bad Request) if the courseMaterial is not valid,
     * or with status 500 (Internal Server Error) if the courseMaterial couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/course-materials")
    @Timed
    public ResponseEntity<CourseMaterial> updateCourseMaterial(@Valid @RequestBody CourseMaterial courseMaterial) throws URISyntaxException {
        log.debug("REST request to update CourseMaterial : {}", courseMaterial);
        if (courseMaterial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CourseMaterial result = courseMaterialRepository.save(courseMaterial);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, courseMaterial.getId().toString()))
            .body(result);
    }

    /**
     * GET  /course-materials : get all the courseMaterials.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of courseMaterials in body
     */
    @GetMapping("/course-materials")
    @Timed
    public ResponseEntity<List<CourseMaterial>> getAllCourseMaterials(Pageable pageable) {
        log.debug("REST request to get a page of CourseMaterials");
        Page<CourseMaterial> page = courseMaterialRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/course-materials");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /course-materials/:id : get the "id" courseMaterial.
     *
     * @param id the id of the courseMaterial to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the courseMaterial, or with status 404 (Not Found)
     */
    @GetMapping("/course-materials/{id}")
    @Timed
    public ResponseEntity<CourseMaterial> getCourseMaterial(@PathVariable Long id) {
        log.debug("REST request to get CourseMaterial : {}", id);
        Optional<CourseMaterial> courseMaterial = courseMaterialRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(courseMaterial);
    }

    /**
     * DELETE  /course-materials/:id : delete the "id" courseMaterial.
     *
     * @param id the id of the courseMaterial to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/course-materials/{id}")
    @Timed
    public ResponseEntity<Void> deleteCourseMaterial(@PathVariable Long id) {
        log.debug("REST request to delete CourseMaterial : {}", id);

        courseMaterialRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
