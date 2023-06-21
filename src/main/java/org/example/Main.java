package org.example;

import org.example.drivers.DriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.SQLOutput;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        DriverSingleton driverSingleton = DriverSingleton.getInstance("Firefox");
        WebDriver driver = driverSingleton.getDriver();

        driver.get("https://www.wikipedia.org/");
        //Para escribir texto:
        WebElement searchBox=  driver.findElement(By.id("searchInput"));
        String busqueda="mafia";
        searchBox.sendKeys(busqueda); //permite mandar texto a un input

        //click search button
        WebElement searchButton= driver.findElement(By.cssSelector("#search-form > fieldset > button"));
        searchButton.click();
        driverSingleton.closeDriver();


    }
}