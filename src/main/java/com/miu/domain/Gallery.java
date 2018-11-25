package com.miu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Gallery.
 */
@Entity
@Table(name = "gallery")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Gallery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_title")
    private String imageTitle;

    
    @Lob
    @Column(name = "gallery_photo", nullable = false)
    private byte[] galleryPhoto;

    @Column(name = "gallery_photo_content_type", nullable = false)
    private String galleryPhotoContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public Gallery imageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
        return this;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public byte[] getGalleryPhoto() {
        return galleryPhoto;
    }

    public Gallery galleryPhoto(byte[] galleryPhoto) {
        this.galleryPhoto = galleryPhoto;
        return this;
    }

    public void setGalleryPhoto(byte[] galleryPhoto) {
        this.galleryPhoto = galleryPhoto;
    }

    public String getGalleryPhotoContentType() {
        return galleryPhotoContentType;
    }

    public Gallery galleryPhotoContentType(String galleryPhotoContentType) {
        this.galleryPhotoContentType = galleryPhotoContentType;
        return this;
    }

    public void setGalleryPhotoContentType(String galleryPhotoContentType) {
        this.galleryPhotoContentType = galleryPhotoContentType;
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
        Gallery gallery = (Gallery) o;
        if (gallery.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gallery.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Gallery{" +
            "id=" + getId() +
            ", imageTitle='" + getImageTitle() + "'" +
            ", galleryPhoto='" + getGalleryPhoto() + "'" +
            ", galleryPhotoContentType='" + getGalleryPhotoContentType() + "'" +
            "}";
    }
}
