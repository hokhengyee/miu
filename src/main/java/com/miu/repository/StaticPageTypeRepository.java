package com.miu.repository;

import com.miu.domain.StaticPageType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StaticPageType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StaticPageTypeRepository extends JpaRepository<StaticPageType, Long> {

}
