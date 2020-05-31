package com.headspin.hackathon.utils;

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

}
