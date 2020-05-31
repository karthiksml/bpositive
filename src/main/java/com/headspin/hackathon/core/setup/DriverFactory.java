package com.headspin.hackathon.core.setup;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.headspin.hackathon.utils.AppConfig;
import com.headspin.hackathon.utils.Utils;

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
			WebDriverManager.getInstance(DriverManagerType.IEXPLORER).version("3.14").setup();
			return new InternetExplorerDriver();
		}
	};

	private static final Supplier<WebDriver> remoteDriverSupplier = new Supplier<WebDriver>() {
		public WebDriver get() {
			AppConfig config = Utils.readAppConfig();
			DesiredCapabilities caps = DesiredCapabilities.chrome();
			caps.setCapability("browserVersion", config.getBrowserVersion());
			caps.setCapability("browserName", config.getBrowserName());
			if (config.isCapture()) {
				caps.setCapability("headspin:capture", true);
			}

			try {
				return new RemoteWebDriver(new URL(config.getGridURL()), caps);
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
