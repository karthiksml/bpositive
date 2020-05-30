package com.headspin.bpositive.test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.headspin.hackathon.core.setup.DriverBase;

public class TestBase extends DriverBase {

	@Test
	public void googleCheeseExample() throws Exception {
		
		WebDriver driver = getDriver();
		driver.get("http://www.google.com");

	}
}
