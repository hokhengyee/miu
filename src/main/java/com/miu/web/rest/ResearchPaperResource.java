package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.ResearchPaper;
import com.miu.repository.ResearchPaperRepository;
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
 * REST controller for managing ResearchPaper.
 */
@RestController
@RequestMapping("/api")
public class ResearchPaperResource {

    private final Logger log = LoggerFactory.getLogger(ResearchPaperResource.class);

    private static final String ENTITY_NAME = "researchPaper";

    private final ResearchPaperRepository researchPaperRepository;

    public ResearchPaperResource(ResearchPaperRepository researchPaperRepository) {
        this.researchPaperRepository = researchPaperRepository;
    }

    /**
     * POST  /research-papers : Create a new researchPaper.
     *
     * @param researchPaper the researchPaper to create
     * @return the ResponseEntity with status 201 (Created) and with body the new researchPaper, or with status 400 (Bad Request) if the researchPaper has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/research-papers")
    @Timed
    public ResponseEntity<ResearchPaper> createResearchPaper(@Valid @RequestBody ResearchPaper researchPaper) throws URISyntaxException {
        log.debug("REST request to save ResearchPaper : {}", researchPaper);
        if (researchPaper.getId() != null) {
            throw new BadRequestAlertException("A new researchPaper cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResearchPaper result = researchPaperRepository.save(researchPaper);
        return ResponseEntity.created(new URI("/api/research-papers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /research-papers : Updates an existing researchPaper.
     *
     * @param researchPaper the researchPaper to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated researchPaper,
     * or with status 400 (Bad Request) if the researchPaper is not valid,
     * or with status 500 (Internal Server Error) if the researchPaper couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/research-papers")
    @Timed
    public ResponseEntity<ResearchPaper> updateResearchPaper(@Valid @RequestBody ResearchPaper researchPaper) throws URISyntaxException {
        log.debug("REST request to update ResearchPaper : {}", researchPaper);
        if (researchPaper.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResearchPaper result = researchPaperRepository.save(researchPaper);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, researchPaper.getId().toString()))
            .body(result);
    }

    /**
     * GET  /research-papers : get all the researchPapers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of researchPapers in body
     */
    @GetMapping("/research-papers")
    @Timed
    public ResponseEntity<List<ResearchPaper>> getAllResearchPapers(Pageable pageable) {
        log.debug("REST request to get a page of ResearchPapers");
        Page<ResearchPaper> page = researchPaperRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/research-papers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /research-papers/:id : get the "id" researchPaper.
     *
     * @param id the id of the researchPaper to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the researchPaper, or with status 404 (Not Found)
     */
    @GetMapping("/research-papers/{id}")
    @Timed
    public ResponseEntity<ResearchPaper> getResearchPaper(@PathVariable Long id) {
        log.debug("REST request to get ResearchPaper : {}", id);
        Optional<ResearchPaper> researchPaper = researchPaperRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(researchPaper);
    }

    /**
     * DELETE  /research-papers/:id : delete the "id" researchPaper.
     *
     * @param id the id of the researchPaper to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/research-papers/{id}")
    @Timed
    public ResponseEntity<Void> deleteResearchPaper(@PathVariable Long id) {
        log.debug("REST request to delete ResearchPaper : {}", id);

        researchPaperRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
