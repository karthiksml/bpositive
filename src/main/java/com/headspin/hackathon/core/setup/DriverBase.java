package com.headspin.hackathon.core.setup;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class DriverBase {
	
	private static ThreadLocal<DriverFactory> driverFactoryThread = new ThreadLocal<DriverFactory>();

	@BeforeSuite(alwaysRun = true)
	public void initReports() {

	}

	@BeforeClass(alwaysRun = true)
	public static void instantiateDriverObject() {
		DriverFactory driverFactory = new DriverFactory();
		driverFactoryThread.set(driverFactory);
	};

	public static WebDriver getDriver() {
		DriverType driverType = DriverType.valueOf(System.getProperty("browser"));
		return driverFactoryThread.get().getDriver(driverType);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriverObjects() {
	}

	@AfterSuite(alwaysRun = true)
	public void closeReports() {

	}
}
