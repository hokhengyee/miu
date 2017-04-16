package com.miu.repository;

import java.util.List;

import com.miu.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.StudentModuleResult;

/**
 * Spring Data JPA repository for the StudentModuleResult entity.
 */
public interface StudentModuleResultRepository extends JpaRepository<StudentModuleResult, Long> {

	@Query("select studentModuleResult from StudentModuleResult studentModuleResult where studentModuleResult.user.login = ?#{principal.username}")
	List<StudentModuleResult> findByUserIsCurrentUser();

    @Query("select studentModuleResult from StudentModuleResult studentModuleResult where studentModuleResult.user = ?1")
	List<StudentModuleResult> findByUser(User user);

	@Query("SELECT r FROM StudentModuleResult r WHERE r.module.moduleType=2 AND r.user.login = ?#{principal.username} ORDER BY r.resultOrder")
	List<StudentModuleResult> getPracticalMinistryResult();

	@Query("SELECT smr FROM StudentModuleResult smr where smr.user.id = ?1")
	List<StudentModuleResult> getResultByUser(Long id);

	@Query("SELECT smr FROM StudentModuleResult smr where smr.user.id = ?1 AND smr.module.moduleCode = ?2")
	StudentModuleResult getResultByUserAndModule(Long id, String moduleCode);

	@Query("SELECT r FROM StudentModuleResult r WHERE r.module.moduleType=1 AND r.user.login = ?#{principal.username} ORDER BY r.resultOrder")
	List<StudentModuleResult> getTheologicalResult();

	@Query("SELECT r FROM StudentModuleResult r WHERE r.module.moduleType=2 AND r.user.id = ?1 ORDER BY r.resultOrder")
	List<StudentModuleResult> getUserPracticalMinistryResult(Long id);

	@Query("SELECT r FROM StudentModuleResult r WHERE r.module.moduleType=1 AND r.user.id = ?1 ORDER BY r.resultOrder")
	List<StudentModuleResult> getUserTheologicalResult(Long id);

}
