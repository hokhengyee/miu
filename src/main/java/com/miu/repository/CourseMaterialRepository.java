package com.miu.repository;

import com.miu.domain.CourseMaterial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CourseMaterial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseMaterialRepository extends JpaRepository<CourseMaterial, Long> {

}
