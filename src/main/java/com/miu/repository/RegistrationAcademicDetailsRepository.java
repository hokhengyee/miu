package com.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.RegistrationAcademicDetails;

/**
 * Spring Data JPA repository for the RegistrationAcademicDetails entity.
 */
public interface RegistrationAcademicDetailsRepository extends JpaRepository<RegistrationAcademicDetails, Long> {

	@Query("SELECT rad FROM RegistrationAcademicDetails rad WHERE rad.md5key = ?1")
	RegistrationAcademicDetails findRADByMd5key(String md5key);

}
