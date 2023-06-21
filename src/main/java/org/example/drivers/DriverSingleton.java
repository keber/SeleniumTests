package org.example.drivers;

import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class DriverSingleton {
    private static DriverSingleton instance =null;
    private static WebDriver driver;
    private DriverSingleton(String browser) {
        instantiate(browser);
    }

    public WebDriver instantiate(String browser){
        DriverStrategy driver= DriverStrategySelector.chooseStrategy(browser);
        this.driver= driver.setStrategy();
        this.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        this.driver.manage().window().maximize();
        return this.driver;
    }

    public static DriverSingleton getInstance(String browser){
        if(instance==null){
            instance= new DriverSingleton(browser);
        }
        return instance;
    }

    public static void closeDriver(){
        instance =null;
        driver.close();
        driver.quit();
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
