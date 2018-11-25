package com.miu.repository;

import com.miu.domain.MinisterialWorkExperience;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MinisterialWorkExperience entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MinisterialWorkExperienceRepository extends JpaRepository<MinisterialWorkExperience, Long> {

}
