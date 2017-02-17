package com.miu.repository;

import com.miu.domain.StudentOtherResult;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the StudentOtherResult entity.
 */
@SuppressWarnings("unused")
public interface StudentOtherResultRepository extends JpaRepository<StudentOtherResult,Long> {

    @Query("select studentOtherResult from StudentOtherResult studentOtherResult where studentOtherResult.user.login = ?#{principal.username}")
    List<StudentOtherResult> findByUserIsCurrentUser();

}
