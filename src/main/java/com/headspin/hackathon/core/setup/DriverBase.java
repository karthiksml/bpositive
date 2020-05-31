package com.headspin.hackathon.core.setup;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.headspin.hackathon.core.listeners.EventHandler;
import com.headspin.hackathon.core.reports.ExtentManager;
import com.headspin.hackathon.core.reports.ListenerThreads;
import com.headspin.hackathon.utils.AppConfig;
import com.headspin.hackathon.utils.DriverUtils;
import com.headspin.hackathon.utils.Utils;

public class DriverBase {
	private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

	private ExtentReports extentReports;

	private static AppConfig appConfig = Utils.readAppConfig();

	private EventFiringWebDriver eventFiringWebDriver = null;
	private EventHandler handler = null;

	@BeforeSuite(alwaysRun = true)
	public void initReports() {
		extentReports = ExtentManager.getReporter();
	}

	@BeforeClass(alwaysRun = true)
	public void initApp() {
		DriverType driverType = DriverType.CHROME;
		WebDriver driver = DriverFactory.getDriver(driverType);
		if (appConfig.isRecordEvents()) {
			driver = captureEvents(driver);
			ListenerThreads.setEvents(new HashMap<String, MediaEntityModelProvider>());
		}
		driverThread.set(driver);
		ListenerThreads.setParentTest(extentReports.createTest(this.getClass().getSimpleName()));
	}

	private WebDriver captureEvents(WebDriver driver) {
		eventFiringWebDriver = new EventFiringWebDriver(driver);
		EventHandler handler = new EventHandler();
		eventFiringWebDriver.register(handler);
		return ((WebDriver) eventFiringWebDriver);
	}

	@BeforeMethod(alwaysRun = true)
	public void initTest(Method method) {
		Test test = method.getDeclaredAnnotation(Test.class);
		String testName = test.testName();

		if (testName.isEmpty()) {
			testName = method.getName();
		}
		ExtentTest childTest = ListenerThreads.getParentTest().createNode(testName);
		ListenerThreads.setChildTest(childTest);
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult failingTest) {
		if (!failingTest.isSuccess()) {
			try {
				WebDriver driver = driverThread.get();
				String screenshotDirectory = System.getProperty("screenshotDirectory", "target/screenshots");
				String screenshotAbsolutePath = screenshotDirectory + File.separator + System.currentTimeMillis() + "_"
						+ failingTest.getName() + ".png";
				File screenshot = new File(screenshotAbsolutePath);
				if (Utils.createFile(screenshot)) {
					try {
						DriverUtils.writeScreenshotToFile(driver, screenshot);
					} catch (ClassCastException weNeedToAugmentOurDriverObject) {
						DriverUtils.writeScreenshotToFile(new Augmenter().augment(driver), screenshot);
					}
					ListenerThreads.getChildTest().fail("<b>Screenshot</b><br> ", MediaEntityBuilder
							.createScreenCaptureFromBase64String(Utils.getBase64EncodedString(screenshot)).build());
				} else {
					System.err.println("Unable to create " + screenshotAbsolutePath);
				}
			} catch (Exception ex) {
				System.err.println("Unable to capture screenshot...");
				ex.printStackTrace();
			}
		}
	}

	@AfterClass(alwaysRun = true)
	public void closeApp() {

		if (driverThread.get() != null) {
			driverThread.get().quit();
		}
		if (eventFiringWebDriver != null) {
			eventFiringWebDriver.unregister(handler);
		}
	}

	@AfterSuite(alwaysRun = true)
	public void closeReports() {
		if (extentReports != null) {
			extentReports.flush();
		}
	}

	/**
	 * Returns the instance of BasePage.
	 * 
	 * @param <T>  - Class Type
	 * @param type - Class type requires to create new instance.
	 * @return BasePage instance
	 */
	public <T extends BasePage> T getPage(Class<T> type) {
		T page = null;
		try {

			page = type.getDeclaredConstructor(WebDriver.class).newInstance(driverThread.get());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return page;
	}

}
