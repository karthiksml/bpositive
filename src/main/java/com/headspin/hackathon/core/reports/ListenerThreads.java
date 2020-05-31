package com.headspin.hackathon.core.reports;

import com.aventstack.extentreports.ExtentTest;

public class ListenerThreads {
	private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	private static ThreadLocal<ExtentTest> childTest = new ThreadLocal<ExtentTest>();

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
}
