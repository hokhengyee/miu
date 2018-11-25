package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.ModuleType;
import com.miu.repository.ModuleTypeRepository;
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
 * REST controller for managing ModuleType.
 */
@RestController
@RequestMapping("/api")
public class ModuleTypeResource {

    private final Logger log = LoggerFactory.getLogger(ModuleTypeResource.class);

    private static final String ENTITY_NAME = "moduleType";

    private final ModuleTypeRepository moduleTypeRepository;

    public ModuleTypeResource(ModuleTypeRepository moduleTypeRepository) {
        this.moduleTypeRepository = moduleTypeRepository;
    }

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
            throw new BadRequestAlertException("A new moduleType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModuleType result = moduleTypeRepository.save(moduleType);
        return ResponseEntity.created(new URI("/api/module-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /module-types : Updates an existing moduleType.
     *
     * @param moduleType the moduleType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated moduleType,
     * or with status 400 (Bad Request) if the moduleType is not valid,
     * or with status 500 (Internal Server Error) if the moduleType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/module-types")
    @Timed
    public ResponseEntity<ModuleType> updateModuleType(@Valid @RequestBody ModuleType moduleType) throws URISyntaxException {
        log.debug("REST request to update ModuleType : {}", moduleType);
        if (moduleType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ModuleType result = moduleTypeRepository.save(moduleType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, moduleType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /module-types : get all the moduleTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of moduleTypes in body
     */
    @GetMapping("/module-types")
    @Timed
    public ResponseEntity<List<ModuleType>> getAllModuleTypes(Pageable pageable) {
        log.debug("REST request to get a page of ModuleTypes");
        Page<ModuleType> page = moduleTypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/module-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
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
        Optional<ModuleType> moduleType = moduleTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(moduleType);
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

        moduleTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
