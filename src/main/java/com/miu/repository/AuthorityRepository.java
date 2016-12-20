package com.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.domain.Authority;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
