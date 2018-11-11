package com.miu.repository;

import com.miu.domain.NewsAndEvent;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the NewsAndEvent entity.
 */
@SuppressWarnings("unused")
public interface NewsAndEventRepository extends JpaRepository<NewsAndEvent,Long> {

}
