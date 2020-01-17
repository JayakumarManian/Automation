package com.selenium.examples.domkeeper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DomKeeper {
	static WebDriver driver;

	// TODO Auto-generated method stub

	WebElement target;
	public static String configFilePath, url,screenShot, multiRepo, localRepo, multiObj, localObj;

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
		driver.findElement(By.name("admin_uname")).sendKeys("admin");
		driver.findElement(By.name("admin_psw")).sendKeys("password123");
		driver.findElement(By.id("login")).click();
		driver.findElement(By.linkText("Add Customer")).click();
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private Map<String, String> startTest(String url, String multiObj, String localObj, String screenShot_path) throws Exception { // Domkeeper - Finding
																							// element new/changed																						// attribute value
		
		target = highlight(By.xpath(multiObj));
		String propType;
		String propValue;
		String actualPropValue,path;
		Map<String,String> status = new HashMap<String,String>();

		Boolean isPresent = driver.findElements(By.xpath(multiObj)).size() >= 0;
		if(localObj.isEmpty() || localObj == null) {
			localObj = "id=default";
		}
			propType = getWebElement(localObj)[0];
			propValue = getWebElement(localObj)[1].replaceAll("^\"|\"$", "");
			actualPropValue = target.getAttribute(propType);
		/* Getting link text */
			if(propType.equals("linkText")) {
				actualPropValue = driver.findElement(By.linkText(propValue)).getText();
			}
			
			System.out.println("propType |||| "+propType+propValue+",propValue |||| "+",actualPropValue |||| "+actualPropValue+"link Text Verify |||| "+actualPropValue.equals(propValue));

			if (isPresent && isAttribtuePresent(target, getWebElement(localObj)[0])
					&& actualPropValue.equals(propValue)) {
				status.put("status", "healthy");
				status.put("message", "Element is Exists");
				status.put("status_code", "0");
				System.out.println("\n Healthy - Element is Exists");
				return status; 
			}

			else if (isPresent && isAttribtuePresent(target, propType) && !actualPropValue.equals(propValue)) {
				takeSnapShot(driver, screenShot_path + target.getTagName()+"_" +propValue+ ".png");
				
				System.out.println("\n Fixed - Changed property element tag element name is '" + target.getTagName()
						+ "' and attribute value '" + actualPropValue  + "' to '" + propValue
						+ "', Page URL :" + driver.getCurrentUrl());
				
				status.put("tagName", target.getTagName());
				status.put("propertyType", propType);
				status.put("propertyValue", propValue);
				status.put("message","The '"+ target.getTagName()+"' tag '"+propType+"' attribute value is changed '"+actualPropValue+ "' to '" +propValue+"'");
				status.put("path", screenShot_path + target.getTagName() +"_" +propValue+ ".png");
				status.put("status", "fixed");
				status.put("status_code", "1");
				return status;
			}

			else {
				status.put("status", "critical");
				status.put("message", "Element not present!!!");
				status.put("status_code", "2");
				System.out.println("\n Critical - Element not present!!!");
				return status;
			}
		}
	
	/*
	 * private void report(JSONArray statusList) { //Write JSON file try (FileWriter
	 * file = new FileWriter("domKeeper.json")) {
	 * 
	 * file.write(statusList.toJSONString()); file.flush();
	 * 
	 * } catch (IOException e) { e.printStackTrace(); } }
	 */

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
		String value;
		try {
			value = element.getAttribute(attribute);
			if(attribute.equals("linkText")) {
				value = "byDefaultTrue";
			}
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
		localRepo = properties.getProperty("localRepo");
		multiRepo = properties.getProperty("multiRepo");
		screenShot = properties.getProperty("screenShot");
		
		/*Reading xpath position from multi object repository*/
		DomKeeper dk = new DomKeeper(); ;
		RepositoryReader repositoryReader = new RepositoryReader();
		Map<String, String> mr = repositoryReader.getXpath(multiRepo);
			
		/* Reading primary execution target from local repository */ 
		Map<String, String> lr = repositoryReader.getExecutionTarget(localRepo);
		Map<String,Object> status = new HashMap<String,Object>();
		for(Map.Entry<String, String> entry : lr.entrySet()) {
			multiObj = mr.get(entry.getKey()).split("=")[1];
			localObj = entry.getValue();
			System.out.println(multiObj+"::"+localObj);	
			Map<String, String> report= dk.startTest(url, multiObj, localObj, screenShot);
			status.put(entry.getKey(), report);
			}
		status.put("project_id", "");
		status.put("project_name", "");
		status.put("version", "1.0");
		status.put("date", LocalDateTime.now());
		status.put("object_count", lr.size());
		Object[] objectArray = status.entrySet().toArray();
		Arrays.toString(objectArray);
		ObjectMapper mapper = new ObjectMapper();
		 ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
		 writer.writeValue(new File("D:/jayakumar/domkeeper.json"), objectArray);
		 System.out.println("--Done--");
		dk.quit();
	}
}
