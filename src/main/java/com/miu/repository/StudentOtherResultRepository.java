package com.miu.repository;

import java.util.List;

import com.miu.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.StudentOtherResult;

/**
 * Spring Data JPA repository for the StudentOtherResult entity.
 */
public interface StudentOtherResultRepository extends JpaRepository<StudentOtherResult, Long> {

    @Query("select studentOtherResult from StudentOtherResult studentOtherResult where studentOtherResult.user.login = ?#{principal.username}")
    List<StudentOtherResult> findByUserIsCurrentUser();

    @Query("select studentOtherResult from StudentOtherResult studentOtherResult where studentOtherResult.user = ?1")
    List<StudentOtherResult> findByUser(User user);

    @Query("SELECT r FROM StudentOtherResult r WHERE r.customStudentReportType=1 AND r.user.login = ?#{principal.username} ORDER BY r.resultOrder")
    List<StudentOtherResult> getMyArticleResults();

    @Query("SELECT r FROM StudentOtherResult r WHERE r.customStudentReportType=2 AND r.user.login = ?#{principal.username} ORDER BY r.resultOrder")
    List<StudentOtherResult> getMyBookReviewResults();

    @Query("SELECT r FROM StudentOtherResult r WHERE r.customStudentReportType=3 AND r.user.login = ?#{principal.username} ORDER BY r.resultOrder")
    List<StudentOtherResult> getMyDissertationResults();

    @Query("SELECT r FROM StudentOtherResult r WHERE r.customStudentReportType=4 AND r.user.login = ?#{principal.username} ORDER BY r.resultOrder")
    List<StudentOtherResult> getMySermonResults();

    @Query("SELECT r FROM StudentOtherResult r WHERE r.customStudentReportType=1 AND r.user.id = ?1 ORDER BY r.resultOrder")
    List<StudentOtherResult> getUserArticleResults(Long id);

    @Query("SELECT r FROM StudentOtherResult r WHERE r.customStudentReportType=2 AND r.user.id = ?1 ORDER BY r.resultOrder")
    List<StudentOtherResult> getUserBookReviewResults(Long id);

    @Query("SELECT r FROM StudentOtherResult r WHERE r.customStudentReportType=3 AND r.user.id = ?1 ORDER BY r.resultOrder")
    List<StudentOtherResult> getUserDissertationResults(Long id);

    @Query("SELECT r FROM StudentOtherResult r WHERE r.customStudentReportType=4 AND r.user.id = ?1 ORDER BY r.resultOrder")
    List<StudentOtherResult> getUserSermonResults(Long id);

}
