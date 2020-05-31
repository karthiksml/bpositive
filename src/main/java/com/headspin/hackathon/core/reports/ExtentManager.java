package com.headspin.hackathon.core.reports;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentKlovReporter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.headspin.hackathon.utils.ReportConfig;

public class ExtentManager {

	private static ExtentReports extent;

	public synchronized static ExtentReports getReporter() {
		String workingDir = System.getProperty("user.dir");
		if (extent == null) {
			extent = new ExtentReports();
			extent.setSystemInfo("OS", System.getProperty("os.name"));
			ExtentHtmlReporter htmlReporter = null;
			ReportConfig reportConfig = readConfig();

			if (System.getProperty("os.name").toLowerCase().contains("win")) {
				htmlReporter = new ExtentHtmlReporter(workingDir + "\\ExtentReports\\" + getBuildInfo());
			} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
				htmlReporter = new ExtentHtmlReporter(workingDir + "/ExtentReports/" + getBuildInfo());
			}

			if (reportConfig.isReportEnabled()) {
				ExtentKlovReporter klovReporter = new ExtentKlovReporter(reportConfig.getProjectName(),
						reportConfig.getReportName());
				klovReporter.initMongoDbConnection(reportConfig.getHost(), reportConfig.getPort());
				klovReporter.initKlovServerConnection(reportConfig.getServerURL());
				extent.attachReporter(htmlReporter, klovReporter);
			} else
				extent.attachReporter(htmlReporter);
		}
		return extent;
	}

	private static ReportConfig readConfig() {
		ReportConfig reportConfig = null;
		try {
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			InputStream envFile = ReportConfig.class.getResourceAsStream("/configuration/report.yaml");
			reportConfig = mapper.readValue(envFile, ReportConfig.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reportConfig;
	}

	private static String getBuildInfo() {
		String buildNo = System.getProperty("buildNo");
		if (buildNo == null || buildNo.isEmpty()) {
			buildNo = String.valueOf(Instant.now().getEpochSecond());
		}
		return "Build_" + buildNo + "_report.html";
	}

}
