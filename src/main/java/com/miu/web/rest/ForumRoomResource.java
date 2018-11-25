package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.ForumRoom;
import com.miu.repository.ForumRoomRepository;
import com.miu.web.rest.errors.BadRequestAlertException;
import com.miu.web.rest.util.HeaderUtil;
import com.miu.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ForumRoom.
 */
@RestController
@RequestMapping("/api")
public class ForumRoomResource {

    private final Logger log = LoggerFactory.getLogger(ForumRoomResource.class);

    private static final String ENTITY_NAME = "forumRoom";

    private final ForumRoomRepository forumRoomRepository;

    public ForumRoomResource(ForumRoomRepository forumRoomRepository) {
        this.forumRoomRepository = forumRoomRepository;
    }

    /**
     * POST  /forum-rooms : Create a new forumRoom.
     *
     * @param forumRoom the forumRoom to create
     * @return the ResponseEntity with status 201 (Created) and with body the new forumRoom, or with status 400 (Bad Request) if the forumRoom has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/forum-rooms")
    @Timed
    public ResponseEntity<ForumRoom> createForumRoom(@Valid @RequestBody ForumRoom forumRoom) throws URISyntaxException {
        log.debug("REST request to save ForumRoom : {}", forumRoom);
        if (forumRoom.getId() != null) {
            throw new BadRequestAlertException("A new forumRoom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ForumRoom result = forumRoomRepository.save(forumRoom);
        return ResponseEntity.created(new URI("/api/forum-rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /forum-rooms : Updates an existing forumRoom.
     *
     * @param forumRoom the forumRoom to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated forumRoom,
     * or with status 400 (Bad Request) if the forumRoom is not valid,
     * or with status 500 (Internal Server Error) if the forumRoom couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/forum-rooms")
    @Timed
    public ResponseEntity<ForumRoom> updateForumRoom(@Valid @RequestBody ForumRoom forumRoom) throws URISyntaxException {
        log.debug("REST request to update ForumRoom : {}", forumRoom);
        if (forumRoom.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ForumRoom result = forumRoomRepository.save(forumRoom);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, forumRoom.getId().toString()))
            .body(result);
    }

    /**
     * GET  /forum-rooms : get all the forumRooms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of forumRooms in body
     */
    @GetMapping("/forum-rooms")
    @Timed
    public ResponseEntity<List<ForumRoom>> getAllForumRooms(Pageable pageable) {
        log.debug("REST request to get a page of ForumRooms");
        Page<ForumRoom> page = forumRoomRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/forum-rooms");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /forum-rooms/:id : get the "id" forumRoom.
     *
     * @param id the id of the forumRoom to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the forumRoom, or with status 404 (Not Found)
     */
    @GetMapping("/forum-rooms/{id}")
    @Timed
    public ResponseEntity<ForumRoom> getForumRoom(@PathVariable Long id) {
        log.debug("REST request to get ForumRoom : {}", id);
        Optional<ForumRoom> forumRoom = forumRoomRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(forumRoom);
    }

    /**
     * DELETE  /forum-rooms/:id : delete the "id" forumRoom.
     *
     * @param id the id of the forumRoom to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/forum-rooms/{id}")
    @Timed
    public ResponseEntity<Void> deleteForumRoom(@PathVariable Long id) {
        log.debug("REST request to delete ForumRoom : {}", id);

        forumRoomRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
