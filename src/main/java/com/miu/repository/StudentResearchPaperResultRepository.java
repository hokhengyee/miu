package com.miu.repository;

import com.miu.domain.StudentResearchPaperResult;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the StudentResearchPaperResult entity.
 */
@SuppressWarnings("unused")
public interface StudentResearchPaperResultRepository extends JpaRepository<StudentResearchPaperResult,Long> {

    @Query("select studentResearchPaperResult from StudentResearchPaperResult studentResearchPaperResult where studentResearchPaperResult.user.login = ?#{principal.username}")
    List<StudentResearchPaperResult> findByUserIsCurrentUser();

}
