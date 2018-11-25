package com.miu.repository;

import com.miu.domain.ResearchPaper;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ResearchPaper entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResearchPaperRepository extends JpaRepository<ResearchPaper, Long> {

}
