package com.miu.repository;

import com.miu.domain.PageViewLog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PageViewLog entity.
 */
@SuppressWarnings("unused")
public interface PageViewLogRepository extends JpaRepository<PageViewLog,Long> {

}
