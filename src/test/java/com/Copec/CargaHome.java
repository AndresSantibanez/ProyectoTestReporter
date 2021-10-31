package com.Copec;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.io.Files;


public class CargaHome {

	WebDriver d;
	private String URL = "https://www.falabella.com/falabella-cl";

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
			
			//captura pantalla sitio web
			File scrFile = ((TakesScreenshot)d).getScreenshotAs(OutputType.FILE);
	        Files.copy(scrFile, new File(System.getProperty("user.dir") + "\\cargaHome.png"));
			
		} catch (IOException e) {
			System.out.println("Error en test: "+e);
		}
	}

	@AfterClass
	public void afterClass() {
		//cierre navegador
		d.quit();
	}
}
