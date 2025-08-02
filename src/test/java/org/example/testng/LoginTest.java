package org.example.testng;

import java.time.Duration;
import java.util.logging.Logger;

import org.example.drivers.DriverStrategy;
import org.example.drivers.DriverStrategySelector;
import org.example.pages.LoginPage;
import org.example.utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class LoginTest {

    private WebDriver driver;
    protected final Logger logger = Logger.getLogger(getClass().getName());
    protected LoginPage loginPage;
    private String browser;
  
    @Parameters({"browser", "baseUrl"})
    @BeforeMethod
    public void setUp(String browser, @Optional("https://wallet.keber.cl/") String baseUrl) {
    //public void setUp(String browser, String baseUrl) {
        DriverStrategy strategy = DriverStrategySelector.chooseStrategy(browser);
        this.driver = strategy.setStrategy();
        this.browser = browser;
        this.driver.get(baseUrl);
        this.loginPage = new LoginPage(this.driver);
        logger.info("Inicializando navegador con TestNG: ".concat(browser));
    }

    @AfterMethod
    public void tearDown() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }

    @Test
    public void testLoginFailedWrongUser(){

        String email = "unkown@notvalid.com";
        String password = "123456789";

        loginPage.login(email, password);
        Utils.takeScreenshot("LoginFailedWrongUser_result_before",this.driver,this.browser);

        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-error-message")));
        Utils.takeScreenshot("LoginFailedWrongUser_result_after",this.driver,this.browser);

        assertEquals("Credenciales incorrectas", loginPage.getErrorMessage());
    }

    @Test
    public void testLoginFailedWrongPassword(){

        String email = "keberflores@gmail.com";
        String password = "123456789";

        loginPage.login(email, password);
        Utils.takeScreenshot("LoginFailedWrongPassword_result_before", this.driver, this.browser);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-error-message")));
        Utils.takeScreenshot("LoginFailedWrongPassword_result_after", this.driver, this.browser);

        assertEquals("Credenciales incorrectas", loginPage.getErrorMessage());
    }
}
