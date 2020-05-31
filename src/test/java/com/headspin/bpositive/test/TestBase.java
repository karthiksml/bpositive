package com.headspin.bpositive.test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.headspin.hackathon.core.setup.DriverBase;
import com.headspin.hackathon.pages.Home;
import com.headspin.hackathon.pages.HotelListingPage;
import com.testautomationguru.ocular.Ocular;


public class TestBase extends DriverBase {
	
	private final static String OCULAR_SNAPSHOTS = "src/test/resources/blog/snapshots";
	private final static String OCULAR_RESULTS = "src/test/resources/blog/results";
	
	private Home homePage;
	private HotelListingPage hotelListingPage;
	private String url = "https://www.makemytrip.com/";
	

	private static void setVisualValidationConfig() {
	    Path ocularSnapshotsPath = Paths.get(".", OCULAR_SNAPSHOTS);
	    File ocularSnapshotsDir = new File(ocularSnapshotsPath.toString());
	    if(!ocularSnapshotsDir.exists()) {
	    	ocularSnapshotsDir.mkdirs();
	    }
	    Path ocularResultsPath = Paths.get(".", OCULAR_RESULTS);
	    File ocularResultsDir = new File(ocularResultsPath.toString());
	    if(!ocularResultsDir.exists()) {
	    	ocularResultsDir.mkdirs();
	    }	
	    
	    Ocular.config()
		.resultPath(ocularResultsPath)
		.snapshotPath(ocularSnapshotsPath)
		.globalSimilarity(50)
		.saveSnapshot(true); 
	
	}
	
	

		

	@BeforeClass
	public void initPages() {
		homePage = getPage(Home.class);
		hotelListingPage = getPage(HotelListingPage.class);
	}

	@Test
	public void verifyMakeMyTripHomePage() throws InterruptedException {
		homePage.loadHomePage(url);
		hotelListingPage.moveSliderBy(1000);
	
	
	}
}
