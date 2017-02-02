package com.miu.repository;

import com.miu.domain.CourseMaterial;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CourseMaterial entity.
 */
@SuppressWarnings("unused")
public interface CourseMaterialRepository extends JpaRepository<CourseMaterial,Long> {

}
