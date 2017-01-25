package com.miu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.StudentProfile;

/**
 * Spring Data JPA repository for the StudentProfile entity.
 */
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {

	@Query("select studentProfile from StudentProfile studentProfile where studentProfile.supervisor.login = ?#{principal.username}")
	List<StudentProfile> findBySupervisorIsCurrentUser();

	@Query("select studentProfile from StudentProfile studentProfile where studentProfile.user.login = ?#{principal.username}")
	StudentProfile findByUserIsCurrentUser();

}
