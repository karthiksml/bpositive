package com.headspin.hackathon.core.listeners;

import java.util.Map;

import org.testng.IConfigurationListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.headspin.hackathon.core.reports.ListenerThreads;

public class CustomReportListener implements ITestListener, IConfigurationListener {

	@Override
	public void onConfigurationSuccess(ITestResult itr) {
		// not implemented
	}

	@Override
	public void onConfigurationFailure(ITestResult itr) {
		if (ListenerThreads.getParentTest() != null
				&& (itr.getMethod().isBeforeClassConfiguration() || itr.getMethod().isAfterClassConfiguration())) {
			ListenerThreads.getParentTest().fail(itr.getThrowable());
		}
	}

	@Override
	public void onConfigurationSkip(ITestResult itr) {
		if (ListenerThreads.getParentTest() != null
				&& (itr.getMethod().isBeforeClassConfiguration() || itr.getMethod().isAfterClassConfiguration())) {
			ListenerThreads.getParentTest().fail(itr.getThrowable());
		}
	}

	@Override
	public void beforeConfiguration(ITestResult tr) {

	}

	@Override
	public void onTestStart(ITestResult result) {

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ListenerThreads.getChildTest().pass(MarkupHelper.createLabel("Test Passed", ExtentColor.GREEN));
		logEvents();
	}

	@Override
	public void onTestFailure(ITestResult failingTest) {
		ListenerThreads.getChildTest().fail(failingTest.getThrowable());
		ListenerThreads.getChildTest().fail(MarkupHelper.createLabel("Test Failed", ExtentColor.RED));
		logEvents();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ListenerThreads.getChildTest().skip(MarkupHelper.createLabel("Test Skipped", ExtentColor.AMBER));
		logEvents();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
	}

	@Override
	public void onFinish(ITestContext context) {

	}

	private void logEvents() {
		Map<String, MediaEntityModelProvider> eventMap = ListenerThreads.getEvents();
		if (eventMap != null && !eventMap.isEmpty()) {
			for (Map.Entry<String, MediaEntityModelProvider> map : eventMap.entrySet()) {
				String event = map.getKey();
				MediaEntityModelProvider screenShot = map.getValue();
				ListenerThreads.getChildTest().info(event, screenShot);
			}
		}
	}

}
