package com.miu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.Course;
import com.miu.domain.ResearchPaper;

/**
 * Spring Data JPA repository for the ResearchPaper entity.
 */
public interface ResearchPaperRepository extends JpaRepository<ResearchPaper, Long> {

	@Query("SELECT rp FROM ResearchPaper rp WHERE rp.code= ?1 ORDER BY rp.id")
	List<ResearchPaper> getByCode(String code);

	@Query("SELECT rp FROM ResearchPaper rp WHERE rp.course= ?1 ORDER BY rp.showOrder")
	List<ResearchPaper> getByCourse(Course courseID);

}
