package com.miu.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.MinisterialWorkExperience;
import com.miu.repository.MinisterialWorkExperienceRepository;
import com.miu.web.rest.util.HeaderUtil;
import com.miu.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing MinisterialWorkExperience.
 */
@RestController
@RequestMapping("/api")
public class MinisterialWorkExperienceResource {

	private final Logger LOGGER = LoggerFactory.getLogger(MinisterialWorkExperienceResource.class);

	@Inject
	private MinisterialWorkExperienceRepository ministerialWorkExperienceRepository;

	/**
	 * POST /ministerial-work-experiences : Create a new
	 * ministerialWorkExperience.
	 *
	 * @param ministerialWorkExperience
	 *            the ministerialWorkExperience to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new ministerialWorkExperience, or with status 400 (Bad Request)
	 *         if the ministerialWorkExperience has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/ministerial-work-experiences")
	@Timed
	public ResponseEntity<MinisterialWorkExperience> createMinisterialWorkExperience(
			@Valid @RequestBody MinisterialWorkExperience ministerialWorkExperience) throws URISyntaxException {
		LOGGER.debug("REST request to save MinisterialWorkExperience : {}", ministerialWorkExperience);
		if (ministerialWorkExperience.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ministerialWorkExperience",
					"idexists", "A new ministerialWorkExperience cannot already have an ID")).body(null);
		}
		MinisterialWorkExperience result = ministerialWorkExperienceRepository.save(ministerialWorkExperience);
		return ResponseEntity.created(new URI("/api/ministerial-work-experiences/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("ministerialWorkExperience", result.getId().toString()))
				.body(result);
	}

	/**
	 * DELETE /ministerial-work-experiences/:id : delete the "id"
	 * ministerialWorkExperience.
	 *
	 * @param id
	 *            the id of the ministerialWorkExperience to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/ministerial-work-experiences/{id}")
	@Timed
	public ResponseEntity<Void> deleteMinisterialWorkExperience(@PathVariable Long id) {
		LOGGER.debug("REST request to delete MinisterialWorkExperience : {}", id);
		ministerialWorkExperienceRepository.delete(id);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityDeletionAlert("ministerialWorkExperience", id.toString())).build();
	}

	@DeleteMapping("/find-ministerial-work-experiences-by-md5/{md5key}")
	@Timed
	public ResponseEntity<Void> deleteMinisterialWorkExperienceByMD5(@PathVariable String md5key) {
		LOGGER.debug("REST request to delete MinisterialWorkExperience : {}", md5key);
		MinisterialWorkExperience mwe = ministerialWorkExperienceRepository.findMWEByMd5key(md5key);
		ministerialWorkExperienceRepository.delete(mwe.getId());
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityDeletionAlert("ministerialWorkExperience", mwe.getId().toString()))
				.build();
	}

	/**
	 * GET /ministerial-work-experiences : get all the
	 * ministerialWorkExperiences.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         ministerialWorkExperiences in body
	 * @throws URISyntaxException
	 *             if there is an error to generate the pagination HTTP headers
	 */
	@GetMapping("/ministerial-work-experiences")
	@Timed
	public ResponseEntity<List<MinisterialWorkExperience>> getAllMinisterialWorkExperiences(@ApiParam Pageable pageable)
			throws URISyntaxException {
		LOGGER.debug("REST request to get a page of MinisterialWorkExperiences");
		Page<MinisterialWorkExperience> page = ministerialWorkExperienceRepository.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ministerial-work-experiences");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /ministerial-work-experiences/:id : get the "id"
	 * ministerialWorkExperience.
	 *
	 * @param id
	 *            the id of the ministerialWorkExperience to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         ministerialWorkExperience, or with status 404 (Not Found)
	 */
	@GetMapping("/ministerial-work-experiences/{id}")
	@Timed
	public ResponseEntity<MinisterialWorkExperience> getMinisterialWorkExperience(@PathVariable Long id) {
		LOGGER.debug("REST request to get MinisterialWorkExperience : {}", id);
		MinisterialWorkExperience ministerialWorkExperience = ministerialWorkExperienceRepository.findOne(id);
		return Optional.ofNullable(ministerialWorkExperience).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/find-ministerial-work-experiences-by-md5/{md5key}")
	@Timed
	public ResponseEntity<MinisterialWorkExperience> getMinisterialWorkExperienceByMD5(@PathVariable String md5key) {
		LOGGER.debug("REST request to get MinisterialWorkExperience : {}", md5key);
		MinisterialWorkExperience ministerialWorkExperience = ministerialWorkExperienceRepository
				.findMWEByMd5key(md5key);
		return Optional.ofNullable(ministerialWorkExperience).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * PUT /ministerial-work-experiences : Updates an existing
	 * ministerialWorkExperience.
	 *
	 * @param ministerialWorkExperience
	 *            the ministerialWorkExperience to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         ministerialWorkExperience, or with status 400 (Bad Request) if
	 *         the ministerialWorkExperience is not valid, or with status 500
	 *         (Internal Server Error) if the ministerialWorkExperience couldnt
	 *         be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/ministerial-work-experiences")
	@Timed
	public ResponseEntity<MinisterialWorkExperience> updateMinisterialWorkExperience(
			@Valid @RequestBody MinisterialWorkExperience ministerialWorkExperience) throws URISyntaxException {
		LOGGER.debug("REST request to update MinisterialWorkExperience : {}", ministerialWorkExperience);
		if (ministerialWorkExperience.getId() == null) {
			return createMinisterialWorkExperience(ministerialWorkExperience);
		}
		MinisterialWorkExperience result = ministerialWorkExperienceRepository.save(ministerialWorkExperience);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("ministerialWorkExperience",
				ministerialWorkExperience.getId().toString())).body(result);
	}

}
