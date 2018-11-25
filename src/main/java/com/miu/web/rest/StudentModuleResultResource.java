package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.StudentModuleResult;
import com.miu.repository.StudentModuleResultRepository;
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
 * REST controller for managing StudentModuleResult.
 */
@RestController
@RequestMapping("/api")
public class StudentModuleResultResource {

    private final Logger log = LoggerFactory.getLogger(StudentModuleResultResource.class);

    private static final String ENTITY_NAME = "studentModuleResult";

    private final StudentModuleResultRepository studentModuleResultRepository;

    public StudentModuleResultResource(StudentModuleResultRepository studentModuleResultRepository) {
        this.studentModuleResultRepository = studentModuleResultRepository;
    }

    /**
     * POST  /student-module-results : Create a new studentModuleResult.
     *
     * @param studentModuleResult the studentModuleResult to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentModuleResult, or with status 400 (Bad Request) if the studentModuleResult has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-module-results")
    @Timed
    public ResponseEntity<StudentModuleResult> createStudentModuleResult(@Valid @RequestBody StudentModuleResult studentModuleResult) throws URISyntaxException {
        log.debug("REST request to save StudentModuleResult : {}", studentModuleResult);
        if (studentModuleResult.getId() != null) {
            throw new BadRequestAlertException("A new studentModuleResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentModuleResult result = studentModuleResultRepository.save(studentModuleResult);
        return ResponseEntity.created(new URI("/api/student-module-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-module-results : Updates an existing studentModuleResult.
     *
     * @param studentModuleResult the studentModuleResult to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentModuleResult,
     * or with status 400 (Bad Request) if the studentModuleResult is not valid,
     * or with status 500 (Internal Server Error) if the studentModuleResult couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-module-results")
    @Timed
    public ResponseEntity<StudentModuleResult> updateStudentModuleResult(@Valid @RequestBody StudentModuleResult studentModuleResult) throws URISyntaxException {
        log.debug("REST request to update StudentModuleResult : {}", studentModuleResult);
        if (studentModuleResult.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentModuleResult result = studentModuleResultRepository.save(studentModuleResult);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentModuleResult.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-module-results : get all the studentModuleResults.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of studentModuleResults in body
     */
    @GetMapping("/student-module-results")
    @Timed
    public ResponseEntity<List<StudentModuleResult>> getAllStudentModuleResults(Pageable pageable) {
        log.debug("REST request to get a page of StudentModuleResults");
        Page<StudentModuleResult> page = studentModuleResultRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-module-results");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /student-module-results/:id : get the "id" studentModuleResult.
     *
     * @param id the id of the studentModuleResult to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentModuleResult, or with status 404 (Not Found)
     */
    @GetMapping("/student-module-results/{id}")
    @Timed
    public ResponseEntity<StudentModuleResult> getStudentModuleResult(@PathVariable Long id) {
        log.debug("REST request to get StudentModuleResult : {}", id);
        Optional<StudentModuleResult> studentModuleResult = studentModuleResultRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(studentModuleResult);
    }

    /**
     * DELETE  /student-module-results/:id : delete the "id" studentModuleResult.
     *
     * @param id the id of the studentModuleResult to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-module-results/{id}")
    @Timed
    public ResponseEntity<Void> deleteStudentModuleResult(@PathVariable Long id) {
        log.debug("REST request to delete StudentModuleResult : {}", id);

        studentModuleResultRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
