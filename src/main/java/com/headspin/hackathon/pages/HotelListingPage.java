package com.headspin.hackathon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.headspin.hackathon.core.setup.BasePage;
import com.headspin.hackathon.utils.DriverUtils;

public class HotelListingPage extends BasePage {
	
	private final Actions action;
	private DriverUtils driverUtils;

	public HotelListingPage(WebDriver driver) {
		super(driver);
		this.action = new Actions(driver);
		PageFactory.initElements(driver, this);
		driverUtils = new DriverUtils(driver);
	}

	@FindBy(xpath = "//div[@id='hlistpg_fr_price_per_night']//span[1]//div[1]")
	private WebElement priceSliderMin;

	@FindBy(xpath = "//label[contains(text(),'4 & above (Very Good)')]")
	private WebElement veryGoodUserratingCheckbox;

	@FindBy(id = "Listing_hotel_4")
	private WebElement hotelName;

	public void moveSliderBy(int p) {
		driverUtils.scrollToElement(priceSliderMin);
		action.dragAndDropBy(priceSliderMin, 8, 0).build().perform();
	}

	public void selectUserRating() {
		driverUtils.scrollToElement(veryGoodUserratingCheckbox);
		driverUtils.clickElement(veryGoodUserratingCheckbox);
	}

	public String scrollandSelectHotel() {
		
		driverUtils.scrollToElement(hotelName);
		String hotelNam = driverUtils.getElementText(hotelName);
		return hotelNam;
	}

}
