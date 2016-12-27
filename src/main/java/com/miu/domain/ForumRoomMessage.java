package com.miu.domain;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "message", nullable = false)
    private String message;

    @NotNull
    @Column(name = "message_datetime", nullable = false)
    private ZonedDateTime messageDatetime;

    @ManyToOne
    @NotNull
    private ForumRoom forumRoom;

    @ManyToOne
    @NotNull
    private User user;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ForumRoomMessage forumRoomMessage = (ForumRoomMessage) o;
        if (forumRoomMessage.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, forumRoomMessage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ForumRoomMessage{" +
            "id=" + id +
            ", message='" + message + "'" +
            ", messageDatetime='" + messageDatetime + "'" +
            '}';
    }
}
