package com.ecommerce.automation.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver() {
        String browser = ConfigReader.get("browser").toLowerCase();
        boolean headless = ConfigReader.getBool("headless");

        switch (browser) {
            case "chrome":
                var chromeOpts = new ChromeOptions();
                if (headless) chromeOpts.addArguments("--headless=new");
                WebDriverManager.chromedriver().setup();
                driver.set(new org.openqa.selenium.chrome.ChromeDriver(chromeOpts));
                break;

            case "firefox":
                var firefoxOpts = new FirefoxOptions();
                if (headless) firefoxOpts.addArguments("--headless");
                WebDriverManager.firefoxdriver().setup();
                driver.set(new org.openqa.selenium.firefox.FirefoxDriver(firefoxOpts));
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        getDriver().manage().timeouts()
                .implicitlyWait(java.time.Duration.ofSeconds(ConfigReader.getInt("implicitWait")));
        getDriver().manage().window().maximize();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}