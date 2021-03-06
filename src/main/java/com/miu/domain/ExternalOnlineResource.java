package com.miu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ExternalOnlineResource.
 */
@Entity
@Table(name = "external_online_resource")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExternalOnlineResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "website_link")
    private String websiteLink;

    @Lob
    @Column(name = "description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public ExternalOnlineResource title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public ExternalOnlineResource websiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
        return this;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public String getDescription() {
        return description;
    }

    public ExternalOnlineResource description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExternalOnlineResource externalOnlineResource = (ExternalOnlineResource) o;
        if (externalOnlineResource.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, externalOnlineResource.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ExternalOnlineResource{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", websiteLink='" + websiteLink + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
