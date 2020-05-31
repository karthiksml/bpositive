package com.headspin.hackathon.core.reports;

import java.util.Map;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityModelProvider;

public class ListenerThreads {
	
	private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	private static ThreadLocal<ExtentTest> childTest = new ThreadLocal<ExtentTest>();
	private static ThreadLocal<Map<String, MediaEntityModelProvider>> events = new ThreadLocal<>();

	public static ExtentTest getParentTest() {
		return parentTest.get();
	}

	public static void setParentTest(ExtentTest extentTest) {
		parentTest.set(extentTest);
	}

	public static ExtentTest getChildTest() {
		return childTest.get();
	}

	public static void setChildTest(ExtentTest extentTest) {
		childTest.set(extentTest);
	}

	public static Map<String, MediaEntityModelProvider> getEvents() {
		return events.get();
	}

	public static void setEvents(Map<String, MediaEntityModelProvider> events) {
		ListenerThreads.events.set(events);
	}

}
