package com.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.OnlineApplication;

/**
 * Spring Data JPA repository for the OnlineApplication entity.
 */
public interface OnlineApplicationRepository extends JpaRepository<OnlineApplication, Long> {

	@Query("SELECT oa FROM OnlineApplication oa WHERE oa.md5key = ?1")
	OnlineApplication findOAByMd5key(String md5key);

}
