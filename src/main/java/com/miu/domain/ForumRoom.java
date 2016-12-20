package com.miu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ForumRoom.
 */
@Entity
@Table(name = "forum_room")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ForumRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "forum_room_name", nullable = false)
    private String forumRoomName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getForumRoomName() {
        return forumRoomName;
    }

    public ForumRoom forumRoomName(String forumRoomName) {
        this.forumRoomName = forumRoomName;
        return this;
    }

    public void setForumRoomName(String forumRoomName) {
        this.forumRoomName = forumRoomName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ForumRoom forumRoom = (ForumRoom) o;
        if (forumRoom.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, forumRoom.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ForumRoom{" +
            "id=" + id +
            ", forumRoomName='" + forumRoomName + "'" +
            '}';
    }
}
