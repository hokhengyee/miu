package com.miu.repository;

import com.miu.domain.ForumRoom;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ForumRoom entity.
 */
@SuppressWarnings("unused")
public interface ForumRoomRepository extends JpaRepository<ForumRoom,Long> {

}
