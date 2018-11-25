package com.miu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A StudentProfile.
 */
@Entity
@Table(name = "student_profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StudentProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "student_id", nullable = false)
    private String studentId;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotNull
    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    @NotNull
    @Column(name = "commencement_date", nullable = false)
    private LocalDate commencementDate;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @Size(max = 1000)
    @Lob
    @Column(name = "mailing_address", length = 1000, nullable = false)
    private String mailingAddress;

    @Lob
    @Column(name = "profile_photo")
    private byte[] profilePhoto;

    @Column(name = "profile_photo_content_type")
    private String profilePhotoContentType;

    @Column(name = "extended_completion_date")
    private LocalDate extendedCompletionDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Salutation salutation;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Gender gender;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private User supervisor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public StudentProfile studentId(String studentId) {
        this.studentId = studentId;
        return this;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public StudentProfile dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public StudentProfile phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public StudentProfile applicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
        return this;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public LocalDate getCommencementDate() {
        return commencementDate;
    }

    public StudentProfile commencementDate(LocalDate commencementDate) {
        this.commencementDate = commencementDate;
        return this;
    }

    public void setCommencementDate(LocalDate commencementDate) {
        this.commencementDate = commencementDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public StudentProfile completionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
        return this;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public StudentProfile mailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
        return this;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public StudentProfile profilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
        return this;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getProfilePhotoContentType() {
        return profilePhotoContentType;
    }

    public StudentProfile profilePhotoContentType(String profilePhotoContentType) {
        this.profilePhotoContentType = profilePhotoContentType;
        return this;
    }

    public void setProfilePhotoContentType(String profilePhotoContentType) {
        this.profilePhotoContentType = profilePhotoContentType;
    }

    public LocalDate getExtendedCompletionDate() {
        return extendedCompletionDate;
    }

    public StudentProfile extendedCompletionDate(LocalDate extendedCompletionDate) {
        this.extendedCompletionDate = extendedCompletionDate;
        return this;
    }

    public void setExtendedCompletionDate(LocalDate extendedCompletionDate) {
        this.extendedCompletionDate = extendedCompletionDate;
    }

    public Salutation getSalutation() {
        return salutation;
    }

    public StudentProfile salutation(Salutation salutation) {
        this.salutation = salutation;
        return this;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
    }

    public Gender getGender() {
        return gender;
    }

    public StudentProfile gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public StudentProfile supervisor(User user) {
        this.supervisor = user;
        return this;
    }

    public void setSupervisor(User user) {
        this.supervisor = user;
    }

    public User getUser() {
        return user;
    }

    public StudentProfile user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        StudentProfile studentProfile = (StudentProfile) o;
        if (studentProfile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentProfile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentProfile{" +
            "id=" + getId() +
            ", studentId='" + getStudentId() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", phone='" + getPhone() + "'" +
            ", applicationDate='" + getApplicationDate() + "'" +
            ", commencementDate='" + getCommencementDate() + "'" +
            ", completionDate='" + getCompletionDate() + "'" +
            ", mailingAddress='" + getMailingAddress() + "'" +
            ", profilePhoto='" + getProfilePhoto() + "'" +
            ", profilePhotoContentType='" + getProfilePhotoContentType() + "'" +
            ", extendedCompletionDate='" + getExtendedCompletionDate() + "'" +
            "}";
    }
}
