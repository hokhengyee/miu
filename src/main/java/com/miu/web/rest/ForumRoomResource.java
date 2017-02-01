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
import com.miu.domain.ForumRoom;
import com.miu.repository.ForumRoomRepository;
import com.miu.web.rest.util.HeaderUtil;
import com.miu.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing ForumRoom.
 */
@RestController
@RequestMapping("/api")
public class ForumRoomResource {

	@Inject
	private ForumRoomRepository forumRoomRepository;

	private final Logger LOGGER = LoggerFactory.getLogger(ForumRoomResource.class);

	/**
	 * POST /forum-rooms : Create a new forumRoom.
	 *
	 * @param forumRoom
	 *            the forumRoom to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new forumRoom, or with status 400 (Bad Request) if the forumRoom
	 *         has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/forum-rooms")
	@Timed
	public ResponseEntity<ForumRoom> createForumRoom(@Valid @RequestBody ForumRoom forumRoom)
			throws URISyntaxException {
		LOGGER.debug("REST request to save ForumRoom : {}", forumRoom);
		
		if (forumRoom.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert("forumRoom", "idexists", "A new forumRoom cannot already have an ID"))
					.body(null);
		}
		
		ForumRoom result = forumRoomRepository.save(forumRoom);
		return ResponseEntity.created(new URI("/api/forum-rooms/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("forumRoom", result.getId().toString())).body(result);
	}

	/**
	 * DELETE /forum-rooms/:id : delete the "id" forumRoom.
	 *
	 * @param id
	 *            the id of the forumRoom to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/forum-rooms/{id}")
	@Timed
	public ResponseEntity<Void> deleteForumRoom(@PathVariable Long id) {
		LOGGER.debug("REST request to delete ForumRoom : {}", id);
		forumRoomRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("forumRoom", id.toString())).build();
	}

	/**
	 * GET /forum-rooms : get all the forumRooms.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         forumRooms in body
	 * @throws URISyntaxException
	 *             if there is an error to generate the pagination HTTP headers
	 */
	@GetMapping("/forum-rooms")
	@Timed
	public ResponseEntity<List<ForumRoom>> getAllForumRooms(@ApiParam Pageable pageable) throws URISyntaxException {
		LOGGER.debug("REST request to get a page of ForumRooms");
		Page<ForumRoom> page = forumRoomRepository.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/forum-rooms");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /forum-rooms/:id : get the "id" forumRoom.
	 *
	 * @param id
	 *            the id of the forumRoom to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         forumRoom, or with status 404 (Not Found)
	 */
	@GetMapping("/forum-rooms/{id}")
	@Timed
	public ResponseEntity<ForumRoom> getForumRoom(@PathVariable Long id) {
		LOGGER.debug("REST request to get ForumRoom : {}", id);
		ForumRoom forumRoom = forumRoomRepository.findOne(id);
		return Optional.ofNullable(forumRoom).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * PUT /forum-rooms : Updates an existing forumRoom.
	 *
	 * @param forumRoom
	 *            the forumRoom to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         forumRoom, or with status 400 (Bad Request) if the forumRoom is
	 *         not valid, or with status 500 (Internal Server Error) if the
	 *         forumRoom couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/forum-rooms")
	@Timed
	public ResponseEntity<ForumRoom> updateForumRoom(@Valid @RequestBody ForumRoom forumRoom)
			throws URISyntaxException {
		LOGGER.debug("REST request to update ForumRoom : {}", forumRoom);
		
		if (forumRoom.getId() == null) {
			return createForumRoom(forumRoom);
		}
		
		ForumRoom result = forumRoomRepository.save(forumRoom);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("forumRoom", forumRoom.getId().toString())).body(result);
	}

}
