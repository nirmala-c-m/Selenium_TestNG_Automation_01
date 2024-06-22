package com.guru99.newtours.tests;

import org.testng.annotations.Test;
import com.guru99.newtours.utility.*;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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

	@BeforeTest
	@Parameters("browser")
	public void setUp(@Optional("chrome") String browser) {

		logger.info("Browser parameter received: " + browser);

		switch (browser.toLowerCase()) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver","/home/nirmala/Eclipse2021/newtours-test-application/drivers/chromedriver");
			/*
			 * ChromeOptions options = new ChromeOptions();
			 * options.addArguments("--headless"); // Run in headless mode driver = new
			 * ChromeDriver(options);
			 */
            
            driver = new ChromeDriver();
			logger.info("Chrome browser is set up.");
			break;

		case "edge":
			System.setProperty("webdriver.edge.driver",
					"/home/nirmala/Eclipse2021/newtours-test-application/drivers/msedgedriver");
			driver = new EdgeDriver();
			logger.info("Edge browser is set up.");
			break;

		/*
		 * case "firefox": System.setProperty("webdriver.gecko.driver",
		 * "/home/nirmala/Eclipse2021/newtours-test-application/drivers/geckodriver");
		 * driver = new FirefoxDriver(); logger.info("Firefox browser is set up.");
		 * break;
		 */

		default:

			System.out.println("No Such Browser found");
		}

		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		TestListener.setDriver(driver);
		driver.get("https://demo.guru99.com/test/newtours/");
		driver.manage().window().maximize();
		logger.info("Browser opened and navigated to demo.guru99.com");
	}

	@Test(priority = 0, dataProvider = "loginData", groups = { "sanity", "smoke" },retryAnalyzer = RetryAnalyzer.class)

	public void login(String username, String password) {
		loginObj = new Login(driver);
		loginObj.username(username);
		loginObj.password(password);
		loginObj.submit();
		logger.info("[Login test executed with username: {} and password: {}]", username, password);

		loginObj.verifyLogin();
	}

	@Test(priority = 1, dataProvider = "registerData", groups = { "smoke" },retryAnalyzer = RetryAnalyzer.class)

	public void register(String firstName, String lastName, String phone, String email, String address, String city,
			String state, String postalCode, String username, String password, String confirmPassword) {
		registerObj = new Register(driver);
		registerObj.registerlink();
		registerObj.reg_contactInfo(firstName, lastName, phone, email);
		registerObj.reg_mailingInfo(address, city, state, postalCode);
		registerObj.reg_userInfo(username, password, confirmPassword);
		registerObj.submit();
		logger.info("[Registration test executed with data: {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}]", firstName,
				lastName, phone, email, address, city, state, postalCode, username, password, confirmPassword);
		registerObj.verifyRegistration();
	}

	@DataProvider(name = "loginData")
	public Object[][] loginData() {
		return ExcelReader.readTestData("/home/nirmala/testdata.xlsx", "LoginData");
	}

	@DataProvider(name = "registerData")
	public Object[][] registerData() {
		return ExcelReader.readTestData("/home/nirmala/testdata.xlsx", "RegisterData");
	}

	@AfterTest
	public void tearDown() {
		if (driver != null)
			driver.quit();
		logger.info("[Browser closed]");
	}

}
