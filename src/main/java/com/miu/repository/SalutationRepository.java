package com.miu.repository;

import com.miu.domain.Salutation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Salutation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalutationRepository extends JpaRepository<Salutation, Long> {

}
