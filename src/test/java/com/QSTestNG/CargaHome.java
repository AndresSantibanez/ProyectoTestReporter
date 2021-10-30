package com.QSTestNG;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class CargaHome {

	WebDriver d;
	private String URL = "http://automationpractice.com/index.php";
	By menuPestanasLocator = By.id("block_top_menu");
	By searchBoxLocator = By.id("search_query_top");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver/chromedriver.exe");
		d = new ChromeDriver();
		d.manage().window().maximize();
		d.get(URL);
	}

	@Test
	public void cargaHome() {
		try {
			String sBL = d.findElement(searchBoxLocator).getAttribute("id");
			if(sBL.contentEquals("search_query_top")) {
				File scrFile = d.findElement(searchBoxLocator).getScreenshotAs(OutputType.FILE);
				FileHandler.copy(scrFile, new File(System.getProperty("user.dir") + "\\cargaHomeBuscador.png"));
			}else {
				System.out.println("No se ha encontrado el objeto....");
				d.quit();
			}

			WebDriverWait w = new WebDriverWait(d, 7);
			w.until(ExpectedConditions.presenceOfElementLocated(searchBoxLocator));
			
			String mPL = d.findElement(menuPestanasLocator).getText().toString();
			if(mPL.contains("WOMEN")) {
				System.out.println("menuPestanas Pass");
				File scrFile1 = d.findElement(menuPestanasLocator).getScreenshotAs(OutputType.FILE);
				FileHandler.copy(scrFile1, new File(System.getProperty("user.dir") + "\\cargaHomePestaña.png"));
			}else {
				System.out.println("No se ha encontrado el objeto....");
			}
//			assertTrue(result.trim().contains("WOMEN"), "No se ha encontrado el objeto....");
		} catch (IOException e) {
			System.out.println("Error en test: "+e);
		}
	}

	@AfterClass
	public void afterClass() {
		d.quit();
	}
}
