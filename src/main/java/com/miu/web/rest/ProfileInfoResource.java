package com.miu.web.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miu.config.DefaultProfileUtil;
import com.miu.config.JHipsterProperties;

/**
 * Resource to return information about the currently running Spring profiles.
 */
@RestController
@RequestMapping("/api")
public class ProfileInfoResource {

	class ProfileInfoResponse {

		public String[] activeProfiles;
		public String ribbonEnv;

		ProfileInfoResponse(String[] activeProfiles, String ribbonEnv) {
			this.activeProfiles = activeProfiles;
			this.ribbonEnv = ribbonEnv;
		}
	}

	@Inject
	private Environment env;

	@Inject
	private JHipsterProperties jHipsterProperties;

	@GetMapping("/profile-info")
	public ProfileInfoResponse getActiveProfiles() {
		String[] activeProfiles = DefaultProfileUtil.getActiveProfiles(env);
		return new ProfileInfoResponse(activeProfiles, getRibbonEnv(activeProfiles));
	}

	private String getRibbonEnv(String[] activeProfiles) {
		String[] displayOnActiveProfiles = jHipsterProperties.getRibbon().getDisplayOnActiveProfiles();

		if (displayOnActiveProfiles == null) {
			return null;
		}

		List<String> ribbonProfiles = new ArrayList<>(Arrays.asList(displayOnActiveProfiles));
		List<String> springBootProfiles = Arrays.asList(activeProfiles);
		ribbonProfiles.retainAll(springBootProfiles);

		if (ribbonProfiles.size() > 0) {
			return ribbonProfiles.get(0);
		}
		
		return null;
	}
}
