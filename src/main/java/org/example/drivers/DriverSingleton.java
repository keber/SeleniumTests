package org.example.drivers;

import java.time.Duration;

import org.openqa.selenium.WebDriver;

@Deprecated
public class DriverSingleton {
    private static DriverSingleton instance = null;
    private static WebDriver driver;
    
    private DriverSingleton(String browser) {
        instantiate(browser);
    }

    public WebDriver instantiate(String browser){
        DriverStrategy strategy = DriverStrategySelector.chooseStrategy(browser);
        this.driver = strategy.setStrategy();
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        this.driver.manage().window().maximize();
        return this.driver;
    }

    public static DriverSingleton getInstance(String browser){
        if(instance == null){
            instance= new DriverSingleton(browser);
        }
        return instance;
    }

    public static void closeDriver(){
        if (driver != null ){
            driver.quit();
            instance = null;
            driver = null;
        }

    }

    public static WebDriver getDriver() {
        return driver;
    }
}
