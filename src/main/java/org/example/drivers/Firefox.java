package org.example.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Firefox implements DriverStrategy {
    @Override
    public WebDriver setStrategy() {
        WebDriverManager.firefoxdriver().setup(); 
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        options.addPreference("intl.accept_languages", "es");
        return new FirefoxDriver(options);
    }
}
