package com.miu.repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.domain.User;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findAllByActivatedIsFalseAndCreatedDateBefore(ZonedDateTime dateTime);

	@Query(value = "select distinct user from User user left join fetch user.authorities", countQuery = "select count(user) from User user")
	List<User> findAllWithAuthorities();

	@Query(value = "select distinct user from User user left join fetch user.authorities", countQuery = "select count(user) from User user")
	Page<User> findAllWithAuthorities(Pageable pageable);

	@Query("select user from User user where user.login = ?#{principal.username}")
	User findByUserIsCurrentUser();

	Optional<User> findOneByActivationKey(String activationKey);

	Optional<User> findOneByEmail(String email);

	Optional<User> findOneByLogin(String login);

	Optional<User> findOneByResetKey(String resetKey);
}
