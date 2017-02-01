package com.miu.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ForumRoom.
 */
@Entity
@Table(name = "forum_room")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ForumRoom implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "forum_room_name", nullable = false)
	private String forumRoomName;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

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

	public ForumRoom forumRoomName(String forumRoomName) {
		this.forumRoomName = forumRoomName;
		return this;
	}

	public String getForumRoomName() {
		return forumRoomName;
	}

	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	public void setForumRoomName(String forumRoomName) {
		this.forumRoomName = forumRoomName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ForumRoom{" + "id=" + id + ", forumRoomName='" + forumRoomName + "'" + '}';
	}
}
