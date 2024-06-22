package com.guru99.newtours.utility;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);
    private static WebDriver driver;
    private static ExtentReports extent;
    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static ThreadLocal<ExtentReports> extentReport = new ThreadLocal<>();
    private static ExtentSparkReporter sparkReporter;


    public static void setDriver(WebDriver driver) {
        TestListener.driver = driver;
        logger.info("WebDriver instance set successfully.");
    }
    
  

    @Override
    public void onStart(ITestContext context) {
        logger.info("(1)------Test context started------- for " + context.getName());
        String browser = context.getCurrentXmlTest().getParameter("browser");

        ExtentSparkReporter sparkReporter = null;    //        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/ExtentReports.html");
        if (browser.equalsIgnoreCase("chrome")) {
            sparkReporter = new ExtentSparkReporter("extent-chrome.html");
        } else if (browser.equalsIgnoreCase("edge")) {
            sparkReporter = new ExtentSparkReporter("extent-edge.html");
        }

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extentReport.set(extent);
        
        
        
    }

    @Override
    public void onTestStart(ITestResult result) {
    	 ExtentTest test = extent.createTest(result.getMethod().getMethodName());
         extentTest.set(test);  
			/*
			 * a new ExtentTest object is created for the test method, and it's set in the
			 * extentTest thread-local variable using extentTest.set(test).
			 */        logger.info("(2)--------Test started----------for " + result.getName());
        extentTest.get().log(Status.INFO, "Test Started");
        
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("(3)-------Test passed---------for " + result.getName());
        extentTest.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("(4)---------Test context finished-----------for " + context.getName());
        extentTest.get().log(Status.PASS, "Test Completed");
        if (extent != null) {
            extent.flush();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("(*)(*)(*)----------Test failed--------for  " + result.getName());
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.log(Status.FAIL, result.getThrowable());
            test.log(Status.INFO, "Test Failed");
            captureScreenshot(result.getName());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("(*)(*)(*)----------Test skipped------------for " + result.getName());
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.log(Status.SKIP, "Test skipped");
        }
    }

    private void captureScreenshot(String testName) {
        if (driver != null) {
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                String screenshotName = testName + "_" + timestamp + ".png";
                String screenshotDir = "screenshots/";

                // Create screenshot directory if it doesn't exist
                if (!Files.exists(Paths.get(screenshotDir))) {
                    Files.createDirectories(Paths.get(screenshotDir));
                }

                File destinationFile = new File(screenshotDir + screenshotName);
                Files.copy(screenshot.toPath(), destinationFile.toPath());
                logger.info("Screenshot saved: " + destinationFile.getAbsolutePath());
                ExtentTest test = extentTest.get();
                if (test != null) {
                    test.addScreenCaptureFromPath(destinationFile.getAbsolutePath());
                }
            } catch (WebDriverException | IOException e) {
                logger.error("Failed to capture screenshot: " + e.getMessage());
            }
        } else {
            logger.error("WebDriver instance is null. Cannot capture screenshot.");
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not implemented
    }
}
