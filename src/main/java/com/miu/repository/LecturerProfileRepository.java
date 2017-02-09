package com.miu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.LecturerProfile;

/**
 * Spring Data JPA repository for the LecturerProfile entity.
 */
public interface LecturerProfileRepository extends JpaRepository<LecturerProfile, Long> {

	@Query("select lecturerProfile from LecturerProfile lecturerProfile where lecturerProfile.user.login = ?#{principal.username}")
	List<LecturerProfile> findByUserIsCurrentUser();

	@Query("select lecturerProfile from LecturerProfile lecturerProfile where lecturerProfile.user.login = ?#{principal.username}")
	LecturerProfile findLecturerIsCurrentUser();

}
