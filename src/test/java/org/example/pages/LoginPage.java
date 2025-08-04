package org.example.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriver driver;

    // Localizadores
    private final By emailInput = By.id("login-email");
    private final By passwordInput = By.id("login-password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.id("login-error-message");
    private final By successMessage = By.id("login-success-message");
    private final By registerButton = By.id("register-button");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public By get_errorMessage(){
        return this.errorMessage;
    }

    public By get_successMessage(){
        return this.successMessage;
    }

    // Acciones
    public void enterEmail(String email) {
        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public void clickRegister() {
        driver.findElement(registerButton).click();
    }

    public void login(String email, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));

        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }

    public void register(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));

        clickRegister();
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    public String getSuccessMessage() {
        return driver.findElement(successMessage).getText();
    }

    public boolean isErrorVisible() {
        return driver.findElement(errorMessage).isDisplayed();
    }
}
