package com.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.domain.Gender;

/**
 * Spring Data JPA repository for the Gender entity.
 */
public interface GenderRepository extends JpaRepository<Gender, Long> {

}
