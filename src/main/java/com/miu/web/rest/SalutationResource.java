package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.Salutation;
import com.miu.repository.SalutationRepository;
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
 * REST controller for managing Salutation.
 */
@RestController
@RequestMapping("/api")
public class SalutationResource {

    private final Logger log = LoggerFactory.getLogger(SalutationResource.class);

    private static final String ENTITY_NAME = "salutation";

    private final SalutationRepository salutationRepository;

    public SalutationResource(SalutationRepository salutationRepository) {
        this.salutationRepository = salutationRepository;
    }

    /**
     * POST  /salutations : Create a new salutation.
     *
     * @param salutation the salutation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salutation, or with status 400 (Bad Request) if the salutation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/salutations")
    @Timed
    public ResponseEntity<Salutation> createSalutation(@Valid @RequestBody Salutation salutation) throws URISyntaxException {
        log.debug("REST request to save Salutation : {}", salutation);
        if (salutation.getId() != null) {
            throw new BadRequestAlertException("A new salutation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Salutation result = salutationRepository.save(salutation);
        return ResponseEntity.created(new URI("/api/salutations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /salutations : Updates an existing salutation.
     *
     * @param salutation the salutation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salutation,
     * or with status 400 (Bad Request) if the salutation is not valid,
     * or with status 500 (Internal Server Error) if the salutation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/salutations")
    @Timed
    public ResponseEntity<Salutation> updateSalutation(@Valid @RequestBody Salutation salutation) throws URISyntaxException {
        log.debug("REST request to update Salutation : {}", salutation);
        if (salutation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Salutation result = salutationRepository.save(salutation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, salutation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /salutations : get all the salutations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of salutations in body
     */
    @GetMapping("/salutations")
    @Timed
    public ResponseEntity<List<Salutation>> getAllSalutations(Pageable pageable) {
        log.debug("REST request to get a page of Salutations");
        Page<Salutation> page = salutationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/salutations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /salutations/:id : get the "id" salutation.
     *
     * @param id the id of the salutation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the salutation, or with status 404 (Not Found)
     */
    @GetMapping("/salutations/{id}")
    @Timed
    public ResponseEntity<Salutation> getSalutation(@PathVariable Long id) {
        log.debug("REST request to get Salutation : {}", id);
        Optional<Salutation> salutation = salutationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(salutation);
    }

    /**
     * DELETE  /salutations/:id : delete the "id" salutation.
     *
     * @param id the id of the salutation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/salutations/{id}")
    @Timed
    public ResponseEntity<Void> deleteSalutation(@PathVariable Long id) {
        log.debug("REST request to delete Salutation : {}", id);

        salutationRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
