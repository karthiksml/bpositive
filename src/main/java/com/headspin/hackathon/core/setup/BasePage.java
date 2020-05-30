package com.headspin.hackathon.core.setup;

import org.openqa.selenium.WebDriver;

public class BasePage {

	private WebDriver driver;

	public BasePage(WebDriver driver) {
		this.driver = driver;
	}

	public void loadURL(String url) {
		driver.get(url);
	}
}
