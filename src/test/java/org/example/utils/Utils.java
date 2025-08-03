package org.example.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
                Path.of(String.format("screenshots/%s_%s_%s.png", timestamp, name, browser ))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Object[][] readDataFile(String relativePath, String separator) throws IOException {
        List<Object[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(relativePath))) {
            String line;
            reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(separator);
                data.add(parts);
            }
        }
        return data.toArray(new Object[0][]);
    }
}
