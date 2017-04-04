package com.miu.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

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
import com.miu.domain.AcademicCertificate;
import com.miu.repository.AcademicCertificateRepository;
import com.miu.web.rest.util.HeaderUtil;
import com.miu.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing AcademicCertificate.
 */
@RestController
@RequestMapping("/api")
public class AcademicCertificateResource {

	@Inject
	private AcademicCertificateRepository academicCertificateRepository;

	private final Logger LOGGER = LoggerFactory.getLogger(AcademicCertificateResource.class);

	/**
	 * POST /academic-certificates : Create a new academicCertificate.
	 *
	 * @param academicCertificate
	 *            the academicCertificate to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new academicCertificate, or with status 400 (Bad Request) if the
	 *         academicCertificate has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/academic-certificates")
	@Timed
	public ResponseEntity<AcademicCertificate> createAcademicCertificate(
			@RequestBody AcademicCertificate academicCertificate) throws URISyntaxException {
		LOGGER.debug("REST request to save AcademicCertificate : {}", academicCertificate);
		if (academicCertificate.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("academicCertificate", "idexists",
					"A new academicCertificate cannot already have an ID")).body(null);
		}
		AcademicCertificate result = academicCertificateRepository.save(academicCertificate);
		return ResponseEntity.created(new URI("/api/academic-certificates/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("academicCertificate", result.getId().toString()))
				.body(result);
	}

	/**
	 * DELETE /academic-certificates/:id : delete the "id" academicCertificate.
	 *
	 * @param id
	 *            the id of the academicCertificate to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/academic-certificates/{id}")
	@Timed
	public ResponseEntity<Void> deleteAcademicCertificate(@PathVariable Long id) {
		LOGGER.debug("REST request to delete AcademicCertificate : {}", id);
		academicCertificateRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("academicCertificate", id.toString()))
				.build();
	}

	@DeleteMapping("/find-academic-certificates-by-md5/{md5key}")
	@Timed
	public ResponseEntity<Void> deleteAcademicCertificateByMD5(@PathVariable String md5key) {
		LOGGER.debug("REST request to delete AcademicCertificate : {}", md5key);
		AcademicCertificate ac = academicCertificateRepository.findACByMd5key(md5key);
		academicCertificateRepository.delete(ac.getId());
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityDeletionAlert("academicCertificate", ac.getId().toString())).build();
	}

	/**
	 * GET /academic-certificates/:id : get the "id" academicCertificate.
	 *
	 * @param id
	 *            the id of the academicCertificate to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         academicCertificate, or with status 404 (Not Found)
	 */
	@GetMapping("/academic-certificates/{id}")
	@Timed
	public ResponseEntity<AcademicCertificate> getAcademicCertificate(@PathVariable Long id) {
		LOGGER.debug("REST request to get AcademicCertificate : {}", id);
		AcademicCertificate academicCertificate = academicCertificateRepository.findOne(id);
		return Optional.ofNullable(academicCertificate).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/find-academic-certificates-by-md5/{md5key}")
	@Timed
	public ResponseEntity<AcademicCertificate> getAcademicCertificateByMD5(@PathVariable String md5key) {
		LOGGER.debug("REST request to get MinisterialWorkExperience : {}", md5key);
		AcademicCertificate academicCertificate = academicCertificateRepository.findACByMd5key(md5key);
		return Optional.ofNullable(academicCertificate).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * GET /academic-certificates : get all the academicCertificates.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         academicCertificates in body
	 * @throws URISyntaxException
	 *             if there is an error to generate the pagination HTTP headers
	 */
	@GetMapping("/academic-certificates")
	@Timed
	public ResponseEntity<List<AcademicCertificate>> getAllAcademicCertificates(@ApiParam Pageable pageable)
			throws URISyntaxException {
		LOGGER.debug("REST request to get a page of AcademicCertificates");
		Page<AcademicCertificate> page = academicCertificateRepository.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/academic-certificates");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * PUT /academic-certificates : Updates an existing academicCertificate.
	 *
	 * @param academicCertificate
	 *            the academicCertificate to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         academicCertificate, or with status 400 (Bad Request) if the
	 *         academicCertificate is not valid, or with status 500 (Internal
	 *         Server Error) if the academicCertificate couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/academic-certificates")
	@Timed
	public ResponseEntity<AcademicCertificate> updateAcademicCertificate(
			@RequestBody AcademicCertificate academicCertificate) throws URISyntaxException {
		LOGGER.debug("REST request to update AcademicCertificate : {}", academicCertificate);
		if (academicCertificate.getId() == null) {
			return createAcademicCertificate(academicCertificate);
		}
		AcademicCertificate result = academicCertificateRepository.save(academicCertificate);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert("academicCertificate", academicCertificate.getId().toString()))
				.body(result);
	}

}
