package org.example.playwright;

public class PlaywrightFirefoxTest extends GenericBrowserTest {
    @Override
    protected String getBrowserName() {
        return "Firefox";
    }
}
