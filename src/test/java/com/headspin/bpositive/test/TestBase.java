package com.headspin.bpositive.test;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import com.headspin.hackathon.core.setup.DriverBase;
import com.testautomationguru.ocular.Ocular;
import com.testautomationguru.ocular.comparator.OcularResult;
import com.testautomationguru.ocular.sample.SampleBuilder;

public class TestBase extends DriverBase {
	
	private final static String OCULAR_SNAPSHOTS = "src/test/resources/blog/snapshots";
	private final static String OCULAR_RESULTS = "src/test/resources/blog/results";
	
	@BeforeClass
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
	
	public OcularResult compare(String snapshotName, WebElement... elements) {
		Path snapshotPath = Paths.get(snapshotName + ".png");
		SampleBuilder builder = Ocular.snapshot().from(snapshotPath)
                     .sample().using(getDriver());
		for (WebElement element : elements) {
			if (null != element) {
				builder = builder.excluding(element);
			}
		}
		return builder.compare();
    }

	@Test
	public void googleCheeseExample() throws Exception {
		
		WebDriver driver = getDriver();
		driver.get("http://www.google.com");
		
		OcularResult result = compare("HomePage", driver.findElement(By.linkText("Gmail")));
		Assert.assertTrue(result.isEqualsImages());
	}
}
