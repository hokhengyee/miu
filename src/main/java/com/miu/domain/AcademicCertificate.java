package com.miu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AcademicCertificate.
 */
@Entity
@Table(name = "academic_certificate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AcademicCertificate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "md_5_key")
    private String md5Key;

    @Lob
    @Column(name = "academic_certificate_1")
    private byte[] academicCertificate1;

    @Column(name = "academic_certificate_1_content_type")
    private String academicCertificate1ContentType;

    @Lob
    @Column(name = "academic_certificate_2")
    private byte[] academicCertificate2;

    @Column(name = "academic_certificate_2_content_type")
    private String academicCertificate2ContentType;

    @Lob
    @Column(name = "academic_certificate_3")
    private byte[] academicCertificate3;

    @Column(name = "academic_certificate_3_content_type")
    private String academicCertificate3ContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMd5Key() {
        return md5Key;
    }

    public AcademicCertificate md5Key(String md5Key) {
        this.md5Key = md5Key;
        return this;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

    public byte[] getAcademicCertificate1() {
        return academicCertificate1;
    }

    public AcademicCertificate academicCertificate1(byte[] academicCertificate1) {
        this.academicCertificate1 = academicCertificate1;
        return this;
    }

    public void setAcademicCertificate1(byte[] academicCertificate1) {
        this.academicCertificate1 = academicCertificate1;
    }

    public String getAcademicCertificate1ContentType() {
        return academicCertificate1ContentType;
    }

    public AcademicCertificate academicCertificate1ContentType(String academicCertificate1ContentType) {
        this.academicCertificate1ContentType = academicCertificate1ContentType;
        return this;
    }

    public void setAcademicCertificate1ContentType(String academicCertificate1ContentType) {
        this.academicCertificate1ContentType = academicCertificate1ContentType;
    }

    public byte[] getAcademicCertificate2() {
        return academicCertificate2;
    }

    public AcademicCertificate academicCertificate2(byte[] academicCertificate2) {
        this.academicCertificate2 = academicCertificate2;
        return this;
    }

    public void setAcademicCertificate2(byte[] academicCertificate2) {
        this.academicCertificate2 = academicCertificate2;
    }

    public String getAcademicCertificate2ContentType() {
        return academicCertificate2ContentType;
    }

    public AcademicCertificate academicCertificate2ContentType(String academicCertificate2ContentType) {
        this.academicCertificate2ContentType = academicCertificate2ContentType;
        return this;
    }

    public void setAcademicCertificate2ContentType(String academicCertificate2ContentType) {
        this.academicCertificate2ContentType = academicCertificate2ContentType;
    }

    public byte[] getAcademicCertificate3() {
        return academicCertificate3;
    }

    public AcademicCertificate academicCertificate3(byte[] academicCertificate3) {
        this.academicCertificate3 = academicCertificate3;
        return this;
    }

    public void setAcademicCertificate3(byte[] academicCertificate3) {
        this.academicCertificate3 = academicCertificate3;
    }

    public String getAcademicCertificate3ContentType() {
        return academicCertificate3ContentType;
    }

    public AcademicCertificate academicCertificate3ContentType(String academicCertificate3ContentType) {
        this.academicCertificate3ContentType = academicCertificate3ContentType;
        return this;
    }

    public void setAcademicCertificate3ContentType(String academicCertificate3ContentType) {
        this.academicCertificate3ContentType = academicCertificate3ContentType;
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
        AcademicCertificate academicCertificate = (AcademicCertificate) o;
        if (academicCertificate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), academicCertificate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AcademicCertificate{" +
            "id=" + getId() +
            ", md5Key='" + getMd5Key() + "'" +
            ", academicCertificate1='" + getAcademicCertificate1() + "'" +
            ", academicCertificate1ContentType='" + getAcademicCertificate1ContentType() + "'" +
            ", academicCertificate2='" + getAcademicCertificate2() + "'" +
            ", academicCertificate2ContentType='" + getAcademicCertificate2ContentType() + "'" +
            ", academicCertificate3='" + getAcademicCertificate3() + "'" +
            ", academicCertificate3ContentType='" + getAcademicCertificate3ContentType() + "'" +
            "}";
    }
}
