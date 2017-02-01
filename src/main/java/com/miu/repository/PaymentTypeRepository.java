package com.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.domain.PaymentType;

/**
 * Spring Data JPA repository for the PaymentType entity.
 */
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {

}
