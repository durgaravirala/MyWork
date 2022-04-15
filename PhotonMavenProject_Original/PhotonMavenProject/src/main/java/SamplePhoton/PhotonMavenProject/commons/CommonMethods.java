package SamplePhoton.PhotonMavenProject.commons;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import SamplePhoton.PhotonMavenProject.config.Config;

public class CommonMethods {
	
	static WebDriver driver;

	public void launchApplication() {
		
		System.setProperty("webdriver.chrome.driver", Config.chromeDriverPath);
		driver = new ChromeDriver();
		driver.get(Config.applicationURL);
		driver.manage().window().maximize();
		
	}
	
	public static void main (String[] args) {
		CommonMethods cm = new CommonMethods();
		cm.launchApplication();
	}
	
}
