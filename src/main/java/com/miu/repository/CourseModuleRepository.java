package com.miu.repository;

import com.miu.domain.CourseModule;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CourseModule entity.
 */
@SuppressWarnings("unused")
public interface CourseModuleRepository extends JpaRepository<CourseModule,Long> {

}
