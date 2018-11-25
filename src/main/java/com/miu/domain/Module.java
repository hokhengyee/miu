package com.miu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Module.
 */
@Entity
@Table(name = "module")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "module_order")
    private Long moduleOrder;

    @NotNull
    @Column(name = "module_code", nullable = false)
    private String moduleCode;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private ModuleType moduleType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Course course;

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

    public Module description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getModuleOrder() {
        return moduleOrder;
    }

    public Module moduleOrder(Long moduleOrder) {
        this.moduleOrder = moduleOrder;
        return this;
    }

    public void setModuleOrder(Long moduleOrder) {
        this.moduleOrder = moduleOrder;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public Module moduleCode(String moduleCode) {
        this.moduleCode = moduleCode;
        return this;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getTitle() {
        return title;
    }

    public Module title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ModuleType getModuleType() {
        return moduleType;
    }

    public Module moduleType(ModuleType moduleType) {
        this.moduleType = moduleType;
        return this;
    }

    public void setModuleType(ModuleType moduleType) {
        this.moduleType = moduleType;
    }

    public Course getCourse() {
        return course;
    }

    public Module course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
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
        Module module = (Module) o;
        if (module.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), module.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Module{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", moduleOrder=" + getModuleOrder() +
            ", moduleCode='" + getModuleCode() + "'" +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
