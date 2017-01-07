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
import com.miu.domain.ResearchPaper;
import com.miu.repository.ResearchPaperRepository;
import com.miu.web.rest.util.HeaderUtil;
import com.miu.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing ResearchPaper.
 */
@RestController
@RequestMapping("/api")
public class ResearchPaperResource {

	private final Logger LOGGER = LoggerFactory.getLogger(ResearchPaperResource.class);

	@Inject
	private ResearchPaperRepository researchPaperRepository;

	/**
	 * POST /research-papers : Create a new researchPaper.
	 *
	 * @param researchPaper
	 *            the researchPaper to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new researchPaper, or with status 400 (Bad Request) if the
	 *         researchPaper has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/research-papers")
	@Timed
	public ResponseEntity<ResearchPaper> createResearchPaper(@Valid @RequestBody ResearchPaper researchPaper)
			throws URISyntaxException {
		LOGGER.debug("REST request to save ResearchPaper : {}", researchPaper);

		if (researchPaper.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("researchPaper", "idexists",
					"A new researchPaper cannot already have an ID")).body(null);
		}

		ResearchPaper result = researchPaperRepository.save(researchPaper);
		return ResponseEntity.created(new URI("/api/research-papers/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("researchPaper", result.getId().toString())).body(result);
	}

	/**
	 * DELETE /research-papers/:id : delete the "id" researchPaper.
	 *
	 * @param id
	 *            the id of the researchPaper to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/research-papers/{id}")
	@Timed
	public ResponseEntity<Void> deleteResearchPaper(@PathVariable Long id) {
		LOGGER.debug("REST request to delete ResearchPaper : {}", id);
		researchPaperRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("researchPaper", id.toString()))
				.build();
	}

	/**
	 * GET /research-papers : get all the researchPapers.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         researchPapers in body
	 * @throws URISyntaxException
	 *             if there is an error to generate the pagination HTTP headers
	 */
	@GetMapping("/research-papers")
	@Timed
	public ResponseEntity<List<ResearchPaper>> getAllResearchPapers(@ApiParam Pageable pageable)
			throws URISyntaxException {
		LOGGER.debug("REST request to get a page of ResearchPapers");
		Page<ResearchPaper> page = researchPaperRepository.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/research-papers");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /research-papers/:id : get the "id" researchPaper.
	 *
	 * @param id
	 *            the id of the researchPaper to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         researchPaper, or with status 404 (Not Found)
	 */
	@GetMapping("/research-papers/{id}")
	@Timed
	public ResponseEntity<ResearchPaper> getResearchPaper(@PathVariable Long id) {
		LOGGER.debug("REST request to get ResearchPaper : {}", id);
		ResearchPaper researchPaper = researchPaperRepository.findOne(id);
		return Optional.ofNullable(researchPaper).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * PUT /research-papers : Updates an existing researchPaper.
	 *
	 * @param researchPaper
	 *            the researchPaper to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         researchPaper, or with status 400 (Bad Request) if the
	 *         researchPaper is not valid, or with status 500 (Internal Server
	 *         Error) if the researchPaper couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/research-papers")
	@Timed
	public ResponseEntity<ResearchPaper> updateResearchPaper(@Valid @RequestBody ResearchPaper researchPaper)
			throws URISyntaxException {
		LOGGER.debug("REST request to update ResearchPaper : {}", researchPaper);

		if (researchPaper.getId() == null) {
			return createResearchPaper(researchPaper);
		}

		ResearchPaper result = researchPaperRepository.save(researchPaper);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("researchPaper", researchPaper.getId().toString()))
				.body(result);
	}

}
