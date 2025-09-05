package com.ecommerce.automation.tests;

import com.ecommerce.automation.base.BaseTest;
import com.ecommerce.automation.pages.LoginPage;
import com.ecommerce.automation.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

import static com.ecommerce.automation.core.DriverFactory.getDriver;

@Epic("E-Commerce Checkout")
@Feature("Login Scenarios")
public class LoginDataTest extends BaseTest {

    @Story("Valid and invalid login")
    @Description("Login flow with multiple credential sets")
    @Test(dataProvider = "loginCredentials", dataProviderClass = TestData.class)
    public void loginScenarios(String user, String pass, String outcome)
    {
        LoginPage login = new LoginPage(getDriver());
        if("VALID".equals(outcome)){
            ProductsPage products = login.loginValid(user, pass);
            String url = getDriver().getCurrentUrl();
            Assert.assertTrue(url.contains("/inventory.html"),
                    "Expected successful login to inventory page");
        }
        else {
            LoginPage samePage = login.loginInvalid(user, pass);
            String error = samePage.getErrorText();

            if ("ERROR_LOCKED".equals(outcome)) {
                Assert.assertTrue(error.contains("locked out"),
                        "Expected locked-out error");
            }
            else {
                Assert.assertTrue(error.contains("do not match"),
                        "Expected credentials error");
            }

        }
    }
}
