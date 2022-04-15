package com.sample;

import java.io.IOException;
import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.sample.Config;

public class CommonMethods extends BaseTest{
	
		protected static final Logger logger = Logger.getLogger(CommonMethods.class);
		
		static WebDriver driver;
	
	//@Test
	public void launchApplication() throws IOException {
		System.setProperty("webdriver.chrome.driver", Config.chromeDriverPath);
		driver = new ChromeDriver();
		driver.get(Config.applicationURL);
		driver.manage().window().maximize();
	//	logInfoWithIBScreenshot("Launched URL", driver, "LaunchURL");
		logInfoWithIBScreenshot("Launched URL", driver, "LaunchURL");
		//Reports.getCurrentReporter().childLog.pass("Google home page displayed");
		logPass("Launched application successful");
		logFail("Not launched application");
	}
	
	public static void main (String[] a) {
		CommonMethods cm = new CommonMethods();
		cm.givenUsingJava8_whenGeneratingRandomAlphabeticString_thenCorrect();
		
	}

	public void givenUsingJava8_whenGeneratingRandomAlphabeticString_thenCorrect() {
	    int leftLimit = 97; 
	    int rightLimit = 122; 
	    int targetStringLength = 3;
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();

	    System.out.println(generatedString);
	}
	
}
