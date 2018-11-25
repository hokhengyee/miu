package com.miu.repository;

import com.miu.domain.CommonResources;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommonResources entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommonResourcesRepository extends JpaRepository<CommonResources, Long> {

}
