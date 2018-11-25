package com.miu.repository;

import com.miu.domain.CourseAccess;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CourseAccess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseAccessRepository extends JpaRepository<CourseAccess, Long> {

    @Query("select course_access from CourseAccess course_access where course_access.user.login = ?#{principal.username}")
    List<CourseAccess> findByUserIsCurrentUser();

}
