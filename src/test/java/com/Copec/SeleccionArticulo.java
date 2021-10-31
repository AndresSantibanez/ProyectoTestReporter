package com.QSTestNG;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

public class SeleccionArticulo {

	WebDriver d;
	By searchBoxLocator = By.id("search_query_top");
//	By articuloLocator = By.xpath("//div[@class='right-block']/h5/a");
	By articuloLocator = By.xpath("//*[@id=\"center_column\"]/ul/li/div/div[1]/div");
	By btnAddLocator = By.className("exclusive");

	@BeforeClass
	public void beforeClass() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver/chromedriver.exe");
		d = new ChromeDriver();
		d.manage().window().maximize();
		d.get("http://automationpractice.com/index.php");
		Thread.sleep(3000);
	}

	@Test
	public void agregaArticulo() throws InterruptedException {
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
			Thread.sleep(3000);

			WebDriverWait w = new WebDriverWait(d, 7);
			w.until(ExpectedConditions.elementToBeClickable(articuloLocator));
			
			String aL = d.findElement(articuloLocator).getAttribute("class");
			if(aL.contains("product-image-container")) {
				File scrFile = d.findElement(articuloLocator).getScreenshotAs(OutputType.FILE);
				FileHandler.copy(scrFile, new File(System.getProperty("user.dir") + "\\saArticulo.png"));	
			}else {
				System.out.println("No se ha encontrado el objeto....");
				d.quit();
			}
			
			retryingFindClick(articuloLocator);
			Thread.sleep(5000);
			
			String add = d.findElement(btnAddLocator).getAttribute("class");
			if(add.contains("exclusive")) {
				File scrFile1 = d.findElement(btnAddLocator).getScreenshotAs(OutputType.FILE);
				FileHandler.copy(scrFile1, new File(System.getProperty("user.dir") + "\\btnAgregar.png"));
			}else {
				System.out.println("No se ha encontrado el objeto....");
				d.quit();
			}
		} catch (Exception e) {
			System.out.println("ERROR en test: " + e);
		}
	}

	@AfterClass
	public void afterClass() {
		d.quit();
	}

	public boolean retryingFindClick(By articuloLocator) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 2) {
			try {
				d.findElement(articuloLocator).click();
				result = true;
				break;
			} catch (NoSuchElementException e) {
			}
			attempts++;
		}
		return result;
	}
}