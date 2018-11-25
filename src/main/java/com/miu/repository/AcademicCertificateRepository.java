package com.miu.repository;

import com.miu.domain.AcademicCertificate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AcademicCertificate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcademicCertificateRepository extends JpaRepository<AcademicCertificate, Long> {

}
