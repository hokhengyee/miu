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
 * A StudentOtherResult.
 */
@Entity
@Table(name = "student_other_result")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StudentOtherResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "title")
    private String title;

    @Column(name = "result")
    private String result;

    @Column(name = "date_graded")
    private LocalDate dateGraded;

    @Column(name = "result_order")
    private Long resultOrder;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private CustomStudentReportType customStudentReportType;

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

    public String getCode() {
        return code;
    }

    public StudentOtherResult code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public StudentOtherResult title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResult() {
        return result;
    }

    public StudentOtherResult result(String result) {
        this.result = result;
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDate getDateGraded() {
        return dateGraded;
    }

    public StudentOtherResult dateGraded(LocalDate dateGraded) {
        this.dateGraded = dateGraded;
        return this;
    }

    public void setDateGraded(LocalDate dateGraded) {
        this.dateGraded = dateGraded;
    }

    public Long getResultOrder() {
        return resultOrder;
    }

    public StudentOtherResult resultOrder(Long resultOrder) {
        this.resultOrder = resultOrder;
        return this;
    }

    public void setResultOrder(Long resultOrder) {
        this.resultOrder = resultOrder;
    }

    public CustomStudentReportType getCustomStudentReportType() {
        return customStudentReportType;
    }

    public StudentOtherResult customStudentReportType(CustomStudentReportType customStudentReportType) {
        this.customStudentReportType = customStudentReportType;
        return this;
    }

    public void setCustomStudentReportType(CustomStudentReportType customStudentReportType) {
        this.customStudentReportType = customStudentReportType;
    }

    public User getUser() {
        return user;
    }

    public StudentOtherResult user(User user) {
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
        StudentOtherResult studentOtherResult = (StudentOtherResult) o;
        if (studentOtherResult.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentOtherResult.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentOtherResult{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", title='" + getTitle() + "'" +
            ", result='" + getResult() + "'" +
            ", dateGraded='" + getDateGraded() + "'" +
            ", resultOrder=" + getResultOrder() +
            "}";
    }
}
