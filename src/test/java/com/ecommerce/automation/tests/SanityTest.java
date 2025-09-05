package com.ecommerce.automation.tests;

import com.ecommerce.automation.base.BaseTest;
import com.ecommerce.automation.core.DriverFactory;
import org.testng.annotations.Test;

public class SanityTest extends BaseTest {

    @Test
    public void openHomePage() {
        // DriverFactory.getDriver() must be visible here
        String title = DriverFactory.getDriver().getTitle();
        System.out.println("Page title: " + title);
    }
}