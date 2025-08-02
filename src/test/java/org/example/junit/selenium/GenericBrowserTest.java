package org.example.junit.selenium;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.logging.Logger;

import org.example.drivers.DriverSingleton;
import org.example.pages.LoginPage;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public abstract class GenericBrowserTest {

    protected WebDriver driver;
    protected DriverSingleton driverSingleton;
    protected final Logger logger = Logger.getLogger(getClass().getName());
    protected LoginPage loginPage;


    protected abstract String getBrowserName();

    @BeforeAll
    void setup() {
        driverSingleton = DriverSingleton.getInstance(getBrowserName());
        driver = driverSingleton.getDriver();
        loginPage = new LoginPage(driver); 
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
    void testLoginFailedWrongUser(){

        driver.get("https://wallet.keber.cl/");

        String email = "unkown@notvalid.com";
        String password = "123456789";

        loginPage.login(email, password);
        takeScreenshot("LoginFailedWrongUser_result_before");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-error-message")));
        takeScreenshot("LoginFailedWrongUser_result_after");

        assertEquals("Credenciales incorrectas", loginPage.getErrorMessage());
    }

    @Test
    @Order(2)
    void testLoginFailedWrongPassword(){
        driver.get("https://wallet.keber.cl/");

        String email = "keberflores@gmail.com";
        String password = "123456789";

        loginPage.login(email, password);
        takeScreenshot("LoginFailedWrongPassword_result_before");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-error-message")));
        takeScreenshot("LoginFailedWrongPassword_result_after");

        assertEquals("Credenciales incorrectas", loginPage.getErrorMessage());
    }

}