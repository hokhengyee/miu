package com.miu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.Course;
import com.miu.domain.CourseMaterial;

/**
 * Spring Data JPA repository for the CourseMaterial entity.
 */
public interface CourseMaterialRepository extends JpaRepository<CourseMaterial, Long> {

	@Query("SELECT cm FROM CourseMaterial cm WHERE cm.course= ?1 ORDER BY cm.displayOrder")
	List<CourseMaterial> getCourseMaterialByCourseTitle(Course course);

	@Query("SELECT cm FROM CourseMaterial cm ORDER BY cm.displayOrder")
	List<CourseMaterial> getAllCourseMaterial();
}
