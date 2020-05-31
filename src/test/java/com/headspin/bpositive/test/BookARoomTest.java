package com.headspin.bpositive.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.headspin.hackathon.core.listeners.CustomReportListener;
import com.headspin.hackathon.core.reports.ListenerThreads;
import com.headspin.hackathon.core.setup.DriverBase;
import com.headspin.hackathon.pages.CheckoutSummaryPage;
import com.headspin.hackathon.pages.DashBoardPage;
import com.headspin.hackathon.pages.Home;
import com.headspin.hackathon.pages.HotelListingPage;
import com.headspin.hackathon.pages.HotelReviewPage;
import com.headspin.hackathon.pages.RoomsPage;
import com.headspin.hackathon.utils.HotelSummary;

@Listeners(CustomReportListener.class)
public class BookARoomTest extends DriverBase {
	private Home homePage;
	private DashBoardPage dashBoardPage;
	private HotelListingPage hotelListingPage;
	private RoomsPage roomsPage;
	private HotelReviewPage hotelReviewPage;
	private CheckoutSummaryPage checkoutSummaryPage;
	private SoftAssert softAssert;

	@BeforeClass
	public void initPages() {
		homePage = getPage(Home.class);
		dashBoardPage = getPage(DashBoardPage.class);
		hotelListingPage = getPage(HotelListingPage.class);
		roomsPage = getPage(RoomsPage.class);
		hotelReviewPage = getPage(HotelReviewPage.class);
		checkoutSummaryPage = getPage(CheckoutSummaryPage.class);
	}

	@Test(testName = "Login and Book a room in makemytrip")
	public void book_a_room_in_makemytrip() {
		ListenerThreads.getChildTest().info("Login to makemytrip.com");
		homePage.loadHomePage();
		homePage.login();

		dashBoardPage.enterCityDetails("Chennai");
		String checkinDate = dashBoardPage.checkInDetails();
		String checkoutDate = dashBoardPage.checkOutDetails();

		dashBoardPage.enterRoomGuestsDetails(2);
		dashBoardPage.enterTravellingFor();

		hotelListingPage.moveSliderBy();
		hotelListingPage.selectUserRating();
		String currentWindow = hotelListingPage.getActiveWindow();
		String hotelNameInListingPage = hotelListingPage.selectAndGetNthHotel(5);

		roomsPage.switchToHotelRooms(currentWindow);
		String roomName = roomsPage.getRoomName();
		roomsPage.selectRoom();

		hotelReviewPage.enterFirstName("AutomationUser");
		hotelReviewPage.enterLastName("Test");
		hotelReviewPage.enterMobileNum("9999999999");
		hotelReviewPage.selectOtherOptions();
		hotelReviewPage.clickOnPayNow();

		HotelSummary hotelSummary = checkoutSummaryPage.getHotelBookingSummary();

		softAssert = new SoftAssert();

		softAssert.assertEquals(hotelNameInListingPage, hotelSummary.getHotelName());
		softAssert.assertEquals(roomName, hotelSummary.getRoomName());
		softAssert.assertEquals(checkinDate, hotelSummary.getCheckInDate());
		softAssert.assertEquals(checkoutDate, hotelSummary.getCheckOutDate());

	}
}
