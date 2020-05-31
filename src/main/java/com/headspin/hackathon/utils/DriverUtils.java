package com.headspin.hackathon.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverUtils {

	private WebDriverWait driverWait = null;
	private WebDriver driver = null;
	JavascriptExecutor js;

	public DriverUtils(WebDriver driver) {
		driverWait = new WebDriverWait(driver, 3);
		this.driver = driver;
		js = (JavascriptExecutor) driver;
	}

	public void loadURL(String url) {
		driver.get(url);
	}

	public void waitForElementToDisplay(WebElement element) {
		driverWait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForElementToEnable(WebElement element) {
		driverWait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void clickElement(WebElement element) {
		waitForElementToDisplay(element);
		waitForElementToEnable(element);
		element.click();

	}

	public String getWindow() {
		return driver.getWindowHandle();
	}

	public void scrollToElement(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public String getElementText(WebElement element) {
		waitForElementToDisplay(element);
		return element.getText().toString();
	}

	public void setInput(WebElement element, String value) {
		waitForElementToDisplay(element);
		element.clear();
		if (value != null)
			element.sendKeys(value);
	}

	public void switchToWindow(String parentWindow) {
		driverWait.until(ExpectedConditions.numberOfWindowsToBe(2));
		Set<String> windows = driver.getWindowHandles();
		for (String cuurentWindow : windows)
			if (!cuurentWindow.equals(parentWindow))
				driver.switchTo().window(cuurentWindow);
	}

	public WebElement findElement(By by) {
		return driver.findElement(by);
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

	public void waitFor(int durationInSeconds) {
		try {
			Thread.sleep(TimeUnit.SECONDS.toMillis(durationInSeconds));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
