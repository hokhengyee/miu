package com.miu.repository;

import com.miu.domain.Salutation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Salutation entity.
 */
@SuppressWarnings("unused")
public interface SalutationRepository extends JpaRepository<Salutation,Long> {

}
