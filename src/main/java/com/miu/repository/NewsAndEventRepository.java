package com.miu.repository;

import com.miu.domain.NewsAndEvent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NewsAndEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NewsAndEventRepository extends JpaRepository<NewsAndEvent, Long> {

}
