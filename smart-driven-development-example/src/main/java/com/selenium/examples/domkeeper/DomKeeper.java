package com.selenium.examples.domkeeper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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

	WebElement target;
	public static String configFilePath, url, multiObj, localObj;

	public DomKeeper() throws FileNotFoundException, IOException { // Initial Setup
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
		options.addArguments("window-size=1200x600");
		driver = new ChromeDriver(options);
		// driver = new HtmlUnitDriver(BrowserVersion.CHROME,true);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(url);
		driver.manage().window().maximize();
	}

	@SuppressWarnings("unchecked")
	private void startTest(String url, String multiObj, String localObj) throws Exception { // Domkeeper - Finding
																							// element new/changed
																							// attribute value
		
		target = highlight(By.xpath(multiObj));
		String propType;
		String propValue;
		String actualPropValue;
		JSONObject healthy = null,fixed = null,critical = null;

		Boolean isPresent = driver.findElements(By.xpath(multiObj)).size() >= 0;
	
			propType = getWebElement(localObj)[0];
			propValue = getWebElement(localObj)[1].replaceAll("^\"|\"$", "");
			actualPropValue = target.getAttribute(propType);

			if (isPresent && isAttribtuePresent(target, getWebElement(localObj)[0])
					&& actualPropValue.equals(propValue)) {
				healthy = new JSONObject();
				healthy.put("tagName", target.getTagName());
				healthy.put("propertyType", propType);
				healthy.put("propertyValue", propValue);
				healthy.put("status", "healthy");
				healthy.put("status_code", "0");
				System.out.println("\n Healthy - Element is Exists");
			}

			else if (isPresent && isAttribtuePresent(target, propType) && !actualPropValue.equals(propValue)) {
				takeSnapShot(driver, "D:\\" + target.getTagName() + ".png");
				System.out.println("\n Fixed - Changed property element tag name is '" + target.getTagName()
						+ "' and attribute value '" + propValue + "' to '" + target.getAttribute(propType)
						+ "', Page URL :" + driver.getCurrentUrl());
				fixed = new JSONObject();
				fixed.put("tagName", target.getTagName());
				fixed.put("propertyType", propType);
				fixed.put("propertyValue", propValue);
				fixed.put("status", "fixed");
				fixed.put("status_code", "1");
			}

			else {
				critical = new JSONObject();
				critical.put("status", "critical");
				critical.put("status_code", "2");
				critical.put("message", "Element not present");
				System.out.println("\n Critical - Element not present!!!");
				
			}
			
			 //Add status to dom keeper list
	        JSONArray dk = new JSONArray();
	        dk.add(healthy);
	        dk.add(fixed);
	        dk.add(critical);
	        report(dk);
	        
			
		}
	
	private void report(JSONArray employeeList) {
		 //Write JSON file
        try (FileWriter file = new FileWriter("domKeeper.json")) {
 
            file.write(employeeList.toJSONString());
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	private String[] getWebElement(String objName) throws InterruptedException {
		String obj[];
		if (objName.startsWith("id")) {
			obj = objName.split("=");
			return obj;
		} else if (objName.startsWith("name")) {
			obj = objName.split("=");
			return obj;
		}else if (objName.startsWith("linkText")) {
			obj = objName.split("=");
			return obj;
		}
		return null;
	}

	private void quit() { // Close the all active window
		driver.close();
		driver.quit();
	}

	private boolean isAttribtuePresent(WebElement element, String attribute) { // To verify element is present or not //
																				// not
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

	public WebElement highlight(By by) { // Draw a border around the found element
		WebElement elem = driver.findElement(by);
		if (driver instanceof JavascriptExecutor) {
			((JavascriptExecutor) driver).executeScript("arguments[0].style.border='5px solid red'", elem);
		}
		return elem;
	}

	public void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception { // Convert web driver object
																							// to TakeScreenshot
		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(fileWithPath);
		FileUtils.copyFile(SrcFile, DestFile);
	}

	public static void main(String[] args) throws Exception { // Main
		configFilePath = "D:\\Jayakumar\\Play-Groud\\eclipse-workspace\\smart-driven-development-example\\src\\main\\java\\com\\selenium\\examples\\domkeeper\\config.properties";
		Properties properties = new Properties();
		properties.load(new FileInputStream(configFilePath));
		url = properties.getProperty("url");
		multiObj = properties.getProperty("multiObj");
		localObj = properties.getProperty("localObj");
		DomKeeper dk = new DomKeeper();
		dk.startTest(url, multiObj, localObj);
		dk.quit();
	}
}
