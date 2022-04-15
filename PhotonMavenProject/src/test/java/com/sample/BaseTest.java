package com.sample;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import javax.imageio.ImageIO;

import java.text.SimpleDateFormat;
import java.text.DateFormat;

import org.apache.commons.io.FileUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.sample.Config;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class BaseTest {
	
	static ExtentHtmlReporter htmlReporter;
	static ExtentReports extent;
	static ExtentTest test;
	public static ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<ExtentTest>();
	public static String status;
	public static ThreadLocal<String> executionStatus = new ThreadLocal<String>();
	public static ThreadLocal<String> methodName = new ThreadLocal<String>();
	public static LinkedHashMap<String, String> treeMap = new LinkedHashMap<String, String>();
	public static String errorMessage = "";
	public static String folderName = "";
	public static List<String> lstScreenShotNames = new ArrayList<String>();
	
	@BeforeSuite(alwaysRun = true)
	public void startReport(ITestContext iTestContext) throws IOException {
		
		folderName = "Report_"
				+ new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(
				Calendar.getInstance().getTime()).toString();
				
		String srcFolder = "";
		if(System.getProperty("os.name").contains("Mac"))  {
			srcFolder = Config.CURRENT_DIR+"/target/Reports/"+folderName;
			new File(Config.CURRENT_DIR+"/target/Reports/").mkdir();
		} else {
			srcFolder = Config.CURRENT_DIR+"\\target\\Reports\\"+folderName;
			new File(Config.CURRENT_DIR+"\\target\\Reports\\").mkdir();
		}
		System.out.println("Source folder is " + srcFolder);
		new File(srcFolder).mkdir();
		System.setProperty("src.folder", srcFolder);
		String workingDir = System.getProperty("src.folder");
		if(System.getProperty("os.name").toLowerCase().contains("win")) {
			FileUtils.cleanDirectory(new File(workingDir));
			htmlReporter = new ExtentHtmlReporter(workingDir + "\\ExtentReportResults.html");
		}
		else {
			FileUtils.cleanDirectory(new File(workingDir));
			htmlReporter = new ExtentHtmlReporter(workingDir + "//ExtentReportResults.html");
		}
		
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName(System.getProperty("user"));
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.DARK);
	}
	
	public static String getScreenshot(WebDriver driver, String fileName) throws IOException {
		
		BufferedImage bufferedImage = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver).getImage();
		//ScrollIntoView
		File src = new File("image.jpg");
		ImageIO.write(bufferedImage, "jpg", src);
		String screenShotName = fileName+ "_Screenshots"+ System.currentTimeMillis()+".jpg";
		String path = null;
		if(System.getProperty("os.name").toLowerCase().contains("win")) {
			path = System.getProperty("src.folder")+"/"+screenShotName; 
		} else {
			path = System.getProperty("src.folder")+screenShotName;
		}
		System.out.println("Final path is " +path);
		File destination = new File(path);
		try {
			FileUtils.copyFile(src, destination);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return screenShotName;
	}
	
	public static String getNormalScreenshot(WebDriver driver, String fileName) throws IOException {
		
		String screenShotName = fileName+".jpg";
		lstScreenShotNames.add(screenShotName);
		String path = null;
		if(System.getProperty("os.name").toLowerCase().contains("win")) {
			path = System.getProperty("src.folder")+"/"+screenShotName;
		} else {
			path = System.getProperty("src.folder")+screenShotName;
		}
		
		File destination = new File(path);
	    File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    FileUtils.copyFile(srcFile, destination);
		
		return screenShotName;
		
	}
	
	@BeforeMethod
	public void beforeMethod(Method method) {
		methodName.set(method.getName());
		test = extent.createTest(method.getName());
		extentTestThreadLocal.set(test);
		
	}
	
	@AfterMethod
