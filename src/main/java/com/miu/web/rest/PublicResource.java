package com.miu.web.rest;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.Course;
import com.miu.domain.Module;
import com.miu.domain.StaticPage;
import com.miu.domain.StaticPageType;
import com.miu.repository.CourseRepository;
import com.miu.repository.ModuleRepository;
import com.miu.repository.StaticPageRepository;
import com.miu.repository.StaticPageTypeRepository;
import com.miu.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing Public APIs.
 */
@RestController
@RequestMapping("/api/public")
public class PublicResource {

	@Inject
	private CourseRepository courseRepository;

	private final Logger LOGGER = LoggerFactory.getLogger(PublicResource.class);

	@Inject
	private ModuleRepository moduleRepository;

	@Inject
	private StaticPageTypeRepository sptRepository;

	@Inject
	private StaticPageRepository staticPageRepository;

	/**
	 * GET /courses : get all the courses.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of courses
	 *         in body
	 * @throws URISyntaxException
	 *             if there is an error to generate the pagination HTTP headers
	 */
	@GetMapping("/courses")
	@Timed
	public ResponseEntity<List<Course>> getAllCourses(@ApiParam Pageable pageable) throws URISyntaxException {
		LOGGER.debug("REST request to get a page of Courses");
		Page<Course> page = courseRepository.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/courses");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /courses/:id : get the "id" course.
	 *
	 * @param id
	 *            the id of the course to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the course,
	 *         or with status 404 (Not Found)
	 */
	@GetMapping("/courses/{id}")
	@Timed
	public ResponseEntity<Course> getCourse(@PathVariable Long id) {
		LOGGER.debug("REST request to get Course : {}", id);
		Course course = courseRepository.findOne(id);
		return Optional.ofNullable(course).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/governance")
	@Timed
	public ResponseEntity<StaticPage> getGovernance() throws URISyntaxException {
		LOGGER.debug("REST request to get Governance");
		StaticPageType staticPageType = sptRepository.findOne(2L);
		StaticPage staticPage = staticPageRepository.getByStaticPageType(staticPageType);
		return Optional.ofNullable(staticPage).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/message-from-president")
	@Timed
	public ResponseEntity<StaticPage> getMessageFromPresident() throws URISyntaxException {
		LOGGER.debug("REST request to get Message From President");
		StaticPageType staticPageType = sptRepository.findOne(1L);
		StaticPage staticPage = staticPageRepository.getByStaticPageType(staticPageType);
		return Optional.ofNullable(staticPage).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * GET /modules/:id : get the "id" module.
	 *
	 * @param id
	 *            the id of the module to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the module,
	 *         or with status 404 (Not Found)
	 */
	@GetMapping("/course/{id}/practical-ministry")
	@Timed
	public ResponseEntity<List<Module>> getPracticalMinistryModule(@PathVariable Long id) {
		LOGGER.debug("REST request to get Modules from course : {}", id);
		Course course = courseRepository.findOne(id);
		List<Module> page = moduleRepository.getPracticalMinistry(course);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(page, headers, HttpStatus.OK);
	}

	@GetMapping("/statement-of-faith")
	@Timed
	public ResponseEntity<StaticPage> getStatementOfFaith() throws URISyntaxException {
		LOGGER.debug("REST request to get Statement Of Faith");
		StaticPageType staticPageType = sptRepository.findOne(3L);
		StaticPage staticPage = staticPageRepository.getByStaticPageType(staticPageType);
		return Optional.ofNullable(staticPage).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/accredited-centers")
	@Timed
	public ResponseEntity<StaticPage> getAccreditedCenters() throws URISyntaxException {
		LOGGER.debug("REST request to get Accredited Centers");
		StaticPageType staticPageType = sptRepository.findOne(4L);
		StaticPage staticPage = staticPageRepository.getByStaticPageType(staticPageType);
		return Optional.ofNullable(staticPage).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * GET /modules/:id : get the "id" module.
	 *
	 * @param id
	 *            the id of the module to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the module,
	 *         or with status 404 (Not Found)
	 */
	@GetMapping("/course/{id}/theological")
	@Timed
	public ResponseEntity<List<Module>> getTheologicalModule(@PathVariable Long id) {
		LOGGER.debug("REST request to get Modules from course : {}", id);
		Course course = courseRepository.findOne(id);
		List<Module> page = moduleRepository.getTheological(course);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(page, headers, HttpStatus.OK);
	}
}
