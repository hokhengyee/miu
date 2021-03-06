package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.CustomStudentReportType;

import com.miu.repository.CustomStudentReportTypeRepository;
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
 * REST controller for managing CustomStudentReportType.
 */
@RestController
@RequestMapping("/api")
public class CustomStudentReportTypeResource {

    private final Logger log = LoggerFactory.getLogger(CustomStudentReportTypeResource.class);
        
    @Inject
    private CustomStudentReportTypeRepository customStudentReportTypeRepository;

    /**
     * POST  /custom-student-report-types : Create a new customStudentReportType.
     *
     * @param customStudentReportType the customStudentReportType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customStudentReportType, or with status 400 (Bad Request) if the customStudentReportType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/custom-student-report-types")
    @Timed
    public ResponseEntity<CustomStudentReportType> createCustomStudentReportType(@Valid @RequestBody CustomStudentReportType customStudentReportType) throws URISyntaxException {
        log.debug("REST request to save CustomStudentReportType : {}", customStudentReportType);
        if (customStudentReportType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("customStudentReportType", "idexists", "A new customStudentReportType cannot already have an ID")).body(null);
        }
        CustomStudentReportType result = customStudentReportTypeRepository.save(customStudentReportType);
        return ResponseEntity.created(new URI("/api/custom-student-report-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("customStudentReportType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /custom-student-report-types : Updates an existing customStudentReportType.
     *
     * @param customStudentReportType the customStudentReportType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customStudentReportType,
     * or with status 400 (Bad Request) if the customStudentReportType is not valid,
     * or with status 500 (Internal Server Error) if the customStudentReportType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/custom-student-report-types")
    @Timed
    public ResponseEntity<CustomStudentReportType> updateCustomStudentReportType(@Valid @RequestBody CustomStudentReportType customStudentReportType) throws URISyntaxException {
        log.debug("REST request to update CustomStudentReportType : {}", customStudentReportType);
        if (customStudentReportType.getId() == null) {
            return createCustomStudentReportType(customStudentReportType);
        }
        CustomStudentReportType result = customStudentReportTypeRepository.save(customStudentReportType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("customStudentReportType", customStudentReportType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /custom-student-report-types : get all the customStudentReportTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of customStudentReportTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/custom-student-report-types")
    @Timed
    public ResponseEntity<List<CustomStudentReportType>> getAllCustomStudentReportTypes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CustomStudentReportTypes");
        Page<CustomStudentReportType> page = customStudentReportTypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/custom-student-report-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /custom-student-report-types/:id : get the "id" customStudentReportType.
     *
     * @param id the id of the customStudentReportType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customStudentReportType, or with status 404 (Not Found)
     */
    @GetMapping("/custom-student-report-types/{id}")
    @Timed
    public ResponseEntity<CustomStudentReportType> getCustomStudentReportType(@PathVariable Long id) {
        log.debug("REST request to get CustomStudentReportType : {}", id);
        CustomStudentReportType customStudentReportType = customStudentReportTypeRepository.findOne(id);
        return Optional.ofNullable(customStudentReportType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /custom-student-report-types/:id : delete the "id" customStudentReportType.
     *
     * @param id the id of the customStudentReportType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/custom-student-report-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomStudentReportType(@PathVariable Long id) {
        log.debug("REST request to delete CustomStudentReportType : {}", id);
        customStudentReportTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("customStudentReportType", id.toString())).build();
    }

}
