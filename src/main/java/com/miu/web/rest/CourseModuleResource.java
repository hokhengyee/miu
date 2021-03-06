package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.CourseModule;

import com.miu.repository.CourseModuleRepository;
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
 * REST controller for managing CourseModule.
 */
@RestController
@RequestMapping("/api")
public class CourseModuleResource {

    private final Logger log = LoggerFactory.getLogger(CourseModuleResource.class);
        
    @Inject
    private CourseModuleRepository courseModuleRepository;

    /**
     * POST  /course-modules : Create a new courseModule.
     *
     * @param courseModule the courseModule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new courseModule, or with status 400 (Bad Request) if the courseModule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/course-modules")
    @Timed
    public ResponseEntity<CourseModule> createCourseModule(@Valid @RequestBody CourseModule courseModule) throws URISyntaxException {
        log.debug("REST request to save CourseModule : {}", courseModule);
        if (courseModule.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("courseModule", "idexists", "A new courseModule cannot already have an ID")).body(null);
        }
        CourseModule result = courseModuleRepository.save(courseModule);
        return ResponseEntity.created(new URI("/api/course-modules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("courseModule", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /course-modules : Updates an existing courseModule.
     *
     * @param courseModule the courseModule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated courseModule,
     * or with status 400 (Bad Request) if the courseModule is not valid,
     * or with status 500 (Internal Server Error) if the courseModule couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/course-modules")
    @Timed
    public ResponseEntity<CourseModule> updateCourseModule(@Valid @RequestBody CourseModule courseModule) throws URISyntaxException {
        log.debug("REST request to update CourseModule : {}", courseModule);
        if (courseModule.getId() == null) {
            return createCourseModule(courseModule);
        }
        CourseModule result = courseModuleRepository.save(courseModule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("courseModule", courseModule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /course-modules : get all the courseModules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of courseModules in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/course-modules")
    @Timed
    public ResponseEntity<List<CourseModule>> getAllCourseModules(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CourseModules");
        Page<CourseModule> page = courseModuleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/course-modules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /course-modules/:id : get the "id" courseModule.
     *
     * @param id the id of the courseModule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the courseModule, or with status 404 (Not Found)
     */
    @GetMapping("/course-modules/{id}")
    @Timed
    public ResponseEntity<CourseModule> getCourseModule(@PathVariable Long id) {
        log.debug("REST request to get CourseModule : {}", id);
        CourseModule courseModule = courseModuleRepository.findOne(id);
        return Optional.ofNullable(courseModule)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /course-modules/:id : delete the "id" courseModule.
     *
     * @param id the id of the courseModule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/course-modules/{id}")
    @Timed
    public ResponseEntity<Void> deleteCourseModule(@PathVariable Long id) {
        log.debug("REST request to delete CourseModule : {}", id);
        courseModuleRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("courseModule", id.toString())).build();
    }

}
