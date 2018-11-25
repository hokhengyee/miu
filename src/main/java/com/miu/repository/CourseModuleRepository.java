package com.miu.repository;

import com.miu.domain.CourseModule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CourseModule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseModuleRepository extends JpaRepository<CourseModule, Long> {

}
