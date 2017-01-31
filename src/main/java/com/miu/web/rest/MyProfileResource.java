package com.miu.web.rest;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.miu.config.Constants;
import com.miu.domain.StudentPayment;
import com.miu.domain.StudentProfile;
import com.miu.repository.StudentPaymentRepository;
import com.miu.repository.StudentProfileRepository;
import com.miu.service.UserService;
import com.miu.web.rest.vm.ManagedUserVM;

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

	private final Logger LOGGER = LoggerFactory.getLogger(MyProfileResource.class);

	@Inject
	private StudentProfileRepository studentProfileRepository;

	@Inject
	private UserService userService;

	@Inject
	private StudentPaymentRepository studentPaymentRepository;

	/**
	 * GET /student-profiles/:id : get the "id" studentProfile.
	 *
	 * @param id
	 *            the id of the studentProfile to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         studentProfile, or with status 404 (Not Found)
	 */
	@GetMapping("/my-student-profiles")
	@Timed
	public ResponseEntity<StudentProfile> getStudentProfile() {
		StudentProfile studentProfile = studentProfileRepository.findByUserIsCurrentUser();
		return Optional.ofNullable(studentProfile).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/my-payments")
	@Timed
	public ResponseEntity<List<StudentPayment>> getStudentPayments() {
		List<StudentPayment> paymentList = studentPaymentRepository.findByUserIsCurrentUser();
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(paymentList, headers, HttpStatus.OK);
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
}
