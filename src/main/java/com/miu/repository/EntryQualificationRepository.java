package com.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.Course;
import com.miu.domain.EntryQualification;

/**
 * Spring Data JPA repository for the EntryQualification entity.
 */
public interface EntryQualificationRepository extends JpaRepository<EntryQualification, Long> {

	@Query("SELECT e FROM EntryQualification e WHERE e.course= ?1")
	EntryQualification getCourseEntryQualifications(Course courseID);

}
