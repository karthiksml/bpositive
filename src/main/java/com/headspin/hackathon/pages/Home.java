package com.headspin.hackathon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
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

	public void loadHomePage() {
		driverUtils.loadURL(appConfig.getAppURL());
	}

	@FindBy(xpath = "//p[contains(., 'Login')]")
	private WebElement login;

	@FindAll({ @FindBy(id = "username"), @FindBy(xpath = "//input[@id='username']") })
	WebElement username;

	@FindAll({ @FindBy(xpath = "//button[@class='capText font16']/span[contains(text(), 'Continue')]"),
			@FindBy(xpath = "//span[contains(text(), 'Continue')]") })
	WebElement continueBtn;

	@FindAll({ @FindBy(id = "password"), @FindBy(xpath = "//input[@id='password']") })
	WebElement password;

	@FindAll({ @FindBy(xpath = "//button[@class='capText font16']/span[contains(text(), 'Login')]"),
			@FindBy(xpath = "//span[contains(text(), 'Login')]") })
	WebElement loginBtn;

	@FindBy(xpath = "//div[@class='modalClose pushRight']/span")
	WebElement modalClose;

	public void login() {
		driverUtils.clickElement(login);
		driverUtils.setInput(username, appConfig.getUsername());
		driverUtils.clickElement(continueBtn);
		driverUtils.setInput(password, appConfig.getPassword());
		driverUtils.clickElement(loginBtn);
		driverUtils.clickElement(modalClose);
	}

}
