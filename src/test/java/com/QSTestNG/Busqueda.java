package com.QSTestNG;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

public class Busqueda {

	WebDriver d;
	By searchBoxLocator = By.id("search_query_top");
	By resultLocator = By.cssSelector("span.heading-counter");////div[@class="left-block"]

	@BeforeMethod
	public void beforeMethod() {
	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver/chromedriver.exe");
		d = new ChromeDriver();
		d.manage().window().maximize();
		d.get("http://automationpractice.com/index.php");
	}

	@Test
	public void Busqueda() {
		try {
			
			String sBL = d.findElement(searchBoxLocator).getAttribute("id");
			if(sBL.contentEquals("search_query_top")) {
				File scrFile = d.findElement(searchBoxLocator).getScreenshotAs(OutputType.FILE);
				FileHandler.copy(scrFile, new File(System.getProperty("user.dir") + "\\cargaHomeBuscador.png"));
			}else {
				System.out.println("No se ha encontrado el objeto....");
				d.quit();
			}
			
			WebElement searchbox = d.findElement(searchBoxLocator);
			searchbox.clear();
			searchbox.sendKeys("blouse");
			searchbox.submit();
			
			WebDriverWait w = new WebDriverWait(d, 7);
			w.until(ExpectedConditions.presenceOfElementLocated(resultLocator));

//			System.out.println("Numero de Resultado " + d.findElement(resultLocator).getText());
			String result = d.findElement(resultLocator).getText().toString();
			assertTrue(result.trim().equals("1 result has been found."), "No se ha encontrado el objeto....");
			File srcFile = d.findElement(resultLocator).getScreenshotAs(OutputType.FILE);
			FileHandler.copy(srcFile, new File(System.getProperty("user.dir") + "\\busqueda.png"));
		}catch(Exception e) {
			System.out.println("ERROR en test: "+e);
		}
	}

	@AfterClass
	public void afterClass() {
		d.quit();
	}
}
