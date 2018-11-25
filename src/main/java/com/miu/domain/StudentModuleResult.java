package com.miu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A StudentModuleResult.
 */
@Entity
@Table(name = "student_module_result")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StudentModuleResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "result")
    private String result;

    @Column(name = "date_graded")
    private LocalDate dateGraded;

    @Column(name = "result_order")
    private Long resultOrder;

    @ManyToOne
    @NotNull
    private User user;

    @ManyToOne
    @NotNull
    private Module module;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public StudentModuleResult result(String result) {
        this.result = result;
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDate getDateGraded() {
        return dateGraded;
    }

    public StudentModuleResult dateGraded(LocalDate dateGraded) {
        this.dateGraded = dateGraded;
        return this;
    }

    public void setDateGraded(LocalDate dateGraded) {
        this.dateGraded = dateGraded;
    }

    public Long getResultOrder() {
        return resultOrder;
    }

    public StudentModuleResult resultOrder(Long resultOrder) {
        this.resultOrder = resultOrder;
        return this;
    }

    public void setResultOrder(Long resultOrder) {
        this.resultOrder = resultOrder;
    }

    public User getUser() {
        return user;
    }

    public StudentModuleResult user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Module getModule() {
        return module;
    }

    public StudentModuleResult module(Module module) {
        this.module = module;
        return this;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StudentModuleResult studentModuleResult = (StudentModuleResult) o;
        if (studentModuleResult.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, studentModuleResult.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StudentModuleResult{" +
            "id=" + id +
            ", result='" + result + "'" +
            ", dateGraded='" + dateGraded + "'" +
            ", resultOrder='" + resultOrder + "'" +
            '}';
    }
}
