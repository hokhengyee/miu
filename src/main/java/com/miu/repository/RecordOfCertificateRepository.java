package com.miu.repository;

import com.miu.domain.RecordOfCertificate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RecordOfCertificate entity.
 */
@SuppressWarnings("unused")
public interface RecordOfCertificateRepository extends JpaRepository<RecordOfCertificate,Long> {

}
