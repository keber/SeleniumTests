package org.example.testng;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.net.URL;

import org.example.drivers.DriverStrategy;
import org.example.drivers.DriverStrategySelector;
import org.example.pages.RegisterPage;
import org.example.utils.Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.testng.Assert.assertEquals;

import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import io.qameta.allure.*;

@Epic("Register")
@Feature("Alta de usuario en el sistema")
public class RegisterTest implements ITest {
    private String testName = "";

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    protected final Logger logger = Logger.getLogger(getClass().getName());
    protected RegisterPage registerPage;
    private String browser;
  
    public WebDriver driver() {
        return driver.get();
    }
  
    private static final List<String> createdUsers = new ArrayList<>();

    @DataProvider(name = "genericData")
    public Object[][] getDataFromXmlParam(ITestContext context) throws IOException {
        String filePath = context.getCurrentXmlTest().getParameter("registerTestFile");
        String separator = filePath.endsWith(".tsv") ? "\t" : ",";
        return Utils.readDataFile("src/test/resources/" + filePath, separator);
    }

    @Parameters({"browser", "baseUrl"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser, @Optional("https://wallet.keber.cl/") String baseUrl) {
        DriverStrategy strategy = DriverStrategySelector.chooseStrategy(browser);
        driver.set(strategy.setStrategy());
        this.browser = browser;
        driver().get(baseUrl);
        this.registerPage = new RegisterPage(driver());
        logger.info("Inicializando navegador con TestNG: ".concat(browser));
    }

    @AfterMethod
    public void tearDown() {
        if (driver() != null) {
            driver().quit();
            driver.remove();
        }
    }

    @AfterMethod
    public void cleanUpCreatedUsers() {
        if ( !createdUsers.isEmpty() ) {
            logger.info("Eliminando usuarios creados durante las pruebas:");
            for (String email : createdUsers) {
                deleteUser(email);
            }
            createdUsers.clear();
        }
    }

    @Test(dataProvider = "genericData", description = "Registro con distintos escenarios")
    @Description("Prueba el registro de usuarios con campos vacíos e incompletos")
    public void testRegister(String name, String email, String password, String confirmPassword, String expectedResult, String expectedMessage){
        testName = String.format("Register [%s - %s] -> %s", email, password, expectedResult);

        registerPage.register(name, email, password, confirmPassword);

        WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(5));
        
        if(expectedResult.equals("error")){
            wait.until(ExpectedConditions.visibilityOfElementLocated(registerPage.get_errorMessage()));
            assertEquals(registerPage.getErrorMessage(), expectedMessage);
        }
        else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(registerPage.get_successMessage()));
            assertEquals(registerPage.getSuccessMessage(), expectedMessage);
            createdUsers.add(email);
        }

        Utils.takeScreenshot("testng_testRegister_after",driver(),this.browser);

    }

    @Override
    public String getTestName() {
        return testName;
    }

    private void deleteUser(String email) {
        try {
            URL url = new URL("https://wallet-api.keber.cl/delete_user/" + email);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            int responseCode = conn.getResponseCode();
            logger.info("Eliminando " + email + " - Código de respuesta: " + responseCode);
        } 
        catch (IOException e) {
            logger.info("Error al eliminar usuario: " + email);
            e.printStackTrace();
        }
    }

}
