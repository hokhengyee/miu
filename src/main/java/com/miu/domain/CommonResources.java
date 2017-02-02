package com.miu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CommonResources.
 */
@Entity
@Table(name = "common_resources")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommonResources implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content")
    private byte[] content;

    @Column(name = "content_content_type")
    private String contentContentType;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "display_order")
    private Long displayOrder;

    @Column(name = "website_link")
    private String websiteLink;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public CommonResources title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getContent() {
        return content;
    }

    public CommonResources content(byte[] content) {
        this.content = content;
        return this;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentContentType() {
        return contentContentType;
    }

    public CommonResources contentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
        return this;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
    }

    public String getDescription() {
        return description;
    }

    public CommonResources description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDisplayOrder() {
        return displayOrder;
    }

    public CommonResources displayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
        return this;
    }

    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public CommonResources websiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
        return this;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommonResources commonResources = (CommonResources) o;
        if (commonResources.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, commonResources.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CommonResources{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", content='" + content + "'" +
            ", contentContentType='" + contentContentType + "'" +
            ", description='" + description + "'" +
            ", displayOrder='" + displayOrder + "'" +
            ", websiteLink='" + websiteLink + "'" +
            '}';
    }
}
