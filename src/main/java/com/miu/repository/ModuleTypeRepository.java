package com.miu.repository;

import com.miu.domain.ModuleType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ModuleType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModuleTypeRepository extends JpaRepository<ModuleType, Long> {

}
