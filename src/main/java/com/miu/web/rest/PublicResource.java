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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.AdjunctFaculty;
import com.miu.domain.Course;
import com.miu.domain.EntryQualification;
import com.miu.domain.Gallery;
import com.miu.domain.LecturerProfile;
import com.miu.domain.MinisterialWorkExperience;
import com.miu.domain.Module;
import com.miu.domain.NewsAndEvent;
import com.miu.domain.OnlineApplication;
import com.miu.domain.RegistrationAcademicDetails;
import com.miu.domain.ResearchPaper;
import com.miu.domain.StaticPage;
import com.miu.repository.AdjunctFacultyRepository;
import com.miu.repository.CourseRepository;
import com.miu.repository.EntryQualificationRepository;
import com.miu.repository.GalleryRepository;
import com.miu.repository.LecturerProfileRepository;
import com.miu.repository.MinisterialWorkExperienceRepository;
import com.miu.repository.ModuleRepository;
import com.miu.repository.NewsAndEventRepository;
import com.miu.repository.OnlineApplicationRepository;
import com.miu.repository.RegistrationAcademicDetailsRepository;
import com.miu.repository.ResearchPaperRepository;
import com.miu.repository.StaticPageRepository;
import com.miu.web.rest.util.HeaderUtil;
import com.miu.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing Public APIs.
 */
@RestController
@RequestMapping("/api/public")
public class PublicResource {

	@Inject
	private AdjunctFacultyRepository adjunctFacultyRepository;

	@Inject
	private CourseRepository courseRepository;

	@Inject
	private EntryQualificationRepository entryQualificationRepository;

	@Inject
	private GalleryRepository galleryRepository;

	@Inject
	private LecturerProfileRepository lecturerProfileRepository;

	private final Logger LOGGER = LoggerFactory.getLogger(PublicResource.class);

	@Inject
	private MinisterialWorkExperienceRepository ministerialWorkExperienceRepository;

	@Inject
	private ModuleRepository moduleRepository;

	@Inject
	private NewsAndEventRepository newsAndEventRepository;

	@Inject
	private OnlineApplicationRepository onlineApplicationRepository;

	@Inject
	private RegistrationAcademicDetailsRepository registrationAcademicDetailsRepository;

	@Inject
	private ResearchPaperRepository researchPaperRepository;

	@Inject
	private StaticPageRepository staticPageRepository;

