package com.headspin.hackathon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.headspin.hackathon.core.setup.BasePage;
import com.headspin.hackathon.utils.DriverUtils;

public class CheckoutSummaryPage extends BasePage {
	private DriverUtils driverUtils;

	public CheckoutSummaryPage(WebDriver driver) {
		super(driver);
		driverUtils = new DriverUtils(driver);
	}

	@FindBy(css = ".hotel_name.pymt-htlInfo-name")
	private WebElement hotelNameInfo;

	@FindBy(css = ".hotel_location.pymt-htlInfo-loc")
	private WebElement hotelLocationInfo;

	@FindBy(css = ".checkin.pull-left .checkin_time span")
	private WebElement checkInDateInfo;

	@FindBy(css = ".checkout.pull-right .checkin_time span")
	private WebElement checkoutDateInfo;

	@FindBy(css = ".room_heading span")
	private WebElement roomInfo;

	@FindBy(css = ".traveler_details.clearfix .traveler_name")
	private WebElement travellerName;

	@FindBy(css = ".traveler_details.clearfix .contact_info")
	private WebElement contactinfo;

	public boolean verifysummaryDetails(String hotelName, String checkInDate, String checkOutDate,
			String roomInformation, String userName) {
		if (driverUtils.getElementText(hotelNameInfo).toString().equalsIgnoreCase(hotelName)
				&& driverUtils.getElementText(checkInDateInfo).toString().equalsIgnoreCase(checkInDate)
				&& driverUtils.getElementText(checkoutDateInfo).toString().equalsIgnoreCase(checkOutDate)
				&& driverUtils.getElementText(roomInfo).toString().contains(roomInformation)
				&& driverUtils.getElementText(travellerName).toString().contains(userName)) {
			return true;
		} else {
			return false;
		}

	}

}
