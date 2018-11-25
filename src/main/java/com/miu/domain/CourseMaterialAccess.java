package com.miu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CourseMaterialAccess.
 */
@Entity
@Table(name = "course_material_access")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CourseMaterialAccess implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "display_order")
    private Long displayOrder;

    @ManyToOne
    @NotNull
    private Course course;

    @ManyToOne
    @NotNull
    private CourseMaterial courseMaterial;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDisplayOrder() {
        return displayOrder;
    }

    public CourseMaterialAccess displayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
        return this;
    }

    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Course getCourse() {
        return course;
    }

    public CourseMaterialAccess course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public CourseMaterial getCourseMaterial() {
        return courseMaterial;
    }

    public CourseMaterialAccess courseMaterial(CourseMaterial courseMaterial) {
        this.courseMaterial = courseMaterial;
        return this;
    }

    public void setCourseMaterial(CourseMaterial courseMaterial) {
        this.courseMaterial = courseMaterial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CourseMaterialAccess courseMaterialAccess = (CourseMaterialAccess) o;
        if (courseMaterialAccess.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, courseMaterialAccess.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CourseMaterialAccess{" +
            "id=" + id +
            ", displayOrder='" + displayOrder + "'" +
            '}';
    }
}
