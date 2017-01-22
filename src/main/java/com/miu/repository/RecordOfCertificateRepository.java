package com.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.domain.RecordOfCertificate;

/**
 * Spring Data JPA repository for the RecordOfCertificate entity.
 */
public interface RecordOfCertificateRepository extends JpaRepository<RecordOfCertificate, Long> {

}
