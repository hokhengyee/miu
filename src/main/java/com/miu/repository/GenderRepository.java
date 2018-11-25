package com.miu.repository;

import com.miu.domain.Gender;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Gender entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenderRepository extends JpaRepository<Gender, Long> {

}
