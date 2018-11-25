package com.miu.repository;

import com.miu.domain.ForumRoom;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ForumRoom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ForumRoomRepository extends JpaRepository<ForumRoom, Long> {

}
