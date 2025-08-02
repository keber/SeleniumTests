package org.example.drivers;

public class DriverStrategySelector {

    public static DriverStrategy chooseStrategy(String browser){
        switch (browser.toLowerCase()){
            case "chrome":
                return new Chrome();
            case "firefox":
                return new Firefox();
            default:
                throw new IllegalArgumentException("Browser no soportado: ".concat(browser));
        }
    }
}
