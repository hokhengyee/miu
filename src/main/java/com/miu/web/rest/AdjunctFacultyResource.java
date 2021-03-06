package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.AdjunctFaculty;

import com.miu.repository.AdjunctFacultyRepository;
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
 * REST controller for managing AdjunctFaculty.
 */
@RestController
@RequestMapping("/api")
public class AdjunctFacultyResource {

    private final Logger log = LoggerFactory.getLogger(AdjunctFacultyResource.class);
        
    @Inject
    private AdjunctFacultyRepository adjunctFacultyRepository;

    /**
     * POST  /adjunct-faculties : Create a new adjunctFaculty.
     *
     * @param adjunctFaculty the adjunctFaculty to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adjunctFaculty, or with status 400 (Bad Request) if the adjunctFaculty has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/adjunct-faculties")
    @Timed
    public ResponseEntity<AdjunctFaculty> createAdjunctFaculty(@Valid @RequestBody AdjunctFaculty adjunctFaculty) throws URISyntaxException {
        log.debug("REST request to save AdjunctFaculty : {}", adjunctFaculty);
        if (adjunctFaculty.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("adjunctFaculty", "idexists", "A new adjunctFaculty cannot already have an ID")).body(null);
        }
        AdjunctFaculty result = adjunctFacultyRepository.save(adjunctFaculty);
        return ResponseEntity.created(new URI("/api/adjunct-faculties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("adjunctFaculty", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /adjunct-faculties : Updates an existing adjunctFaculty.
     *
     * @param adjunctFaculty the adjunctFaculty to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adjunctFaculty,
     * or with status 400 (Bad Request) if the adjunctFaculty is not valid,
     * or with status 500 (Internal Server Error) if the adjunctFaculty couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/adjunct-faculties")
    @Timed
    public ResponseEntity<AdjunctFaculty> updateAdjunctFaculty(@Valid @RequestBody AdjunctFaculty adjunctFaculty) throws URISyntaxException {
        log.debug("REST request to update AdjunctFaculty : {}", adjunctFaculty);
        if (adjunctFaculty.getId() == null) {
            return createAdjunctFaculty(adjunctFaculty);
        }
        AdjunctFaculty result = adjunctFacultyRepository.save(adjunctFaculty);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("adjunctFaculty", adjunctFaculty.getId().toString()))
            .body(result);
    }

    /**
     * GET  /adjunct-faculties : get all the adjunctFaculties.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of adjunctFaculties in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/adjunct-faculties")
    @Timed
    public ResponseEntity<List<AdjunctFaculty>> getAllAdjunctFaculties(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of AdjunctFaculties");
        Page<AdjunctFaculty> page = adjunctFacultyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/adjunct-faculties");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /adjunct-faculties/:id : get the "id" adjunctFaculty.
     *
     * @param id the id of the adjunctFaculty to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adjunctFaculty, or with status 404 (Not Found)
     */
    @GetMapping("/adjunct-faculties/{id}")
    @Timed
    public ResponseEntity<AdjunctFaculty> getAdjunctFaculty(@PathVariable Long id) {
        log.debug("REST request to get AdjunctFaculty : {}", id);
        AdjunctFaculty adjunctFaculty = adjunctFacultyRepository.findOne(id);
        return Optional.ofNullable(adjunctFaculty)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /adjunct-faculties/:id : delete the "id" adjunctFaculty.
     *
     * @param id the id of the adjunctFaculty to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/adjunct-faculties/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdjunctFaculty(@PathVariable Long id) {
        log.debug("REST request to delete AdjunctFaculty : {}", id);
        adjunctFacultyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("adjunctFaculty", id.toString())).build();
    }

}
