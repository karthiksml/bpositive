package com.headspin.hackathon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.headspin.hackathon.core.setup.BasePage;
import com.headspin.hackathon.utils.AppConfig;
import com.headspin.hackathon.utils.DriverUtils;

public class Home extends BasePage {

	private DriverUtils driverUtils;
	private AppConfig appConfig = DriverUtils.readAppConfig();

	public Home(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		driverUtils = new DriverUtils(driver);
	}

	public void loadHomePage(String url) {
		driverUtils.loadURL(url);
	}

	public void loadHomePage() {
		driverUtils.loadURL(appConfig.getAppURL());
	}

}
