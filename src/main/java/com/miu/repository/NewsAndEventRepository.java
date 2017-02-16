package com.miu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.domain.NewsAndEvent;

/**
 * Spring Data JPA repository for the NewsAndEvent entity.
 */
public interface NewsAndEventRepository extends JpaRepository<NewsAndEvent, Long> {

	Page<NewsAndEvent> findAllByOrderByStartDTDesc(Pageable page);

	List<NewsAndEvent> findTop3ByOrderByStartDTDesc();

}
