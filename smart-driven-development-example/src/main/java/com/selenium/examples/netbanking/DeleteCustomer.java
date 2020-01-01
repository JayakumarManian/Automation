package com.selenium.examples.netbanking;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

public class DeleteCustomer {
	private WebDriver driver;
	JavascriptExecutor js;

	@Test
	public void deleteCustomer() throws InterruptedException {
		driver.get("http://localhost/net-banking/admin_login.php");
		driver.manage().window().setSize(new Dimension(1050, 728));
		driver.findElement(By.name("admin_uname")).sendKeys("admin");
		driver.findElement(By.name("admin_psw")).sendKeys("password123");
		driver.findElement(By.cssSelector(".flex-item:nth-child(1) > button")).click();
		driver.findElement(By.linkText("Manage Customers")).click();
		driver.findElement(By.cssSelector(".flex-item:nth-child(5) .dropbtn")).click();
		driver.findElement(By.linkText("Delete")).click();
		Assert.assertEquals(driver.findElement(By.id("info")).getText(), "Customer Deleted Successfully !");
		Assert.assertEquals(driver.findElement(By.cssSelector(".flex-item:nth-child(2) > #info")).getText(),
				"Customer\\\'s Passbook Deleted Successfully !");
		Assert.assertEquals(driver.findElement(By.cssSelector(".flex-item:nth-child(3) > #info")).getText(),
				"Customer\\\'s Beneficiary Deleted Successfully !");
		
		try {
			driver.findElement(By.id("logout")).click();
		} catch (UnhandledAlertException f) {
		    try {
		        Alert alert = driver.switchTo().alert();
		        String alertText = alert.getText();
		        System.out.println("Alert data: " + alertText);
		        alert.accept();
		    } catch (NoAlertPresentException e) {
		        e.printStackTrace();
		    }
		}
//		System.out.println(driver.switchTo().alert().getText());
//		Assert.assertEquals(driver.switchTo().alert().getText(),"Are you sure?");
//	    driver.switchTo().alert().accept();
	}

	@BeforeMethod
	public void setBaseURL() {
		System.setProperty("webdriver.chrome.driver", "G:\\Jayakumar\\Selenium\\chromedriver.exe");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS", true);
		capabilities.setCapability("ACCEPT_SSL_CERTS", true);
		capabilities.setCapability("SUPPORTS_ALERTS", true);
		capabilities.setCapability("UNEXPECTED_ALERT_BEHAVIOR", true);
		capabilities.setCapability("IE_ENSURE_CLEAN_SESSION", true);
		capabilities.setCapability("ENABLE_ELEMENT_CACHE_CLEANUP", true);
		capabilities.setCapability("nativeEvents", false);
		capabilities.setCapability("requireWindowFocus", false);
		capabilities.setJavascriptEnabled(true);
		capabilities.setCapability("ignoreProtectedModeSettings", true);
		ChromeOptions options = new ChromeOptions();
		options.merge(capabilities);
		driver = new ChromeDriver(options);
	}

	@AfterMethod
	public void tearDown() {
		//driver.close();
		driver.quit();
	}

}
