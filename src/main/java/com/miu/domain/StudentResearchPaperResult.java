package com.miu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "result")
    private String result;

    @Column(name = "date_graded")
    private LocalDate dateGraded;

    @Column(name = "result_order")
    private Long resultOrder;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private ResearchPaper researchPaper;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StudentResearchPaperResult studentResearchPaperResult = (StudentResearchPaperResult) o;
        if (studentResearchPaperResult.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentResearchPaperResult.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentResearchPaperResult{" +
            "id=" + getId() +
            ", result='" + getResult() + "'" +
            ", dateGraded='" + getDateGraded() + "'" +
            ", resultOrder=" + getResultOrder() +
            "}";
    }
}
