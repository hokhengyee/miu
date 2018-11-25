package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.LecturerProfile;
import com.miu.repository.LecturerProfileRepository;
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
 * REST controller for managing LecturerProfile.
 */
@RestController
@RequestMapping("/api")
public class LecturerProfileResource {

    private final Logger log = LoggerFactory.getLogger(LecturerProfileResource.class);

    private static final String ENTITY_NAME = "lecturerProfile";

    private final LecturerProfileRepository lecturerProfileRepository;

    public LecturerProfileResource(LecturerProfileRepository lecturerProfileRepository) {
        this.lecturerProfileRepository = lecturerProfileRepository;
    }

    /**
     * POST  /lecturer-profiles : Create a new lecturerProfile.
     *
     * @param lecturerProfile the lecturerProfile to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lecturerProfile, or with status 400 (Bad Request) if the lecturerProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lecturer-profiles")
    @Timed
    public ResponseEntity<LecturerProfile> createLecturerProfile(@Valid @RequestBody LecturerProfile lecturerProfile) throws URISyntaxException {
        log.debug("REST request to save LecturerProfile : {}", lecturerProfile);
        if (lecturerProfile.getId() != null) {
            throw new BadRequestAlertException("A new lecturerProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LecturerProfile result = lecturerProfileRepository.save(lecturerProfile);
        return ResponseEntity.created(new URI("/api/lecturer-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lecturer-profiles : Updates an existing lecturerProfile.
     *
     * @param lecturerProfile the lecturerProfile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lecturerProfile,
     * or with status 400 (Bad Request) if the lecturerProfile is not valid,
     * or with status 500 (Internal Server Error) if the lecturerProfile couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lecturer-profiles")
    @Timed
    public ResponseEntity<LecturerProfile> updateLecturerProfile(@Valid @RequestBody LecturerProfile lecturerProfile) throws URISyntaxException {
        log.debug("REST request to update LecturerProfile : {}", lecturerProfile);
        if (lecturerProfile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LecturerProfile result = lecturerProfileRepository.save(lecturerProfile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lecturerProfile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lecturer-profiles : get all the lecturerProfiles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lecturerProfiles in body
     */
    @GetMapping("/lecturer-profiles")
    @Timed
    public ResponseEntity<List<LecturerProfile>> getAllLecturerProfiles(Pageable pageable) {
        log.debug("REST request to get a page of LecturerProfiles");
        Page<LecturerProfile> page = lecturerProfileRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lecturer-profiles");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /lecturer-profiles/:id : get the "id" lecturerProfile.
     *
     * @param id the id of the lecturerProfile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lecturerProfile, or with status 404 (Not Found)
     */
    @GetMapping("/lecturer-profiles/{id}")
    @Timed
    public ResponseEntity<LecturerProfile> getLecturerProfile(@PathVariable Long id) {
        log.debug("REST request to get LecturerProfile : {}", id);
        Optional<LecturerProfile> lecturerProfile = lecturerProfileRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lecturerProfile);
    }

    /**
     * DELETE  /lecturer-profiles/:id : delete the "id" lecturerProfile.
     *
     * @param id the id of the lecturerProfile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lecturer-profiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteLecturerProfile(@PathVariable Long id) {
        log.debug("REST request to delete LecturerProfile : {}", id);

        lecturerProfileRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
