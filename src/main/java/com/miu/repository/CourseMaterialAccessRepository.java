package com.miu.repository;

import com.miu.domain.CourseMaterialAccess;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CourseMaterialAccess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseMaterialAccessRepository extends JpaRepository<CourseMaterialAccess, Long> {

}
