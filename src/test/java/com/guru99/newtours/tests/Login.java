package com.guru99.newtours.tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.guru99.newtours.utility.TestListener;

public class Login {
	private static final Logger logger = LoggerFactory.getLogger(Register.class);
	private ExtentTest extentTest = TestListener.extentTest.get();
	public WebDriver driver;
	public WebDriverWait wait;

	By Username = By.xpath("//*[@name='userName']");
	By Password = By.xpath("//*[@name=\"password\"]");
	By Submit = By.xpath("//*[@name=\"submit\"]");

	public Login(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));

	}

	public void username(String name) {
		wait.until(ExpectedConditions.presenceOfElementLocated(Username)).sendKeys(name);

	}

	public void password(String password) {
		wait.until(ExpectedConditions.presenceOfElementLocated(Password)).sendKeys(password);
	}

	public void submit() {
		wait.until(ExpectedConditions.presenceOfElementLocated(Submit)).click();
		extentTest.log(Status.INFO, "Username and Password added successfully");
		logger.info("Logged in Successfully ");
		extentTest.log(Status.PASS, "Logged in Successfully");
	}

	public void verifyLogin() {
		boolean isLoggedIn = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("SIGN-OFF")))
				.isDisplayed();
		Assert.assertTrue(isLoggedIn, "Login failed");
	}

}
