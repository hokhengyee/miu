package com.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.domain.OnlineApplication;

/**
 * Spring Data JPA repository for the OnlineApplication entity.
 */
public interface OnlineApplicationRepository extends JpaRepository<OnlineApplication, Long> {

}
