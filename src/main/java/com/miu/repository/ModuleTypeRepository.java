package com.miu.repository;

import com.miu.domain.ModuleType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ModuleType entity.
 */
@SuppressWarnings("unused")
public interface ModuleTypeRepository extends JpaRepository<ModuleType,Long> {

}
