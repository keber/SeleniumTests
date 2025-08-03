package org.example.testng;

import java.io.IOException;
import java.time.Duration;
import java.util.logging.Logger;

import org.example.drivers.DriverStrategy;
import org.example.drivers.DriverStrategySelector;
import org.example.pages.LoginPage;
import org.example.utils.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.testng.Assert.assertEquals;

import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import io.qameta.allure.*;

// anotaciones de allure
@Epic("Login")
@Feature("Validación de crecenciales")
public class LoginTest implements ITest {
    private String testName = "";

    private WebDriver driver;
    protected final Logger logger = Logger.getLogger(getClass().getName());
    protected LoginPage loginPage;
    private String browser;
  
    @DataProvider(name = "genericData")
    public Object[][] getDataFromXmlParam(ITestContext context) throws IOException {
        String filePath = context.getCurrentXmlTest().getParameter("credentialsFile");
        String separator = filePath.endsWith(".tsv") ? "\t" : ",";
        return Utils.readDataFile("src/test/resources/" + filePath, separator);
    }

    @Parameters({"browser", "baseUrl"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser, @Optional("https://wallet.keber.cl/") String baseUrl) {
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

    
    @Test(dataProvider = "genericData", description = "Login con distintos escenarios")
    @Description("Prueba el inicio de sesión con distintos usuarios y contraseñas")
    public void testLogin(String email, String password, String expectedResult, String expectedMessage){
        testName = String.format("Logn [%s - %s] -> %s", email, password, expectedResult);

        loginPage.login(email, password);
        Utils.takeScreenshot("testng_testLogin_before",this.driver,this.browser);

        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(5));
        
        if(expectedResult.equals("error")){
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.get_errorMessage()));
            assertEquals(loginPage.getErrorMessage(), expectedMessage);
        }
        else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.get_successMessage()));
            assertEquals(loginPage.getSuccessMessage(), expectedMessage);
        }

        Utils.takeScreenshot("testng_testLogin_after",this.driver,this.browser);

    }

    @Override
    public String getTestName() {
        return testName;
    }
}
