package com.miu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MinisterialWorkExperience.
 */
@Entity
@Table(name = "ministerial_work_experience")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MinisterialWorkExperience implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 1000)
    @Column(name = "name_of_ministry_2", length = 1000)
    private String nameOfMinistry2;

    @Size(max = 1000)
    @Column(name = "area_of_ministry_2", length = 1000)
    private String areaOfMinistry2;

    @Size(max = 1000)
    @Column(name = "name_of_ministry_3", length = 1000)
    private String nameOfMinistry3;

    @Size(max = 1000)
    @Column(name = "area_of_ministry_3", length = 1000)
    private String areaOfMinistry3;

    @Size(max = 1000)
    @Column(name = "name_of_ministry_4", length = 1000)
    private String nameOfMinistry4;

    @Size(max = 1000)
    @Column(name = "area_of_ministry_4", length = 1000)
    private String areaOfMinistry4;

    @Column(name = "md_5_key")
    private String md5Key;

    @Min(value = 1L)
    @Max(value = 100L)
    @Column(name = "years_2")
    private Long years2;

    @Min(value = 1L)
    @Max(value = 100L)
    @Column(name = "years_3")
    private Long years3;

    @Min(value = 1L)
    @Max(value = 100L)
    @Column(name = "years_4")
    private Long years4;

    @Size(max = 1000)
    @Column(name = "name_of_ministry_1", length = 1000)
    private String nameOfMinistry1;

    @Size(max = 1000)
    @Column(name = "area_of_ministry_1", length = 1000)
    private String areaOfMinistry1;

    @Min(value = 1L)
    @Max(value = 100L)
    @Column(name = "years_1")
    private Long years1;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOfMinistry2() {
        return nameOfMinistry2;
    }

    public MinisterialWorkExperience nameOfMinistry2(String nameOfMinistry2) {
        this.nameOfMinistry2 = nameOfMinistry2;
        return this;
    }

    public void setNameOfMinistry2(String nameOfMinistry2) {
        this.nameOfMinistry2 = nameOfMinistry2;
    }

    public String getAreaOfMinistry2() {
        return areaOfMinistry2;
    }

    public MinisterialWorkExperience areaOfMinistry2(String areaOfMinistry2) {
        this.areaOfMinistry2 = areaOfMinistry2;
        return this;
    }

    public void setAreaOfMinistry2(String areaOfMinistry2) {
        this.areaOfMinistry2 = areaOfMinistry2;
    }

    public String getNameOfMinistry3() {
        return nameOfMinistry3;
    }

    public MinisterialWorkExperience nameOfMinistry3(String nameOfMinistry3) {
        this.nameOfMinistry3 = nameOfMinistry3;
        return this;
    }

    public void setNameOfMinistry3(String nameOfMinistry3) {
        this.nameOfMinistry3 = nameOfMinistry3;
    }

    public String getAreaOfMinistry3() {
        return areaOfMinistry3;
    }

    public MinisterialWorkExperience areaOfMinistry3(String areaOfMinistry3) {
        this.areaOfMinistry3 = areaOfMinistry3;
        return this;
    }

    public void setAreaOfMinistry3(String areaOfMinistry3) {
        this.areaOfMinistry3 = areaOfMinistry3;
    }

    public String getNameOfMinistry4() {
        return nameOfMinistry4;
    }

    public MinisterialWorkExperience nameOfMinistry4(String nameOfMinistry4) {
        this.nameOfMinistry4 = nameOfMinistry4;
        return this;
    }

    public void setNameOfMinistry4(String nameOfMinistry4) {
        this.nameOfMinistry4 = nameOfMinistry4;
    }

    public String getAreaOfMinistry4() {
        return areaOfMinistry4;
    }

    public MinisterialWorkExperience areaOfMinistry4(String areaOfMinistry4) {
        this.areaOfMinistry4 = areaOfMinistry4;
        return this;
    }

    public void setAreaOfMinistry4(String areaOfMinistry4) {
        this.areaOfMinistry4 = areaOfMinistry4;
    }

    public String getMd5Key() {
        return md5Key;
    }

    public MinisterialWorkExperience md5Key(String md5Key) {
        this.md5Key = md5Key;
        return this;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

    public Long getYears2() {
        return years2;
    }

    public MinisterialWorkExperience years2(Long years2) {
        this.years2 = years2;
        return this;
    }

    public void setYears2(Long years2) {
        this.years2 = years2;
    }

    public Long getYears3() {
        return years3;
    }

    public MinisterialWorkExperience years3(Long years3) {
        this.years3 = years3;
        return this;
    }

    public void setYears3(Long years3) {
        this.years3 = years3;
    }

    public Long getYears4() {
        return years4;
    }

    public MinisterialWorkExperience years4(Long years4) {
        this.years4 = years4;
        return this;
    }

    public void setYears4(Long years4) {
        this.years4 = years4;
    }

    public String getNameOfMinistry1() {
        return nameOfMinistry1;
    }

    public MinisterialWorkExperience nameOfMinistry1(String nameOfMinistry1) {
        this.nameOfMinistry1 = nameOfMinistry1;
        return this;
    }

    public void setNameOfMinistry1(String nameOfMinistry1) {
        this.nameOfMinistry1 = nameOfMinistry1;
    }

    public String getAreaOfMinistry1() {
        return areaOfMinistry1;
    }

    public MinisterialWorkExperience areaOfMinistry1(String areaOfMinistry1) {
        this.areaOfMinistry1 = areaOfMinistry1;
        return this;
    }

    public void setAreaOfMinistry1(String areaOfMinistry1) {
        this.areaOfMinistry1 = areaOfMinistry1;
    }

    public Long getYears1() {
        return years1;
    }

    public MinisterialWorkExperience years1(Long years1) {
        this.years1 = years1;
        return this;
    }

    public void setYears1(Long years1) {
        this.years1 = years1;
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
        MinisterialWorkExperience ministerialWorkExperience = (MinisterialWorkExperience) o;
        if (ministerialWorkExperience.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ministerialWorkExperience.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MinisterialWorkExperience{" +
            "id=" + getId() +
            ", nameOfMinistry2='" + getNameOfMinistry2() + "'" +
            ", areaOfMinistry2='" + getAreaOfMinistry2() + "'" +
            ", nameOfMinistry3='" + getNameOfMinistry3() + "'" +
            ", areaOfMinistry3='" + getAreaOfMinistry3() + "'" +
            ", nameOfMinistry4='" + getNameOfMinistry4() + "'" +
            ", areaOfMinistry4='" + getAreaOfMinistry4() + "'" +
            ", md5Key='" + getMd5Key() + "'" +
            ", years2=" + getYears2() +
            ", years3=" + getYears3() +
            ", years4=" + getYears4() +
            ", nameOfMinistry1='" + getNameOfMinistry1() + "'" +
            ", areaOfMinistry1='" + getAreaOfMinistry1() + "'" +
            ", years1=" + getYears1() +
            "}";
    }
}
