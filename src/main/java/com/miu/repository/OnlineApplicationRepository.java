package com.miu.repository;

import com.miu.domain.OnlineApplication;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OnlineApplication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OnlineApplicationRepository extends JpaRepository<OnlineApplication, Long> {

}
