package com.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.AcademicCertificate;

/**
 * Spring Data JPA repository for the AcademicCertificate entity.
 */
public interface AcademicCertificateRepository extends JpaRepository<AcademicCertificate, Long> {

	@Query("SELECT ac FROM AcademicCertificate ac WHERE ac.md5Key = ?1")
	AcademicCertificate findACByMd5key(String md5Key);

}
