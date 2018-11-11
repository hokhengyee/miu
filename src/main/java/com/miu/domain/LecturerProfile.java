package com.miu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LecturerProfile.
 */
@Entity
@Table(name = "lecturer_profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LecturerProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "other_titles")
    private String otherTitles;

    @Column(name = "age")
    private Long age;

    @Column(name = "ordination")
    private String ordination;

    @Lob
    @Column(name = "academic_history")
    private String academicHistory;

    @Lob
    @Column(name = "professional_history")
    private String professionalHistory;

    @Lob
    @Column(name = "past_and_current_ministry")
    private String pastAndCurrentMinistry;

    @Lob
    @Column(name = "publications")
    private String publications;

    @Lob
    @Column(name = "family_details")
    private String familyDetails;

    @Lob
    @Column(name = "reference")
    private String reference;

    @Lob
    @Column(name = "profile_photo")
    private byte[] profilePhoto;

    @Column(name = "profile_photo_content_type")
    private String profilePhotoContentType;

    @ManyToOne
    @NotNull
    private User user;

    @ManyToOne
    private Salutation salutation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOtherTitles() {
        return otherTitles;
    }

    public LecturerProfile otherTitles(String otherTitles) {
        this.otherTitles = otherTitles;
        return this;
    }

    public void setOtherTitles(String otherTitles) {
        this.otherTitles = otherTitles;
    }

    public Long getAge() {
        return age;
    }

    public LecturerProfile age(Long age) {
        this.age = age;
        return this;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getOrdination() {
        return ordination;
    }

    public LecturerProfile ordination(String ordination) {
        this.ordination = ordination;
        return this;
    }

    public void setOrdination(String ordination) {
        this.ordination = ordination;
    }

    public String getAcademicHistory() {
        return academicHistory;
    }

    public LecturerProfile academicHistory(String academicHistory) {
        this.academicHistory = academicHistory;
        return this;
    }

    public void setAcademicHistory(String academicHistory) {
        this.academicHistory = academicHistory;
    }

    public String getProfessionalHistory() {
        return professionalHistory;
    }

    public LecturerProfile professionalHistory(String professionalHistory) {
        this.professionalHistory = professionalHistory;
        return this;
    }

    public void setProfessionalHistory(String professionalHistory) {
        this.professionalHistory = professionalHistory;
    }

    public String getPastAndCurrentMinistry() {
        return pastAndCurrentMinistry;
    }

    public LecturerProfile pastAndCurrentMinistry(String pastAndCurrentMinistry) {
        this.pastAndCurrentMinistry = pastAndCurrentMinistry;
        return this;
    }

    public void setPastAndCurrentMinistry(String pastAndCurrentMinistry) {
        this.pastAndCurrentMinistry = pastAndCurrentMinistry;
    }

    public String getPublications() {
        return publications;
    }

    public LecturerProfile publications(String publications) {
        this.publications = publications;
        return this;
    }

    public void setPublications(String publications) {
        this.publications = publications;
    }

    public String getFamilyDetails() {
        return familyDetails;
    }

    public LecturerProfile familyDetails(String familyDetails) {
        this.familyDetails = familyDetails;
        return this;
    }

    public void setFamilyDetails(String familyDetails) {
        this.familyDetails = familyDetails;
    }

    public String getReference() {
        return reference;
    }

    public LecturerProfile reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public LecturerProfile profilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
        return this;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getProfilePhotoContentType() {
        return profilePhotoContentType;
    }

    public LecturerProfile profilePhotoContentType(String profilePhotoContentType) {
        this.profilePhotoContentType = profilePhotoContentType;
        return this;
    }

    public void setProfilePhotoContentType(String profilePhotoContentType) {
        this.profilePhotoContentType = profilePhotoContentType;
    }

    public User getUser() {
        return user;
    }

    public LecturerProfile user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Salutation getSalutation() {
        return salutation;
    }

    public LecturerProfile salutation(Salutation salutation) {
        this.salutation = salutation;
        return this;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LecturerProfile lecturerProfile = (LecturerProfile) o;
        if (lecturerProfile.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lecturerProfile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LecturerProfile{" +
            "id=" + id +
            ", otherTitles='" + otherTitles + "'" +
            ", age='" + age + "'" +
            ", ordination='" + ordination + "'" +
            ", academicHistory='" + academicHistory + "'" +
            ", professionalHistory='" + professionalHistory + "'" +
            ", pastAndCurrentMinistry='" + pastAndCurrentMinistry + "'" +
            ", publications='" + publications + "'" +
            ", familyDetails='" + familyDetails + "'" +
            ", reference='" + reference + "'" +
            ", profilePhoto='" + profilePhoto + "'" +
            ", profilePhotoContentType='" + profilePhotoContentType + "'" +
            '}';
    }
}
