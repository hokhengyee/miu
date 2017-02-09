package com.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.domain.ExternalOnlineResource;

/**
 * Spring Data JPA repository for the ExternalOnlineResource entity.
 */
public interface ExternalOnlineResourceRepository extends JpaRepository<ExternalOnlineResource, Long> {

}
