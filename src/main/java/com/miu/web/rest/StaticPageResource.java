package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.StaticPage;
import com.miu.repository.StaticPageRepository;
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
 * REST controller for managing StaticPage.
 */
@RestController
@RequestMapping("/api")
public class StaticPageResource {

    private final Logger log = LoggerFactory.getLogger(StaticPageResource.class);

    private static final String ENTITY_NAME = "staticPage";

    private final StaticPageRepository staticPageRepository;

    public StaticPageResource(StaticPageRepository staticPageRepository) {
        this.staticPageRepository = staticPageRepository;
    }

    /**
     * POST  /static-pages : Create a new staticPage.
     *
     * @param staticPage the staticPage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new staticPage, or with status 400 (Bad Request) if the staticPage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/static-pages")
    @Timed
    public ResponseEntity<StaticPage> createStaticPage(@Valid @RequestBody StaticPage staticPage) throws URISyntaxException {
        log.debug("REST request to save StaticPage : {}", staticPage);
        if (staticPage.getId() != null) {
            throw new BadRequestAlertException("A new staticPage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StaticPage result = staticPageRepository.save(staticPage);
        return ResponseEntity.created(new URI("/api/static-pages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /static-pages : Updates an existing staticPage.
     *
     * @param staticPage the staticPage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated staticPage,
     * or with status 400 (Bad Request) if the staticPage is not valid,
     * or with status 500 (Internal Server Error) if the staticPage couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/static-pages")
    @Timed
    public ResponseEntity<StaticPage> updateStaticPage(@Valid @RequestBody StaticPage staticPage) throws URISyntaxException {
        log.debug("REST request to update StaticPage : {}", staticPage);
        if (staticPage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StaticPage result = staticPageRepository.save(staticPage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, staticPage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /static-pages : get all the staticPages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of staticPages in body
     */
    @GetMapping("/static-pages")
    @Timed
    public ResponseEntity<List<StaticPage>> getAllStaticPages(Pageable pageable) {
        log.debug("REST request to get a page of StaticPages");
        Page<StaticPage> page = staticPageRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/static-pages");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /static-pages/:id : get the "id" staticPage.
     *
     * @param id the id of the staticPage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the staticPage, or with status 404 (Not Found)
     */
    @GetMapping("/static-pages/{id}")
    @Timed
    public ResponseEntity<StaticPage> getStaticPage(@PathVariable Long id) {
        log.debug("REST request to get StaticPage : {}", id);
        Optional<StaticPage> staticPage = staticPageRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(staticPage);
    }

    /**
     * DELETE  /static-pages/:id : delete the "id" staticPage.
     *
     * @param id the id of the staticPage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/static-pages/{id}")
    @Timed
    public ResponseEntity<Void> deleteStaticPage(@PathVariable Long id) {
        log.debug("REST request to delete StaticPage : {}", id);

        staticPageRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
