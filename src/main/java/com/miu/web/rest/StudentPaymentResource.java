package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.StudentPayment;
import com.miu.repository.StudentPaymentRepository;
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
 * REST controller for managing StudentPayment.
 */
@RestController
@RequestMapping("/api")
public class StudentPaymentResource {

    private final Logger log = LoggerFactory.getLogger(StudentPaymentResource.class);

    private static final String ENTITY_NAME = "studentPayment";

    private final StudentPaymentRepository studentPaymentRepository;

    public StudentPaymentResource(StudentPaymentRepository studentPaymentRepository) {
        this.studentPaymentRepository = studentPaymentRepository;
    }

    /**
     * POST  /student-payments : Create a new studentPayment.
     *
     * @param studentPayment the studentPayment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentPayment, or with status 400 (Bad Request) if the studentPayment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-payments")
    @Timed
    public ResponseEntity<StudentPayment> createStudentPayment(@Valid @RequestBody StudentPayment studentPayment) throws URISyntaxException {
        log.debug("REST request to save StudentPayment : {}", studentPayment);
        if (studentPayment.getId() != null) {
            throw new BadRequestAlertException("A new studentPayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentPayment result = studentPaymentRepository.save(studentPayment);
        return ResponseEntity.created(new URI("/api/student-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-payments : Updates an existing studentPayment.
     *
     * @param studentPayment the studentPayment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentPayment,
     * or with status 400 (Bad Request) if the studentPayment is not valid,
     * or with status 500 (Internal Server Error) if the studentPayment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-payments")
    @Timed
    public ResponseEntity<StudentPayment> updateStudentPayment(@Valid @RequestBody StudentPayment studentPayment) throws URISyntaxException {
        log.debug("REST request to update StudentPayment : {}", studentPayment);
        if (studentPayment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentPayment result = studentPaymentRepository.save(studentPayment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentPayment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-payments : get all the studentPayments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of studentPayments in body
     */
    @GetMapping("/student-payments")
    @Timed
    public ResponseEntity<List<StudentPayment>> getAllStudentPayments(Pageable pageable) {
        log.debug("REST request to get a page of StudentPayments");
        Page<StudentPayment> page = studentPaymentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-payments");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /student-payments/:id : get the "id" studentPayment.
     *
     * @param id the id of the studentPayment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentPayment, or with status 404 (Not Found)
     */
    @GetMapping("/student-payments/{id}")
    @Timed
    public ResponseEntity<StudentPayment> getStudentPayment(@PathVariable Long id) {
        log.debug("REST request to get StudentPayment : {}", id);
        Optional<StudentPayment> studentPayment = studentPaymentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(studentPayment);
    }

    /**
     * DELETE  /student-payments/:id : delete the "id" studentPayment.
     *
     * @param id the id of the studentPayment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-payments/{id}")
    @Timed
    public ResponseEntity<Void> deleteStudentPayment(@PathVariable Long id) {
        log.debug("REST request to delete StudentPayment : {}", id);

        studentPaymentRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
