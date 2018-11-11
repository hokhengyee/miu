package com.miu.repository;

import com.miu.domain.CourseMaterialAccess;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CourseMaterialAccess entity.
 */
@SuppressWarnings("unused")
public interface CourseMaterialAccessRepository extends JpaRepository<CourseMaterialAccess,Long> {

}
