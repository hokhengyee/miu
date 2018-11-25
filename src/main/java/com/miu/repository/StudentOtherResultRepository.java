package com.miu.repository;

import com.miu.domain.StudentOtherResult;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the StudentOtherResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentOtherResultRepository extends JpaRepository<StudentOtherResult, Long> {

    @Query("select student_other_result from StudentOtherResult student_other_result where student_other_result.user.login = ?#{principal.username}")
    List<StudentOtherResult> findByUserIsCurrentUser();

}
