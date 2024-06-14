package com.guru99.newtours.tests;

import org.testng.annotations.Test;
import com.guru99.newtours.utility.*;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;

public class TestBase {
    private static final Logger logger = LoggerFactory.getLogger(TestBase.class);

    public WebDriver driver;
    public WebDriverWait wait;
    public Login loginObj;
    public Register registerObj;


	


	@BeforeSuite
	@Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
		System.setProperty("webdriver.chrome.driver", "/home/nirmala/JavaProj/newtours-test-application/drivers/chromedriver");
        driver = new ChromeDriver();
	
//	
//		if(browser.equalsIgnoreCase("chrome")) {
//            System.setProperty("webdriver.chrome.driver", "/home/nirmala/JavaProj/newtours-test-application/drivers/chromedriver");
//            driver = new ChromeDriver();
//        } else if(browser.equalsIgnoreCase("firefox")) {
//            System.setProperty("webdriver.gecko.driver", "/home/nirmala/JavaProj/newtours-test-application/drivers/geckodriver");
//            driver = new FirefoxDriver();
//        }
//            else if(browser.equalsIgnoreCase("edge")) {
//            System.setProperty("webdriver.edge.driver", "/home/nirmala/JavaProj/newtours-test-application/drivers/msedgedriver");
//            driver = new EdgeDriver();
//        }
		
//		
//        System.setProperty("webdriver.chrome.driver", "/home/nirmala/JavaProj/newtours-test-application/drivers/chromedriver");
//        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        TestListener.setDriver(driver);    // Set WebDriver instance in TestListener
        driver.get("https://demo.guru99.com/test/newtours/");
        driver.manage().window().maximize();
        logger.info("[Browser opened and navigated to demo.guru99.com]");
    }

    @Test(priority = 0, dataProvider = "loginData")
   
    public void login(String username, String password) {
        loginObj = new Login(driver);
        loginObj.username(username);
        loginObj.password(password);
        loginObj.submit();
        logger.info("[Login test executed with username: {} and password: {}]", username, password);
    }

    @Test(priority = 1, dataProvider = "registerData")
    
    public void register(String firstName, String lastName, String phone, String email, String address, String city, String state, String postalCode, String username, String password, String confirmPassword) {
        registerObj = new Register(driver);
        registerObj.registerlink();
        registerObj.reg_contactInfo(firstName, lastName, phone, email);
        registerObj.reg_mailingInfo(address, city, state, postalCode);
        registerObj.reg_userInfo(username, password, confirmPassword);
        registerObj.submit();
        logger.info("[Registration test executed with data: {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}]", firstName, lastName, phone, email, address, city, state, postalCode, username, password, confirmPassword);
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return ExcelReader.readTestData("/home/nirmala/testdata.xlsx", "LoginData");
    }

    @DataProvider(name = "registerData")
    public Object[][] registerData() {
        return ExcelReader.readTestData("/home/nirmala/testdata.xlsx", "RegisterData");
    }


	@AfterSuite
    public void tearDown() {
        if (driver != null)
            driver.quit();
        logger.info("[Browser closed]");
    }
}
