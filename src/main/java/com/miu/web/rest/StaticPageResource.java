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
import com.miu.domain.StaticPage;
import com.miu.repository.StaticPageRepository;
import com.miu.web.rest.util.HeaderUtil;
import com.miu.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing StaticPage.
 */
@RestController
@RequestMapping("/api")
public class StaticPageResource {

	private final Logger LOGGER = LoggerFactory.getLogger(StaticPageResource.class);

	@Inject
	private StaticPageRepository staticPageRepository;

	/**
	 * POST /static-pages : Create a new staticPage.
	 *
	 * @param staticPage
	 *            the staticPage to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new staticPage, or with status 400 (Bad Request) if the
	 *         staticPage has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/static-pages")
	@Timed
	public ResponseEntity<StaticPage> createStaticPage(@Valid @RequestBody StaticPage staticPage)
			throws URISyntaxException {
		LOGGER.debug("REST request to save StaticPage : {}", staticPage);
		
		if (staticPage.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("staticPage", "idexists",
					"A new staticPage cannot already have an ID")).body(null);
		}
		
		StaticPage result = staticPageRepository.save(staticPage);
		return ResponseEntity.created(new URI("/api/static-pages/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("staticPage", result.getId().toString())).body(result);
	}

	/**
	 * DELETE /static-pages/:id : delete the "id" staticPage.
	 *
	 * @param id
	 *            the id of the staticPage to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/static-pages/{id}")
	@Timed
	public ResponseEntity<Void> deleteStaticPage(@PathVariable Long id) {
		LOGGER.debug("REST request to delete StaticPage : {}", id);
		staticPageRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("staticPage", id.toString())).build();
	}

	/**
	 * GET /static-pages : get all the staticPages.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         staticPages in body
	 * @throws URISyntaxException
	 *             if there is an error to generate the pagination HTTP headers
	 */
	@GetMapping("/static-pages")
	@Timed
	public ResponseEntity<List<StaticPage>> getAllStaticPages(@ApiParam Pageable pageable) throws URISyntaxException {
		LOGGER.debug("REST request to get a page of StaticPages");
		Page<StaticPage> page = staticPageRepository.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/static-pages");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /static-pages/:id : get the "id" staticPage.
	 *
	 * @param id
	 *            the id of the staticPage to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         staticPage, or with status 404 (Not Found)
	 */
	@GetMapping("/static-pages/{id}")
	@Timed
	public ResponseEntity<StaticPage> getStaticPage(@PathVariable Long id) {
		LOGGER.debug("REST request to get StaticPage : {}", id);
		StaticPage staticPage = staticPageRepository.findOne(id);
		return Optional.ofNullable(staticPage).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * PUT /static-pages : Updates an existing staticPage.
	 *
	 * @param staticPage
	 *            the staticPage to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         staticPage, or with status 400 (Bad Request) if the staticPage is
	 *         not valid, or with status 500 (Internal Server Error) if the
	 *         staticPage couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/static-pages")
	@Timed
	public ResponseEntity<StaticPage> updateStaticPage(@Valid @RequestBody StaticPage staticPage)
			throws URISyntaxException {
		LOGGER.debug("REST request to update StaticPage : {}", staticPage);
		
		if (staticPage.getId() == null) {
			return createStaticPage(staticPage);
		}
		
		StaticPage result = staticPageRepository.save(staticPage);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("staticPage", staticPage.getId().toString())).body(result);
	}

}
