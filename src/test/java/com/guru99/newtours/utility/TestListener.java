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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);
    private static WebDriver driver;

    public static void setDriver(WebDriver driver) {
        TestListener.driver = driver;
        logger.info("WebDriver instance set successfully.");
    }
    
    @Override
    public void onStart(ITestContext context) {
        logger.info("(1)------Test context started------- for " + context.getName());
        
    }
    
    
    @Override
    public void onTestStart(ITestResult result) {
        logger.info("(2)--------Test started----------for " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("(3)-------Test passed---------for " + result.getName());
        captureScreenshot(result.getName());
    }

    
    @Override
    public void onFinish(ITestContext context) {
        logger.info("(4)---------Test context finished-----------for " + context.getName());
        
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("(*)(*)(*)----------Test failed--------for  " + result.getName());
        captureScreenshot(result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("(*)(*)(*)----------Test skipped------------for " + result.getName());
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
            } catch (WebDriverException | IOException e) {
                logger.error("Failed to capture screenshot: " + e.getMessage());
            }
        } else {
            logger.error("WebDriver instance is null. Cannot capture screenshot.");
        }
    }

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}



}
