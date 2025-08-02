package org.example.junit.playwright;

public class PlaywrightChromeTest extends GenericBrowserTest {
    @Override
    protected String getBrowserName() {
        return "chromium";
    }
}
