package com.miu.repository;

import com.miu.domain.OnlineApplication;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OnlineApplication entity.
 */
@SuppressWarnings("unused")
public interface OnlineApplicationRepository extends JpaRepository<OnlineApplication,Long> {

}
