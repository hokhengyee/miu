package com.miu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ForumRoomMessage.
 */
@Entity
@Table(name = "forum_room_message")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ForumRoomMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "message_datetime")
    private ZonedDateTime messageDatetime;

    @ManyToOne
    @JsonIgnoreProperties("")
    private ForumRoom forumRoom;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public ForumRoomMessage message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ZonedDateTime getMessageDatetime() {
        return messageDatetime;
    }

    public ForumRoomMessage messageDatetime(ZonedDateTime messageDatetime) {
        this.messageDatetime = messageDatetime;
        return this;
    }

    public void setMessageDatetime(ZonedDateTime messageDatetime) {
        this.messageDatetime = messageDatetime;
    }

    public ForumRoom getForumRoom() {
        return forumRoom;
    }

    public ForumRoomMessage forumRoom(ForumRoom forumRoom) {
        this.forumRoom = forumRoom;
        return this;
    }

    public void setForumRoom(ForumRoom forumRoom) {
        this.forumRoom = forumRoom;
    }

    public User getUser() {
        return user;
    }

    public ForumRoomMessage user(User user) {
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
        ForumRoomMessage forumRoomMessage = (ForumRoomMessage) o;
        if (forumRoomMessage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), forumRoomMessage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ForumRoomMessage{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", messageDatetime='" + getMessageDatetime() + "'" +
            "}";
    }
}
