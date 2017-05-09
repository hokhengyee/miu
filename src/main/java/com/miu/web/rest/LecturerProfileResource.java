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
import com.miu.domain.CourseAccess;
import com.miu.domain.ForumRoomMessage;
import com.miu.domain.LecturerProfile;
import com.miu.domain.User;
import com.miu.repository.CourseAccessRepository;
import com.miu.repository.ForumRoomMessageRepository;
import com.miu.repository.LecturerProfileRepository;
import com.miu.web.rest.util.HeaderUtil;
import com.miu.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing LecturerProfile.
 */
@RestController
@RequestMapping("/api")
public class LecturerProfileResource {

	@Inject
	private CourseAccessRepository courseAccessRepository;

	@Inject
	private ForumRoomMessageRepository forumRoomMessageRepository;

	@Inject
	private LecturerProfileRepository lecturerProfileRepository;

	private final Logger LOGGER = LoggerFactory.getLogger(LecturerProfileResource.class);

	/**
	 * POST /lecturer-profiles : Create a new lecturerProfile.
	 *
	 * @param lecturerProfile
	 *            the lecturerProfile to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new lecturerProfile, or with status 400 (Bad Request) if the
	 *         lecturerProfile has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/lecturer-profiles")
	@Timed
	public ResponseEntity<LecturerProfile> createLecturerProfile(@Valid @RequestBody LecturerProfile lecturerProfile)
			throws URISyntaxException {
		LOGGER.debug("REST request to save LecturerProfile : {}", lecturerProfile);

		if (lecturerProfile.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lecturerProfile", "idexists",
					"A new lecturerProfile cannot already have an ID")).body(null);
		}

		LecturerProfile result = lecturerProfileRepository.save(lecturerProfile);
		return ResponseEntity.created(new URI("/api/lecturer-profiles/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("lecturerProfile", result.getId().toString()))
				.body(result);
	}

	/**
	 * DELETE /lecturer-profiles/:id : delete the "id" lecturerProfile.
	 *
	 * @param id
	 *            the id of the lecturerProfile to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/lecturer-profiles/{id}")
	@Timed
	public ResponseEntity<Void> deleteLecturerProfile(@PathVariable Long id) {
		LOGGER.debug("REST request to delete LecturerProfile : {}", id);

		/* delete course access */
		LecturerProfile currProf = lecturerProfileRepository.findOne(id);
		User user = currProf.getUser();
		CourseAccess ca = courseAccessRepository.findByUser(user);
		if (ca != null) {
			LOGGER.info("Deleting Lecturer Course Access: " + user.getLogin());
			courseAccessRepository.delete(ca.getId());
		}

		/* delete forum message */
		List<ForumRoomMessage> messages = forumRoomMessageRepository.findByUser(user);
		if (messages != null && messages.size() > 0) {
			LOGGER.info("Deleting Lecturer Forum Messages: " + user.getLogin());
			for (ForumRoomMessage item : messages) {
				forumRoomMessageRepository.delete(item.getId());
			}
		}

		lecturerProfileRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lecturerProfile", id.toString()))
				.build();
	}

	/**
	 * GET /lecturer-profiles : get all the lecturerProfiles.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         lecturerProfiles in body
	 * @throws URISyntaxException
	 *             if there is an error to generate the pagination HTTP headers
	 */
	@GetMapping("/lecturer-profiles")
	@Timed
	public ResponseEntity<List<LecturerProfile>> getAllLecturerProfiles(@ApiParam Pageable pageable)
			throws URISyntaxException {
		LOGGER.debug("REST request to get a page of LecturerProfiles");
		Page<LecturerProfile> page = lecturerProfileRepository.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lecturer-profiles");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

    @GetMapping("/all-lecturer-profiles")
    @Timed
    public ResponseEntity<List<LecturerProfile>> getAllLecturerProfilesSorted()
        throws URISyntaxException {
        LOGGER.debug("REST request to get a page of LecturerProfiles Sorted");
        List<LecturerProfile> lecturerProfileList = lecturerProfileRepository.findAllOrderByUsername();
        // HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lecturer-profiles");
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<List<LecturerProfile>>(lecturerProfileList, headers, HttpStatus.OK);
    }

	/**
	 * GET /lecturer-profiles/:id : get the "id" lecturerProfile.
	 *
	 * @param id
	 *            the id of the lecturerProfile to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         lecturerProfile, or with status 404 (Not Found)
	 */
	@GetMapping("/lecturer-profiles/{id}")
	@Timed
	public ResponseEntity<LecturerProfile> getLecturerProfile(@PathVariable Long id) {
		LOGGER.debug("REST request to get LecturerProfile : {}", id);
		LecturerProfile lecturerProfile = lecturerProfileRepository.findOne(id);
		return Optional.ofNullable(lecturerProfile).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * PUT /lecturer-profiles : Updates an existing lecturerProfile.
	 *
	 * @param lecturerProfile
	 *            the lecturerProfile to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         lecturerProfile, or with status 400 (Bad Request) if the
	 *         lecturerProfile is not valid, or with status 500 (Internal Server
	 *         Error) if the lecturerProfile couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/lecturer-profiles")
	@Timed
	public ResponseEntity<LecturerProfile> updateLecturerProfile(@Valid @RequestBody LecturerProfile lecturerProfile)
			throws URISyntaxException {
		LOGGER.debug("REST request to update LecturerProfile : {}", lecturerProfile);

		if (lecturerProfile.getId() == null) {
			return createLecturerProfile(lecturerProfile);
		}

		LecturerProfile result = lecturerProfileRepository.save(lecturerProfile);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("lecturerProfile", lecturerProfile.getId().toString()))
				.body(result);
	}

}
