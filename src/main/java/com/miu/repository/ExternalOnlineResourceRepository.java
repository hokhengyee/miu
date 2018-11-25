package com.miu.repository;

import com.miu.domain.ExternalOnlineResource;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ExternalOnlineResource entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExternalOnlineResourceRepository extends JpaRepository<ExternalOnlineResource, Long> {

}
