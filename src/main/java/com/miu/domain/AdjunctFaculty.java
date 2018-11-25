package com.miu.domain;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "show_order")
    private Long showOrder;

    @ManyToOne
    @NotNull
    private LecturerProfile lecturerProfile;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AdjunctFaculty adjunctFaculty = (AdjunctFaculty) o;
        if (adjunctFaculty.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, adjunctFaculty.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AdjunctFaculty{" +
            "id=" + id +
            ", showOrder='" + showOrder + "'" +
            '}';
    }
}
