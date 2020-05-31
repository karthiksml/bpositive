package com.headspin.hackathon.core.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentKlovReporter;

public class ExtentManager {

	private static ExtentReports extent;

	public synchronized static ExtentReports getReporter() {
		String workingDir = System.getProperty("user.dir");
		if (extent == null) {
			extent = new ExtentReports();
			extent.setSystemInfo("OS", System.getProperty("os.name"));
			ExtentHtmlReporter htmlReporter = null;

			if (System.getProperty("os.name").toLowerCase().contains("win")) {
				htmlReporter = new ExtentHtmlReporter(workingDir + "\\ExtentReports\\ExtentReportResults.html");
			} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
				htmlReporter = new ExtentHtmlReporter(workingDir + "/ExtentReports/ExtentReportResults.html");
			}

			ExtentKlovReporter klovReporter = new ExtentKlovReporter("HeadSpin", "Automation");
			klovReporter.initMongoDbConnection("localhost", 27017);
			klovReporter.initKlovServerConnection("http://localhost:8082");
			extent.attachReporter(htmlReporter, klovReporter);
		}
		return extent;
	}
}
