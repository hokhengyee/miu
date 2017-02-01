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
 * A StudentPayment.
 */
@Entity
@Table(name = "student_payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StudentPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @NotNull
    @Size(max = 1000)
    @Column(name = "amount", length = 1000, nullable = false)
    private String amount;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "paid")
    private Boolean paid;

    @ManyToOne
    @NotNull
    private User user;

    @ManyToOne
    @NotNull
    private Course course;

    @ManyToOne
    @NotNull
    private PaymentType paymentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public StudentPayment createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getAmount() {
        return amount;
    }

    public StudentPayment amount(String amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public StudentPayment description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public StudentPayment paymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Boolean isPaid() {
        return paid;
    }

    public StudentPayment paid(Boolean paid) {
        this.paid = paid;
        return this;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public User getUser() {
        return user;
    }

    public StudentPayment user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public StudentPayment course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public StudentPayment paymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StudentPayment studentPayment = (StudentPayment) o;
        if (studentPayment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, studentPayment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StudentPayment{" +
            "id=" + id +
            ", createdDate='" + createdDate + "'" +
            ", amount='" + amount + "'" +
            ", description='" + description + "'" +
            ", paymentDate='" + paymentDate + "'" +
            ", paid='" + paid + "'" +
            '}';
    }
}
