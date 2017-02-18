package com.miu.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
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
import com.miu.config.Constants;
import com.miu.domain.Course;
import com.miu.domain.CourseAccess;
import com.miu.domain.CourseMaterial;
import com.miu.domain.ForumRoom;
import com.miu.domain.ForumRoomMessage;
import com.miu.domain.LecturerProfile;
import com.miu.domain.StudentModuleResult;
import com.miu.domain.StudentOtherResult;
import com.miu.domain.StudentPayment;
import com.miu.domain.StudentProfile;
import com.miu.domain.StudentResearchPaperResult;
import com.miu.domain.User;
import com.miu.repository.CourseAccessRepository;
import com.miu.repository.CourseMaterialRepository;
import com.miu.repository.CourseRepository;
import com.miu.repository.ForumRoomMessageRepository;
import com.miu.repository.ForumRoomRepository;
import com.miu.repository.LecturerProfileRepository;
import com.miu.repository.StudentModuleResultRepository;
import com.miu.repository.StudentOtherResultRepository;
import com.miu.repository.StudentPaymentRepository;
import com.miu.repository.StudentProfileRepository;
import com.miu.repository.StudentResearchPaperResultRepository;
import com.miu.repository.UserRepository;
import com.miu.service.UserService;
import com.miu.service.dto.ForumMessageDto;
import com.miu.web.rest.util.HeaderUtil;
import com.miu.web.rest.util.PaginationUtil;
import com.miu.web.rest.vm.ManagedUserVM;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing users.
 *
 * <p>
 * This class accesses the User entity, and needs to fetch its collection of
 * authorities.
 * </p>
 * <p>
 * For a normal use-case, it would be better to have an eager relationship
 * between User and Authority, and send everything to the client side: there
 * would be no View Model and DTO, a lot less code, and an outer-join which
 * would be good for performance.
 * </p>
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities,
 * because people will quite often do relationships with the user, and we don't
 * want them to get the authorities all the time for nothing (for performance
 * reasons). This is the #1 goal: we should not impact our users' application
 * because of this use-case.</li>
 * <li>Not having an outer join causes n+1 requests to the database. This is not
 * a real issue as we have by default a second-level cache. This means on the
 * first HTTP call we do the n+1 requests, but then all authorities come from
 * the cache, so in fact it's much better than doing an outer join (which will
 * get lots of data from the database, for each HTTP call).</li>
 * <li>As this manages users, for security reasons, we'd rather have a DTO
 * layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this
 * case.
 * </p>
 */
@RestController
@RequestMapping("/api")
public class MyProfileResource {

	@Inject
	private CourseAccessRepository courseAccessRepository;

	@Inject
	private CourseMaterialRepository courseMaterialRepository;

	@Inject
	private CourseRepository courseRepository;

	@Inject
	private ForumRoomMessageRepository forumRoomMessageRepository;

	@Inject
	private ForumRoomRepository forumRoomRepository;

	@Inject
	private LecturerProfileRepository lecturerProfileRepository;

	private final Logger LOGGER = LoggerFactory.getLogger(MyProfileResource.class);

	@Inject
	private StudentModuleResultRepository studentModuleResultRepository;

	@Inject
	private StudentOtherResultRepository studentOtherResultRepository;

	@Inject
	private StudentPaymentRepository studentPaymentRepository;

	@Inject
	private StudentProfileRepository studentProfileRepository;

	@Inject
	private StudentResearchPaperResultRepository studentResearchPaperResultRepository;

	@Inject
	private UserRepository userRepository;

	@Inject
	private UserService userService;

