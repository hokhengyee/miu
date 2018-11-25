package com.miu.repository;

import com.miu.domain.Gender;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Gender entity.
 */
@SuppressWarnings("unused")
public interface GenderRepository extends JpaRepository<Gender,Long> {

}
