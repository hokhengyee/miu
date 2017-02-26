package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.CommonResources;

import com.miu.repository.CommonResourcesRepository;
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
 * REST controller for managing CommonResources.
 */
@RestController
@RequestMapping("/api")
public class CommonResourcesResource {

	private final Logger log = LoggerFactory.getLogger(CommonResourcesResource.class);

	@Inject
	private CommonResourcesRepository commonResourcesRepository;

	/**
	 * POST /common-resources : Create a new commonResources.
	 *
	 * @param commonResources
	 *            the commonResources to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new commonResources, or with status 400 (Bad Request) if the
	 *         commonResources has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/common-resources")
	@Timed
	public ResponseEntity<CommonResources> createCommonResources(@Valid @RequestBody CommonResources commonResources)
			throws URISyntaxException {
		log.debug("REST request to save CommonResources : {}", commonResources);
		if (commonResources.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("commonResources", "idexists",
					"A new commonResources cannot already have an ID")).body(null);
		}
		CommonResources result = commonResourcesRepository.save(commonResources);
		return ResponseEntity.created(new URI("/api/common-resources/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("commonResources", result.getId().toString()))
				.body(result);
	}

	/**
	 * PUT /common-resources : Updates an existing commonResources.
	 *
	 * @param commonResources
	 *            the commonResources to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         commonResources, or with status 400 (Bad Request) if the
	 *         commonResources is not valid, or with status 500 (Internal Server
	 *         Error) if the commonResources couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/common-resources")
	@Timed
	public ResponseEntity<CommonResources> updateCommonResources(@Valid @RequestBody CommonResources commonResources)
			throws URISyntaxException {
		log.debug("REST request to update CommonResources : {}", commonResources);
		if (commonResources.getId() == null) {
			return createCommonResources(commonResources);
		}
		CommonResources result = commonResourcesRepository.save(commonResources);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("commonResources", commonResources.getId().toString()))
				.body(result);
	}

	/**
	 * GET /common-resources : get all the commonResources.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         commonResources in body
	 * @throws URISyntaxException
	 *             if there is an error to generate the pagination HTTP headers
	 */
	@GetMapping("/common-resources")
	@Timed
	public ResponseEntity<List<CommonResources>> getAllCommonResources(@ApiParam Pageable pageable)
			throws URISyntaxException {
		log.debug("REST request to get a page of CommonResources");
		Page<CommonResources> page = commonResourcesRepository.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/common-resources");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	@GetMapping("/ordered-common-resources")
	@Timed
	public ResponseEntity<List<CommonResources>> getAllCommonResourcesOrdered() throws URISyntaxException {
		log.debug("REST request to get a page of ordered CommonResources");
		List<CommonResources> crList = commonResourcesRepository.findAllByOrderByDisplayOrder();
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(crList, headers, HttpStatus.OK);
	}

	/**
	 * GET /common-resources/:id : get the "id" commonResources.
	 *
	 * @param id
	 *            the id of the commonResources to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         commonResources, or with status 404 (Not Found)
	 */
	@GetMapping("/common-resources/{id}")
	@Timed
	public ResponseEntity<CommonResources> getCommonResources(@PathVariable Long id) {
		log.debug("REST request to get CommonResources : {}", id);
		CommonResources commonResources = commonResourcesRepository.findOne(id);
		return Optional.ofNullable(commonResources).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /common-resources/:id : delete the "id" commonResources.
	 *
	 * @param id
	 *            the id of the commonResources to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/common-resources/{id}")
	@Timed
	public ResponseEntity<Void> deleteCommonResources(@PathVariable Long id) {
		log.debug("REST request to delete CommonResources : {}", id);
		commonResourcesRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("commonResources", id.toString()))
				.build();
	}

}
