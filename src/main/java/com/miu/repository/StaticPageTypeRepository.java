package com.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.domain.StaticPageType;

/**
 * Spring Data JPA repository for the StaticPageType entity.
 */
public interface StaticPageTypeRepository extends JpaRepository<StaticPageType, Long> {

}
