package com.headspin.hackathon.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverUtils {

	private WebDriverWait driverWait = null;
	private WebDriver driver = null;

	public DriverUtils(WebDriver driver) {
		driverWait = new WebDriverWait(driver, 10);
		this.driver = driver;
	}

	public void loadURL(String url) {
		driver.get(url);
	}

	public void click(WebElement element) {

	}

	public static void writeScreenshotToFile(WebDriver driver, File screenshot) {
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
