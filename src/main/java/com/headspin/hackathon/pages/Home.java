package com.headspin.hackathon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import com.headspin.hackathon.core.setup.BasePage;


public class Home extends BasePage {
	

	public Home(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void loadHomePage(String url) {
		loadURL(url);
	}
	


}
