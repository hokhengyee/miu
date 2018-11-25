package com.miu.repository;

import com.miu.domain.StaticPage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the StaticPage entity.
 */
@SuppressWarnings("unused")
public interface StaticPageRepository extends JpaRepository<StaticPage,Long> {

}
