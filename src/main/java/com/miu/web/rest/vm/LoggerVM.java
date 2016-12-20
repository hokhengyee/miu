package com.miu.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonCreator;

import ch.qos.logback.classic.Logger;

/**
 * View Model object for storing a Logback logger.
 */
public class LoggerVM {

	private String level;

	private String name;

	@JsonCreator
	public LoggerVM() {
	}

	public LoggerVM(Logger logger) {
		this.name = logger.getName();
		this.level = logger.getEffectiveLevel().toString();
	}

	public String getLevel() {
		return level;
	}

	public String getName() {
		return name;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "LoggerVM{" + "name='" + name + '\'' + ", level='" + level + '\'' + '}';
	}
}
