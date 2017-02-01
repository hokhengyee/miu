package com.miu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ModuleType.
 */
@Entity
@Table(name = "module_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ModuleType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "module_type_order", nullable = false)
    private Long moduleTypeOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public ModuleType title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public ModuleType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getModuleTypeOrder() {
        return moduleTypeOrder;
    }

    public ModuleType moduleTypeOrder(Long moduleTypeOrder) {
        this.moduleTypeOrder = moduleTypeOrder;
        return this;
    }

    public void setModuleTypeOrder(Long moduleTypeOrder) {
        this.moduleTypeOrder = moduleTypeOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModuleType moduleType = (ModuleType) o;
        if (moduleType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, moduleType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ModuleType{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            ", moduleTypeOrder='" + moduleTypeOrder + "'" +
            '}';
    }
}
