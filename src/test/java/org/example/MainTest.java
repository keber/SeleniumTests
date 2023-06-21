package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    static WebDriver driver;
    @BeforeAll
    static void initializeDriver(){
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        //Crea el objeto del driver.
        driver = new ChromeDriver();
        //Implicit wait
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Maximizar la ventana
        driver.manage().window().maximize();
    }

    @Test
    public void holamundo(){
        System.out.println("Hola mundo");
        assertTrue(true);
    }
    @Test
    public void testWikipediaSearch(){
        //Obtiene una URL, navega al sitio.
        driver.get("https://www.wikipedia.org/");

        //Buscar un texto:
        WebElement searchBox= driver.findElement(By.id("searchInput"));
        String busqueda= "gatos";
        searchBox.sendKeys(busqueda);

        //DAR CLICK AL BUTTON
        WebElement buttonSearch= driver.findElement(By.cssSelector("#search-form > fieldset > button"));
        buttonSearch.click();

        //validar si funciono
        WebElement titleSearchedPage= driver.findElement(By.cssSelector("#firstHeading > span"));
        assertEquals(titleSearchedPage.getText(), "Felis silvestris catus");
        assertEquals(driver.getCurrentUrl(), "https://es.wikipedia.org/wiki/Felis_silvestris_catus");
    }

    @AfterAll
    static void closeDriver(){
        driver.close();
        driver.quit();
    }

}