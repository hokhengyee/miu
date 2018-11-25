package com.miu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ResearchPaper.
 */
@Entity
@Table(name = "research_paper")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ResearchPaper implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Size(max = 1000)
    @Column(name = "title", length = 1000, nullable = false)
    private String title;

    @Column(name = "show_order")
    private Long showOrder;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @NotNull
    private Course course;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public ResearchPaper code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public ResearchPaper title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getShowOrder() {
        return showOrder;
    }

    public ResearchPaper showOrder(Long showOrder) {
        this.showOrder = showOrder;
        return this;
    }

    public void setShowOrder(Long showOrder) {
        this.showOrder = showOrder;
    }

    public String getDescription() {
        return description;
    }

    public ResearchPaper description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course getCourse() {
        return course;
    }

    public ResearchPaper course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
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

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ResearchPaper{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", title='" + title + "'" +
            ", showOrder='" + showOrder + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
