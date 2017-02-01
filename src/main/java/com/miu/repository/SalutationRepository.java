package com.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.domain.Salutation;

/**
 * Spring Data JPA repository for the Salutation entity.
 */
public interface SalutationRepository extends JpaRepository<Salutation, Long> {

}
