package com.miu.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.domain.PersistentAuditEvent;

/**
 * Spring Data JPA repository for the PersistentAuditEvent entity.
 */
public interface PersistenceAuditEventRepository extends JpaRepository<PersistentAuditEvent, Long> {

	Page<PersistentAuditEvent> findAllByAuditEventDateBetween(LocalDateTime fromDate, LocalDateTime toDate,
			Pageable pageable);

	List<PersistentAuditEvent> findByAuditEventDateAfter(LocalDateTime after);

	List<PersistentAuditEvent> findByPrincipal(String principal);

	List<PersistentAuditEvent> findByPrincipalAndAuditEventDateAfter(String principal, LocalDateTime after);

	List<PersistentAuditEvent> findByPrincipalAndAuditEventDateAfterAndAuditEventType(String principle,
			LocalDateTime after, String type);
}
