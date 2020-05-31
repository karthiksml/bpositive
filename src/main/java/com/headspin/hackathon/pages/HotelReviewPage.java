package com.headspin.hackathon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.headspin.hackathon.core.setup.BasePage;
import com.headspin.hackathon.utils.DriverUtils;

public class HotelReviewPage extends BasePage{
	private DriverUtils driverUtils;
	
	public HotelReviewPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		driverUtils = new DriverUtils(driver);
	}
	
	
	@FindBy(id="fName")
	private WebElement firstName;
	
	@FindBy(id="lName")
	private WebElement lastName;
	
	@FindBy(id="mNo")
	private WebElement mobileNo;
	
	@FindBy(xpath="//label[contains(text(),'Late check-in')]")
	private WebElement lateCheckInOption;
	
	@FindBy(xpath="//label[contains(text(),'Twin beds')]")
	private WebElement twinBedsOption;
	
	@FindBy(css="#donation")
	private WebElement donationCheckBox;
	
	@FindBy(xpath="//div[contains(text(),'Pay Now')]")
	private WebElement payNow;
	
	
	public void enterFirstName(String firstNameText) {
		driverUtils.scrollToElement(firstName);
		driverUtils.setInput(firstName, firstNameText);
	}
	
	public void enterLastName(String lastNameText) {
		driverUtils.scrollToElement(lastName);
		driverUtils.setInput(lastName, lastNameText);
	}
	
	public void enterMobileNum(int mobNo) {
		driverUtils.scrollToElement(mobileNo);
		driverUtils.setInput(mobileNo, String.valueOf(mobNo));
	}
	
	public void selectOtherOptions() {
		driverUtils.scrollToElement(lateCheckInOption);
		driverUtils.clickElement(lateCheckInOption);
		driverUtils.clickElement(twinBedsOption);
		
		driverUtils.scrollToElement(donationCheckBox);
		driverUtils.clickElement(donationCheckBox);
	
	}
	
	public void clickOnPayNow() {
		driverUtils.scrollToElement(payNow);
		driverUtils.clickElement(payNow);
		
	}	
	
	

}
