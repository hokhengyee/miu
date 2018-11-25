package com.miu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A OnlineApplication.
 */
@Entity
@Table(name = "online_application")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OnlineApplication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    @Size(max = 1000)
    @Column(name = "telephone", length = 1000, nullable = false)
    private String telephone;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Size(max = 1000)
    @Column(name = "city", length = 1000, nullable = false)
    private String city;

    @NotNull
    @Size(max = 1000)
    @Column(name = "state", length = 1000, nullable = false)
    private String state;

    @NotNull
    @Size(max = 1000)
    @Column(name = "country", length = 1000, nullable = false)
    private String country;

    @NotNull
    @Size(max = 1000)
    @Column(name = "postcode", length = 1000, nullable = false)
    private String postcode;

    @Column(name = "registration_datetime")
    private ZonedDateTime registrationDatetime;

    @NotNull
    @Size(max = 1000)
    @Column(name = "surname", length = 1000, nullable = false)
    private String surname;

    @NotNull
    @Size(max = 1000)
    @Column(name = "given_name", length = 1000, nullable = false)
    private String givenName;

    @NotNull
    @Size(max = 3000)
    @Column(name = "address", length = 3000, nullable = false)
    private String address;

    @Lob
    @Column(name = "profile_photo")
    private byte[] profilePhoto;

    @Column(name = "profile_photo_content_type")
    private String profilePhotoContentType;

    @Lob
    @Column(name = "academic_certificate")
    private byte[] academicCertificate;

    @Column(name = "academic_certificate_content_type")
    private String academicCertificateContentType;

    @Lob
    @Column(name = "letter_of_recommendation")
    private byte[] letterOfRecommendation;

    @Column(name = "letter_of_recommendation_content_type")
    private String letterOfRecommendationContentType;

    @Lob
    @Column(name = "profile_document")
    private byte[] profileDocument;

    @Column(name = "profile_document_content_type")
    private String profileDocumentContentType;

    @Column(name = "md_5_key")
    private String md5key;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Course course;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public OnlineApplication dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getTelephone() {
        return telephone;
    }

    public OnlineApplication telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public OnlineApplication email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public OnlineApplication city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public OnlineApplication state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public OnlineApplication country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostcode() {
        return postcode;
    }

    public OnlineApplication postcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public ZonedDateTime getRegistrationDatetime() {
        return registrationDatetime;
    }

    public OnlineApplication registrationDatetime(ZonedDateTime registrationDatetime) {
        this.registrationDatetime = registrationDatetime;
        return this;
    }

    public void setRegistrationDatetime(ZonedDateTime registrationDatetime) {
        this.registrationDatetime = registrationDatetime;
    }

    public String getSurname() {
        return surname;
    }

    public OnlineApplication surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGivenName() {
        return givenName;
    }

    public OnlineApplication givenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getAddress() {
        return address;
    }

    public OnlineApplication address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public OnlineApplication profilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
        return this;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getProfilePhotoContentType() {
        return profilePhotoContentType;
    }

    public OnlineApplication profilePhotoContentType(String profilePhotoContentType) {
        this.profilePhotoContentType = profilePhotoContentType;
        return this;
    }

    public void setProfilePhotoContentType(String profilePhotoContentType) {
        this.profilePhotoContentType = profilePhotoContentType;
    }

    public byte[] getAcademicCertificate() {
        return academicCertificate;
    }

    public OnlineApplication academicCertificate(byte[] academicCertificate) {
        this.academicCertificate = academicCertificate;
        return this;
    }

    public void setAcademicCertificate(byte[] academicCertificate) {
        this.academicCertificate = academicCertificate;
    }

    public String getAcademicCertificateContentType() {
        return academicCertificateContentType;
    }

    public OnlineApplication academicCertificateContentType(String academicCertificateContentType) {
        this.academicCertificateContentType = academicCertificateContentType;
        return this;
    }

    public void setAcademicCertificateContentType(String academicCertificateContentType) {
        this.academicCertificateContentType = academicCertificateContentType;
    }

    public byte[] getLetterOfRecommendation() {
        return letterOfRecommendation;
    }

    public OnlineApplication letterOfRecommendation(byte[] letterOfRecommendation) {
        this.letterOfRecommendation = letterOfRecommendation;
        return this;
    }

    public void setLetterOfRecommendation(byte[] letterOfRecommendation) {
        this.letterOfRecommendation = letterOfRecommendation;
    }

    public String getLetterOfRecommendationContentType() {
        return letterOfRecommendationContentType;
    }

    public OnlineApplication letterOfRecommendationContentType(String letterOfRecommendationContentType) {
        this.letterOfRecommendationContentType = letterOfRecommendationContentType;
        return this;
    }

    public void setLetterOfRecommendationContentType(String letterOfRecommendationContentType) {
        this.letterOfRecommendationContentType = letterOfRecommendationContentType;
    }

    public byte[] getProfileDocument() {
        return profileDocument;
    }

    public OnlineApplication profileDocument(byte[] profileDocument) {
        this.profileDocument = profileDocument;
        return this;
    }

    public void setProfileDocument(byte[] profileDocument) {
        this.profileDocument = profileDocument;
    }

    public String getProfileDocumentContentType() {
        return profileDocumentContentType;
    }

    public OnlineApplication profileDocumentContentType(String profileDocumentContentType) {
        this.profileDocumentContentType = profileDocumentContentType;
        return this;
    }

    public void setProfileDocumentContentType(String profileDocumentContentType) {
        this.profileDocumentContentType = profileDocumentContentType;
    }

    public String getMd5key() {
        return md5key;
    }

    public OnlineApplication md5key(String md5key) {
        this.md5key = md5key;
        return this;
    }

    public void setMd5key(String md5key) {
        this.md5key = md5key;
    }

    public Course getCourse() {
        return course;
    }

    public OnlineApplication course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
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
        OnlineApplication onlineApplication = (OnlineApplication) o;
        if (onlineApplication.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), onlineApplication.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OnlineApplication{" +
            "id=" + getId() +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", country='" + getCountry() + "'" +
            ", postcode='" + getPostcode() + "'" +
            ", registrationDatetime='" + getRegistrationDatetime() + "'" +
            ", surname='" + getSurname() + "'" +
            ", givenName='" + getGivenName() + "'" +
            ", address='" + getAddress() + "'" +
            ", profilePhoto='" + getProfilePhoto() + "'" +
            ", profilePhotoContentType='" + getProfilePhotoContentType() + "'" +
            ", academicCertificate='" + getAcademicCertificate() + "'" +
            ", academicCertificateContentType='" + getAcademicCertificateContentType() + "'" +
            ", letterOfRecommendation='" + getLetterOfRecommendation() + "'" +
            ", letterOfRecommendationContentType='" + getLetterOfRecommendationContentType() + "'" +
            ", profileDocument='" + getProfileDocument() + "'" +
            ", profileDocumentContentType='" + getProfileDocumentContentType() + "'" +
            ", md5key='" + getMd5key() + "'" +
            "}";
    }
}
