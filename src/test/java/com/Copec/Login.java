package com.Copec;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertTrue;

import java.io.File;

//import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterClass;

public class Login {

	WebDriver d;
	private String URL = "https://www.falabella.com/falabella-cl";
	By homeLocator = By.xpath("//*[@id=\"testId-UserAction-userinfo\"]/div/div[2]");
	By loginLocator = By.id("testId-loggedout-item-0");
	By mailLocator = By.id("testId-cc-login-form-email-input");
	By passLocator = By.id("testId-cc-login-form-password-input");
	

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver/chromedriver.exe");
		d = new ChromeDriver();
		d.manage().window().maximize();
		d.get(URL);
	}

	@Test
	public void Login() {
		try {
			WebElement welcomeLogin = d.findElement(homeLocator);
			welcomeLogin.click();
			
			WebElement inSesion = d.findElement(loginLocator);
			inSesion.click();
			Thread.sleep(2000);
			
			WebElement m = d.findElement(mailLocator);
			if(m.isDisplayed()) {
				assertTrue(true, "elemento usuario encontrado....");
				m.sendKeys("user@gmail.com");
				File scrFile = d.findElement(mailLocator).getScreenshotAs(OutputType.FILE);
				FileHandler.copy(scrFile, new File(System.getProperty("user.dir") + "\\mailLogin.png"));
				Thread.sleep(2000);
			}else {
				assertTrue(false, "elemento usuario no encontrado....");
				d.quit();
			}
			
			WebElement p = d.findElement(passLocator);
			if(p.isDisplayed()) {
				assertTrue(true, "elemento password encontrado....");
				p.sendKeys("123123");
				File scrFile = d.findElement(passLocator).getScreenshotAs(OutputType.FILE);
				FileHandler.copy(scrFile, new File(System.getProperty("user.dir") + "\\passwordLogin.png"));
				Thread.sleep(2000);
			}else {
				assertTrue(false, "elemento password no encontrado....");
				d.quit();
			}
		}catch(Exception e) {
			System.out.println("ERROR en test: "+e);
		}
	}

	@AfterClass
	public void afterClass() {
		//cierre navegador
		d.quit();
	}
}
