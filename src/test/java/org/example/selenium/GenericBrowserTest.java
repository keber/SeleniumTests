package org.example.selenium;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.example.drivers.DriverSingleton;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public abstract class GenericBrowserTest {

    protected WebDriver driver;
    protected DriverSingleton driverSingleton;
    protected final Logger logger = Logger.getLogger(getClass().getName());

    protected abstract String getBrowserName();

    @BeforeAll
    void setup() {
        driverSingleton = DriverSingleton.getInstance(getBrowserName());
        driver = driverSingleton.getDriver();
        logger.info("Inicializando navegador ".concat(getBrowserName() ));
    }

    @AfterAll
    void teardown() {
        if ( driverSingleton != null ){
            driverSingleton.closeDriver();
        }
    }

    protected void takeScreenshot(String name) {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
        try {
            Files.createDirectories(Path.of("screenshots"));
            Files.copy(
                screenshotFile.toPath(),
                Path.of(String.format("screenshots/%s_%s_%d.png", getBrowserName(), name, System.currentTimeMillis()))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void testWikipediaSearch(){
        //Obtiene una URL, navega al sitio.
        driver.get("https://www.wikipedia.org/");

        //Buscar un texto:
        WebElement searchBox= driver.findElement(By.id("searchInput"));
        String busqueda= "gatos";
        searchBox.sendKeys(busqueda);

        //DAR CLICK AL BUTTON
        WebElement buttonSearch= driver.findElement(By.cssSelector("#search-form > fieldset > button"));
        buttonSearch.click();

        takeScreenshot("wikipediaSearch_result");

        //validar si funciono
        WebElement titleSearchedPage= driver.findElement(By.cssSelector("#firstHeading > span"));
        assertEquals("Felis catus", titleSearchedPage.getText());
    }

    @Test
    @Order(2)
    void testWikipediaURL(){
        driver.get("https://www.wikipedia.org/");

        //Buscar un texto:
        WebElement searchBox= driver.findElement(By.id("searchInput"));
        String busqueda= "gatos";
        searchBox.sendKeys(busqueda);

        //DAR CLICK AL BUTTON
        WebElement buttonSearch= driver.findElement(By.cssSelector("#search-form > fieldset > button"));
        buttonSearch.click();

        takeScreenshot("wikipediaURL_result");  

        //validar si funciono
        assertEquals("https://es.wikipedia.org/wiki/Felis_catus", driver.getCurrentUrl());
    }

}