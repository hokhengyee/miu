package com.miu.repository;

import com.miu.domain.AdjunctFaculty;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AdjunctFaculty entity.
 */
@SuppressWarnings("unused")
public interface AdjunctFacultyRepository extends JpaRepository<AdjunctFaculty,Long> {

}
