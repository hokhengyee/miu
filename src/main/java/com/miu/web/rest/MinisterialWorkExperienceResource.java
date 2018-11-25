package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.MinisterialWorkExperience;
import com.miu.repository.MinisterialWorkExperienceRepository;
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
 * REST controller for managing MinisterialWorkExperience.
 */
@RestController
@RequestMapping("/api")
public class MinisterialWorkExperienceResource {

    private final Logger log = LoggerFactory.getLogger(MinisterialWorkExperienceResource.class);

    private static final String ENTITY_NAME = "ministerialWorkExperience";

    private final MinisterialWorkExperienceRepository ministerialWorkExperienceRepository;

    public MinisterialWorkExperienceResource(MinisterialWorkExperienceRepository ministerialWorkExperienceRepository) {
        this.ministerialWorkExperienceRepository = ministerialWorkExperienceRepository;
    }

    /**
     * POST  /ministerial-work-experiences : Create a new ministerialWorkExperience.
     *
     * @param ministerialWorkExperience the ministerialWorkExperience to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ministerialWorkExperience, or with status 400 (Bad Request) if the ministerialWorkExperience has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ministerial-work-experiences")
    @Timed
    public ResponseEntity<MinisterialWorkExperience> createMinisterialWorkExperience(@Valid @RequestBody MinisterialWorkExperience ministerialWorkExperience) throws URISyntaxException {
        log.debug("REST request to save MinisterialWorkExperience : {}", ministerialWorkExperience);
        if (ministerialWorkExperience.getId() != null) {
            throw new BadRequestAlertException("A new ministerialWorkExperience cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MinisterialWorkExperience result = ministerialWorkExperienceRepository.save(ministerialWorkExperience);
        return ResponseEntity.created(new URI("/api/ministerial-work-experiences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ministerial-work-experiences : Updates an existing ministerialWorkExperience.
     *
     * @param ministerialWorkExperience the ministerialWorkExperience to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ministerialWorkExperience,
     * or with status 400 (Bad Request) if the ministerialWorkExperience is not valid,
     * or with status 500 (Internal Server Error) if the ministerialWorkExperience couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ministerial-work-experiences")
    @Timed
    public ResponseEntity<MinisterialWorkExperience> updateMinisterialWorkExperience(@Valid @RequestBody MinisterialWorkExperience ministerialWorkExperience) throws URISyntaxException {
        log.debug("REST request to update MinisterialWorkExperience : {}", ministerialWorkExperience);
        if (ministerialWorkExperience.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MinisterialWorkExperience result = ministerialWorkExperienceRepository.save(ministerialWorkExperience);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ministerialWorkExperience.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ministerial-work-experiences : get all the ministerialWorkExperiences.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ministerialWorkExperiences in body
     */
    @GetMapping("/ministerial-work-experiences")
    @Timed
    public ResponseEntity<List<MinisterialWorkExperience>> getAllMinisterialWorkExperiences(Pageable pageable) {
        log.debug("REST request to get a page of MinisterialWorkExperiences");
        Page<MinisterialWorkExperience> page = ministerialWorkExperienceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ministerial-work-experiences");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /ministerial-work-experiences/:id : get the "id" ministerialWorkExperience.
     *
     * @param id the id of the ministerialWorkExperience to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ministerialWorkExperience, or with status 404 (Not Found)
     */
    @GetMapping("/ministerial-work-experiences/{id}")
    @Timed
    public ResponseEntity<MinisterialWorkExperience> getMinisterialWorkExperience(@PathVariable Long id) {
        log.debug("REST request to get MinisterialWorkExperience : {}", id);
        Optional<MinisterialWorkExperience> ministerialWorkExperience = ministerialWorkExperienceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ministerialWorkExperience);
    }

    /**
     * DELETE  /ministerial-work-experiences/:id : delete the "id" ministerialWorkExperience.
     *
     * @param id the id of the ministerialWorkExperience to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ministerial-work-experiences/{id}")
    @Timed
    public ResponseEntity<Void> deleteMinisterialWorkExperience(@PathVariable Long id) {
        log.debug("REST request to delete MinisterialWorkExperience : {}", id);

        ministerialWorkExperienceRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
