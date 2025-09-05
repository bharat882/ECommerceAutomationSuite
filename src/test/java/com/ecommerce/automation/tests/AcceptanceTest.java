package com.ecommerce.automation.tests;

import com.ecommerce.automation.base.BaseTest;
import com.ecommerce.automation.pages.CartPage;
import com.ecommerce.automation.pages.CheckoutPage;
import com.ecommerce.automation.pages.LoginPage;
import com.ecommerce.automation.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.ecommerce.automation.core.DriverFactory.getDriver;

public class AcceptanceTest extends BaseTest {

    @Test(description = "Invalid login display error")
    public void invalidLoginShowsError(){
        String error = new LoginPage(getDriver()).loginInvalid("invalid_user", "wrong_pass").getErrorText();

        Assert.assertTrue(error.contains("Username and password do not match"),
                "Expected error message for bad credentials");

    }

    @Test(description = "Happy path: login, add to cart, checkout")
    public  void fullPurchaseFlow(){
        // 1. Login
        ProductsPage products = new LoginPage(getDriver()).loginValid("standard_user","secret_sauce");

        // 2. Add items to cart
        products.addProductToCart("Sauce Labs Backpack");

        // 3. Verify and navigate to cart
        CartPage cart = products.goToCart();
        Assert.assertTrue(cart.isInCart("Sauce Labs Backpack"),"Item should be in cart");

        // 4. Checkout
        CheckoutPage checkout = cart.clickCheckout().enterUserInfo("John","Doe","12345").clickContinue().clickFinish();

        // 5. Assert confirmation
        String confirmation = checkout.getConfirmation();
        // in AcceptanceTest.fullPurchaseFlow(), before the Assert

        System.out.println("DEBUG — confirmationHeader text: [" + confirmation + "]");
        // in AcceptanceTest.fullPurchaseFlow()
        String expected = "Thank you for your order!";
        Assert.assertEquals(confirmation, expected,
                "Order completion message should be exactly [" + expected + "] but was [" + confirmation + "]");
    }
}
