package com.headspin.hackathon.pages;

import com.headspin.hackathon.utils.DriverUtils.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.headspin.hackathon.core.setup.BasePage;
import com.headspin.hackathon.utils.DriverUtils;

public class DashBoardPage extends BasePage {

	private DriverUtils driverUtils;
	private String checkInDate;

	public DashBoardPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		driverUtils = new DriverUtils(driver);
	}

	@FindAll({
			@FindBy(xpath = "//a[@href='https://www.makemytrip.com/hotels/' and @class=' makeFlex hrtlCenter column']"),
			@FindBy(partialLinkText = "hotels") })
	WebElement hotels;

	@FindAll({ @FindBy(id = "city"), @FindBy(xpath = "//input[@id='city' and @type='text']") })
	WebElement cityName;

	@FindBy(xpath = "//input[@placeholder='Enter city/ Hotel/ Area/ Building' and @type='text']")
	WebElement enteCityName;

	@FindBy(xpath = "(//ul[@role='listbox']/li)[1]")
	WebElement selectCity;

	@FindAll({ @FindBy(id = "checkin"), @FindBy(xpath = "//input[@id='checkin' and @type='text']") })
	WebElement checkIn;

	@FindBy(xpath = "//div[@class='DayPicker-Day DayPicker-Day--start DayPicker-Day--selected' and @aria-selected='true']")
	WebElement selectedCheckInDate;

	@FindBy(xpath = "//span[@class='selectedDateField appendBottom8 pointer' and @data-cy='selectCheckInDate']")
	WebElement getSelectedCheckInDate;

	@FindAll({ @FindBy(id = "guest"), @FindBy(xpath = "//input[@id='guest']' and @type='text']") })
	WebElement selectGuests;

	@FindBy(xpath = "//button[@type='button' and text()='APPLY']")
	WebElement applyBtn;

	@FindAll({ @FindBy(id = "travelFor"), @FindBy(xpath = "//input[@id='travelFor']' and @type='text']") })
	WebElement travelFor;

	@FindAll({ @FindBy(xpath = "//li[contains(text(), 'Work')]"),
			@FindBy(xpath = "//ul[@class='travelForPopup']/li[contains(text(), 'Work')]") })
	WebElement travelForWork;

	@FindAll({ @FindBy(id = "hsw_search_button"),
			@FindBy(xpath = "//button[@id='hsw_search_button' and @type='button']") })
	WebElement searchBtn;

	@FindBy(xpath = "//div[@class='addRoomRow']")
	List<WebElement> roomCount;

	@FindBy(xpath = "//span[@class='selectedDateField appendBottom8 pointer' and @data-cy='selectCheckOutDate']")
	WebElement getSelectedCheckOutDate;

	public void enterCityDetails(String city) {
		driverUtils.clickElement(hotels);
		driverUtils.clickElement(cityName);
		driverUtils.setInput(enteCityName, city);
		driverUtils.clickElement(selectCity);
	}

	public String checkInDetails() {
		driverUtils.clickElement(checkIn);
		driverUtils.clickElement(selectedCheckInDate);
		checkInDate = selectedCheckInDate.getText();
		return getSelectedCheckInDate.getText();
	}

	public String checkOutDetails() {
		WebElement element = driverUtils.findElement(By.xpath(
				"//div[@class='DayPicker-Day' and contains(text(), " + Integer.parseInt(checkInDate) + 2 + ")]"));
		driverUtils.clickElement(element);
		return getSelectedCheckOutDate.getText();
	}

	public Integer enterRoomGuestsDetails(Integer guests) {
		driverUtils.clickElement(selectGuests);
		driverUtils.clickElement(
				driverUtils.findElement(By.xpath("//ul[@data-cy='adultCount']/li[text()=" + guests + "]")));
		driverUtils.clickElement(applyBtn);
		return roomCount.size();
	}

	public void enterTravellingFor() {
		driverUtils.clickElement(travelFor);
		driverUtils.clickElement(travelForWork);
		driverUtils.clickElement(searchBtn);
	}

}
