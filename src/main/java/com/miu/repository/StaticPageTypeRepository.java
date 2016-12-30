package com.miu.repository;

import com.miu.domain.StaticPageType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the StaticPageType entity.
 */
@SuppressWarnings("unused")
public interface StaticPageTypeRepository extends JpaRepository<StaticPageType,Long> {

}
