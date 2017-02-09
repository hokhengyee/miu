package com.miu.repository;

import com.miu.domain.LecturerProfile;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LecturerProfile entity.
 */
@SuppressWarnings("unused")
public interface LecturerProfileRepository extends JpaRepository<LecturerProfile,Long> {

    @Query("select lecturerProfile from LecturerProfile lecturerProfile where lecturerProfile.user.login = ?#{principal.username}")
    List<LecturerProfile> findByUserIsCurrentUser();

}
