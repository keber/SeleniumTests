package org.example.junit.selenium;

public class SeleniumFirefoxTest extends GenericBrowserTest {
    @Override
    protected String getBrowserName() {
        return "Firefox";
    }
}
