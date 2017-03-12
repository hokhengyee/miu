package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.OnlineApplication;

import com.miu.repository.OnlineApplicationRepository;
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
 * REST controller for managing OnlineApplication.
 */
@RestController
@RequestMapping("/api")
public class OnlineApplicationResource {

	private final Logger LOGGER = LoggerFactory.getLogger(OnlineApplicationResource.class);

	@Inject
	private OnlineApplicationRepository onlineApplicationRepository;

	/**
	 * POST /online-applications : Create a new onlineApplication.
	 *
	 * @param onlineApplication
	 *            the onlineApplication to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new onlineApplication, or with status 400 (Bad Request) if the
	 *         onlineApplication has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/online-applications")
	@Timed
	public ResponseEntity<OnlineApplication> createOnlineApplication(
			@Valid @RequestBody OnlineApplication onlineApplication) throws URISyntaxException {
		LOGGER.debug("REST request to save OnlineApplication : {}", onlineApplication);
		if (onlineApplication.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("onlineApplication", "idexists",
					"A new onlineApplication cannot already have an ID")).body(null);
		}

		OnlineApplication tmpOA = onlineApplicationRepository.findOAByMd5key(onlineApplication.getMd5key());
		if (tmpOA != null) {
			LOGGER.error("tmpOA: " + tmpOA.toString());
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("onlineApplication", "idexists",
					"This email has been registered. Please try a different email.")).body(null);
		}

		OnlineApplication result = onlineApplicationRepository.save(onlineApplication);
		return ResponseEntity.created(new URI("/api/online-applications/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("onlineApplication", result.getId().toString()))
				.body(result);
	}

	/**
	 * DELETE /online-applications/:id : delete the "id" onlineApplication.
	 *
	 * @param id
	 *            the id of the onlineApplication to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/online-applications/{id}")
	@Timed
	public ResponseEntity<Void> deleteOnlineApplication(@PathVariable Long id) {
		LOGGER.debug("REST request to delete OnlineApplication : {}", id);
		onlineApplicationRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("onlineApplication", id.toString()))
				.build();
	}

	/**
	 * GET /online-applications : get all the onlineApplications.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         onlineApplications in body
	 * @throws URISyntaxException
	 *             if there is an error to generate the pagination HTTP headers
	 */
	@GetMapping("/online-applications")
	@Timed
	public ResponseEntity<List<OnlineApplication>> getAllOnlineApplications(@ApiParam Pageable pageable)
			throws URISyntaxException {
		LOGGER.debug("REST request to get a page of OnlineApplications");
		Page<OnlineApplication> page = onlineApplicationRepository.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/online-applications");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /online-applications/:id : get the "id" onlineApplication.
	 *
	 * @param id
	 *            the id of the onlineApplication to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         onlineApplication, or with status 404 (Not Found)
	 */
	@GetMapping("/online-applications/{id}")
	@Timed
	public ResponseEntity<OnlineApplication> getOnlineApplication(@PathVariable Long id) {
		LOGGER.debug("REST request to get OnlineApplication : {}", id);
		OnlineApplication onlineApplication = onlineApplicationRepository.findOne(id);
		return Optional.ofNullable(onlineApplication).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * PUT /online-applications : Updates an existing onlineApplication.
	 *
	 * @param onlineApplication
	 *            the onlineApplication to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         onlineApplication, or with status 400 (Bad Request) if the
	 *         onlineApplication is not valid, or with status 500 (Internal
	 *         Server Error) if the onlineApplication couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/online-applications")
	@Timed
	public ResponseEntity<OnlineApplication> updateOnlineApplication(
			@Valid @RequestBody OnlineApplication onlineApplication) throws URISyntaxException {
		LOGGER.debug("REST request to update OnlineApplication : {}", onlineApplication);
		if (onlineApplication.getId() == null) {
			return createOnlineApplication(onlineApplication);
		}

		OnlineApplication result = onlineApplicationRepository.save(onlineApplication);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("onlineApplication", onlineApplication.getId().toString()))
				.body(result);
	}

}
