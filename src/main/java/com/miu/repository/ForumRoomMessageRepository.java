package com.miu.repository;

import com.miu.domain.ForumRoom;
import com.miu.domain.ForumRoomMessage;
import com.miu.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the ForumRoomMessage entity.
 */
public interface ForumRoomMessageRepository extends JpaRepository<ForumRoomMessage, Long> {

    @Query("select forumRoomMessage from ForumRoomMessage forumRoomMessage where forumRoomMessage.user = ?1")
    List<ForumRoomMessage> findByUser(User user);

    @Query("select forumRoomMessage from ForumRoomMessage forumRoomMessage where forumRoomMessage.user.login = ?#{principal.username}")
    List<ForumRoomMessage> findByUserIsCurrentUser();

    @Query("SELECT m FROM ForumRoomMessage m WHERE m.forumRoom= ?1 ORDER BY m.messageDatetime DESC")
    Page<ForumRoomMessage> findForumRoomMessagesByForumRoom(ForumRoom forumRoom, Pageable pageable);

}
