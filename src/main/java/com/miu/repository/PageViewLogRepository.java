package com.miu.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.domain.PageViewLog;

/**
 * Spring Data JPA repository for the PageViewLog entity.
 */
public interface PageViewLogRepository extends JpaRepository<PageViewLog, Long> {

	PageViewLog findByCreatedDate(LocalDate dateToday);

}
