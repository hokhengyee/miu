package com.miu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.AdjunctFaculty;

/**
 * Spring Data JPA repository for the AdjunctFaculty entity.
 */
public interface AdjunctFacultyRepository extends JpaRepository<AdjunctFaculty, Long> {

	@Query(value = "select distinct a from AdjunctFaculty a order by a.showOrder", countQuery = "select count(a) from AdjunctFaculty a")
	Page<AdjunctFaculty> findAllByOrderByShowOrder(Pageable pageable);
}
