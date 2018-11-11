package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.StudentResearchPaperResult;

import com.miu.repository.StudentResearchPaperResultRepository;
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
 * REST controller for managing StudentResearchPaperResult.
 */
@RestController
@RequestMapping("/api")
public class StudentResearchPaperResultResource {

    private final Logger log = LoggerFactory.getLogger(StudentResearchPaperResultResource.class);
        
    @Inject
    private StudentResearchPaperResultRepository studentResearchPaperResultRepository;

    /**
     * POST  /student-research-paper-results : Create a new studentResearchPaperResult.
     *
     * @param studentResearchPaperResult the studentResearchPaperResult to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentResearchPaperResult, or with status 400 (Bad Request) if the studentResearchPaperResult has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-research-paper-results")
    @Timed
    public ResponseEntity<StudentResearchPaperResult> createStudentResearchPaperResult(@Valid @RequestBody StudentResearchPaperResult studentResearchPaperResult) throws URISyntaxException {
        log.debug("REST request to save StudentResearchPaperResult : {}", studentResearchPaperResult);
        if (studentResearchPaperResult.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("studentResearchPaperResult", "idexists", "A new studentResearchPaperResult cannot already have an ID")).body(null);
        }
        StudentResearchPaperResult result = studentResearchPaperResultRepository.save(studentResearchPaperResult);
        return ResponseEntity.created(new URI("/api/student-research-paper-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("studentResearchPaperResult", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-research-paper-results : Updates an existing studentResearchPaperResult.
     *
     * @param studentResearchPaperResult the studentResearchPaperResult to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentResearchPaperResult,
     * or with status 400 (Bad Request) if the studentResearchPaperResult is not valid,
     * or with status 500 (Internal Server Error) if the studentResearchPaperResult couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-research-paper-results")
    @Timed
    public ResponseEntity<StudentResearchPaperResult> updateStudentResearchPaperResult(@Valid @RequestBody StudentResearchPaperResult studentResearchPaperResult) throws URISyntaxException {
        log.debug("REST request to update StudentResearchPaperResult : {}", studentResearchPaperResult);
        if (studentResearchPaperResult.getId() == null) {
            return createStudentResearchPaperResult(studentResearchPaperResult);
        }
        StudentResearchPaperResult result = studentResearchPaperResultRepository.save(studentResearchPaperResult);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("studentResearchPaperResult", studentResearchPaperResult.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-research-paper-results : get all the studentResearchPaperResults.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of studentResearchPaperResults in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/student-research-paper-results")
    @Timed
    public ResponseEntity<List<StudentResearchPaperResult>> getAllStudentResearchPaperResults(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of StudentResearchPaperResults");
        Page<StudentResearchPaperResult> page = studentResearchPaperResultRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-research-paper-results");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /student-research-paper-results/:id : get the "id" studentResearchPaperResult.
     *
     * @param id the id of the studentResearchPaperResult to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentResearchPaperResult, or with status 404 (Not Found)
     */
    @GetMapping("/student-research-paper-results/{id}")
    @Timed
    public ResponseEntity<StudentResearchPaperResult> getStudentResearchPaperResult(@PathVariable Long id) {
        log.debug("REST request to get StudentResearchPaperResult : {}", id);
        StudentResearchPaperResult studentResearchPaperResult = studentResearchPaperResultRepository.findOne(id);
        return Optional.ofNullable(studentResearchPaperResult)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /student-research-paper-results/:id : delete the "id" studentResearchPaperResult.
     *
     * @param id the id of the studentResearchPaperResult to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-research-paper-results/{id}")
    @Timed
    public ResponseEntity<Void> deleteStudentResearchPaperResult(@PathVariable Long id) {
        log.debug("REST request to delete StudentResearchPaperResult : {}", id);
        studentResearchPaperResultRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("studentResearchPaperResult", id.toString())).build();
    }

}
