package com.miu.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.domain.PersistentToken;
import com.miu.domain.User;

/**
 * Spring Data JPA repository for the PersistentToken entity.
 */
public interface PersistentTokenRepository extends JpaRepository<PersistentToken, String> {

	List<PersistentToken> findByTokenDateBefore(LocalDate localDate);

	List<PersistentToken> findByUser(User user);

}
