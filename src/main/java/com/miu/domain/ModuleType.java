package com.miu.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ModuleType.
 */
@Entity
@Table(name = "module_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ModuleType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "description")
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(name = "module_type_order", nullable = false)
	private Long moduleTypeOrder;

	@NotNull
	@Column(name = "title", nullable = false)
	private String title;

	public ModuleType description(String description) {
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
		ModuleType moduleType = (ModuleType) o;
		if (moduleType.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, moduleType.id);
	}

	public String getDescription() {
		return description;
	}

	public Long getId() {
		return id;
	}

	public Long getModuleTypeOrder() {
		return moduleTypeOrder;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	public ModuleType moduleTypeOrder(Long moduleTypeOrder) {
		this.moduleTypeOrder = moduleTypeOrder;
		return this;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setModuleTypeOrder(Long moduleTypeOrder) {
		this.moduleTypeOrder = moduleTypeOrder;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ModuleType title(String title) {
		this.title = title;
		return this;
	}

	@Override
	public String toString() {
		return "ModuleType{" + "id=" + id + ", title='" + title + "'" + ", description='" + description + "'"
				+ ", moduleTypeOrder='" + moduleTypeOrder + "'" + '}';
	}
}
