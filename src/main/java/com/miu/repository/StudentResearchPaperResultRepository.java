package com.miu.repository;

import com.miu.domain.StudentResearchPaperResult;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the StudentResearchPaperResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentResearchPaperResultRepository extends JpaRepository<StudentResearchPaperResult, Long> {

    @Query("select student_research_paper_result from StudentResearchPaperResult student_research_paper_result where student_research_paper_result.user.login = ?#{principal.username}")
    List<StudentResearchPaperResult> findByUserIsCurrentUser();

}
