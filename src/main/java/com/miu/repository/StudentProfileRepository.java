package com.miu.repository;

import com.miu.domain.StudentProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the StudentProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {

    @Query("select student_profile from StudentProfile student_profile where student_profile.supervisor.login = ?#{principal.username}")
    List<StudentProfile> findBySupervisorIsCurrentUser();

    @Query("select student_profile from StudentProfile student_profile where student_profile.user.login = ?#{principal.username}")
    List<StudentProfile> findByUserIsCurrentUser();

}
