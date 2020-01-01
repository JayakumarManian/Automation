package com.selenium.examples;

import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NetbankingTest {

	private WebDriver driver;
	JavascriptExecutor js;

	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
		new HashMap<String, Object>();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	  @Test
	  public void addnewcustomer() {
	    driver.get("http://localhost/net-banking/admin_login.php");
	    driver.findElement(By.name("admin_uname")).sendKeys("admin");
	    driver.findElement(By.name("admin_psw")).sendKeys("password123");
	    driver.findElement(By.name("admin_uname")).click();
	    driver.findElement(By.name("admin_psw")).click();
	    driver.findElement(By.cssSelector(".flex-item:nth-child(1) > button")).click();
	    driver.findElement(By.linkText("Add Customer")).click();
	    driver.findElement(By.name("fname")).click();
	    driver.findElement(By.name("fname")).sendKeys("jayakumar");
	    driver.findElement(By.name("lname")).click();
	    driver.findElement(By.name("lname")).sendKeys("manian");
	    driver.findElement(By.name("dob")).click();
	    driver.findElement(By.name("dob")).sendKeys("1999-05-18");
	    driver.findElement(By.id("radio-label")).click();
	    driver.findElement(By.name("aadhar")).click();
	    driver.findElement(By.name("aadhar")).sendKeys("8745632894");
	    driver.findElement(By.name("email")).click();
	    driver.findElement(By.name("email")).sendKeys("jayakumar.manian@gmail.com");
	    driver.findElement(By.name("phno")).click();
	    driver.findElement(By.name("phno")).sendKeys("9791444078");
	    driver.findElement(By.name("address")).click();
	    driver.findElement(By.name("address")).sendKeys("CSS Corp, MPEZ, Chennai");
	    driver.findElement(By.name("branch")).click();
	    {
	      WebElement dropdown = driver.findElement(By.name("branch"));
	      dropdown.findElement(By.xpath("//option[. = 'New York']")).click();
	    }
	    driver.findElement(By.name("branch")).click();
	    driver.findElement(By.name("acno")).click();
	    driver.findElement(By.name("acno")).sendKeys("246789046");
	    driver.findElement(By.name("o_balance")).click();
	    driver.findElement(By.name("o_balance")).sendKeys("7346439724");
	    driver.findElement(By.name("pin")).click();
	    driver.findElement(By.name("pin")).sendKeys("4466");
	    driver.findElement(By.name("cus_uname")).click();
	    driver.findElement(By.name("cus_uname")).sendKeys("jayakumar");
	    driver.findElement(By.name("cus_pwd")).click();
	    driver.findElement(By.name("cus_pwd")).sendKeys("Mjk@bank078");
	    driver.findElement(By.cssSelector(".container:nth-child(1) > button")).click();
	    Assert.assertEquals(driver.findElement(By.id("info")).getText(), "Customer created successfully !");
	    Assert.assertEquals(driver.findElement(By.cssSelector(".flex-item:nth-child(2) > #info")).getText(), "Passbook created successfully !");
	    Assert.assertEquals(driver.findElement(By.cssSelector(".flex-item:nth-child(3) > #info")).getText(), "Passbook updated successfully !");
	    Assert.assertEquals(driver.findElement(By.cssSelector(".flex-item:nth-child(4) > #info")).getText(), "Beneficiary created successfully !");
	    driver.findElement(By.id("logout")).click();
	    Assert.assertEquals(driver.switchTo().alert().getText(), "Are you sure?");
	    driver.switchTo().alert().accept();
	  }
}
