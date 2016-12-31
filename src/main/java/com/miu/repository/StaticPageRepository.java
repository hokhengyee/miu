package com.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.StaticPage;
import com.miu.domain.StaticPageType;

/**
 * Spring Data JPA repository for the StaticPage entity.
 */
public interface StaticPageRepository extends JpaRepository<StaticPage, Long> {

	@Query("SELECT sp FROM StaticPage sp WHERE sp.staticPageType= ?1")
	StaticPage getByStaticPageType(StaticPageType staticPageType);

}