	@PostMapping("/ministerial-work-experiences")
	@Timed
	public ResponseEntity<MinisterialWorkExperience> createMinisterialWorkExperience(
			@Valid @RequestBody MinisterialWorkExperience ministerialWorkExperience) throws URISyntaxException {
		LOGGER.debug("REST request to save MinisterialWorkExperience : {}", ministerialWorkExperience);
		if (ministerialWorkExperience.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ministerialWorkExperience",
					"idexists", "A new ministerialWorkExperience cannot already have an ID")).body(null);
		}
		MinisterialWorkExperience result = ministerialWorkExperienceRepository.save(ministerialWorkExperience);
		return ResponseEntity.created(new URI("/api/ministerial-work-experiences/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("ministerialWorkExperience", result.getId().toString()))
				.body(result);
	}

	/**
	 * POST /online-applications : Create a new onlineApplication.
	 *
	 * @param onlineApplication
	 *            the onlineApplication to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new onlineApplication, or with status 400 (Bad Request) if the
	 *         onlineApplication has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/online-applications")
	@Timed
	public ResponseEntity<OnlineApplication> createOnlineApplication(
			@Valid @RequestBody OnlineApplication onlineApplication) throws URISyntaxException {
		LOGGER.debug("REST request to save OnlineApplication : {}", onlineApplication);

		if (onlineApplication.getRegistrationDatetime() == null) {
			onlineApplication.setRegistrationDatetime(ZonedDateTime.now());
		}

		if (onlineApplication.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("onlineApplication", "idexists",
					"A new onlineApplication cannot already have an ID")).body(null);
		}

		OnlineApplication tmpOA = onlineApplicationRepository.findOAByMd5key(onlineApplication.getMd5key());
		LOGGER.error("tmpOA: " + tmpOA.toString());
		if (tmpOA != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("onlineApplication", "idexists",
					"This email has been registered. Please try a different email.")).body(null);
		}

		OnlineApplication result = onlineApplicationRepository.save(onlineApplication);
		return ResponseEntity.created(new URI("/api/online-applications/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("onlineApplication", result.getId().toString()))
				.body(result);
	}

	@PostMapping("/registration-academic-details")
	@Timed
	public ResponseEntity<RegistrationAcademicDetails> createRegistrationAcademicDetails(
			@Valid @RequestBody RegistrationAcademicDetails registrationAcademicDetails) throws URISyntaxException {
		LOGGER.debug("REST request to save RegistrationAcademicDetails : {}", registrationAcademicDetails);
		if (registrationAcademicDetails.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("registrationAcademicDetails",
					"idexists", "A new registrationAcademicDetails cannot already have an ID")).body(null);
		}
		RegistrationAcademicDetails result = registrationAcademicDetailsRepository.save(registrationAcademicDetails);
		return ResponseEntity.created(new URI("/api/registration-academic-details/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("registrationAcademicDetails", result.getId().toString()))
				.body(result);
	}

	@GetMapping("/accredited-centers")
	@Timed
	public ResponseEntity<StaticPage> getAccreditedCenters() throws URISyntaxException {
		LOGGER.debug("REST request to get Accredited Centers");
		StaticPage staticPage = staticPageRepository.getStaticPageByTitle("Accredited Centers");
		return Optional.ofNullable(staticPage).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/adjunct-faculty")
	@Timed
	public ResponseEntity<List<AdjunctFaculty>> getAllAdjunctFaculties(@ApiParam Pageable pageable)
			throws URISyntaxException {
		LOGGER.debug("REST request to get a page of AdjunctFaculties");
		Page<AdjunctFaculty> page = adjunctFacultyRepository.findAllByOrderByShowOrder(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/adjunct-faculty");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
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
	 * GET /galleries : get all the galleries.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of galleries
	 *         in body
	 * @throws URISyntaxException
	 *             if there is an error to generate the pagination HTTP headers
	 */
	@GetMapping("/galleries")
	@Timed
	public ResponseEntity<List<Gallery>> getAllGalleries(@ApiParam Pageable pageable) throws URISyntaxException {
		LOGGER.debug("REST request to get a page of Galleries");
		Page<Gallery> page = galleryRepository.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/galleries");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /news-and-events : get latest 3 newsAndEvents.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         newsAndEvents in body
	 * @throws URISyntaxException
	 *             if there is an error to generate the pagination HTTP headers
	 */
	@GetMapping("/news-and-events-top3")
	@Timed
	public ResponseEntity<List<NewsAndEvent>> getAllNewsAndEvents() throws URISyntaxException {
		LOGGER.debug("REST request to get a page of NewsAndEvents");
		List<NewsAndEvent> page = newsAndEventRepository.findTop3ByOrderByStartDTDesc();
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(page, headers, HttpStatus.OK);
	}

	@GetMapping("/news-and-events")
	@Timed
	public ResponseEntity<List<NewsAndEvent>> getAllNewsAndEvents(@ApiParam Pageable pageable)
			throws URISyntaxException {
		LOGGER.debug("REST request to get a page of NewsAndEvents");
		Page<NewsAndEvent> page = newsAndEventRepository.findAllByOrderByStartDTDesc(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/news-and-events");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	@GetMapping("/online-applications")
	@Timed
	public ResponseEntity<List<OnlineApplication>> getAllOnlineApplications(@ApiParam Pageable pageable)
			throws URISyntaxException {
		LOGGER.debug("REST request to get a page of OnlineApplications");
		List<OnlineApplication> oaList = onlineApplicationRepository.findAll();
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(oaList, headers, HttpStatus.OK);
	}

	@GetMapping("/alumni")
	@Timed
	public ResponseEntity<StaticPage> getAlumni() throws URISyntaxException {
		LOGGER.debug("REST request to get Alumni");
		StaticPage staticPage = staticPageRepository.getStaticPageByTitle("Alumni");
		return Optional.ofNullable(staticPage).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
	 * GET /entry-qualifications/:id : get the "id" entryQualification.
	 *
	 * @param id
	 *            the id of the entryQualification to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         entryQualification, or with status 404 (Not Found)
	 */
	@GetMapping("/course/{id}/entry-qualifications")
	@Timed
	public ResponseEntity<EntryQualification> getEntryQualification(@PathVariable Long id) {
		LOGGER.debug("REST request to get EntryQualification : {}", id);
		Course course = courseRepository.findOne(id);
		EntryQualification entryQualification = entryQualificationRepository.getCourseEntryQualifications(course);
		return Optional.ofNullable(entryQualification).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * GET /galleries/:id : get the "id" gallery.
	 *
	 * @param id
	 *            the id of the gallery to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         gallery, or with status 404 (Not Found)
	 */
	@GetMapping("/galleries/{id}")
	@Timed
	public ResponseEntity<Gallery> getGallery(@PathVariable Long id) {
		LOGGER.debug("REST request to get Gallery : {}", id);
		Gallery gallery = galleryRepository.findOne(id);
		return Optional.ofNullable(gallery).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/governance")
	@Timed
	public ResponseEntity<StaticPage> getGovernance() throws URISyntaxException {
		LOGGER.debug("REST request to get Governance");
		StaticPage staticPage = staticPageRepository.getStaticPageByTitle("Governance");
		return Optional.ofNullable(staticPage).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/grading")
	@Timed
	public ResponseEntity<StaticPage> getGrading() throws URISyntaxException {
		LOGGER.debug("REST request to get Grading");
		StaticPage staticPage = staticPageRepository.getStaticPageByTitle("Grading");
		return Optional.ofNullable(staticPage).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/home")
	@Timed
	public ResponseEntity<StaticPage> getHomepage() throws URISyntaxException {
		LOGGER.debug("REST request to get Homepage");
		StaticPage staticPage = staticPageRepository.getStaticPageByTitle("Home");
		return Optional.ofNullable(staticPage).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/public-lecturer-profiles/{id}")
	@Timed
	public ResponseEntity<LecturerProfile> getLecturerProfile(@PathVariable Long id) {
		LOGGER.debug("REST request to get LecturerProfile : {}", id);
		LecturerProfile lecturerProfile = lecturerProfileRepository.findOne(id);
		return Optional.ofNullable(lecturerProfile).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/message-from-president")
	@Timed
	public ResponseEntity<StaticPage> getMessageFromPresident() throws URISyntaxException {
		LOGGER.debug("REST request to get Message From President");
		StaticPage staticPage = staticPageRepository.getStaticPageByTitle("Message from the President");
		return Optional.ofNullable(staticPage).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/news-and-events/{id}")
	@Timed
	public ResponseEntity<NewsAndEvent> getNewsAndEvent(@PathVariable Long id) {
		LOGGER.debug("REST request to get NewsAndEvent : {}", id);
		NewsAndEvent newsAndEvent = newsAndEventRepository.findOne(id);
		return Optional.ofNullable(newsAndEvent).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
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

	@GetMapping("/refund-policy")
	@Timed
	public ResponseEntity<StaticPage> getRefundPolicy() throws URISyntaxException {
		LOGGER.debug("REST request to get Refund Policy");
		StaticPage staticPage = staticPageRepository.getStaticPageByTitle("Refund Policy");
		return Optional.ofNullable(staticPage).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/course/{id}/research-papers")
	@Timed
	public ResponseEntity<List<ResearchPaper>> getResearchPaperList(@PathVariable Long id) {
		LOGGER.debug("REST request to get Research Papers from course : {}", id);
		Course course = courseRepository.findOne(id);
		List<ResearchPaper> page = researchPaperRepository.getByCourse(course);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(page, headers, HttpStatus.OK);
	}

	@GetMapping("/statement-of-faith")
	@Timed
	public ResponseEntity<StaticPage> getStatementOfFaith() throws URISyntaxException {
		LOGGER.debug("REST request to get Statement Of Faith");
		StaticPage staticPage = staticPageRepository.getStaticPageByTitle("Statement Of Faith");
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

	@GetMapping("/video-gallery")
	@Timed
	public ResponseEntity<StaticPage> getVideoGallery() throws URISyntaxException {
		LOGGER.debug("REST request to get Video Gallery");
		StaticPage staticPage = staticPageRepository.getStaticPageByTitle("Video Gallery");
		return Optional.ofNullable(staticPage).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PutMapping("/ministerial-work-experiences")
	@Timed
	public ResponseEntity<MinisterialWorkExperience> updateMinisterialWorkExperience(
			@Valid @RequestBody MinisterialWorkExperience ministerialWorkExperience) throws URISyntaxException {
		LOGGER.debug("REST request to update MinisterialWorkExperience : {}", ministerialWorkExperience);
		if (ministerialWorkExperience.getId() == null) {
			return createMinisterialWorkExperience(ministerialWorkExperience);
		}

		MinisterialWorkExperience result = ministerialWorkExperienceRepository.save(ministerialWorkExperience);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("ministerialWorkExperience",
				ministerialWorkExperience.getId().toString())).body(result);
	}

	@PutMapping("/registration-academic-details")
	@Timed
	public ResponseEntity<RegistrationAcademicDetails> updateRegistrationAcademicDetails(
			@Valid @RequestBody RegistrationAcademicDetails registrationAcademicDetails) throws URISyntaxException {
		LOGGER.debug("REST request to update RegistrationAcademicDetails : {}", registrationAcademicDetails);
		if (registrationAcademicDetails.getId() == null) {
			return createRegistrationAcademicDetails(registrationAcademicDetails);
		}

		RegistrationAcademicDetails result = registrationAcademicDetailsRepository.save(registrationAcademicDetails);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("registrationAcademicDetails",
				registrationAcademicDetails.getId().toString())).body(result);
	}

}
