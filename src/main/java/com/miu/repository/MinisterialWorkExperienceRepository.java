package com.miu.repository;

import com.miu.domain.MinisterialWorkExperience;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MinisterialWorkExperience entity.
 */
@SuppressWarnings("unused")
public interface MinisterialWorkExperienceRepository extends JpaRepository<MinisterialWorkExperience,Long> {

}
