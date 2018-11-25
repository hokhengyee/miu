package com.miu.repository;

import com.miu.domain.StaticPage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StaticPage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StaticPageRepository extends JpaRepository<StaticPage, Long> {

}
