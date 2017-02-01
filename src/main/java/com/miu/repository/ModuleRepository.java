package com.miu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.Course;
import com.miu.domain.Module;

/**
 * Spring Data JPA repository for the Module entity.
 */
public interface ModuleRepository extends JpaRepository<Module, Long> {

	@Query("SELECT m FROM Module m WHERE m.course= ?1 AND m.moduleType=2 ORDER BY m.moduleOrder")
	List<Module> getPracticalMinistry(Course courseID);

	@Query("SELECT m FROM Module m WHERE m.course= ?1 AND m.moduleType=1 ORDER BY m.moduleOrder")
	List<Module> getTheological(Course courseID);
}
