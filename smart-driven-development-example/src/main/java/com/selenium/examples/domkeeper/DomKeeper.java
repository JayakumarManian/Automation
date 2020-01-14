package com.selenium.examples.domkeeper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DomKeeper {
	static WebDriver driver;

	// TODO Auto-generated method stub

	By userName = By.xpath("//button");
	WebElement user_name;
	public static String configFilePath, url, multiObj, localObj ;


	public DomKeeper() throws FileNotFoundException, IOException { // Initial Setup
		
		
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
		options.addArguments("window-size=1200x600");
		driver = new ChromeDriver(options);
		//driver = new HtmlUnitDriver(BrowserVersion.CHROME,true);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(url);
		driver.manage().window().maximize();
	}

	private void startTest(String url,String multiObj, String localObj) throws Exception { //Domkeeper - Finding element new/changed attribute value 
		
		user_name = highlight(By.xpath(multiObj));
		
		Boolean isPresent = driver.findElements(userName).size() >= 0;

		if (isPresent == true && isAttribtuePresent(user_name, "name") && user_name.getAttribute("name").equals(propValue)) {
			System.out.println("\n Healthy - Element is Exists");
		} 
		
		else if (isPresent && isAttribtuePresent(user_name, "name") && !user_name.getAttribute("name").equals(propValue)) {
			
			takeSnapShot(driver,"D:\\"+ user_name.getTagName()+ ".png");
			System.out.println("\n Fixed - Changed property element tag name is '" + user_name.getTagName()
					+ "' and attribute value 'login' to '" + user_name.getAttribute("name") + "', Page URL :"
					+ driver.getCurrentUrl());
		} 
		
		else {
			System.out.println("\n Critical - Element not present!!!");
		}
	}

	private Boolean getNameValue(WebElement element,String propValue) {
		if(element.getAttribute("name").equals(propValue)) {return true;}
		else {return false;}	
	}	
	
	private Boolean getIdValue(WebElement element, String propValue) {
		if(element.getAttribute("id").equals(propValue)) {return true;}
		else {return false;}
	}	
	
	private void quit() { // Close the all active window
		driver.close();
		driver.quit();
	}

	private boolean isAttribtuePresent(WebElement element, String attribute) { // To verify element is present or not																				// not
		Boolean result = false;
		try {
			String value = element.getAttribute(attribute);
			if (value != null && !value.isEmpty()) {
				result = true;
			}
		} catch (Exception e) {
		}
		return result;
	}
	
	public WebElement highlight(By by) {	// Draw a border around the found element
		WebElement elem = driver.findElement(by);
		if (driver instanceof JavascriptExecutor) {
			((JavascriptExecutor) driver).executeScript("arguments[0].style.border='5px solid red'", elem);
		}
		return elem;
	}

	public void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {	// Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(fileWithPath);
		FileUtils.copyFile(SrcFile, DestFile);
	}
	
	public static void main(String[] args) throws Exception {  // Main
		configFilePath="D:\\Jayakumar\\Play-Groud\\eclipse-workspace\\smart-driven-development-example\\src\\main\\java\\com\\selenium\\examples\\domkeeper\\config.properties";
		Properties properties = new Properties();
		properties.load(new FileInputStream(configFilePath));
		url = properties.getProperty("url");
		multiObj = properties.getProperty("multiObj");
		localObj = properties.getProperty("localObj");
		DomKeeper dk = new DomKeeper();
		dk.startTest(url,multiObj,localObj);
		dk.quit();
	}
}




