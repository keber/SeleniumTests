package org.example.playwright;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class GenericBrowserTest {

    static Playwright playwright;
    static Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected final Logger logger = Logger.getLogger(getClass().getName());

    protected abstract String getBrowserName(); // "chromium", "firefox", "webkit"

    @BeforeAll
    void setup() {
        playwright = Playwright.create();

        switch (getBrowserName()) {
            case "firefox" -> browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(true));
            case "webkit" -> browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(true));
            default -> browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        }

        context = browser.newContext(new Browser.NewContextOptions().setLocale("es-CL"));
        page = context.newPage();

        logger.info("Inicializando navegador con Playwright: ".concat(getBrowserName()));

    }

    @AfterAll
    void teardown() {
        if (page != null) page.close();
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }


    protected void takeScreenshot(String name) {
        try {
            Files.createDirectories(Path.of("screenshots"));
            page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(
                    String.format("screenshots/%s_%s_%d.png", getBrowserName(), name, System.currentTimeMillis())
                    )
                )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void testWikipediaSearch() {
        page.navigate("https://www.wikipedia.org/");
        page.locator("#searchInput").fill("gatos");
        page.locator("button[type='submit']").click();

        takeScreenshot("wikipediaSearch_result");

        String title = page.locator("#firstHeading span").innerText();
        assertEquals("Felis catus", title);
    }

    @Test
    @Order(2)
    void testWikipediaURL() {
        page.navigate("https://www.wikipedia.org/");
        page.locator("#searchInput").fill("gatos");
        page.locator("button[type='submit']").click();

        takeScreenshot("wikipediaURL_result");

        String url = page.url();
        assertEquals("https://es.wikipedia.org/wiki/Felis_catus", url);
    }
}
