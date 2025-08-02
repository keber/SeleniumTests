package org.example.junit.playwright;

public class PlaywrightFirefoxTest extends GenericBrowserTest {
    @Override
    protected String getBrowserName() {
        return "Firefox";
    }
}
