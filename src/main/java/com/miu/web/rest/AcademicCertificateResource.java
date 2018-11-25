package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.AcademicCertificate;
import com.miu.repository.AcademicCertificateRepository;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AcademicCertificate.
 */
@RestController
@RequestMapping("/api")
public class AcademicCertificateResource {

    private final Logger log = LoggerFactory.getLogger(AcademicCertificateResource.class);

    private static final String ENTITY_NAME = "academicCertificate";

    private final AcademicCertificateRepository academicCertificateRepository;

    public AcademicCertificateResource(AcademicCertificateRepository academicCertificateRepository) {
        this.academicCertificateRepository = academicCertificateRepository;
    }

    /**
     * POST  /academic-certificates : Create a new academicCertificate.
     *
     * @param academicCertificate the academicCertificate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new academicCertificate, or with status 400 (Bad Request) if the academicCertificate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/academic-certificates")
    @Timed
    public ResponseEntity<AcademicCertificate> createAcademicCertificate(@RequestBody AcademicCertificate academicCertificate) throws URISyntaxException {
        log.debug("REST request to save AcademicCertificate : {}", academicCertificate);
        if (academicCertificate.getId() != null) {
            throw new BadRequestAlertException("A new academicCertificate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AcademicCertificate result = academicCertificateRepository.save(academicCertificate);
        return ResponseEntity.created(new URI("/api/academic-certificates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /academic-certificates : Updates an existing academicCertificate.
     *
     * @param academicCertificate the academicCertificate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated academicCertificate,
     * or with status 400 (Bad Request) if the academicCertificate is not valid,
     * or with status 500 (Internal Server Error) if the academicCertificate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/academic-certificates")
    @Timed
    public ResponseEntity<AcademicCertificate> updateAcademicCertificate(@RequestBody AcademicCertificate academicCertificate) throws URISyntaxException {
        log.debug("REST request to update AcademicCertificate : {}", academicCertificate);
        if (academicCertificate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AcademicCertificate result = academicCertificateRepository.save(academicCertificate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, academicCertificate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /academic-certificates : get all the academicCertificates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of academicCertificates in body
     */
    @GetMapping("/academic-certificates")
    @Timed
    public ResponseEntity<List<AcademicCertificate>> getAllAcademicCertificates(Pageable pageable) {
        log.debug("REST request to get a page of AcademicCertificates");
        Page<AcademicCertificate> page = academicCertificateRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/academic-certificates");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /academic-certificates/:id : get the "id" academicCertificate.
     *
     * @param id the id of the academicCertificate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the academicCertificate, or with status 404 (Not Found)
     */
    @GetMapping("/academic-certificates/{id}")
    @Timed
    public ResponseEntity<AcademicCertificate> getAcademicCertificate(@PathVariable Long id) {
        log.debug("REST request to get AcademicCertificate : {}", id);
        Optional<AcademicCertificate> academicCertificate = academicCertificateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(academicCertificate);
    }

    /**
     * DELETE  /academic-certificates/:id : delete the "id" academicCertificate.
     *
     * @param id the id of the academicCertificate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/academic-certificates/{id}")
    @Timed
    public ResponseEntity<Void> deleteAcademicCertificate(@PathVariable Long id) {
        log.debug("REST request to delete AcademicCertificate : {}", id);

        academicCertificateRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
