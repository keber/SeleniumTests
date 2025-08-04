package org.example.pages;

import java.time.Duration;

import org.example.pages.LoginPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {
    
    private final WebDriver driver;

    private final By nameInput = By.id("register-name");
    private final By emailInput = By.id("register-email");
    private final By passwordInput = By.id("register-password");
    private final By confirmPasswordInput = By.id("register-confirmpassword");
    private final By registerButton = By.xpath("//button[contains(.,'Sign up')]");
    private final By errorMessage = By.id("register-error-message");
    private final By successMessage = By.id("register-success-message");

    protected LoginPage loginPage;

    public By get_errorMessage(){
        return this.errorMessage;
    }

    public By get_successMessage(){
        return this.successMessage;
    }

    public RegisterPage(WebDriver driver){
        this.driver = driver;
        this.loginPage = new LoginPage(this.driver);
    }

    public void enterName(String name){
        driver.findElement(nameInput).clear();
        driver.findElement(nameInput).sendKeys(name);

    }

    public void enterEmail(String email){
        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);
    }

    public void enterPassword(String password){
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void enterConfirmPassword(String confirmPassword){
        driver.findElement(confirmPasswordInput).clear();
        driver.findElement(confirmPasswordInput).sendKeys(confirmPassword);
    }

    public void clickRegister() {
        driver.findElement(registerButton).click();
    }

    public void register(String name, String email, String password, String confirmPassword) {
        loginPage.clickRegister();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));

        enterName(name);
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
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
