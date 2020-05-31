package com.headspin.hackathon.utils;

import java.io.IOException;
import java.io.InputStream;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class DriverUtils {

	private  WebDriverWait driverWait = null;
	private WebDriver driver = null;
	JavascriptExecutor js;

	public DriverUtils(WebDriver driver) {
		driverWait = new WebDriverWait(driver, 10);
		this.driver = driver;
		js = (JavascriptExecutor) driver;
	}

	public void loadURL(String url) {
		driver.get(url);
	}

	public void waitForElementToDisplay(WebElement element) {
		driverWait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForElementToEnable(WebElement element) {
		driverWait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public  void clickElement(WebElement element) {
		waitForElementToDisplay(element);
		waitForElementToEnable(element);
		element.click();

	}
	
	public void scrollToElement(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public String getElementText(WebElement element) {
		waitForElementToDisplay(element);
		return element.getText().toString();
	}
	
	public  void setInput(WebElement element, String value) {
		waitForElementToDisplay(element);
		element.clear();
		if(value !=null)
			element.sendKeys(value);

	}
	
	public WebElement findElement(By by) {
		return driver.findElement(by);
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
