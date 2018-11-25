package com.miu.repository;

import com.miu.domain.RegistrationAcademicDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RegistrationAcademicDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistrationAcademicDetailsRepository extends JpaRepository<RegistrationAcademicDetails, Long> {

}
