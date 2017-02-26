package com.miu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.domain.CommonResources;

/**
 * Spring Data JPA repository for the CommonResources entity.
 */
public interface CommonResourcesRepository extends JpaRepository<CommonResources, Long> {

	List<CommonResources> findAllByOrderByDisplayOrder();

}
