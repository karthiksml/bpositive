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

	public void loadHomePage(String url) {
		driverUtils.loadURL(url);
	}

	public void loadHomePage() {
		driverUtils.loadURL(appConfig.getAppURL());
	}

	@FindBy(xpath = "//p[contains(., \"Login\")]")
	WebElement login;

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

	@FindAll({
			@FindBy(xpath = "//a[@href='https://www.makemytrip.com/hotels/' and @class=' makeFlex hrtlCenter column']"),
			@FindBy(partialLinkText = "hotels") })
	WebElement hotels;

	@FindAll({ @FindBy(id = "city"), @FindBy(xpath = "//input[@id='city' and @type='text']") })
	WebElement cityName;
	
	@FindBy(xpath = "//input[@placeholder='Enter city/ Hotel/ Area/ Building' and @type='text']")
	WebElement enteCityName;
	
	

}
