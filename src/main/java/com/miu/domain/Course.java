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
 * A Course.
 */
@Entity
@Table(name = "course")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Course implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "course_order")
	private Long courseOrder;

	@Column(name = "description")
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(name = "title", nullable = false)
	private String title;

	public Course courseOrder(Long courseOrder) {
		this.courseOrder = courseOrder;
		return this;
	}

	public Course description(String description) {
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
		Course course = (Course) o;
		if (course.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, course.id);
	}

	public Long getCourseOrder() {
		return courseOrder;
	}

	public String getDescription() {
		return description;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	public void setCourseOrder(Long courseOrder) {
		this.courseOrder = courseOrder;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Course title(String title) {
		this.title = title;
		return this;
	}

	@Override
	public String toString() {
		return "Course{" + "id=" + id + ", description='" + description + "'" + ", courseOrder='" + courseOrder + "'"
				+ ", title='" + title + "'" + '}';
	}
}
