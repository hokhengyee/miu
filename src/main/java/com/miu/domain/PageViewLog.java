package com.miu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PageViewLog.
 */
@Entity
@Table(name = "page_view_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PageViewLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "views")
    private Long views;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public PageViewLog createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Long getViews() {
        return views;
    }

    public PageViewLog views(Long views) {
        this.views = views;
        return this;
    }

    public void setViews(Long views) {
        this.views = views;
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
        PageViewLog pageViewLog = (PageViewLog) o;
        if (pageViewLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pageViewLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PageViewLog{" +
            "id=" + getId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", views=" + getViews() +
            "}";
    }
}
