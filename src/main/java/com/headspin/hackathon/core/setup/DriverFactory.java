package com.headspin.hackathon.core.setup;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	private static final Map<DriverType, Supplier<WebDriver>> driverMap = new HashMap<DriverType, Supplier<WebDriver>>();

	public WebDriver getDriver(DriverType driverType) {
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
			WebDriverManager.getInstance(DriverManagerType.IEXPLORER).version("3.14").setup();
			return new InternetExplorerDriver();
		}
	};
	
	private static final Supplier<WebDriver> remoteDriverSupplier = new Supplier<WebDriver>() {
		public WebDriver get() {
			DesiredCapabilities caps = DesiredCapabilities.chrome();
	        caps.setCapability("browserVersion", "76.0.3809.100");
	        caps.setCapability("browserName", "chrome");
	        caps.setCapability("headspin:capture", true);

	        try {
				return new RemoteWebDriver(new URL("https://dev-us-pao-0.headspin.io:9092/v0/666ef1c13b974b14bfe62f8777184ae2/wd/hub"), caps);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			return null;
		}
	};
	
	

	static {
		driverMap.put(DriverType.CHROME, chromeDriverSupplier);
		driverMap.put(DriverType.FIREFOX, ffDriverSupplier);
		driverMap.put(DriverType.IE, ieDriverSupplier);
		driverMap.put(DriverType.HEADSPIN, remoteDriverSupplier);
		
	}

}
