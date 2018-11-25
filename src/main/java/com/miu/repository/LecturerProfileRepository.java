package com.miu.repository;

import com.miu.domain.LecturerProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the LecturerProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LecturerProfileRepository extends JpaRepository<LecturerProfile, Long> {

    @Query("select lecturer_profile from LecturerProfile lecturer_profile where lecturer_profile.user.login = ?#{principal.username}")
    List<LecturerProfile> findByUserIsCurrentUser();

}
