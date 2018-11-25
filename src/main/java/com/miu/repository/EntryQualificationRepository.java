package com.miu.repository;

import com.miu.domain.EntryQualification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EntryQualification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntryQualificationRepository extends JpaRepository<EntryQualification, Long> {

}
