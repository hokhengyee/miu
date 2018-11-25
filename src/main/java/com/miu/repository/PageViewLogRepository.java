package com.miu.repository;

import com.miu.domain.PageViewLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PageViewLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PageViewLogRepository extends JpaRepository<PageViewLog, Long> {

}
