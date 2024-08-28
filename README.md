# Automation Framework for E2E Testing with Selenium

* This is a boilerplate framework designed to help you write and execute end-to-end (E2E) automation tests using Selenium. <br>
* The framework is built with TestNG and incorporates various utilities such as Excel data reading, custom test listeners, and extend reports.<br>
* It supports parallel and cross-browser execution, along with grouping and retry mechanisms for failed tests.


### Test application
https://demo.guru99.com/test/newtours/

## Prerequisites
 **Test Application**
Before running the tests, ensure you have the test application set up. Clone the application from the following repository:
  
## Plugins Needed
* TestNG
* Maven
  
## 1. Locally
Run the tests directly from your IDE using the TestNG.xml file.

## 2. Through Maven
You can execute tests via Maven using the following commands:<br>


Execute all tests:
	```
mvn clean test
	```<br>

  Execute specific groups:
	```
mvn test -Dgroups="smoke"
	```<br>

 
 Pass browser type and execution type from Maven CLI:
	```
mvn test -Dbrowser=firefox -DexecType=local
	```<br>
 

 ## 3. Through TestNG XML
Configure and run tests via the testng.xml file for more granular control over parallelism, grouping, and browser configurations.


## 4. Through the TestRunner Class
The test execution starts from the runners.TestRunner.java class. Tests to be executed are defined in the TestNG.xml and identified by the appropriate group names.

## Test Execution Configurations
* Parallel and Cross-Browser Execution
* Execution mode (parallel or sequential) is configured in the testng.xml file. Set the parallel attribute to methods, tests, or classes for parallel execution.
* The number of parallel threads is also specified in testng.xml. The default value is 2.

## Retry Analyzer
* Failed tests can be automatically retried using the custom RetryAnalyzer class.

## Grouping
* Tests can be grouped using the groups attribute in the testng.xml file. This allows you to run specific categories of tests (e.g., smoke, regression).

## Test Case Creation
* Test Case Definition-Test cases are defined in the src/test/java/tests directory. Each test class corresponds to a specific feature or module of the application.

## Test data
* Test data is managed through Excel files located in the src/test/resources/testdata directory. The Excel data is read using a custom ExcelReader utility.
  
 ## Test Reporting
 * Extend Reports-Test reports are generated using Extend Reports, providing detailed insights into test execution, including screenshots for failed steps.
  
## Logs
 * Logging is implemented using slf4j integrated with Log4j, allowing easy switching of logging frameworks if required.
 
 ## Configuration
* Browser Configuration-Browsers (e.g., Chrome, Firefox) are managed through a custom BrowserFactory class. Browser types and execution modes are configurable via the Maven CLI or testng.xml.

 ## Parallel Execution and Test Context
 * The framework supports parallel execution by maintaining isolated browser instances and test data per thread using ThreadLocal.

 ## Excel Data Reading
 * Test data is provided in Excel files and read using the ExcelReader utility. This allows for dynamic data-driven testing.


 ## Advanced Features
* Retry Mechanism: Retry failed tests automatically using RetryAnalyzer.
* Parallel Execution: Run tests in parallel, with the ability to control thread count via TestNG.
* Cross-Browser Testing: Execute tests across multiple browsers using the BrowserFactory.
* Custom Test Listeners: Implement custom listeners for enhanced reporting and logging.
* Action and Wait Helpers: Helper classes to simplify Selenium actions and manage waits efficiently.

 ## Feature Enhancements
 **Some additional features that could be added in the future:**

* Dockerize the Framework: Containerize the test execution environment using Docker.
* Database Connection: Implement a singleton pattern for database connections.
* Slack Integration: Integrate with Slack for real-time test execution notifications.
* BrowserStack/Sauce Labs Integration: Add support for cloud-based cross-browser testing services.
* AWS Integration: Integrate with AWS for scaling test execution environments.
* Artifactory Integration: Set up a Maven repository manager for managing framework dependencies.



