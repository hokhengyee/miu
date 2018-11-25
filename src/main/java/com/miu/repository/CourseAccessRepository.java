package com.miu.repository;

import com.miu.domain.CourseAccess;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CourseAccess entity.
 */
@SuppressWarnings("unused")
public interface CourseAccessRepository extends JpaRepository<CourseAccess,Long> {

    @Query("select courseAccess from CourseAccess courseAccess where courseAccess.user.login = ?#{principal.username}")
    List<CourseAccess> findByUserIsCurrentUser();

}
