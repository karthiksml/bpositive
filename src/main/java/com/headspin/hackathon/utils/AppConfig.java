package com.headspin.hackathon.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppConfig {

	private String browserVersion;
	private String browserName;
	private boolean capture;
	private String gridURL;
	private String appURL;
	private boolean recordEvents;
}
