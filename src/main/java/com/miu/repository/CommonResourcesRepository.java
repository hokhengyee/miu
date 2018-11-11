package com.miu.repository;

import com.miu.domain.CommonResources;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CommonResources entity.
 */
@SuppressWarnings("unused")
public interface CommonResourcesRepository extends JpaRepository<CommonResources,Long> {

}
