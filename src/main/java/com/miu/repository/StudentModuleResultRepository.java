package com.miu.repository;

import com.miu.domain.StudentModuleResult;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the StudentModuleResult entity.
 */
@SuppressWarnings("unused")
public interface StudentModuleResultRepository extends JpaRepository<StudentModuleResult,Long> {

    @Query("select studentModuleResult from StudentModuleResult studentModuleResult where studentModuleResult.user.login = ?#{principal.username}")
    List<StudentModuleResult> findByUserIsCurrentUser();

}
