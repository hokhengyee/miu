package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.StaticPageType;

import com.miu.repository.StaticPageTypeRepository;
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
 * REST controller for managing StaticPageType.
 */
@RestController
@RequestMapping("/api")
public class StaticPageTypeResource {

    private final Logger log = LoggerFactory.getLogger(StaticPageTypeResource.class);
        
    @Inject
    private StaticPageTypeRepository staticPageTypeRepository;

    /**
     * POST  /static-page-types : Create a new staticPageType.
     *
     * @param staticPageType the staticPageType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new staticPageType, or with status 400 (Bad Request) if the staticPageType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/static-page-types")
    @Timed
    public ResponseEntity<StaticPageType> createStaticPageType(@Valid @RequestBody StaticPageType staticPageType) throws URISyntaxException {
        log.debug("REST request to save StaticPageType : {}", staticPageType);
        if (staticPageType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("staticPageType", "idexists", "A new staticPageType cannot already have an ID")).body(null);
        }
        StaticPageType result = staticPageTypeRepository.save(staticPageType);
        return ResponseEntity.created(new URI("/api/static-page-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("staticPageType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /static-page-types : Updates an existing staticPageType.
     *
     * @param staticPageType the staticPageType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated staticPageType,
     * or with status 400 (Bad Request) if the staticPageType is not valid,
     * or with status 500 (Internal Server Error) if the staticPageType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/static-page-types")
    @Timed
    public ResponseEntity<StaticPageType> updateStaticPageType(@Valid @RequestBody StaticPageType staticPageType) throws URISyntaxException {
        log.debug("REST request to update StaticPageType : {}", staticPageType);
        if (staticPageType.getId() == null) {
            return createStaticPageType(staticPageType);
        }
        StaticPageType result = staticPageTypeRepository.save(staticPageType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("staticPageType", staticPageType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /static-page-types : get all the staticPageTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of staticPageTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/static-page-types")
    @Timed
    public ResponseEntity<List<StaticPageType>> getAllStaticPageTypes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of StaticPageTypes");
        Page<StaticPageType> page = staticPageTypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/static-page-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /static-page-types/:id : get the "id" staticPageType.
     *
     * @param id the id of the staticPageType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the staticPageType, or with status 404 (Not Found)
     */
    @GetMapping("/static-page-types/{id}")
    @Timed
    public ResponseEntity<StaticPageType> getStaticPageType(@PathVariable Long id) {
        log.debug("REST request to get StaticPageType : {}", id);
        StaticPageType staticPageType = staticPageTypeRepository.findOne(id);
        return Optional.ofNullable(staticPageType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /static-page-types/:id : delete the "id" staticPageType.
     *
     * @param id the id of the staticPageType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/static-page-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteStaticPageType(@PathVariable Long id) {
        log.debug("REST request to delete StaticPageType : {}", id);
        staticPageTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("staticPageType", id.toString())).build();
    }

}
