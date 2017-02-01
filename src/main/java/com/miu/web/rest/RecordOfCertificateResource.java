package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.RecordOfCertificate;

import com.miu.repository.RecordOfCertificateRepository;
import com.miu.web.rest.util.HeaderUtil;
import com.miu.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
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
        
    @Inject
    private RecordOfCertificateRepository recordOfCertificateRepository;

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
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("recordOfCertificate", "idexists", "A new recordOfCertificate cannot already have an ID")).body(null);
        }
        RecordOfCertificate result = recordOfCertificateRepository.save(recordOfCertificate);
        return ResponseEntity.created(new URI("/api/record-of-certificates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("recordOfCertificate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /record-of-certificates : Updates an existing recordOfCertificate.
     *
     * @param recordOfCertificate the recordOfCertificate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recordOfCertificate,
     * or with status 400 (Bad Request) if the recordOfCertificate is not valid,
     * or with status 500 (Internal Server Error) if the recordOfCertificate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/record-of-certificates")
    @Timed
    public ResponseEntity<RecordOfCertificate> updateRecordOfCertificate(@Valid @RequestBody RecordOfCertificate recordOfCertificate) throws URISyntaxException {
        log.debug("REST request to update RecordOfCertificate : {}", recordOfCertificate);
        if (recordOfCertificate.getId() == null) {
            return createRecordOfCertificate(recordOfCertificate);
        }
        RecordOfCertificate result = recordOfCertificateRepository.save(recordOfCertificate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("recordOfCertificate", recordOfCertificate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /record-of-certificates : get all the recordOfCertificates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of recordOfCertificates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/record-of-certificates")
    @Timed
    public ResponseEntity<List<RecordOfCertificate>> getAllRecordOfCertificates(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RecordOfCertificates");
        Page<RecordOfCertificate> page = recordOfCertificateRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/record-of-certificates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
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
        RecordOfCertificate recordOfCertificate = recordOfCertificateRepository.findOne(id);
        return Optional.ofNullable(recordOfCertificate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
        recordOfCertificateRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("recordOfCertificate", id.toString())).build();
    }

}
