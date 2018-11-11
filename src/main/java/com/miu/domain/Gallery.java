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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "image_title")
    private String imageTitle;

    @NotNull
    @Lob
    @Column(name = "gallery_photo", nullable = false)
    private byte[] galleryPhoto;

    @Column(name = "gallery_photo_content_type", nullable = false)
    private String galleryPhotoContentType;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Gallery gallery = (Gallery) o;
        if (gallery.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, gallery.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Gallery{" +
            "id=" + id +
            ", imageTitle='" + imageTitle + "'" +
            ", galleryPhoto='" + galleryPhoto + "'" +
            ", galleryPhotoContentType='" + galleryPhotoContentType + "'" +
            '}';
    }
}
