package com.miu.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ForumRoomMessage.
 */
@Entity
@Table(name = "forum_room_message")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ForumRoomMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@NotNull
	private ForumRoom forumRoom;

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
	private User user;

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

	public ForumRoomMessage forumRoom(ForumRoom forumRoom) {
		this.forumRoom = forumRoom;
		return this;
	}

	public ForumRoom getForumRoom() {
		return forumRoom;
	}

	public Long getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public ZonedDateTime getMessageDatetime() {
		return messageDatetime;
	}

	public User getUser() {
		return user;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	public ForumRoomMessage message(String message) {
		this.message = message;
		return this;
	}

	public ForumRoomMessage messageDatetime(ZonedDateTime messageDatetime) {
		this.messageDatetime = messageDatetime;
		return this;
	}

	public void setForumRoom(ForumRoom forumRoom) {
		this.forumRoom = forumRoom;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setMessageDatetime(ZonedDateTime messageDatetime) {
		this.messageDatetime = messageDatetime;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "ForumRoomMessage{" + "id=" + id + ", message='" + message + "'" + ", messageDatetime='"
				+ messageDatetime + "'" + '}';
	}

	public ForumRoomMessage user(User user) {
		this.user = user;
		return this;
	}
}
