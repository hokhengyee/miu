package com.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.RecordOfCertificate;

/**
 * Spring Data JPA repository for the RecordOfCertificate entity.
 */
public interface RecordOfCertificateRepository extends JpaRepository<RecordOfCertificate, Long> {

	@Query("select rc from RecordOfCertificate rc where rc.name = ?1 and degree = ?2")
	RecordOfCertificate findByNameAndDegree(String name, String degree);

}
