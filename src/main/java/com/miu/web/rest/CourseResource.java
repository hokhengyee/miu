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
import com.miu.domain.Course;
import com.miu.repository.CourseRepository;
import com.miu.web.rest.util.HeaderUtil;
import com.miu.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing Course.
 */
@RestController
@RequestMapping("/api")
public class CourseResource {

	@Inject
	private CourseRepository courseRepository;

	private final Logger LOGGER = LoggerFactory.getLogger(CourseResource.class);

	/**
	 * POST /courses : Create a new course.
	 *
	 * @param course
	 *            the course to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new course, or with status 400 (Bad Request) if the course has
	 *         already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/courses")
	@Timed
	public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course) throws URISyntaxException {
		LOGGER.debug("REST request to save Course : {}", course);

		if (course.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert("course", "idexists", "A new course cannot already have an ID"))
					.body(null);
		}

		Course result = courseRepository.save(course);
		return ResponseEntity.created(new URI("/api/courses/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("course", result.getId().toString())).body(result);
	}

	/**
	 * DELETE /courses/:id : delete the "id" course.
	 *
	 * @param id
	 *            the id of the course to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/courses/{id}")
	@Timed
	public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
		LOGGER.debug("REST request to delete Course : {}", id);
		courseRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("course", id.toString())).build();
	}

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

	/**
	 * PUT /courses : Updates an existing course.
	 *
	 * @param course
	 *            the course to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         course, or with status 400 (Bad Request) if the course is not
	 *         valid, or with status 500 (Internal Server Error) if the course
	 *         couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/courses")
	@Timed
	public ResponseEntity<Course> updateCourse(@Valid @RequestBody Course course) throws URISyntaxException {
		LOGGER.debug("REST request to update Course : {}", course);

		if (course.getId() == null) {
			return createCourse(course);
		}

		Course result = courseRepository.save(course);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("course", course.getId().toString()))
				.body(result);
	}

}
