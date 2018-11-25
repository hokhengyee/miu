package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.RecordOfCertificate;
import com.miu.repository.RecordOfCertificateRepository;
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
 * REST controller for managing RecordOfCertificate.
 */
@RestController
@RequestMapping("/api")
public class RecordOfCertificateResource {

    private final Logger log = LoggerFactory.getLogger(RecordOfCertificateResource.class);

    private static final String ENTITY_NAME = "recordOfCertificate";

    private final RecordOfCertificateRepository recordOfCertificateRepository;

    public RecordOfCertificateResource(RecordOfCertificateRepository recordOfCertificateRepository) {
        this.recordOfCertificateRepository = recordOfCertificateRepository;
    }

    /**
     * POST  /record-of-certificates : Create a new recordOfCertificate.
     *
     * @param recordOfCertificate the recordOfCertificate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recordOfCertificate, or with status 400 (Bad Request) if the recordOfCertificate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/record-of-certificates")
    @Timed
    public ResponseEntity<RecordOfCertificate> createRecordOfCertificate(@Valid @RequestBody RecordOfCertificate recordOfCertificate) throws URISyntaxException {
        log.debug("REST request to save RecordOfCertificate : {}", recordOfCertificate);
        if (recordOfCertificate.getId() != null) {
            throw new BadRequestAlertException("A new recordOfCertificate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecordOfCertificate result = recordOfCertificateRepository.save(recordOfCertificate);
        return ResponseEntity.created(new URI("/api/record-of-certificates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /record-of-certificates : Updates an existing recordOfCertificate.
     *
     * @param recordOfCertificate the recordOfCertificate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recordOfCertificate,
     * or with status 400 (Bad Request) if the recordOfCertificate is not valid,
     * or with status 500 (Internal Server Error) if the recordOfCertificate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/record-of-certificates")
    @Timed
    public ResponseEntity<RecordOfCertificate> updateRecordOfCertificate(@Valid @RequestBody RecordOfCertificate recordOfCertificate) throws URISyntaxException {
        log.debug("REST request to update RecordOfCertificate : {}", recordOfCertificate);
        if (recordOfCertificate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecordOfCertificate result = recordOfCertificateRepository.save(recordOfCertificate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recordOfCertificate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /record-of-certificates : get all the recordOfCertificates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of recordOfCertificates in body
     */
    @GetMapping("/record-of-certificates")
    @Timed
    public ResponseEntity<List<RecordOfCertificate>> getAllRecordOfCertificates(Pageable pageable) {
        log.debug("REST request to get a page of RecordOfCertificates");
        Page<RecordOfCertificate> page = recordOfCertificateRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/record-of-certificates");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /record-of-certificates/:id : get the "id" recordOfCertificate.
     *
     * @param id the id of the recordOfCertificate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recordOfCertificate, or with status 404 (Not Found)
     */
    @GetMapping("/record-of-certificates/{id}")
    @Timed
    public ResponseEntity<RecordOfCertificate> getRecordOfCertificate(@PathVariable Long id) {
        log.debug("REST request to get RecordOfCertificate : {}", id);
        Optional<RecordOfCertificate> recordOfCertificate = recordOfCertificateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(recordOfCertificate);
    }

    /**
     * DELETE  /record-of-certificates/:id : delete the "id" recordOfCertificate.
     *
     * @param id the id of the recordOfCertificate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/record-of-certificates/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecordOfCertificate(@PathVariable Long id) {
        log.debug("REST request to delete RecordOfCertificate : {}", id);

        recordOfCertificateRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
