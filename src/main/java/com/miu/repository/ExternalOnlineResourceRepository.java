package com.miu.repository;

import com.miu.domain.ExternalOnlineResource;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ExternalOnlineResource entity.
 */
@SuppressWarnings("unused")
public interface ExternalOnlineResourceRepository extends JpaRepository<ExternalOnlineResource,Long> {

}
