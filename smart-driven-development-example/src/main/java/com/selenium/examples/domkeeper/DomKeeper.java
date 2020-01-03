package com.selenium.examples.domkeeper;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

public class DomKeeper {
	static WebDriver driver;
	private String basePath;

	// TODO Auto-generated method stub

	By userName = By.xpath("//button");
	WebElement user_name;
	String baseURL = "http://localhost";
	

	public DomKeeper() throws URISyntaxException {
		System.setProperty("webdriver.chrome.driver", "G:\\Jayakumar\\Selenium\\chromedriver.exe");
		 URL resourceFolderURL = this.getClass().getClassLoader().getResource("images");
	     basePath = resourceFolderURL.toURI().getPath() + "/";
		driver = new HtmlUnitDriver(BrowserVersion.CHROME);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(baseURL + "/net-banking/admin_login.php");
		driver.manage().window().maximize();
	}

	private void startTest() {
		user_name = driver.findElement(userName);
		JSONParser jparser = new JSONParser();
		//Object obj = jparser.parse(in)   

		Boolean isPresent = driver.findElements(userName).size() >= 0;
		// System.out.println(isPresent+ user_name.getAttribute("name"));

		if (isPresent == true && isAttribtuePresent(user_name, "name")
				&& user_name.getAttribute("name").equals("login")) {
			System.out.println("\n Healthy - Element Exists");
		} else if (isPresent && isAttribtuePresent(user_name, "name")
				&& !user_name.getAttribute("name").equals("login")) {
			System.out.println("\n Fixed - Changed property element tag name is '" + user_name.getTagName()
					+ "' and attribute value 'login' to '" + user_name.getAttribute("name") + "', Page URL :"
					+ driver.getCurrentUrl());
		} else {
			System.out.println("\n Critical - Element not present!!!");
		}
	}

	private void quit() {
		driver.close();
		driver.quit();
	}

	private static boolean isAttribtuePresent(WebElement element, String attribute) {
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

	public static void main(String[] args) throws URISyntaxException {
		DomKeeper dk = new DomKeeper();
		dk.startTest();
		dk.quit();
	}
}


// System.out.println("Id of the button is:- " + user_name.getAttribute("id"));
// System.out.println("Link of the button is:- " +
// user_name.getAttribute("href"));
// System.out.println("Class of the button is:- " +
// user_name.getAttribute("class"));
// System.out.println("CSS Value :- "+ user_name.getCssValue("class"));
