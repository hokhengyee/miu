package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.EntryQualification;
import com.miu.repository.EntryQualificationRepository;
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
 * REST controller for managing EntryQualification.
 */
@RestController
@RequestMapping("/api")
public class EntryQualificationResource {

    private final Logger log = LoggerFactory.getLogger(EntryQualificationResource.class);

    private static final String ENTITY_NAME = "entryQualification";

    private final EntryQualificationRepository entryQualificationRepository;

    public EntryQualificationResource(EntryQualificationRepository entryQualificationRepository) {
        this.entryQualificationRepository = entryQualificationRepository;
    }

    /**
     * POST  /entry-qualifications : Create a new entryQualification.
     *
     * @param entryQualification the entryQualification to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entryQualification, or with status 400 (Bad Request) if the entryQualification has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entry-qualifications")
    @Timed
    public ResponseEntity<EntryQualification> createEntryQualification(@Valid @RequestBody EntryQualification entryQualification) throws URISyntaxException {
        log.debug("REST request to save EntryQualification : {}", entryQualification);
        if (entryQualification.getId() != null) {
            throw new BadRequestAlertException("A new entryQualification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntryQualification result = entryQualificationRepository.save(entryQualification);
        return ResponseEntity.created(new URI("/api/entry-qualifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entry-qualifications : Updates an existing entryQualification.
     *
     * @param entryQualification the entryQualification to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entryQualification,
     * or with status 400 (Bad Request) if the entryQualification is not valid,
     * or with status 500 (Internal Server Error) if the entryQualification couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entry-qualifications")
    @Timed
    public ResponseEntity<EntryQualification> updateEntryQualification(@Valid @RequestBody EntryQualification entryQualification) throws URISyntaxException {
        log.debug("REST request to update EntryQualification : {}", entryQualification);
        if (entryQualification.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EntryQualification result = entryQualificationRepository.save(entryQualification);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entryQualification.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entry-qualifications : get all the entryQualifications.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of entryQualifications in body
     */
    @GetMapping("/entry-qualifications")
    @Timed
    public ResponseEntity<List<EntryQualification>> getAllEntryQualifications(Pageable pageable) {
        log.debug("REST request to get a page of EntryQualifications");
        Page<EntryQualification> page = entryQualificationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/entry-qualifications");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /entry-qualifications/:id : get the "id" entryQualification.
     *
     * @param id the id of the entryQualification to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entryQualification, or with status 404 (Not Found)
     */
    @GetMapping("/entry-qualifications/{id}")
    @Timed
    public ResponseEntity<EntryQualification> getEntryQualification(@PathVariable Long id) {
        log.debug("REST request to get EntryQualification : {}", id);
        Optional<EntryQualification> entryQualification = entryQualificationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(entryQualification);
    }

    /**
     * DELETE  /entry-qualifications/:id : delete the "id" entryQualification.
     *
     * @param id the id of the entryQualification to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entry-qualifications/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntryQualification(@PathVariable Long id) {
        log.debug("REST request to delete EntryQualification : {}", id);

        entryQualificationRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
