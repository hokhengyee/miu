package com.miu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.StudentPayment;

/**
 * Spring Data JPA repository for the StudentPayment entity.
 */
public interface StudentPaymentRepository extends JpaRepository<StudentPayment, Long> {

	@Query("select studentPayment from StudentPayment studentPayment where studentPayment.user.login = ?#{principal.username}")
	List<StudentPayment> findByUserIsCurrentUser();

}
