package com.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.MinisterialWorkExperience;

/**
 * Spring Data JPA repository for the MinisterialWorkExperience entity.
 */
public interface MinisterialWorkExperienceRepository extends JpaRepository<MinisterialWorkExperience, Long> {

	@Query("SELECT mwe FROM MinisterialWorkExperience mwe WHERE mwe.md5Key = ?1")
	MinisterialWorkExperience findMWEByMd5key(String md5Key);

}
