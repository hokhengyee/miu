package com.miu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.StudentOtherResult;

/**
 * Spring Data JPA repository for the StudentOtherResult entity.
 */
public interface StudentOtherResultRepository extends JpaRepository<StudentOtherResult, Long> {

	@Query("select studentOtherResult from StudentOtherResult studentOtherResult where studentOtherResult.user.login = ?#{principal.username}")
	List<StudentOtherResult> findByUserIsCurrentUser();

}
