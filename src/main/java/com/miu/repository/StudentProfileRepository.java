package com.miu.repository;

import com.miu.domain.StudentProfile;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the StudentProfile entity.
 */
@SuppressWarnings("unused")
public interface StudentProfileRepository extends JpaRepository<StudentProfile,Long> {

    @Query("select studentProfile from StudentProfile studentProfile where studentProfile.supervisor.login = ?#{principal.username}")
    List<StudentProfile> findBySupervisorIsCurrentUser();

    @Query("select studentProfile from StudentProfile studentProfile where studentProfile.user.login = ?#{principal.username}")
    List<StudentProfile> findByUserIsCurrentUser();

}
