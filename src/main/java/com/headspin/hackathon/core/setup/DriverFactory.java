package com.headspin.hackathon.core.setup;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	private static final Map<DriverType, Supplier<WebDriver>> driverMap = new HashMap<DriverType, Supplier<WebDriver>>();

	public static WebDriver getDriver(DriverType driverType) {
		return driverMap.get(driverType).get();
	}

	private static final Supplier<WebDriver> ffDriverSupplier = new Supplier<WebDriver>() {
		public WebDriver get() {
			WebDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
			return new FirefoxDriver();
		}
	};

	private static final Supplier<WebDriver> chromeDriverSupplier = new Supplier<WebDriver>() {
		public WebDriver get() {
			WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
			return new ChromeDriver();
		}
	};

	private static final Supplier<WebDriver> ieDriverSupplier = new Supplier<WebDriver>() {
		public WebDriver get() {
			WebDriverManager.getInstance(DriverManagerType.IEXPLORER).setup();
			return new InternetExplorerDriver();
		}
	};

	static {
		driverMap.put(DriverType.CHROME, chromeDriverSupplier);
		driverMap.put(DriverType.FIREFOX, ffDriverSupplier);
		driverMap.put(DriverType.IE, ieDriverSupplier);
	}

}
