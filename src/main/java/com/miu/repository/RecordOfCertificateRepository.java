package com.miu.repository;

import com.miu.domain.RecordOfCertificate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RecordOfCertificate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecordOfCertificateRepository extends JpaRepository<RecordOfCertificate, Long> {

}
