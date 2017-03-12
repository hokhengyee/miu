package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.Module;

import com.miu.repository.ModuleRepository;
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
 * REST controller for managing Module.
 */
@RestController
@RequestMapping("/api")
public class ModuleResource {

	private final Logger LOGGER = LoggerFactory.getLogger(ModuleResource.class);

	@Inject
	private ModuleRepository moduleRepository;

	/**
	 * POST /modules : Create a new module.
	 *
	 * @param module
	 *            the module to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new module, or with status 400 (Bad Request) if the module has
	 *         already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/modules")
	@Timed
	public ResponseEntity<Module> createModule(@Valid @RequestBody Module module) throws URISyntaxException {
		LOGGER.debug("REST request to save Module : {}", module);
		if (module.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert("module", "idexists", "A new module cannot already have an ID"))
					.body(null);
		}
		Module result = moduleRepository.save(module);
		return ResponseEntity.created(new URI("/api/modules/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("module", result.getId().toString())).body(result);
	}

	/**
	 * PUT /modules : Updates an existing module.
	 *
	 * @param module
	 *            the module to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         module, or with status 400 (Bad Request) if the module is not
	 *         valid, or with status 500 (Internal Server Error) if the module
	 *         couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/modules")
	@Timed
	public ResponseEntity<Module> updateModule(@Valid @RequestBody Module module) throws URISyntaxException {
		LOGGER.debug("REST request to update Module : {}", module);
		if (module.getId() == null) {
			return createModule(module);
		}
		Module result = moduleRepository.save(module);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("module", module.getId().toString()))
				.body(result);
	}

	/**
	 * GET /modules : get all the modules.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of modules
	 *         in body
	 * @throws URISyntaxException
	 *             if there is an error to generate the pagination HTTP headers
	 */
	@GetMapping("/modules")
	@Timed
	public ResponseEntity<List<Module>> getAllModules(@ApiParam Pageable pageable) throws URISyntaxException {
		LOGGER.debug("REST request to get a page of Modules");
		Page<Module> page = moduleRepository.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/modules");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	@GetMapping("/all-modules")
	@Timed
	public ResponseEntity<List<Module>> getAllModulesList() throws URISyntaxException {
		LOGGER.debug("REST request to get a page of Modules");
		List<Module> results = moduleRepository.findAll();
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(results, headers, HttpStatus.OK);
	}

	/**
	 * GET /modules/:id : get the "id" module.
	 *
	 * @param id
	 *            the id of the module to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the module,
	 *         or with status 404 (Not Found)
	 */
	@GetMapping("/modules/{id}")
	@Timed
	public ResponseEntity<Module> getModule(@PathVariable Long id) {
		LOGGER.debug("REST request to get Module : {}", id);
		Module module = moduleRepository.findOne(id);
		return Optional.ofNullable(module).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /modules/:id : delete the "id" module.
	 *
	 * @param id
	 *            the id of the module to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/modules/{id}")
	@Timed
	public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
		LOGGER.debug("REST request to delete Module : {}", id);
		moduleRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("module", id.toString())).build();
	}

}
