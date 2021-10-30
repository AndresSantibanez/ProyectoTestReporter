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
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

public class ContinuarCompra {

	WebDriver d;
	By searchBoxLocator = By.id("search_query_top");
	By articuloLocator = By.xpath("//div[@class='right-block']/h5/a");
	By btnAddLocator = By.xpath("//*[@id=\"add_to_cart\"]/button");
	By tituloOk = By.xpath("//div[@class='layer_cart_product col-xs-12 col-md-6']/h2");
	By btnContinuar = By.xpath("//div[@class='button-container']/a");
	By tituloSummary = By.id("cart_title");

	@BeforeClass
	public void beforeClass() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver/chromedriver.exe");
		ChromeOptions op = new ChromeOptions();
		op.addArguments("--incognite");
		d = new ChromeDriver(op);
		d.manage().window().maximize();
		d.get("http://automationpractice.com/index.php");
		Thread.sleep(3000);
	}

	@Test
	public void continuarCompra() throws InterruptedException {
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
			w.until(ExpectedConditions.presenceOfElementLocated(articuloLocator));

			String aL = d.findElement(articuloLocator).getAttribute("class");
			if(aL.contains("product-image-container")) {
				File scrFile = d.findElement(articuloLocator).getScreenshotAs(OutputType.FILE);
				FileHandler.copy(scrFile, new File(System.getProperty("user.dir") + "\\ccArticulo.png"));	
			}else {
				System.out.println("No se ha encontrado el objeto....");
				d.quit();
			}

			retryingFindClick(articuloLocator);
			Thread.sleep(3000);

			WebElement btnAdd = d.findElement(btnAddLocator);
			assertTrue(d.findElement(btnAddLocator).isDisplayed(), "no se ha encontrado el objeto...");
			File scrFile1 = d.findElement(btnAddLocator).getScreenshotAs(OutputType.FILE);
			FileHandler.copy(scrFile1, new File(System.getProperty("user.dir") + "\\btnAgregar.png"));

			btnAdd.click();
			Thread.sleep(1000);

			WebElement titulo = d.findElement(tituloOk);
			String t = d.findElement(tituloOk).getText().toString();

			if (titulo.isDisplayed()) {
				File scrFile2 = d.findElement(tituloOk).getScreenshotAs(OutputType.FILE);
				FileHandler.copy(scrFile2, new File(System.getProperty("user.dir") + "\\titulo.png"));
			} else {
				System.out.println("no se ha encontrado el titulo OK");
			}

			WebElement continuar = d.findElement(btnContinuar);
			File scrFile2 = d.findElement(btnContinuar).getScreenshotAs(OutputType.FILE);
			FileHandler.copy(scrFile2, new File(System.getProperty("user.dir") + "\\btnContinuar.png"));
			continuar.click();
			Thread.sleep(3000);

			WebElement tituloCarrito = d.findElement(tituloSummary);
			if (tituloCarrito.getText().contains("SHOPPING-CART SUMMARY")) {
				File scrFile3 = d.findElement(tituloSummary).getScreenshotAs(OutputType.FILE);
				FileHandler.copy(scrFile3, new File(System.getProperty("user.dir") + "\\tituloSummary.png"));
				System.out.println("articulo agregado al carro de compra de forma correcta!!");
			} else {
				System.out.println("no se ha agregado el articulo al carro de compras");
			}

		}catch(Exception e) {
			System.out.println("ERROR en test: " + e);
		}

	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(2000);
		d.close();
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
