package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.ForumRoomMessage;

import com.miu.repository.ForumRoomMessageRepository;
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
 * REST controller for managing ForumRoomMessage.
 */
@RestController
@RequestMapping("/api")
public class ForumRoomMessageResource {

    private final Logger log = LoggerFactory.getLogger(ForumRoomMessageResource.class);
        
    @Inject
    private ForumRoomMessageRepository forumRoomMessageRepository;

    /**
     * POST  /forum-room-messages : Create a new forumRoomMessage.
     *
     * @param forumRoomMessage the forumRoomMessage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new forumRoomMessage, or with status 400 (Bad Request) if the forumRoomMessage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/forum-room-messages")
    @Timed
    public ResponseEntity<ForumRoomMessage> createForumRoomMessage(@Valid @RequestBody ForumRoomMessage forumRoomMessage) throws URISyntaxException {
        log.debug("REST request to save ForumRoomMessage : {}", forumRoomMessage);
        if (forumRoomMessage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("forumRoomMessage", "idexists", "A new forumRoomMessage cannot already have an ID")).body(null);
        }
        ForumRoomMessage result = forumRoomMessageRepository.save(forumRoomMessage);
        return ResponseEntity.created(new URI("/api/forum-room-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("forumRoomMessage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /forum-room-messages : Updates an existing forumRoomMessage.
     *
     * @param forumRoomMessage the forumRoomMessage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated forumRoomMessage,
     * or with status 400 (Bad Request) if the forumRoomMessage is not valid,
     * or with status 500 (Internal Server Error) if the forumRoomMessage couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/forum-room-messages")
    @Timed
    public ResponseEntity<ForumRoomMessage> updateForumRoomMessage(@Valid @RequestBody ForumRoomMessage forumRoomMessage) throws URISyntaxException {
        log.debug("REST request to update ForumRoomMessage : {}", forumRoomMessage);
        if (forumRoomMessage.getId() == null) {
            return createForumRoomMessage(forumRoomMessage);
        }
        ForumRoomMessage result = forumRoomMessageRepository.save(forumRoomMessage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("forumRoomMessage", forumRoomMessage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /forum-room-messages : get all the forumRoomMessages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of forumRoomMessages in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/forum-room-messages")
    @Timed
    public ResponseEntity<List<ForumRoomMessage>> getAllForumRoomMessages(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ForumRoomMessages");
        Page<ForumRoomMessage> page = forumRoomMessageRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/forum-room-messages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /forum-room-messages/:id : get the "id" forumRoomMessage.
     *
     * @param id the id of the forumRoomMessage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the forumRoomMessage, or with status 404 (Not Found)
     */
    @GetMapping("/forum-room-messages/{id}")
    @Timed
    public ResponseEntity<ForumRoomMessage> getForumRoomMessage(@PathVariable Long id) {
        log.debug("REST request to get ForumRoomMessage : {}", id);
        ForumRoomMessage forumRoomMessage = forumRoomMessageRepository.findOne(id);
        return Optional.ofNullable(forumRoomMessage)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /forum-room-messages/:id : delete the "id" forumRoomMessage.
     *
     * @param id the id of the forumRoomMessage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/forum-room-messages/{id}")
    @Timed
    public ResponseEntity<Void> deleteForumRoomMessage(@PathVariable Long id) {
        log.debug("REST request to delete ForumRoomMessage : {}", id);
        forumRoomMessageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("forumRoomMessage", id.toString())).build();
    }

}
