package com.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.domain.ModuleType;

/**
 * Spring Data JPA repository for the ModuleType entity.
 */
public interface ModuleTypeRepository extends JpaRepository<ModuleType, Long> {

}
