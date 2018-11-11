package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.Gender;

import com.miu.repository.GenderRepository;
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
 * REST controller for managing Gender.
 */
@RestController
@RequestMapping("/api")
public class GenderResource {

    private final Logger log = LoggerFactory.getLogger(GenderResource.class);
        
    @Inject
    private GenderRepository genderRepository;

    /**
     * POST  /genders : Create a new gender.
     *
     * @param gender the gender to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gender, or with status 400 (Bad Request) if the gender has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/genders")
    @Timed
    public ResponseEntity<Gender> createGender(@Valid @RequestBody Gender gender) throws URISyntaxException {
        log.debug("REST request to save Gender : {}", gender);
        if (gender.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("gender", "idexists", "A new gender cannot already have an ID")).body(null);
        }
        Gender result = genderRepository.save(gender);
        return ResponseEntity.created(new URI("/api/genders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("gender", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /genders : Updates an existing gender.
     *
     * @param gender the gender to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gender,
     * or with status 400 (Bad Request) if the gender is not valid,
     * or with status 500 (Internal Server Error) if the gender couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/genders")
    @Timed
    public ResponseEntity<Gender> updateGender(@Valid @RequestBody Gender gender) throws URISyntaxException {
        log.debug("REST request to update Gender : {}", gender);
        if (gender.getId() == null) {
            return createGender(gender);
        }
        Gender result = genderRepository.save(gender);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("gender", gender.getId().toString()))
            .body(result);
    }

    /**
     * GET  /genders : get all the genders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of genders in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/genders")
    @Timed
    public ResponseEntity<List<Gender>> getAllGenders(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Genders");
        Page<Gender> page = genderRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/genders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /genders/:id : get the "id" gender.
     *
     * @param id the id of the gender to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gender, or with status 404 (Not Found)
     */
    @GetMapping("/genders/{id}")
    @Timed
    public ResponseEntity<Gender> getGender(@PathVariable Long id) {
        log.debug("REST request to get Gender : {}", id);
        Gender gender = genderRepository.findOne(id);
        return Optional.ofNullable(gender)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /genders/:id : delete the "id" gender.
     *
     * @param id the id of the gender to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/genders/{id}")
    @Timed
    public ResponseEntity<Void> deleteGender(@PathVariable Long id) {
        log.debug("REST request to delete Gender : {}", id);
        genderRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("gender", id.toString())).build();
    }

}
