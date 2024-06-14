package com.guru99.newtours.tests;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Register {
	private static final Logger logger = LoggerFactory.getLogger(Register.class);
	public WebDriver driver;
	public WebDriverWait wait;
	
	
	By Register   =By.xpath("//*[text()='REGISTER']");
	By FirstName  =By.xpath("//*[@name='firstName']");
	By LastName   =By.xpath("//*[@name='lastName']");
	By Phone      =By.xpath("//*[@name='phone']");
	By Email      =By.xpath("//*[@id='userName']");
	By Address1   =By.xpath("//*[@name='address1']");
	By City       =By.xpath("//*[@name='city']");
	By State      =By.xpath("//*[@name='state']");
	By PostalCode =By.xpath("//*[@name='postalCode']");
	By Country    =By.xpath("//*[@name='country']");
	By UserName   =By.xpath("//*[@name='email']");
	By Password   =By.xpath("//*[@name='password']");
	By ConfirmPassword =By.xpath("//*[@name='confirmPassword']");
	By Submit     =By.xpath("//*[@name='submit']");
	
	
	
	
	
	public Register(WebDriver driver)
	{
	
		this.driver=driver;
		this.wait=new WebDriverWait(driver, Duration.ofSeconds(30));
	}
	
	public void registerlink()
	{
		wait.until(ExpectedConditions.presenceOfElementLocated(Register)).click();
		logger.info("[Clicked on REGISTER link]");		
	}
	
	public void reg_contactInfo(String fName,String lName,String phonenumber,String email)
	{
		wait.until(ExpectedConditions.presenceOfElementLocated(FirstName)).sendKeys(fName);
		wait.until(ExpectedConditions.presenceOfElementLocated(LastName)).sendKeys(lName);
		wait.until(ExpectedConditions.presenceOfElementLocated(Phone)).sendKeys(phonenumber);
		wait.until(ExpectedConditions.presenceOfElementLocated(Email)).sendKeys(email);
		logger.info("[Entered contact info: {}, {}, {}, {}]", fName, lName, phonenumber, email);
	}

	
	public void reg_mailingInfo(String address,String city,String state,String postalCode)
	{
		wait.until(ExpectedConditions.presenceOfElementLocated(Address1)).sendKeys(address);
		wait.until(ExpectedConditions.presenceOfElementLocated(City)).sendKeys(city);
		wait.until(ExpectedConditions.presenceOfElementLocated(State)).sendKeys(state);
		wait.until(ExpectedConditions.presenceOfElementLocated(PostalCode)).sendKeys(postalCode);

		
		WebElement countryElement = wait.until(ExpectedConditions.presenceOfElementLocated(Country));
		Select country_dropdownobj = new Select(countryElement);
		country_dropdownobj.selectByVisibleText("INDIA");
		logger.info("[Entered mailing info: {}, {}, {}, {}, {}]", address, city, state, postalCode, "INDIA");
		
		
	}

	
	public void reg_userInfo(String username,String password,String confirmpassword)
	{
		wait.until(ExpectedConditions.presenceOfElementLocated(UserName)).sendKeys(username);
		wait.until(ExpectedConditions.presenceOfElementLocated(Password)).sendKeys(password);
		wait.until(ExpectedConditions.presenceOfElementLocated(ConfirmPassword)).sendKeys(confirmpassword);
		logger.info("[Entered User info]" );
		
	}

	public void submit()
	{
		wait.until(ExpectedConditions.presenceOfElementLocated(Submit)).click();
		logger.info("[Registration Submitted]");
	}
	
	
	

}
