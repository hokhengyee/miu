package com.miu.repository;

import com.miu.domain.AdjunctFaculty;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AdjunctFaculty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdjunctFacultyRepository extends JpaRepository<AdjunctFaculty, Long> {

}
