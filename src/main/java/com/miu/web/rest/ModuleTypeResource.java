package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.ModuleType;

import com.miu.repository.ModuleTypeRepository;
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
 * REST controller for managing ModuleType.
 */
@RestController
@RequestMapping("/api")
public class ModuleTypeResource {

    private final Logger log = LoggerFactory.getLogger(ModuleTypeResource.class);
        
    @Inject
    private ModuleTypeRepository moduleTypeRepository;

    /**
     * POST  /module-types : Create a new moduleType.
     *
     * @param moduleType the moduleType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new moduleType, or with status 400 (Bad Request) if the moduleType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/module-types")
    @Timed
    public ResponseEntity<ModuleType> createModuleType(@Valid @RequestBody ModuleType moduleType) throws URISyntaxException {
        log.debug("REST request to save ModuleType : {}", moduleType);
        if (moduleType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("moduleType", "idexists", "A new moduleType cannot already have an ID")).body(null);
        }
        ModuleType result = moduleTypeRepository.save(moduleType);
        return ResponseEntity.created(new URI("/api/module-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("moduleType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /module-types : Updates an existing moduleType.
     *
     * @param moduleType the moduleType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated moduleType,
     * or with status 400 (Bad Request) if the moduleType is not valid,
     * or with status 500 (Internal Server Error) if the moduleType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/module-types")
    @Timed
    public ResponseEntity<ModuleType> updateModuleType(@Valid @RequestBody ModuleType moduleType) throws URISyntaxException {
        log.debug("REST request to update ModuleType : {}", moduleType);
        if (moduleType.getId() == null) {
            return createModuleType(moduleType);
        }
        ModuleType result = moduleTypeRepository.save(moduleType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("moduleType", moduleType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /module-types : get all the moduleTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of moduleTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/module-types")
    @Timed
    public ResponseEntity<List<ModuleType>> getAllModuleTypes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ModuleTypes");
        Page<ModuleType> page = moduleTypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/module-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /module-types/:id : get the "id" moduleType.
     *
     * @param id the id of the moduleType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the moduleType, or with status 404 (Not Found)
     */
    @GetMapping("/module-types/{id}")
    @Timed
    public ResponseEntity<ModuleType> getModuleType(@PathVariable Long id) {
        log.debug("REST request to get ModuleType : {}", id);
        ModuleType moduleType = moduleTypeRepository.findOne(id);
        return Optional.ofNullable(moduleType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /module-types/:id : delete the "id" moduleType.
     *
     * @param id the id of the moduleType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/module-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteModuleType(@PathVariable Long id) {
        log.debug("REST request to delete ModuleType : {}", id);
        moduleTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("moduleType", id.toString())).build();
    }

}
