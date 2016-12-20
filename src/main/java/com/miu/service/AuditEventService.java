package com.miu.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miu.config.audit.AuditEventConverter;
import com.miu.repository.PersistenceAuditEventRepository;

/**
 * Service for managing audit events.
 * <p>
 * This is the default implementation to support SpringBoot Actuator
 * AuditEventRepository
 * </p>
 */
@Service
@Transactional
public class AuditEventService {

	private AuditEventConverter auditEventConverter;

	private PersistenceAuditEventRepository persistenceAuditEventRepository;

	@Inject
	public AuditEventService(PersistenceAuditEventRepository persistenceAuditEventRepository,
			AuditEventConverter auditEventConverter) {

		this.persistenceAuditEventRepository = persistenceAuditEventRepository;
		this.auditEventConverter = auditEventConverter;
	}

	public Optional<AuditEvent> find(Long id) {
		return Optional.ofNullable(persistenceAuditEventRepository.findOne(id))
				.map(auditEventConverter::convertToAuditEvent);
	}

	public Page<AuditEvent> findAll(Pageable pageable) {
		return persistenceAuditEventRepository.findAll(pageable).map(auditEventConverter::convertToAuditEvent);
	}

	public Page<AuditEvent> findByDates(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable) {
		return persistenceAuditEventRepository.findAllByAuditEventDateBetween(fromDate, toDate, pageable)
				.map(auditEventConverter::convertToAuditEvent);
	}
}
