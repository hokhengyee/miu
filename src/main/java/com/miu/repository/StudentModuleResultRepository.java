package com.miu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.StudentModuleResult;

/**
 * Spring Data JPA repository for the StudentModuleResult entity.
 */
public interface StudentModuleResultRepository extends JpaRepository<StudentModuleResult, Long> {

	@Query("select studentModuleResult from StudentModuleResult studentModuleResult where studentModuleResult.user.login = ?#{principal.username}")
	List<StudentModuleResult> findByUserIsCurrentUser();

	@Query("SELECT r FROM StudentModuleResult r WHERE r.module.moduleType=2 AND r.user.login = ?#{principal.username} ORDER BY r.resultOrder")
	List<StudentModuleResult> getPracticalMinistryResult();

	@Query("SELECT r FROM StudentModuleResult r WHERE r.module.moduleType=1 AND r.user.login = ?#{principal.username} ORDER BY r.resultOrder")
	List<StudentModuleResult> getTheologicalResult();

}
