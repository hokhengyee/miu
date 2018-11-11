package com.miu.repository;

import com.miu.domain.StudentPayment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the StudentPayment entity.
 */
@SuppressWarnings("unused")
public interface StudentPaymentRepository extends JpaRepository<StudentPayment,Long> {

    @Query("select studentPayment from StudentPayment studentPayment where studentPayment.user.login = ?#{principal.username}")
    List<StudentPayment> findByUserIsCurrentUser();

}
