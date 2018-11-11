package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.CourseMaterialAccess;

import com.miu.repository.CourseMaterialAccessRepository;
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
 * REST controller for managing CourseMaterialAccess.
 */
@RestController
@RequestMapping("/api")
public class CourseMaterialAccessResource {

    private final Logger log = LoggerFactory.getLogger(CourseMaterialAccessResource.class);
        
    @Inject
    private CourseMaterialAccessRepository courseMaterialAccessRepository;

    /**
     * POST  /course-material-accesses : Create a new courseMaterialAccess.
     *
     * @param courseMaterialAccess the courseMaterialAccess to create
     * @return the ResponseEntity with status 201 (Created) and with body the new courseMaterialAccess, or with status 400 (Bad Request) if the courseMaterialAccess has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/course-material-accesses")
    @Timed
    public ResponseEntity<CourseMaterialAccess> createCourseMaterialAccess(@Valid @RequestBody CourseMaterialAccess courseMaterialAccess) throws URISyntaxException {
        log.debug("REST request to save CourseMaterialAccess : {}", courseMaterialAccess);
        if (courseMaterialAccess.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("courseMaterialAccess", "idexists", "A new courseMaterialAccess cannot already have an ID")).body(null);
        }
        CourseMaterialAccess result = courseMaterialAccessRepository.save(courseMaterialAccess);
        return ResponseEntity.created(new URI("/api/course-material-accesses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("courseMaterialAccess", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /course-material-accesses : Updates an existing courseMaterialAccess.
     *
     * @param courseMaterialAccess the courseMaterialAccess to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated courseMaterialAccess,
     * or with status 400 (Bad Request) if the courseMaterialAccess is not valid,
     * or with status 500 (Internal Server Error) if the courseMaterialAccess couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/course-material-accesses")
    @Timed
    public ResponseEntity<CourseMaterialAccess> updateCourseMaterialAccess(@Valid @RequestBody CourseMaterialAccess courseMaterialAccess) throws URISyntaxException {
        log.debug("REST request to update CourseMaterialAccess : {}", courseMaterialAccess);
        if (courseMaterialAccess.getId() == null) {
            return createCourseMaterialAccess(courseMaterialAccess);
        }
        CourseMaterialAccess result = courseMaterialAccessRepository.save(courseMaterialAccess);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("courseMaterialAccess", courseMaterialAccess.getId().toString()))
            .body(result);
    }

    /**
     * GET  /course-material-accesses : get all the courseMaterialAccesses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of courseMaterialAccesses in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/course-material-accesses")
    @Timed
    public ResponseEntity<List<CourseMaterialAccess>> getAllCourseMaterialAccesses(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CourseMaterialAccesses");
        Page<CourseMaterialAccess> page = courseMaterialAccessRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/course-material-accesses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /course-material-accesses/:id : get the "id" courseMaterialAccess.
     *
     * @param id the id of the courseMaterialAccess to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the courseMaterialAccess, or with status 404 (Not Found)
     */
    @GetMapping("/course-material-accesses/{id}")
    @Timed
    public ResponseEntity<CourseMaterialAccess> getCourseMaterialAccess(@PathVariable Long id) {
        log.debug("REST request to get CourseMaterialAccess : {}", id);
        CourseMaterialAccess courseMaterialAccess = courseMaterialAccessRepository.findOne(id);
        return Optional.ofNullable(courseMaterialAccess)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /course-material-accesses/:id : delete the "id" courseMaterialAccess.
     *
     * @param id the id of the courseMaterialAccess to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/course-material-accesses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCourseMaterialAccess(@PathVariable Long id) {
        log.debug("REST request to delete CourseMaterialAccess : {}", id);
        courseMaterialAccessRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("courseMaterialAccess", id.toString())).build();
    }

}
