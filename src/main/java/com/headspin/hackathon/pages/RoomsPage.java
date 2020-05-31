package com.headspin.hackathon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.headspin.hackathon.core.setup.BasePage;
import com.headspin.hackathon.utils.DriverUtils;

public class RoomsPage extends BasePage {

	private DriverUtils driverUtils;

	public RoomsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		driverUtils = new DriverUtils(driver);
	}

	@FindBy(xpath = "(//span[@class='latoBlack'])[1]")
	WebElement checkInTime;

	@FindBy(xpath = "(//span[@class='latoBlack'])[2]")
	WebElement checkOutTime;

	@FindBy(xpath = "(//div[@class='roomWrap']//h2)[1]")
	WebElement roomType;

	@FindBy(xpath = "(//div[@class='roomWrap']//a[text()='SELECT ROOM'])[1]")
	WebElement selectRoom;

	@FindBy(xpath = "(//div[@class='roomWrap']//span[@class='bxNegotiate appendBottom5'])[1]")
	WebElement roomFare;
	
	@FindBy(id = "detpg_hotel_name")
	WebElement hotelName;
	

	public void switchToHotelRooms(String parentWindow) {
		driverUtils.switchToWindow(parentWindow);
	}
	
	public void selectRoom() {
		driverUtils.clickElement(selectRoom);
	}

}