package com.miu.repository;

import com.miu.domain.CustomStudentReportType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CustomStudentReportType entity.
 */
@SuppressWarnings("unused")
public interface CustomStudentReportTypeRepository extends JpaRepository<CustomStudentReportType,Long> {

}
