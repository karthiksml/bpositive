package com.headspin.hackathon.utils;

import java.io.IOException;
import java.io.InputStream;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class DriverUtils {

	private WebDriverWait driverWait = null;
	private WebDriver driver = null;

	public DriverUtils(WebDriver driver) {
		driverWait = new WebDriverWait(driver, 10);
		this.driver = driver;
	}
	
	public void loadURL(String url) {
		driver.get(url);
	}
	
	public void click(WebElement element) {
		
	}
	
	public static AppConfig readAppConfig() {
		AppConfig appConfig = null;
		try {
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			InputStream envFile = AppConfig.class.getResourceAsStream("/application/app.yaml");
			appConfig = mapper.readValue(envFile, AppConfig.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return appConfig;
	}

}
