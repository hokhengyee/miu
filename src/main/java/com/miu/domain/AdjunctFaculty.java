package com.miu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AdjunctFaculty.
 */
@Entity
@Table(name = "adjunct_faculty")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdjunctFaculty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "show_order")
    private Long showOrder;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private LecturerProfile lecturerProfile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShowOrder() {
        return showOrder;
    }

    public AdjunctFaculty showOrder(Long showOrder) {
        this.showOrder = showOrder;
        return this;
    }

    public void setShowOrder(Long showOrder) {
        this.showOrder = showOrder;
    }

    public LecturerProfile getLecturerProfile() {
        return lecturerProfile;
    }

    public AdjunctFaculty lecturerProfile(LecturerProfile lecturerProfile) {
        this.lecturerProfile = lecturerProfile;
        return this;
    }

    public void setLecturerProfile(LecturerProfile lecturerProfile) {
        this.lecturerProfile = lecturerProfile;
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
        AdjunctFaculty adjunctFaculty = (AdjunctFaculty) o;
        if (adjunctFaculty.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adjunctFaculty.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdjunctFaculty{" +
            "id=" + getId() +
            ", showOrder=" + getShowOrder() +
            "}";
    }
}
