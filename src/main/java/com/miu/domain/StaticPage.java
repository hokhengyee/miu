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
 * A StaticPage.
 */
@Entity
@Table(name = "static_page")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StaticPage implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(max = 10000)
	@Column(name = "content", length = 10000, nullable = false)
	private String content;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@NotNull
	private StaticPageType staticPageType;

	public StaticPage content(String content) {
		this.content = content;
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

		StaticPage staticPage = (StaticPage) o;
		if (staticPage.id == null || id == null) {
			return false;
		}

		return Objects.equals(id, staticPage.id);
	}

	public String getContent() {
		return content;
	}

	public Long getId() {
		return id;
	}

	public StaticPageType getStaticPageType() {
		return staticPageType;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStaticPageType(StaticPageType staticPageType) {
		this.staticPageType = staticPageType;
	}

	public StaticPage staticPageType(StaticPageType staticPageType) {
		this.staticPageType = staticPageType;
		return this;
	}

	@Override
	public String toString() {
		return "StaticPage{" + "id=" + id + ", content='" + content + "'" + '}';
	}
}
