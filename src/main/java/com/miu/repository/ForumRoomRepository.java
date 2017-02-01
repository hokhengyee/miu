package com.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.domain.ForumRoom;

/**
 * Spring Data JPA repository for the ForumRoom entity.
 */
public interface ForumRoomRepository extends JpaRepository<ForumRoom, Long> {

}
