package com.miu.web.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

/**
 * Utility class for HTTP headers creation.
 */
public final class HeaderUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(HeaderUtil.class);

	public static HttpHeaders createAlert(String message, String param) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-miuApp-alert", message);
		headers.add("X-miuApp-params", param);
		return headers;
	}

	public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
		return createAlert("A new " + entityName + " is created with identifier " + param, param);
	}

	public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
		return createAlert("A " + entityName + " is deleted with identifier " + param, param);
	}

	public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
		return createAlert("A " + entityName + " is updated with identifier " + param, param);
	}

	public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
		LOGGER.error("Entity creation failed, {}", defaultMessage);
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-miuApp-error", defaultMessage);
		headers.add("X-miuApp-params", entityName);
		return headers;
	}

	private HeaderUtil() {
	}
}
