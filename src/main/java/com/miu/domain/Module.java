package com.miu.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Module.
 */
@Entity
@Table(name = "module")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Module implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@NotNull
	private Course course;

	@Column(name = "description")
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(name = "module_code", nullable = false)
	private String moduleCode;

	@Column(name = "module_order")
	private Long moduleOrder;

	@ManyToOne
	@NotNull
	private ModuleType moduleType;

	@NotNull
	@Column(name = "title", nullable = false)
	private String title;

	public Module course(Course course) {
		this.course = course;
		return this;
	}

	public Module description(String description) {
		this.description = description;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Module module = (Module) o;
		if (module.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, module.id);
	}

	public Course getCourse() {
		return course;
	}

	public String getDescription() {
		return description;
	}

	public Long getId() {
		return id;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public Long getModuleOrder() {
		return moduleOrder;
	}

	public ModuleType getModuleType() {
		return moduleType;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	public Module moduleCode(String moduleCode) {
		this.moduleCode = moduleCode;
		return this;
	}

	public Module moduleOrder(Long moduleOrder) {
		this.moduleOrder = moduleOrder;
		return this;
	}

	public Module moduleType(ModuleType moduleType) {
		this.moduleType = moduleType;
		return this;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public void setModuleOrder(Long moduleOrder) {
		this.moduleOrder = moduleOrder;
	}

	public void setModuleType(ModuleType moduleType) {
		this.moduleType = moduleType;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Module title(String title) {
		this.title = title;
		return this;
	}

	@Override
	public String toString() {
		return "Module{" + "id=" + id + ", description='" + description + "'" + ", moduleOrder='" + moduleOrder + "'"
				+ ", moduleCode='" + moduleCode + "'" + ", title='" + title + "'" + '}';
	}
}
