package com.sample;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.sample.Config;

public class Reports {

	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;
	public ExtentTest childLog;
	private static final ThreadLocal<String> currentStep = new InheritableThreadLocal<String>();
	private static final ThreadLocal<Reports> currentReporter = new InheritableThreadLocal<Reports>();
	private static final Logger logger = Logger.getLogger(Reports.class);
	
	@Parameters({"OS", "browser"})
	@BeforeTest
	public void startReport(String OS, String browser) {
		// Initialize the HtmlReporter
		htmlReporter = new ExtentHtmlReporter(Config.CURRENT_DIR+"/test-output/testReport.html");
		
		// Initialize ExtentReports and attach the HtmlReporter
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		
		//To add system or environment info by using the setSystemInfo method.
		extent.setSystemInfo("OS", OS);
		extent.setSystemInfo("Browser", browser);
		
		// Configuration items to change the look and feel
		// add content, manage tests etc
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("Extent Report Demo");
		htmlReporter.config().setReportName("Test Report");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setTimeStampFormat("EEE, MMMM dd, yyyy, hh:mm a ('zzz')'");
		setCurrentReporter(this);
		
	}
	
	public static void setCurrentReporter(Reports report) {
		currentReporter.set(report);
	}
	
	public static void getCurrentReporter() {
		currentReporter.get();
	}
	
	public static void setCurrentStep(String step) {
		currentStep.set(step);
	}
	
	public static void getCurrentStep() {
		currentStep.get();
	}
	
}
