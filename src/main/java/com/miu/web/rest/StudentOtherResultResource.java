package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.StudentOtherResult;
import com.miu.repository.StudentOtherResultRepository;
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
 * REST controller for managing StudentOtherResult.
 */
@RestController
@RequestMapping("/api")
public class StudentOtherResultResource {

    private final Logger log = LoggerFactory.getLogger(StudentOtherResultResource.class);

    private static final String ENTITY_NAME = "studentOtherResult";

    private final StudentOtherResultRepository studentOtherResultRepository;

    public StudentOtherResultResource(StudentOtherResultRepository studentOtherResultRepository) {
        this.studentOtherResultRepository = studentOtherResultRepository;
    }

    /**
     * POST  /student-other-results : Create a new studentOtherResult.
     *
     * @param studentOtherResult the studentOtherResult to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentOtherResult, or with status 400 (Bad Request) if the studentOtherResult has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-other-results")
    @Timed
    public ResponseEntity<StudentOtherResult> createStudentOtherResult(@Valid @RequestBody StudentOtherResult studentOtherResult) throws URISyntaxException {
        log.debug("REST request to save StudentOtherResult : {}", studentOtherResult);
        if (studentOtherResult.getId() != null) {
            throw new BadRequestAlertException("A new studentOtherResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentOtherResult result = studentOtherResultRepository.save(studentOtherResult);
        return ResponseEntity.created(new URI("/api/student-other-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-other-results : Updates an existing studentOtherResult.
     *
     * @param studentOtherResult the studentOtherResult to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentOtherResult,
     * or with status 400 (Bad Request) if the studentOtherResult is not valid,
     * or with status 500 (Internal Server Error) if the studentOtherResult couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-other-results")
    @Timed
    public ResponseEntity<StudentOtherResult> updateStudentOtherResult(@Valid @RequestBody StudentOtherResult studentOtherResult) throws URISyntaxException {
        log.debug("REST request to update StudentOtherResult : {}", studentOtherResult);
        if (studentOtherResult.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentOtherResult result = studentOtherResultRepository.save(studentOtherResult);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentOtherResult.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-other-results : get all the studentOtherResults.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of studentOtherResults in body
     */
    @GetMapping("/student-other-results")
    @Timed
    public ResponseEntity<List<StudentOtherResult>> getAllStudentOtherResults(Pageable pageable) {
        log.debug("REST request to get a page of StudentOtherResults");
        Page<StudentOtherResult> page = studentOtherResultRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-other-results");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /student-other-results/:id : get the "id" studentOtherResult.
     *
     * @param id the id of the studentOtherResult to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentOtherResult, or with status 404 (Not Found)
     */
    @GetMapping("/student-other-results/{id}")
    @Timed
    public ResponseEntity<StudentOtherResult> getStudentOtherResult(@PathVariable Long id) {
        log.debug("REST request to get StudentOtherResult : {}", id);
        Optional<StudentOtherResult> studentOtherResult = studentOtherResultRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(studentOtherResult);
    }

    /**
     * DELETE  /student-other-results/:id : delete the "id" studentOtherResult.
     *
     * @param id the id of the studentOtherResult to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-other-results/{id}")
    @Timed
    public ResponseEntity<Void> deleteStudentOtherResult(@PathVariable Long id) {
        log.debug("REST request to delete StudentOtherResult : {}", id);

        studentOtherResultRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
