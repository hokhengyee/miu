package com.miu.domain;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @NotNull
    @Lob
    @Column(name = "application_form", nullable = false)
    private byte[] applicationForm;

    @Column(name = "application_form_content_type", nullable = false)
    private String applicationFormContentType;

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

    @ManyToOne
    @NotNull
    private Course course;

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

    public byte[] getApplicationForm() {
        return applicationForm;
    }

    public OnlineApplication applicationForm(byte[] applicationForm) {
        this.applicationForm = applicationForm;
        return this;
    }

    public void setApplicationForm(byte[] applicationForm) {
        this.applicationForm = applicationForm;
    }

    public String getApplicationFormContentType() {
        return applicationFormContentType;
    }

    public OnlineApplication applicationFormContentType(String applicationFormContentType) {
        this.applicationFormContentType = applicationFormContentType;
        return this;
    }

    public void setApplicationFormContentType(String applicationFormContentType) {
        this.applicationFormContentType = applicationFormContentType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OnlineApplication onlineApplication = (OnlineApplication) o;
        if (onlineApplication.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, onlineApplication.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OnlineApplication{" +
            "id=" + id +
            ", dateOfBirth='" + dateOfBirth + "'" +
            ", telephone='" + telephone + "'" +
            ", email='" + email + "'" +
            ", city='" + city + "'" +
            ", state='" + state + "'" +
            ", country='" + country + "'" +
            ", postcode='" + postcode + "'" +
            ", registrationDatetime='" + registrationDatetime + "'" +
            ", surname='" + surname + "'" +
            ", givenName='" + givenName + "'" +
            ", address='" + address + "'" +
            ", applicationForm='" + applicationForm + "'" +
            ", applicationFormContentType='" + applicationFormContentType + "'" +
            ", profilePhoto='" + profilePhoto + "'" +
            ", profilePhotoContentType='" + profilePhotoContentType + "'" +
            ", academicCertificate='" + academicCertificate + "'" +
            ", academicCertificateContentType='" + academicCertificateContentType + "'" +
            ", letterOfRecommendation='" + letterOfRecommendation + "'" +
            ", letterOfRecommendationContentType='" + letterOfRecommendationContentType + "'" +
            ", profileDocument='" + profileDocument + "'" +
            ", profileDocumentContentType='" + profileDocumentContentType + "'" +
            '}';
    }
}
