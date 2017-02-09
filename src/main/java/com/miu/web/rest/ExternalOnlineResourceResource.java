package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.ExternalOnlineResource;

import com.miu.repository.ExternalOnlineResourceRepository;
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
 * REST controller for managing ExternalOnlineResource.
 */
@RestController
@RequestMapping("/api")
public class ExternalOnlineResourceResource {

    private final Logger log = LoggerFactory.getLogger(ExternalOnlineResourceResource.class);
        
    @Inject
    private ExternalOnlineResourceRepository externalOnlineResourceRepository;

    /**
     * POST  /external-online-resources : Create a new externalOnlineResource.
     *
     * @param externalOnlineResource the externalOnlineResource to create
     * @return the ResponseEntity with status 201 (Created) and with body the new externalOnlineResource, or with status 400 (Bad Request) if the externalOnlineResource has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/external-online-resources")
    @Timed
    public ResponseEntity<ExternalOnlineResource> createExternalOnlineResource(@Valid @RequestBody ExternalOnlineResource externalOnlineResource) throws URISyntaxException {
        log.debug("REST request to save ExternalOnlineResource : {}", externalOnlineResource);
        if (externalOnlineResource.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("externalOnlineResource", "idexists", "A new externalOnlineResource cannot already have an ID")).body(null);
        }
        ExternalOnlineResource result = externalOnlineResourceRepository.save(externalOnlineResource);
        return ResponseEntity.created(new URI("/api/external-online-resources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("externalOnlineResource", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /external-online-resources : Updates an existing externalOnlineResource.
     *
     * @param externalOnlineResource the externalOnlineResource to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated externalOnlineResource,
     * or with status 400 (Bad Request) if the externalOnlineResource is not valid,
     * or with status 500 (Internal Server Error) if the externalOnlineResource couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/external-online-resources")
    @Timed
    public ResponseEntity<ExternalOnlineResource> updateExternalOnlineResource(@Valid @RequestBody ExternalOnlineResource externalOnlineResource) throws URISyntaxException {
        log.debug("REST request to update ExternalOnlineResource : {}", externalOnlineResource);
        if (externalOnlineResource.getId() == null) {
            return createExternalOnlineResource(externalOnlineResource);
        }
        ExternalOnlineResource result = externalOnlineResourceRepository.save(externalOnlineResource);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("externalOnlineResource", externalOnlineResource.getId().toString()))
            .body(result);
    }

    /**
     * GET  /external-online-resources : get all the externalOnlineResources.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of externalOnlineResources in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/external-online-resources")
    @Timed
    public ResponseEntity<List<ExternalOnlineResource>> getAllExternalOnlineResources(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ExternalOnlineResources");
        Page<ExternalOnlineResource> page = externalOnlineResourceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/external-online-resources");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /external-online-resources/:id : get the "id" externalOnlineResource.
     *
     * @param id the id of the externalOnlineResource to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the externalOnlineResource, or with status 404 (Not Found)
     */
    @GetMapping("/external-online-resources/{id}")
    @Timed
    public ResponseEntity<ExternalOnlineResource> getExternalOnlineResource(@PathVariable Long id) {
        log.debug("REST request to get ExternalOnlineResource : {}", id);
        ExternalOnlineResource externalOnlineResource = externalOnlineResourceRepository.findOne(id);
        return Optional.ofNullable(externalOnlineResource)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /external-online-resources/:id : delete the "id" externalOnlineResource.
     *
     * @param id the id of the externalOnlineResource to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/external-online-resources/{id}")
    @Timed
    public ResponseEntity<Void> deleteExternalOnlineResource(@PathVariable Long id) {
        log.debug("REST request to delete ExternalOnlineResource : {}", id);
        externalOnlineResourceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("externalOnlineResource", id.toString())).build();
    }

}
