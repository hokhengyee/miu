package com.miu.repository;

import com.miu.domain.StudentResearchPaperResult;
import com.miu.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the StudentResearchPaperResult entity.
 */
public interface StudentResearchPaperResultRepository extends JpaRepository<StudentResearchPaperResult, Long> {

    @Query("select studentResearchPaperResult from StudentResearchPaperResult studentResearchPaperResult where studentResearchPaperResult.user.login = ?#{principal.username}")
    List<StudentResearchPaperResult> findByUserIsCurrentUser();

    @Query("select studentResearchPaperResult from StudentResearchPaperResult studentResearchPaperResult where studentResearchPaperResult.user = ?1")
    List<StudentResearchPaperResult> findByUserIsCurrentUser(User user);

    @Query("SELECT r FROM StudentResearchPaperResult r WHERE r.user.login = ?#{principal.username} ORDER BY r.resultOrder")
    List<StudentResearchPaperResult> getMyResearchPaperResults();

    @Query("SELECT r FROM StudentResearchPaperResult r WHERE r.user.id = ?1")
    List<StudentResearchPaperResult> getResultByUser(Long id);

    @Query("SELECT r FROM StudentResearchPaperResult r WHERE r.user.id = ?1 AND r.researchPaper.code = ?2")
    StudentResearchPaperResult getResultByUserAndResearchPaper(Long id, String researchPaperCode);

    @Query("SELECT r FROM StudentResearchPaperResult r WHERE r.user.id = ?1 ORDER BY r.resultOrder")
    List<StudentResearchPaperResult> getUserResearchPaperResults(Long id);

}
