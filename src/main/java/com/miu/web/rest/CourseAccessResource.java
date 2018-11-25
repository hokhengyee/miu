package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.CourseAccess;
import com.miu.repository.CourseAccessRepository;
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
 * REST controller for managing CourseAccess.
 */
@RestController
@RequestMapping("/api")
public class CourseAccessResource {

    private final Logger log = LoggerFactory.getLogger(CourseAccessResource.class);

    private static final String ENTITY_NAME = "courseAccess";

    private final CourseAccessRepository courseAccessRepository;

    public CourseAccessResource(CourseAccessRepository courseAccessRepository) {
        this.courseAccessRepository = courseAccessRepository;
    }

    /**
     * POST  /course-accesses : Create a new courseAccess.
     *
     * @param courseAccess the courseAccess to create
     * @return the ResponseEntity with status 201 (Created) and with body the new courseAccess, or with status 400 (Bad Request) if the courseAccess has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/course-accesses")
    @Timed
    public ResponseEntity<CourseAccess> createCourseAccess(@Valid @RequestBody CourseAccess courseAccess) throws URISyntaxException {
        log.debug("REST request to save CourseAccess : {}", courseAccess);
        if (courseAccess.getId() != null) {
            throw new BadRequestAlertException("A new courseAccess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseAccess result = courseAccessRepository.save(courseAccess);
        return ResponseEntity.created(new URI("/api/course-accesses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /course-accesses : Updates an existing courseAccess.
     *
     * @param courseAccess the courseAccess to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated courseAccess,
     * or with status 400 (Bad Request) if the courseAccess is not valid,
     * or with status 500 (Internal Server Error) if the courseAccess couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/course-accesses")
    @Timed
    public ResponseEntity<CourseAccess> updateCourseAccess(@Valid @RequestBody CourseAccess courseAccess) throws URISyntaxException {
        log.debug("REST request to update CourseAccess : {}", courseAccess);
        if (courseAccess.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CourseAccess result = courseAccessRepository.save(courseAccess);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, courseAccess.getId().toString()))
            .body(result);
    }

    /**
     * GET  /course-accesses : get all the courseAccesses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of courseAccesses in body
     */
    @GetMapping("/course-accesses")
    @Timed
    public ResponseEntity<List<CourseAccess>> getAllCourseAccesses(Pageable pageable) {
        log.debug("REST request to get a page of CourseAccesses");
        Page<CourseAccess> page = courseAccessRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/course-accesses");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /course-accesses/:id : get the "id" courseAccess.
     *
     * @param id the id of the courseAccess to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the courseAccess, or with status 404 (Not Found)
     */
    @GetMapping("/course-accesses/{id}")
    @Timed
    public ResponseEntity<CourseAccess> getCourseAccess(@PathVariable Long id) {
        log.debug("REST request to get CourseAccess : {}", id);
        Optional<CourseAccess> courseAccess = courseAccessRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(courseAccess);
    }

    /**
     * DELETE  /course-accesses/:id : delete the "id" courseAccess.
     *
     * @param id the id of the courseAccess to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/course-accesses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCourseAccess(@PathVariable Long id) {
        log.debug("REST request to delete CourseAccess : {}", id);

        courseAccessRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
