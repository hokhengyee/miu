package com.miu.repository;

import com.miu.domain.CustomStudentReportType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CustomStudentReportType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomStudentReportTypeRepository extends JpaRepository<CustomStudentReportType, Long> {

}
