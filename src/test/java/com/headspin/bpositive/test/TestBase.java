package com.headspin.bpositive.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.headspin.hackathon.core.setup.DriverBase;
import com.headspin.hackathon.pages.Home;

public class TestBase extends DriverBase {

	private Home homePage;
	private String url = "https://www.makemytrip.com/";

	@BeforeClass
	public void initPages() {
		homePage = getPage(Home.class);
	}

	@Test
	public void verifyMakeMyTripHomePage() {
		homePage.loadHomePage(url);
	}
}
