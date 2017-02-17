package com.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.domain.CustomStudentReportType;

/**
 * Spring Data JPA repository for the CustomStudentReportType entity.
 */
public interface CustomStudentReportTypeRepository extends JpaRepository<CustomStudentReportType, Long> {

}
