package com.miu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CourseModule.
 */
@Entity
@Table(name = "course_module")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CourseModule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "display_order")
    private Long displayOrder;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Course course;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Module module;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDisplayOrder() {
        return displayOrder;
    }

    public CourseModule displayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
        return this;
    }

    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Course getCourse() {
        return course;
    }

    public CourseModule course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Module getModule() {
        return module;
    }

    public CourseModule module(Module module) {
        this.module = module;
        return this;
    }

    public void setModule(Module module) {
        this.module = module;
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
        CourseModule courseModule = (CourseModule) o;
        if (courseModule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), courseModule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CourseModule{" +
            "id=" + getId() +
            ", displayOrder=" + getDisplayOrder() +
            "}";
    }
}
