package com.headspin.hackathon.core.setup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
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
import com.headspin.hackathon.core.reports.ExtentManager;
import com.headspin.hackathon.core.reports.ListenerThreads;

public class DriverBase {
	private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

	private ExtentReports extentReports;

	@BeforeSuite(alwaysRun = true)
	public void initReports() {
		extentReports = ExtentManager.getReporter();
	}

	@BeforeClass(alwaysRun = true)
	public void initApp() {
		DriverType driverType = DriverType.valueOf(System.getProperty("browser"));
		WebDriver driver = DriverFactory.getDriver(driverType);
		driverThread.set(driver);
		ListenerThreads.setParentTest(extentReports.createTest(this.getClass().getSimpleName()));
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
				if (createFile(screenshot)) {
					try {
						writeScreenshotToFile(driver, screenshot);
					} catch (ClassCastException weNeedToAugmentOurDriverObject) {
						writeScreenshotToFile(new Augmenter().augment(driver), screenshot);
					}
					byte[] failedAt = Base64.getEncoder().encode(FileUtils.readFileToByteArray(screenshot));
					String base64String = new String(failedAt);
					ListenerThreads.getChildTest().fail("<b>Screenshot</b><br> ",
							MediaEntityBuilder.createScreenCaptureFromBase64String(base64String).build());
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

	private boolean createFile(File screenshot) {
		boolean fileCreated = false;

		if (screenshot.exists()) {
			fileCreated = true;
		} else {
			File parentDirectory = new File(screenshot.getParent());
			if (parentDirectory.exists() || parentDirectory.mkdirs()) {
				try {
					fileCreated = screenshot.createNewFile();
				} catch (IOException errorCreatingScreenshot) {
					errorCreatingScreenshot.printStackTrace();
				}
			}
		}

		return fileCreated;
	}

	private void writeScreenshotToFile(WebDriver driver, File screenshot) {
		try {
			FileOutputStream screenshotStream = new FileOutputStream(screenshot);
			screenshotStream.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
			screenshotStream.close();
		} catch (IOException unableToWriteScreenshot) {
			System.err.println("Unable to write " + screenshot.getAbsolutePath());
			unableToWriteScreenshot.printStackTrace();
		}
	}
}
