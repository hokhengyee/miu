package com.miu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "course_order")
    private Long courseOrder;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "credit_hours", nullable = false)
    private Long creditHours;

    @NotNull
    @Column(name = "application_fee", nullable = false)
    private String applicationFee;

    @NotNull
    @Column(name = "registration_fee", nullable = false)
    private String registrationFee;

    @NotNull
    @Column(name = "course_fee", nullable = false)
    private String courseFee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Course description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCourseOrder() {
        return courseOrder;
    }

    public Course courseOrder(Long courseOrder) {
        this.courseOrder = courseOrder;
        return this;
    }

    public void setCourseOrder(Long courseOrder) {
        this.courseOrder = courseOrder;
    }

    public String getTitle() {
        return title;
    }

    public Course title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCreditHours() {
        return creditHours;
    }

    public Course creditHours(Long creditHours) {
        this.creditHours = creditHours;
        return this;
    }

    public void setCreditHours(Long creditHours) {
        this.creditHours = creditHours;
    }

    public String getApplicationFee() {
        return applicationFee;
    }

    public Course applicationFee(String applicationFee) {
        this.applicationFee = applicationFee;
        return this;
    }

    public void setApplicationFee(String applicationFee) {
        this.applicationFee = applicationFee;
    }

    public String getRegistrationFee() {
        return registrationFee;
    }

    public Course registrationFee(String registrationFee) {
        this.registrationFee = registrationFee;
        return this;
    }

    public void setRegistrationFee(String registrationFee) {
        this.registrationFee = registrationFee;
    }

    public String getCourseFee() {
        return courseFee;
    }

    public Course courseFee(String courseFee) {
        this.courseFee = courseFee;
        return this;
    }

    public void setCourseFee(String courseFee) {
        this.courseFee = courseFee;
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
        Course course = (Course) o;
        if (course.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), course.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", courseOrder=" + getCourseOrder() +
            ", title='" + getTitle() + "'" +
            ", creditHours=" + getCreditHours() +
            ", applicationFee='" + getApplicationFee() + "'" +
            ", registrationFee='" + getRegistrationFee() + "'" +
            ", courseFee='" + getCourseFee() + "'" +
            "}";
    }
}
