package com.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.domain.CourseMaterialAccess;

/**
 * Spring Data JPA repository for the CourseMaterialAccess entity.
 */
public interface CourseMaterialAccessRepository extends JpaRepository<CourseMaterialAccess, Long> {

}