	/**
	 * POST /forum-room-messages : Create a new forumRoomMessage.
	 *
	 * @param forumRoomMessage
	 *            the forumRoomMessage to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new forumRoomMessage, or with status 400 (Bad Request) if the
	 *         forumRoomMessage has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/forum/messages")
	@Timed
	public ResponseEntity<ForumRoomMessage> createForumRoomMessage(@Valid @RequestBody ForumMessageDto fm)
			throws URISyntaxException {
		LOGGER.debug("REST request to save ForumRoomMessage : {}");

		ForumRoomMessage forumRoomMessage = new ForumRoomMessage();
		forumRoomMessage.setMessage(fm.getMessage());

		if (forumRoomMessage.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("forumRoomMessage", "idexists",
					"A new forumRoomMessage cannot already have an ID")).body(null);
		}

		if (forumRoomMessage.getMessageDatetime() == null) {
			forumRoomMessage.setMessageDatetime(ZonedDateTime.now());
		}

		if (forumRoomMessage.getUser() == null) {
			User user = userRepository.findByUserIsCurrentUser();
			forumRoomMessage.setUser(user);
		}

		ForumRoom forumRoom = forumRoomRepository.findOne(fm.getCourseID());
		forumRoomMessage.setForumRoom(forumRoom);

		ForumRoomMessage result = forumRoomMessageRepository.save(forumRoomMessage);
		return ResponseEntity.created(new URI("/api/forum-room-messages/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("forumRoom", result.getId().toString())).body(result);
	}

	/**
	 * DELETE /forum-room-messages/:id : delete the "id" forumRoomMessage.
	 *
	 * @param id
	 *            the id of the forumRoomMessage to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/forum/message/{id}")
	@Timed
	public ResponseEntity<Void> deleteForumRoomMessage(@PathVariable Long id) {
		LOGGER.debug("REST request to delete ForumRoomMessage : {}", id);

		User user = userRepository.findByUserIsCurrentUser();
		ForumRoomMessage message = forumRoomMessageRepository.findOne(id);
		if ("admin".contentEquals(user.getLogin()) || message.getUser().equals(user)) {
			forumRoomMessageRepository.delete(id);
		}

		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("forumRoomMessage", id.toString()))
				.build();
	}

	@GetMapping("/forum/{id}/messages")
	@Timed
	public ResponseEntity<List<ForumRoomMessage>> getAllForumRoomMessages(@PathVariable Long id,
			@ApiParam Pageable pageable) throws URISyntaxException {
		LOGGER.debug("REST request to get a page of ForumRoomMessages");
		ForumRoom forumRoom = forumRoomRepository.findOne(id);
		Page<ForumRoomMessage> page = forumRoomMessageRepository.findForumRoomMessagesByForumRoom(forumRoom, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/forum/{id}/messages");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	@GetMapping("/course/{id}/course-materials")
	@Timed
	public ResponseEntity<List<CourseMaterial>> getCourseMaterials(@PathVariable Long id) throws URISyntaxException {
		LOGGER.debug("REST request to get Course Materials");
		Course course = courseRepository.findOne(id);
		List<CourseMaterial> courseMaterialList = courseMaterialRepository.getCourseMaterialByCourseTitle(course);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(courseMaterialList, headers, HttpStatus.OK);
	}

	@GetMapping("/forum/message/{id}")
	@Timed
	public ResponseEntity<ForumRoomMessage> getForumRoomMessage(@PathVariable Long id) {
		LOGGER.debug("REST request to get ForumRoomMessage : {}", id);
		ForumRoomMessage forumRoomMessage = forumRoomMessageRepository.findOne(id);
		return Optional.ofNullable(forumRoomMessage).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/my-lecturer-profiles")
	@Timed
	public ResponseEntity<LecturerProfile> getLecturerProfile() {
		LecturerProfile profile = lecturerProfileRepository.findLecturerIsCurrentUser();
		return Optional.ofNullable(profile).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/my-practical-ministry-modules-results")
	@Timed
	public ResponseEntity<List<StudentModuleResult>> getPracticalMinistryResults() {
		List<StudentModuleResult> results = studentModuleResultRepository.getPracticalMinistryResult();
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(results, headers, HttpStatus.OK);
	}

	@GetMapping("/my-articles-results")
	@Timed
	public ResponseEntity<List<StudentOtherResult>> getStudentArticleResults() {
		List<StudentOtherResult> results = studentOtherResultRepository.getMyArticleResults();
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(results, headers, HttpStatus.OK);
	}

	@GetMapping("/my-book-reviews-results")
	@Timed
	public ResponseEntity<List<StudentOtherResult>> getStudentBookReviewResults() {
		List<StudentOtherResult> results = studentOtherResultRepository.getMyBookReviewResults();
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(results, headers, HttpStatus.OK);
	}

	@GetMapping("/my-courses")
	@Timed
	public ResponseEntity<List<CourseAccess>> getStudentCourses() {
		List<CourseAccess> courseList = courseAccessRepository.findByUserIsCurrentUser();
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(courseList, headers, HttpStatus.OK);
	}

	@GetMapping("/my-dissertation-results")
	@Timed
	public ResponseEntity<List<StudentOtherResult>> getStudentDissertationResults() {
		List<StudentOtherResult> results = studentOtherResultRepository.getMyDissertationResults();
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(results, headers, HttpStatus.OK);
	}

	@GetMapping("/my-payments")
	@Timed
	public ResponseEntity<List<StudentPayment>> getStudentPayments() {
		List<StudentPayment> paymentList = studentPaymentRepository.findByUserIsCurrentUser();
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(paymentList, headers, HttpStatus.OK);
	}

	@GetMapping("/my-student-profiles")
	@Timed
	public ResponseEntity<StudentProfile> getStudentProfile() {
		StudentProfile studentProfile = studentProfileRepository.findByUserIsCurrentUser();
		return Optional.ofNullable(studentProfile).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/my-research-papers-results")
	@Timed
	public ResponseEntity<List<StudentResearchPaperResult>> getStudentResearchPaperResults() {
		List<StudentResearchPaperResult> results = studentResearchPaperResultRepository.getMyResearchPaperResults();
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(results, headers, HttpStatus.OK);
	}

	@GetMapping("/my-sermon-results")
	@Timed
	public ResponseEntity<List<StudentOtherResult>> getStudentSermonResults() {
		List<StudentOtherResult> results = studentOtherResultRepository.getMySermonResults();
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(results, headers, HttpStatus.OK);
	}

	@GetMapping("/my-theological-modules-results")
	@Timed
	public ResponseEntity<List<StudentModuleResult>> getTheologicalResults() {
		List<StudentModuleResult> results = studentModuleResultRepository.getTheologicalResult();
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(results, headers, HttpStatus.OK);
	}

	/**
	 * GET /users/:login : get the "login" user.
	 *
	 * @param login
	 *            the login of the user to find
	 * @return the ResponseEntity with status 200 (OK) and with body the "login"
	 *         user, or with status 404 (Not Found)
	 */
	@GetMapping("/my-profile/{login:" + Constants.LOGIN_REGEX + "}")
	@Timed
	public ResponseEntity<ManagedUserVM> getUser(@PathVariable String login) {
		LOGGER.debug("REST request to get User : {}", login);
		return userService.getUserWithAuthoritiesByLogin(login).map(ManagedUserVM::new)
				.map(managedUserVM -> new ResponseEntity<>(managedUserVM, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * PUT /student-profiles : Updates an existing studentProfile.
	 *
	 * @param studentProfile
	 *            the studentProfile to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         studentProfile, or with status 400 (Bad Request) if the
	 *         studentProfile is not valid, or with status 500 (Internal Server
	 *         Error) if the studentProfile couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/my-student-profiles")
	@Timed
	public ResponseEntity<StudentProfile> updateStudentProfile(@Valid @RequestBody StudentProfile studentProfile)
			throws URISyntaxException {
		LOGGER.debug("REST request to update StudentProfile : {}", studentProfile);
		StudentProfile result = studentProfileRepository.save(studentProfile);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("studentProfile", studentProfile.getId().toString()))
				.body(result);
	}
}
