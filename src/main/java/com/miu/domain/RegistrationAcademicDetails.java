package com.miu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A RegistrationAcademicDetails.
 */
@Entity
@Table(name = "registration_academic_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RegistrationAcademicDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 1000)
    @Column(name = "name_of_institution_2", length = 1000)
    private String nameOfInstitution2;

    @Size(max = 1000)
    @Column(name = "exam_passed_2", length = 1000)
    private String examPassed2;

    @Min(value = 1900L)
    @Max(value = 9999L)
    @Column(name = "year_2")
    private Long year2;

    @Column(name = "grade_2")
    private String grade2;

    @Size(max = 1000)
    @Column(name = "name_of_institution_3", length = 1000)
    private String nameOfInstitution3;

    @Size(max = 1000)
    @Column(name = "exam_passed_3", length = 1000)
    private String examPassed3;

    @Min(value = 1900L)
    @Max(value = 9999L)
    @Column(name = "year_3")
    private Long year3;

    @Column(name = "grade_3")
    private String grade3;

    @Size(max = 1000)
    @Column(name = "name_of_institution_4", length = 1000)
    private String nameOfInstitution4;

    @Size(max = 1000)
    @Column(name = "exam_passed_4", length = 1000)
    private String examPassed4;

    @Min(value = 1900L)
    @Max(value = 9999L)
    @Column(name = "year_4")
    private Long year4;

    @Column(name = "grade_4")
    private String grade4;

    @Column(name = "md_5_key")
    private String md5key;

    @Size(max = 1000)
    @Column(name = "name_of_institution_1", length = 1000)
    private String nameOfInstitution1;

    @Size(max = 1000)
    @Column(name = "exam_passed_1", length = 1000)
    private String examPassed1;

    @Min(value = 1900L)
    @Max(value = 9999L)
    @Column(name = "year_1")
    private Long year1;

    @Column(name = "grade_1")
    private String grade1;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOfInstitution2() {
        return nameOfInstitution2;
    }

    public RegistrationAcademicDetails nameOfInstitution2(String nameOfInstitution2) {
        this.nameOfInstitution2 = nameOfInstitution2;
        return this;
    }

    public void setNameOfInstitution2(String nameOfInstitution2) {
        this.nameOfInstitution2 = nameOfInstitution2;
    }

    public String getExamPassed2() {
        return examPassed2;
    }

    public RegistrationAcademicDetails examPassed2(String examPassed2) {
        this.examPassed2 = examPassed2;
        return this;
    }

    public void setExamPassed2(String examPassed2) {
        this.examPassed2 = examPassed2;
    }

    public Long getYear2() {
        return year2;
    }

    public RegistrationAcademicDetails year2(Long year2) {
        this.year2 = year2;
        return this;
    }

    public void setYear2(Long year2) {
        this.year2 = year2;
    }

    public String getGrade2() {
        return grade2;
    }

    public RegistrationAcademicDetails grade2(String grade2) {
        this.grade2 = grade2;
        return this;
    }

    public void setGrade2(String grade2) {
        this.grade2 = grade2;
    }

    public String getNameOfInstitution3() {
        return nameOfInstitution3;
    }

    public RegistrationAcademicDetails nameOfInstitution3(String nameOfInstitution3) {
        this.nameOfInstitution3 = nameOfInstitution3;
        return this;
    }

    public void setNameOfInstitution3(String nameOfInstitution3) {
        this.nameOfInstitution3 = nameOfInstitution3;
    }

    public String getExamPassed3() {
        return examPassed3;
    }

    public RegistrationAcademicDetails examPassed3(String examPassed3) {
        this.examPassed3 = examPassed3;
        return this;
    }

    public void setExamPassed3(String examPassed3) {
        this.examPassed3 = examPassed3;
    }

    public Long getYear3() {
        return year3;
    }

    public RegistrationAcademicDetails year3(Long year3) {
        this.year3 = year3;
        return this;
    }

    public void setYear3(Long year3) {
        this.year3 = year3;
    }

    public String getGrade3() {
        return grade3;
    }

    public RegistrationAcademicDetails grade3(String grade3) {
        this.grade3 = grade3;
        return this;
    }

    public void setGrade3(String grade3) {
        this.grade3 = grade3;
    }

    public String getNameOfInstitution4() {
        return nameOfInstitution4;
    }

    public RegistrationAcademicDetails nameOfInstitution4(String nameOfInstitution4) {
        this.nameOfInstitution4 = nameOfInstitution4;
        return this;
    }

    public void setNameOfInstitution4(String nameOfInstitution4) {
        this.nameOfInstitution4 = nameOfInstitution4;
    }

    public String getExamPassed4() {
        return examPassed4;
    }

    public RegistrationAcademicDetails examPassed4(String examPassed4) {
        this.examPassed4 = examPassed4;
        return this;
    }

    public void setExamPassed4(String examPassed4) {
        this.examPassed4 = examPassed4;
    }

    public Long getYear4() {
        return year4;
    }

    public RegistrationAcademicDetails year4(Long year4) {
        this.year4 = year4;
        return this;
    }

    public void setYear4(Long year4) {
        this.year4 = year4;
    }

    public String getGrade4() {
        return grade4;
    }

    public RegistrationAcademicDetails grade4(String grade4) {
        this.grade4 = grade4;
        return this;
    }

    public void setGrade4(String grade4) {
        this.grade4 = grade4;
    }

    public String getMd5key() {
        return md5key;
    }

    public RegistrationAcademicDetails md5key(String md5key) {
        this.md5key = md5key;
        return this;
    }

    public void setMd5key(String md5key) {
        this.md5key = md5key;
    }

    public String getNameOfInstitution1() {
        return nameOfInstitution1;
    }

    public RegistrationAcademicDetails nameOfInstitution1(String nameOfInstitution1) {
        this.nameOfInstitution1 = nameOfInstitution1;
        return this;
    }

    public void setNameOfInstitution1(String nameOfInstitution1) {
        this.nameOfInstitution1 = nameOfInstitution1;
    }

    public String getExamPassed1() {
        return examPassed1;
    }

    public RegistrationAcademicDetails examPassed1(String examPassed1) {
        this.examPassed1 = examPassed1;
        return this;
    }

    public void setExamPassed1(String examPassed1) {
        this.examPassed1 = examPassed1;
    }

    public Long getYear1() {
        return year1;
    }

    public RegistrationAcademicDetails year1(Long year1) {
        this.year1 = year1;
        return this;
    }

    public void setYear1(Long year1) {
        this.year1 = year1;
    }

    public String getGrade1() {
        return grade1;
    }

    public RegistrationAcademicDetails grade1(String grade1) {
        this.grade1 = grade1;
        return this;
    }

    public void setGrade1(String grade1) {
        this.grade1 = grade1;
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
        RegistrationAcademicDetails registrationAcademicDetails = (RegistrationAcademicDetails) o;
        if (registrationAcademicDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), registrationAcademicDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RegistrationAcademicDetails{" +
            "id=" + getId() +
            ", nameOfInstitution2='" + getNameOfInstitution2() + "'" +
            ", examPassed2='" + getExamPassed2() + "'" +
            ", year2=" + getYear2() +
            ", grade2='" + getGrade2() + "'" +
            ", nameOfInstitution3='" + getNameOfInstitution3() + "'" +
            ", examPassed3='" + getExamPassed3() + "'" +
            ", year3=" + getYear3() +
            ", grade3='" + getGrade3() + "'" +
            ", nameOfInstitution4='" + getNameOfInstitution4() + "'" +
            ", examPassed4='" + getExamPassed4() + "'" +
            ", year4=" + getYear4() +
            ", grade4='" + getGrade4() + "'" +
            ", md5key='" + getMd5key() + "'" +
            ", nameOfInstitution1='" + getNameOfInstitution1() + "'" +
            ", examPassed1='" + getExamPassed1() + "'" +
            ", year1=" + getYear1() +
            ", grade1='" + getGrade1() + "'" +
            "}";
    }
}
