package com.miu.repository;

import com.miu.domain.AcademicCertificate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AcademicCertificate entity.
 */
@SuppressWarnings("unused")
public interface AcademicCertificateRepository extends JpaRepository<AcademicCertificate,Long> {

}
