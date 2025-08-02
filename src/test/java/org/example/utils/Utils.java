package org.example.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class Utils {

    public static void takeScreenshot(String name, WebDriver driver, String browser) {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
        try {
            Files.createDirectories(Path.of("screenshots"));
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Files.copy(
                screenshotFile.toPath(),
                Path.of(String.format("screenshots/%s_%s_%s.png", browser, name, timestamp))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
