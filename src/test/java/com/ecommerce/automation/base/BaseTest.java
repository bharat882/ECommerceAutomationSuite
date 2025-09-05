package com.ecommerce.automation.base;

import com.ecommerce.automation.core.ConfigReader;
import com.ecommerce.automation.core.DriverFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;


@Listeners({
        com.ecommerce.automation.base.TestListener.class,
        io.qameta.allure.testng.AllureTestNg.class
})
public abstract class BaseTest {

    @BeforeMethod
    public void setUp() {
        DriverFactory.initDriver();
        DriverFactory.getDriver().get(ConfigReader.get("url"));
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}