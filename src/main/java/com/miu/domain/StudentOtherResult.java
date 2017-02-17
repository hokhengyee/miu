package com.miu.domain;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "title")
    private String title;

    @Column(name = "result")
    private String result;

    @Column(name = "date_graded")
    private LocalDate dateGraded;

    @ManyToOne
    @NotNull
    private CustomStudentReportType customStudentReportType;

    @ManyToOne
    @NotNull
    private User user;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StudentOtherResult studentOtherResult = (StudentOtherResult) o;
        if (studentOtherResult.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, studentOtherResult.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StudentOtherResult{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", title='" + title + "'" +
            ", result='" + result + "'" +
            ", dateGraded='" + dateGraded + "'" +
            '}';
    }
}
