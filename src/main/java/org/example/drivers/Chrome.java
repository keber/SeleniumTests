package org.example.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Chrome implements DriverStrategy {
    @Override
    public WebDriver setStrategy() {
        //SETEAR LA PROPIEDAD DE DONDE ESTA EL DRIVER
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        //DEFINIR OPTIONS
        ChromeOptions options= new ChromeOptions();
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--no-sandbox");
        return new ChromeDriver(options);
    }
}
