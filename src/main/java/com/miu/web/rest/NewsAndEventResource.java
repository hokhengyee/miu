package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.NewsAndEvent;

import com.miu.repository.NewsAndEventRepository;
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
 * REST controller for managing NewsAndEvent.
 */
@RestController
@RequestMapping("/api")
public class NewsAndEventResource {

    private final Logger log = LoggerFactory.getLogger(NewsAndEventResource.class);
        
    @Inject
    private NewsAndEventRepository newsAndEventRepository;

    /**
     * POST  /news-and-events : Create a new newsAndEvent.
     *
     * @param newsAndEvent the newsAndEvent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new newsAndEvent, or with status 400 (Bad Request) if the newsAndEvent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/news-and-events")
    @Timed
    public ResponseEntity<NewsAndEvent> createNewsAndEvent(@Valid @RequestBody NewsAndEvent newsAndEvent) throws URISyntaxException {
        log.debug("REST request to save NewsAndEvent : {}", newsAndEvent);
        if (newsAndEvent.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("newsAndEvent", "idexists", "A new newsAndEvent cannot already have an ID")).body(null);
        }
        NewsAndEvent result = newsAndEventRepository.save(newsAndEvent);
        return ResponseEntity.created(new URI("/api/news-and-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("newsAndEvent", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /news-and-events : Updates an existing newsAndEvent.
     *
     * @param newsAndEvent the newsAndEvent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated newsAndEvent,
     * or with status 400 (Bad Request) if the newsAndEvent is not valid,
     * or with status 500 (Internal Server Error) if the newsAndEvent couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/news-and-events")
    @Timed
    public ResponseEntity<NewsAndEvent> updateNewsAndEvent(@Valid @RequestBody NewsAndEvent newsAndEvent) throws URISyntaxException {
        log.debug("REST request to update NewsAndEvent : {}", newsAndEvent);
        if (newsAndEvent.getId() == null) {
            return createNewsAndEvent(newsAndEvent);
        }
        NewsAndEvent result = newsAndEventRepository.save(newsAndEvent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("newsAndEvent", newsAndEvent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /news-and-events : get all the newsAndEvents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of newsAndEvents in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/news-and-events")
    @Timed
    public ResponseEntity<List<NewsAndEvent>> getAllNewsAndEvents(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of NewsAndEvents");
        Page<NewsAndEvent> page = newsAndEventRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/news-and-events");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /news-and-events/:id : get the "id" newsAndEvent.
     *
     * @param id the id of the newsAndEvent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the newsAndEvent, or with status 404 (Not Found)
     */
    @GetMapping("/news-and-events/{id}")
    @Timed
    public ResponseEntity<NewsAndEvent> getNewsAndEvent(@PathVariable Long id) {
        log.debug("REST request to get NewsAndEvent : {}", id);
        NewsAndEvent newsAndEvent = newsAndEventRepository.findOne(id);
        return Optional.ofNullable(newsAndEvent)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /news-and-events/:id : delete the "id" newsAndEvent.
     *
     * @param id the id of the newsAndEvent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/news-and-events/{id}")
    @Timed
    public ResponseEntity<Void> deleteNewsAndEvent(@PathVariable Long id) {
        log.debug("REST request to delete NewsAndEvent : {}", id);
        newsAndEventRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("newsAndEvent", id.toString())).build();
    }

}
