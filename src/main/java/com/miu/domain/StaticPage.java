package com.miu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A StaticPage.
 */
@Entity
@Table(name = "static_page")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StaticPage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 10000)
    @Column(name = "content", length = 10000, nullable = false)
    private String content;

    @ManyToOne
    @NotNull
    private StaticPageType staticPageType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public StaticPage content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public StaticPageType getStaticPageType() {
        return staticPageType;
    }

    public StaticPage staticPageType(StaticPageType staticPageType) {
        this.staticPageType = staticPageType;
        return this;
    }

    public void setStaticPageType(StaticPageType staticPageType) {
        this.staticPageType = staticPageType;
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

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StaticPage{" +
            "id=" + id +
            ", content='" + content + "'" +
            '}';
    }
}
