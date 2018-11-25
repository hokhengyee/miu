package com.miu.repository;

import com.miu.domain.StudentModuleResult;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the StudentModuleResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentModuleResultRepository extends JpaRepository<StudentModuleResult, Long> {

    @Query("select student_module_result from StudentModuleResult student_module_result where student_module_result.user.login = ?#{principal.username}")
    List<StudentModuleResult> findByUserIsCurrentUser();

}
