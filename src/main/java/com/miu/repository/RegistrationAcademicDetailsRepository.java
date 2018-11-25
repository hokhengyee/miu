package com.miu.repository;

import com.miu.domain.RegistrationAcademicDetails;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RegistrationAcademicDetails entity.
 */
@SuppressWarnings("unused")
public interface RegistrationAcademicDetailsRepository extends JpaRepository<RegistrationAcademicDetails,Long> {

}
