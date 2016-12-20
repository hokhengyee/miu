package com.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.domain.Module;

/**
 * Spring Data JPA repository for the Module entity.
 */
public interface ModuleRepository extends JpaRepository<Module, Long> {

}
