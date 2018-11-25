package com.miu.repository;

import com.miu.domain.PaymentType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PaymentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {

}
