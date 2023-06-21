package org.example.drivers;

public class DriverStrategySelector {

    public static DriverStrategy chooseStrategy(String browser){
        switch (browser){
            case "Chrome":
                return new Chrome();
            case "Firefox":
                return new Firefox();
            default:
                return null;
        }
    }
}
