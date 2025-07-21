package org.example.playwright;

public class PlaywrightChromeTest extends GenericBrowserTest {
    @Override
    protected String getBrowserName() {
        return "chromium";
    }
}
