package com.miu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A RecordOfCertificate.
 */
@Entity
@Table(name = "record_of_certificate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RecordOfCertificate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 1000)
    @Column(name = "name", length = 1000, nullable = false)
    private String name;

    @NotNull
    @Column(name = "jhi_degree", nullable = false)
    private String degree;

    @NotNull
    @Column(name = "student_no", nullable = false)
    private String studentNo;

    @NotNull
    @Column(name = "cert_number", nullable = false)
    private String certNumber;

    @Lob
    @Column(name = "cert_scan_file")
    private byte[] certScanFile;

    @Column(name = "cert_scan_file_content_type")
    private String certScanFileContentType;

    @Column(name = "cert_date")
    private LocalDate certDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public RecordOfCertificate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return degree;
    }

    public RecordOfCertificate degree(String degree) {
        this.degree = degree;
        return this;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public RecordOfCertificate studentNo(String studentNo) {
        this.studentNo = studentNo;
        return this;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getCertNumber() {
        return certNumber;
    }

    public RecordOfCertificate certNumber(String certNumber) {
        this.certNumber = certNumber;
        return this;
    }

    public void setCertNumber(String certNumber) {
        this.certNumber = certNumber;
    }

    public byte[] getCertScanFile() {
        return certScanFile;
    }

    public RecordOfCertificate certScanFile(byte[] certScanFile) {
        this.certScanFile = certScanFile;
        return this;
    }

    public void setCertScanFile(byte[] certScanFile) {
        this.certScanFile = certScanFile;
    }

    public String getCertScanFileContentType() {
        return certScanFileContentType;
    }

    public RecordOfCertificate certScanFileContentType(String certScanFileContentType) {
        this.certScanFileContentType = certScanFileContentType;
        return this;
    }

    public void setCertScanFileContentType(String certScanFileContentType) {
        this.certScanFileContentType = certScanFileContentType;
    }

    public LocalDate getCertDate() {
        return certDate;
    }

    public RecordOfCertificate certDate(LocalDate certDate) {
        this.certDate = certDate;
        return this;
    }

    public void setCertDate(LocalDate certDate) {
        this.certDate = certDate;
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
        RecordOfCertificate recordOfCertificate = (RecordOfCertificate) o;
        if (recordOfCertificate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recordOfCertificate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecordOfCertificate{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", degree='" + getDegree() + "'" +
            ", studentNo='" + getStudentNo() + "'" +
            ", certNumber='" + getCertNumber() + "'" +
            ", certScanFile='" + getCertScanFile() + "'" +
            ", certScanFileContentType='" + getCertScanFileContentType() + "'" +
            ", certDate='" + getCertDate() + "'" +
            "}";
    }
}
