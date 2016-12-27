package com.miu.repository;

import com.miu.domain.EntryQualification;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EntryQualification entity.
 */
@SuppressWarnings("unused")
public interface EntryQualificationRepository extends JpaRepository<EntryQualification,Long> {

}
