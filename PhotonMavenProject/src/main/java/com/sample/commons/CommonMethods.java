package com.sample.commons;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.sample.Config;

public class CommonMethods {
	
	static WebDriver driver;
//	BaseTest baseTest = new BaseTest();
	
	@Test
	public void launchApplication() throws IOException {
		System.setProperty("webdriver.chrome.driver", Config.chromeDriverPath);
		driver = new ChromeDriver();
		driver.get(Config.applicationURL);
		driver.manage().window().maximize();
	//	baseTest.logInfoWithIBScreenshot("Launched URL", driver, "LaunchURL");
	}

}
