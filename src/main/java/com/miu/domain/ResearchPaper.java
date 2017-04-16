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
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ResearchPaper.
 */
@Entity
@Table(name = "research_paper")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ResearchPaper implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "code", nullable = false)
	private String code;

	@ManyToOne
	@NotNull
	private Course course;

	@Column(name = "description")
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "show_order")
	private Long showOrder;

	@NotNull
	@Size(max = 1000)
	@Column(name = "title", length = 1000, nullable = false)
	private String title;

	public ResearchPaper code(String code) {
		this.code = code;
		return this;
	}

	public ResearchPaper course(Course course) {
		this.course = course;
		return this;
	}

	public ResearchPaper description(String description) {
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
		ResearchPaper researchPaper = (ResearchPaper) o;
		if (researchPaper.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, researchPaper.id);
	}

	public String getCode() {
		return code;
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

	public Long getShowOrder() {
		return showOrder;
	}

	public String getTitle() {
		return title + " - " + this.getCourse().getTitle();
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	public void setCode(String code) {
		this.code = code;
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

	public void setShowOrder(Long showOrder) {
		this.showOrder = showOrder;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ResearchPaper showOrder(Long showOrder) {
		this.showOrder = showOrder;
		return this;
	}

	public ResearchPaper title(String title) {
		this.title = title;
		return this;
	}

	@Override
	public String toString() {
		return "ResearchPaper{" + "id=" + id + ", code='" + code + "'" + ", title='" + title + "'" + ", showOrder='"
				+ showOrder + "'" + ", description='" + description + "'" + '}';
	}
}
