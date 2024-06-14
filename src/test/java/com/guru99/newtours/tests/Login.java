package com.guru99.newtours.tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Login {
	private static final Logger logger = LoggerFactory.getLogger(Register.class);
	public WebDriver driver;
	public WebDriverWait wait;
	
	
	By Username = By.xpath("//*[@name='userName']");
	By Password = By.xpath("//*[@name=\"password\"]");
	By Submit =By.xpath("//*[@name=\"submit\"]");
	
	public Login(WebDriver driver)
	{
		this.driver=driver;
		this.wait= new WebDriverWait(driver, Duration.ofSeconds(30));
		
	}
	
	public void username(String name)
	{
		wait.until(ExpectedConditions.presenceOfElementLocated(Username)).sendKeys(name);
		
	}
	
	public void password(String password)
	{
		wait.until(ExpectedConditions.presenceOfElementLocated(Password)).sendKeys(password);
	}
	
	public void submit()
	{
		wait.until(ExpectedConditions.presenceOfElementLocated(Submit)).click();
		logger.info("[Login Successfull]");
	}
	

}
