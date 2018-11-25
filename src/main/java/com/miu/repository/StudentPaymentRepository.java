package com.miu.repository;

import com.miu.domain.StudentPayment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the StudentPayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentPaymentRepository extends JpaRepository<StudentPayment, Long> {

    @Query("select student_payment from StudentPayment student_payment where student_payment.user.login = ?#{principal.username}")
    List<StudentPayment> findByUserIsCurrentUser();

}
