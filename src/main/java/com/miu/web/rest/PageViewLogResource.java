package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.PageViewLog;
import com.miu.repository.PageViewLogRepository;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PageViewLog.
 */
@RestController
@RequestMapping("/api")
public class PageViewLogResource {

    private final Logger log = LoggerFactory.getLogger(PageViewLogResource.class);

    private static final String ENTITY_NAME = "pageViewLog";

    private final PageViewLogRepository pageViewLogRepository;

    public PageViewLogResource(PageViewLogRepository pageViewLogRepository) {
        this.pageViewLogRepository = pageViewLogRepository;
    }

    /**
     * POST  /page-view-logs : Create a new pageViewLog.
     *
     * @param pageViewLog the pageViewLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pageViewLog, or with status 400 (Bad Request) if the pageViewLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/page-view-logs")
    @Timed
    public ResponseEntity<PageViewLog> createPageViewLog(@RequestBody PageViewLog pageViewLog) throws URISyntaxException {
        log.debug("REST request to save PageViewLog : {}", pageViewLog);
        if (pageViewLog.getId() != null) {
            throw new BadRequestAlertException("A new pageViewLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PageViewLog result = pageViewLogRepository.save(pageViewLog);
        return ResponseEntity.created(new URI("/api/page-view-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /page-view-logs : Updates an existing pageViewLog.
     *
     * @param pageViewLog the pageViewLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pageViewLog,
     * or with status 400 (Bad Request) if the pageViewLog is not valid,
     * or with status 500 (Internal Server Error) if the pageViewLog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/page-view-logs")
    @Timed
    public ResponseEntity<PageViewLog> updatePageViewLog(@RequestBody PageViewLog pageViewLog) throws URISyntaxException {
        log.debug("REST request to update PageViewLog : {}", pageViewLog);
        if (pageViewLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PageViewLog result = pageViewLogRepository.save(pageViewLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pageViewLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /page-view-logs : get all the pageViewLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pageViewLogs in body
     */
    @GetMapping("/page-view-logs")
    @Timed
    public ResponseEntity<List<PageViewLog>> getAllPageViewLogs(Pageable pageable) {
        log.debug("REST request to get a page of PageViewLogs");
        Page<PageViewLog> page = pageViewLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/page-view-logs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /page-view-logs/:id : get the "id" pageViewLog.
     *
     * @param id the id of the pageViewLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pageViewLog, or with status 404 (Not Found)
     */
    @GetMapping("/page-view-logs/{id}")
    @Timed
    public ResponseEntity<PageViewLog> getPageViewLog(@PathVariable Long id) {
        log.debug("REST request to get PageViewLog : {}", id);
        Optional<PageViewLog> pageViewLog = pageViewLogRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pageViewLog);
    }

    /**
     * DELETE  /page-view-logs/:id : delete the "id" pageViewLog.
     *
     * @param id the id of the pageViewLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/page-view-logs/{id}")
    @Timed
    public ResponseEntity<Void> deletePageViewLog(@PathVariable Long id) {
        log.debug("REST request to delete PageViewLog : {}", id);

        pageViewLogRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
