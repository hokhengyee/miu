package com.miu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A StudentResearchPaperResult.
 */
@Entity
@Table(name = "student_research_paper_result")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StudentResearchPaperResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "result")
    private String result;

    @Column(name = "date_graded")
    private LocalDate dateGraded;

    @Column(name = "result_order")
    private Long resultOrder;

    @ManyToOne
    @NotNull
    private ResearchPaper researchPaper;

    @ManyToOne
    @NotNull
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public StudentResearchPaperResult result(String result) {
        this.result = result;
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDate getDateGraded() {
        return dateGraded;
    }

    public StudentResearchPaperResult dateGraded(LocalDate dateGraded) {
        this.dateGraded = dateGraded;
        return this;
    }

    public void setDateGraded(LocalDate dateGraded) {
        this.dateGraded = dateGraded;
    }

    public Long getResultOrder() {
        return resultOrder;
    }

    public StudentResearchPaperResult resultOrder(Long resultOrder) {
        this.resultOrder = resultOrder;
        return this;
    }

    public void setResultOrder(Long resultOrder) {
        this.resultOrder = resultOrder;
    }

    public ResearchPaper getResearchPaper() {
        return researchPaper;
    }

    public StudentResearchPaperResult researchPaper(ResearchPaper researchPaper) {
        this.researchPaper = researchPaper;
        return this;
    }

    public void setResearchPaper(ResearchPaper researchPaper) {
        this.researchPaper = researchPaper;
    }

    public User getUser() {
        return user;
    }

    public StudentResearchPaperResult user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StudentResearchPaperResult studentResearchPaperResult = (StudentResearchPaperResult) o;
        if (studentResearchPaperResult.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, studentResearchPaperResult.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StudentResearchPaperResult{" +
            "id=" + id +
            ", result='" + result + "'" +
            ", dateGraded='" + dateGraded + "'" +
            ", resultOrder='" + resultOrder + "'" +
            '}';
    }
}
