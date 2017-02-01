package com.miu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.ForumRoomMessage;

/**
 * Spring Data JPA repository for the ForumRoomMessage entity.
 */
public interface ForumRoomMessageRepository extends JpaRepository<ForumRoomMessage, Long> {

	@Query("select forumRoomMessage from ForumRoomMessage forumRoomMessage where forumRoomMessage.user.login = ?#{principal.username}")
	List<ForumRoomMessage> findByUserIsCurrentUser();

}
