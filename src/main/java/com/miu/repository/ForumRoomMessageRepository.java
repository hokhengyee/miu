package com.miu.repository;

import com.miu.domain.ForumRoomMessage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ForumRoomMessage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ForumRoomMessageRepository extends JpaRepository<ForumRoomMessage, Long> {

    @Query("select forum_room_message from ForumRoomMessage forum_room_message where forum_room_message.user.login = ?#{principal.username}")
    List<ForumRoomMessage> findByUserIsCurrentUser();

}
