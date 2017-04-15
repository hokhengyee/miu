package com.miu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.CourseAccess;
import com.miu.domain.User;

/**
 * Spring Data JPA repository for the CourseAccess entity.
 */
public interface CourseAccessRepository extends JpaRepository<CourseAccess, Long> {

	@Query("select courseAccess from CourseAccess courseAccess where courseAccess.user = ?1")
	CourseAccess findByUser(User user);

	@Query("select courseAccess from CourseAccess courseAccess where courseAccess.user.login = ?#{principal.username}")
	List<CourseAccess> findByUserIsCurrentUser();

}
