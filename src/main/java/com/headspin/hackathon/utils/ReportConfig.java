package com.headspin.hackathon.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportConfig {

	private boolean reportEnabled;
	private String host;
	private Integer port;
	private String projectName;
	private String reportName;
	private String serverURL;
}
