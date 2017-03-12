package com.miu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.CourseMaterialAccess;

/**
 * Spring Data JPA repository for the CourseMaterialAccess entity.
 */
public interface CourseMaterialAccessRepository extends JpaRepository<CourseMaterialAccess, Long> {

	@Query("select cma from CourseMaterialAccess cma where cma.course.id = ?1 ORDER BY cma.displayOrder")
	List<CourseMaterialAccess> findCourseMaterialAccessByCourse(Long id);

}
