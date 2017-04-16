package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.*;
import com.miu.repository.*;
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
 * REST controller for managing StudentProfile.
 */
@RestController
@RequestMapping("/api")
public class StudentProfileResource {

    @Inject
    private CourseAccessRepository courseAccessRepository;

    @Inject
    private ForumRoomMessageRepository forumRoomMessageRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(StudentProfileResource.class);

    @Inject
    private StudentProfileRepository studentProfileRepository;

    @Inject
    private StudentModuleResultRepository studentModuleResultRepository;

    @Inject
    private StudentOtherResultRepository studentOtherResultRepository;

    @Inject
    private StudentPaymentRepository studentPaymentRepository;

    @Inject
    private StudentResearchPaperResultRepository studentResearchPaperResultRepository;

    /**
     * POST /student-profiles : Create a new studentProfile.
     *
     * @param studentProfile the studentProfile to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new studentProfile, or with status 400 (Bad Request) if the
     * studentProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-profiles")
    @Timed
    public ResponseEntity<StudentProfile> createStudentProfile(@Valid @RequestBody StudentProfile studentProfile)
        throws URISyntaxException {
        LOGGER.debug("REST request to save StudentProfile : {}", studentProfile);

        if (studentProfile.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("studentProfile", "idexists",
                "A new studentProfile cannot already have an ID")).body(null);
        }

        StudentProfile result = studentProfileRepository.save(studentProfile);
        return ResponseEntity.created(new URI("/api/student-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("studentProfile", result.getId().toString()))
            .body(result);
    }

    /**
     * DELETE /student-profiles/:id : delete the "id" studentProfile.
     *
     * @param id the id of the studentProfile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-profiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteStudentProfile(@PathVariable Long id) {
        LOGGER.debug("REST request to delete StudentProfile : {}", id);

		/* delete course access */
        StudentProfile currProf = studentProfileRepository.findOne(id);
        User user = currProf.getUser();
        CourseAccess ca = courseAccessRepository.findByUser(user);
        if (ca != null) {
            LOGGER.info("Deleting Student Course Access: " + user.getLogin());
            courseAccessRepository.delete(ca.getId());
        }

		/* delete forum message */
        List<ForumRoomMessage> messages = forumRoomMessageRepository.findByUser(user);
        if (messages != null && messages.size() > 0) {
            LOGGER.info("Deleting Student Forum Messages: " + user.getLogin());
            for (ForumRoomMessage item : messages) {
                forumRoomMessageRepository.delete(item.getId());
            }
        }

        /* delete student module result */
        List<StudentModuleResult> result = studentModuleResultRepository.findByUser(user);
        if (result != null && result.size() > 0) {
            LOGGER.info("Deleting Student Module results: " + user.getLogin());
            for (StudentModuleResult item : result) {
                studentModuleResultRepository.delete(item.getId());
            }
        }

        /* delete student other result */
        List<StudentOtherResult> otherResult = studentOtherResultRepository.findByUser(user);
        if (otherResult != null && otherResult.size() > 0) {
            LOGGER.info("Deleting Student other results: " + user.getLogin());
            for (StudentOtherResult item : otherResult) {
                studentOtherResultRepository.delete(item.getId());
            }
        }

        /* delete student payments */
        List<StudentResearchPaperResult> rpResults = studentResearchPaperResultRepository.findByUserIsCurrentUser();
        if (rpResults != null && rpResults.size() > 0) {
            LOGGER.info("Deleting Student research paper results: " + user.getLogin());
            for (StudentResearchPaperResult item : rpResults) {
                studentResearchPaperResultRepository.delete(item.getId());
            }
        }

        /* delete student payments */
        List<StudentPayment> payments = studentPaymentRepository.findAdminUserList(user.getId());
        if (payments != null && payments.size() > 0) {
            LOGGER.info("Deleting Student payments: " + user.getLogin());
            for (StudentPayment item : payments) {
                studentPaymentRepository.delete(item.getId());
            }
        }

        studentProfileRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("studentProfile", id.toString()))
            .build();
    }

    /**
     * GET /student-profiles : get all the studentProfiles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of
     * studentProfiles in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/student-profiles")
    @Timed
    public ResponseEntity<List<StudentProfile>> getAllStudentProfiles(@ApiParam Pageable pageable)
        throws URISyntaxException {
        LOGGER.debug("REST request to get a page of StudentProfiles");
        Page<StudentProfile> page = studentProfileRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-profiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /student-profiles/:id : get the "id" studentProfile.
     *
     * @param id the id of the studentProfile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * studentProfile, or with status 404 (Not Found)
     */
    @GetMapping("/student-profiles/{id}")
    @Timed
    public ResponseEntity<StudentProfile> getStudentProfile(@PathVariable Long id) {
        LOGGER.debug("REST request to get StudentProfile : {}", id);
        StudentProfile studentProfile = studentProfileRepository.findOne(id);
        return Optional.ofNullable(studentProfile).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * PUT /student-profiles : Updates an existing studentProfile.
     *
     * @param studentProfile the studentProfile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * studentProfile, or with status 400 (Bad Request) if the
     * studentProfile is not valid, or with status 500 (Internal Server
     * Error) if the studentProfile couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-profiles")
    @Timed
    public ResponseEntity<StudentProfile> updateStudentProfile(@Valid @RequestBody StudentProfile studentProfile)
        throws URISyntaxException {
        LOGGER.debug("REST request to update StudentProfile : {}", studentProfile);

        if (studentProfile.getId() == null) {
            return createStudentProfile(studentProfile);
        }

        StudentProfile result = studentProfileRepository.save(studentProfile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("studentProfile", studentProfile.getId().toString()))
            .body(result);
    }

}
