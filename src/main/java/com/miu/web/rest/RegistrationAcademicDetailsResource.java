package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.RegistrationAcademicDetails;

import com.miu.repository.RegistrationAcademicDetailsRepository;
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
 * REST controller for managing RegistrationAcademicDetails.
 */
@RestController
@RequestMapping("/api")
public class RegistrationAcademicDetailsResource {

	private final Logger LOGGER = LoggerFactory.getLogger(RegistrationAcademicDetailsResource.class);

	@Inject
	private RegistrationAcademicDetailsRepository registrationAcademicDetailsRepository;

	/**
	 * POST /registration-academic-details : Create a new
	 * registrationAcademicDetails.
	 *
	 * @param registrationAcademicDetails
	 *            the registrationAcademicDetails to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new registrationAcademicDetails, or with status 400 (Bad Request)
	 *         if the registrationAcademicDetails has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/registration-academic-details")
	@Timed
	public ResponseEntity<RegistrationAcademicDetails> createRegistrationAcademicDetails(
			@Valid @RequestBody RegistrationAcademicDetails registrationAcademicDetails) throws URISyntaxException {
		LOGGER.debug("REST request to save RegistrationAcademicDetails : {}", registrationAcademicDetails);
		if (registrationAcademicDetails.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("registrationAcademicDetails",
					"idexists", "A new registrationAcademicDetails cannot already have an ID")).body(null);
		}
		RegistrationAcademicDetails result = registrationAcademicDetailsRepository.save(registrationAcademicDetails);
		return ResponseEntity.created(new URI("/api/registration-academic-details/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("registrationAcademicDetails", result.getId().toString()))
				.body(result);
	}

	/**
	 * DELETE /registration-academic-details/:id : delete the "id"
	 * registrationAcademicDetails.
	 *
	 * @param id
	 *            the id of the registrationAcademicDetails to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/registration-academic-details/{id}")
	@Timed
	public ResponseEntity<Void> deleteRegistrationAcademicDetails(@PathVariable Long id) {
		LOGGER.debug("REST request to delete RegistrationAcademicDetails : {}", id);
		registrationAcademicDetailsRepository.delete(id);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityDeletionAlert("registrationAcademicDetails", id.toString())).build();
	}

	@DeleteMapping("/find-registration-academic-details-by-md5/{md5key}")
	@Timed
	public ResponseEntity<Void> deleteRegistrationAcademicDetails(@PathVariable String md5key) {
		LOGGER.debug("REST request to delete RegistrationAcademicDetails : {}", md5key);
		RegistrationAcademicDetails rad = registrationAcademicDetailsRepository.findRADByMd5key(md5key);
		registrationAcademicDetailsRepository.delete(rad.getId());
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityDeletionAlert("registrationAcademicDetails", rad.getId().toString()))
				.build();
	}

	/**
	 * GET /registration-academic-details : get all the
	 * registrationAcademicDetails.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         registrationAcademicDetails in body
	 * @throws URISyntaxException
	 *             if there is an error to generate the pagination HTTP headers
	 */
	@GetMapping("/registration-academic-details")
	@Timed
	public ResponseEntity<List<RegistrationAcademicDetails>> getAllRegistrationAcademicDetails(
			@ApiParam Pageable pageable) throws URISyntaxException {
		LOGGER.debug("REST request to get a page of RegistrationAcademicDetails");
		Page<RegistrationAcademicDetails> page = registrationAcademicDetailsRepository.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/registration-academic-details");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /registration-academic-details/:id : get the "id"
	 * registrationAcademicDetails.
	 *
	 * @param id
	 *            the id of the registrationAcademicDetails to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         registrationAcademicDetails, or with status 404 (Not Found)
	 */
	@GetMapping("/registration-academic-details/{id}")
	@Timed
	public ResponseEntity<RegistrationAcademicDetails> getRegistrationAcademicDetails(@PathVariable Long id) {
		LOGGER.debug("REST request to get RegistrationAcademicDetails : {}", id);
		RegistrationAcademicDetails registrationAcademicDetails = registrationAcademicDetailsRepository.findOne(id);
		return Optional.ofNullable(registrationAcademicDetails)
				.map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * GET /registration-academic-details/:id : get the "id"
	 * registrationAcademicDetails.
	 *
	 * @param id
	 *            the id of the registrationAcademicDetails to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         registrationAcademicDetails, or with status 404 (Not Found)
	 */
	@GetMapping("/find-registration-academic-details-by-md5/{md5key}")
	@Timed
	public ResponseEntity<RegistrationAcademicDetails> getRegistrationAcademicDetailsByMd5key(
			@PathVariable String md5key) {
		LOGGER.debug("REST request to get RegistrationAcademicDetails : {}", md5key);
		RegistrationAcademicDetails registrationAcademicDetails = registrationAcademicDetailsRepository
				.findRADByMd5key(md5key);
		return Optional.ofNullable(registrationAcademicDetails)
				.map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * PUT /registration-academic-details : Updates an existing
	 * registrationAcademicDetails.
	 *
	 * @param registrationAcademicDetails
	 *            the registrationAcademicDetails to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         registrationAcademicDetails, or with status 400 (Bad Request) if
	 *         the registrationAcademicDetails is not valid, or with status 500
	 *         (Internal Server Error) if the registrationAcademicDetails
	 *         couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/registration-academic-details")
	@Timed
	public ResponseEntity<RegistrationAcademicDetails> updateRegistrationAcademicDetails(
			@Valid @RequestBody RegistrationAcademicDetails registrationAcademicDetails) throws URISyntaxException {
		LOGGER.debug("REST request to update RegistrationAcademicDetails : {}", registrationAcademicDetails);
		if (registrationAcademicDetails.getId() == null) {
			return createRegistrationAcademicDetails(registrationAcademicDetails);
		}
		RegistrationAcademicDetails result = registrationAcademicDetailsRepository.save(registrationAcademicDetails);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("registrationAcademicDetails",
				registrationAcademicDetails.getId().toString())).body(result);
	}

}
