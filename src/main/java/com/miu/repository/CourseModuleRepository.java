package com.miu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.CourseModule;

/**
 * Spring Data JPA repository for the CourseModule entity.
 */
public interface CourseModuleRepository extends JpaRepository<CourseModule, Long> {

	@Query("SELECT cm FROM CourseModule cm WHERE cm.course.id = ?1 AND cm.module.moduleType = 2 ORDER BY cm.displayOrder")
	List<CourseModule> getPracticalMinistry(Long id);

	@Query("SELECT cm FROM CourseModule cm WHERE cm.course.id = ?1 AND cm.module.moduleType = 1 ORDER BY cm.displayOrder")
	List<CourseModule> getTheological(Long id);

}
