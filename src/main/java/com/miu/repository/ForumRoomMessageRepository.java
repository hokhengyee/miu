package com.miu.repository;

import com.miu.domain.ForumRoomMessage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ForumRoomMessage entity.
 */
@SuppressWarnings("unused")
public interface ForumRoomMessageRepository extends JpaRepository<ForumRoomMessage,Long> {

    @Query("select forumRoomMessage from ForumRoomMessage forumRoomMessage where forumRoomMessage.user.login = ?#{principal.username}")
    List<ForumRoomMessage> findByUserIsCurrentUser();

}