public void getResult(ITestResult result) {
	
	if(result.getStatus() == ITestResult.SUCCESS) {
		executionStatus.set("PASS");
		extentTestThreadLocal.get().log(Status.PASS, MarkupHelper.createLabel(result.getName() + "Test Case PASSED", ExtentColor.GREEN));
	} else {
		executionStatus.set("FAIL");		
		extentTestThreadLocal.get().log(Status.FAIL, MarkupHelper.createLabel(result.getName() + "Test Cased FAILED due to below issues:", ExtentColor.RED));
		String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
		extentTestThreadLocal.get().fail("<details" + "<sumary>" + "<b>" + "<font color=" + "red>" + "Exception Occured: click to see"
		+"</font>"+"</b>"+"</summary>"+exceptionMessage.replaceAll(",", "<br>")+"</details>"+" \n");
		
	}
	treeMap.put(methodName.get(), executionStatus.get());
}


public static void logInfo(String info) {
	extentTestThreadLocal.get().log(Status.INFO, info);
}


public void logInfoWithIBScreenshot(String info, WebDriver driver, String fileName) throws IOException {
	extentTestThreadLocal.get().info("<b>" + "<font color=" + "green>" + info
			+"</font>" + "</b>",MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot(driver,fileName)).build());
}

public void logInfoWithNormalIBScreenshot(String info, WebDriver driver, String fileName) throws IOException {
	extentTestThreadLocal.get().info("<b>" + "<font color=" + "green>" + info
			+"</font>" + "</b>",MediaEntityBuilder.createScreenCaptureFromPath(getNormalScreenshot(driver,fileName)).build());

}

public static void logInfoResponse(String info) {
	extentTestThreadLocal.get().log(Status.INFO,"<details>" + "<summary>"+"<b>"+ "<font color=" + "red>" + "Response: click to see"
			+"</font>" + "</b>" + "</summary>"+info+"</details>"+" \n");

}

public static void logPass(String message) {
	extentTestThreadLocal.get().log(Status.PASS, message);
}

public static void logFail(String message) {
	extentTestThreadLocal.get().log(Status.FAIL, message);
}

@AfterSuite
public void tearDown() {
	extent.flush();
}
	
@AfterSuite
public void afterSuite() throws Exception {
	// In casea any DB status is there code goes here.
	
}

/*public static void pdfGenerator() throws Exception {
	String file = "D:/sample.pdf";
	Document doc = new Document();
	PdfWriter.getInstance(doc, new FileOutputStream(file));
	doc.open();
	doc.add(new Paragraph("Test case fail :1"));
	doc.add(new Paragraph("Error ID not found"));
	doc.close();
}*/

/*public static void wordGenerator() throws Exception {
	XWPFDocument document = new XWPFDocument();
	//set document name and destination
	String sOutputDoc = System.getProperty("src.folder")+"/"+"Report.docx";
	FileOutputStream fileOutputStream = new FileOutputStream(sOutputDoc);
	// Add a paragpah to the document
	XWPFParagraph paragraph = document.createParagraph();
	
	// Add name of failed test
	XWPFRun run = paragraph.createRun();
	
	String sFileName = "";
	for(String screenShotName : lstScreenShotNames) {
		if(sFileName.equals(screenShotName.split("_")[0])) {
			
		} else {
			sFileName = screenShotName.split("_")[0];
			run.setText(sFileName);
			run.addCarriageReturn();
			run.addCarriageReturn();
		}

	// Create image file input stream
	File image = new File(String.valueOf(System.getProperty("src.folder") + "\\" + screenShotName));
	FileInputStream imageData = new FileInputStream(image);

	// Set image type and get image name
	int imageType = XWPFDocument.PICTURE_TYPE_JPEG;
	String imageFilenName = image.getName();
	
	// set image width/height
	int imageWidth = 500;
	int imageHeight = 250;
	
	
	// Add screenshot to document
	run.addPicture(imageData,  imageType, imageFilenName, Units.toEMU(imageWidth), Units.toEMU(imageHeight));
	run.addCarriageReturn();
	run.addCarriageReturn();
	}
	document.write(fileOutputStream);
	// cleanup
	fileOutputStream.close();
	document.close();
	
}*/

}

