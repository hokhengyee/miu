package com.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.domain.NewsAndEvent;

/**
 * Spring Data JPA repository for the NewsAndEvent entity.
 */
public interface NewsAndEventRepository extends JpaRepository<NewsAndEvent, Long> {

}
