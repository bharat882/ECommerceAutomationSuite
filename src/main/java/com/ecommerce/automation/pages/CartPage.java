package com.ecommerce.automation.pages;

import com.ecommerce.automation.core.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;

import java.util.List;

public class CartPage {
    private final WebDriver driver;
    private final WaitUtils wait;

    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(css = "[data-test='checkout']")
    private WebElement checkoutButton;

    public CartPage(WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver,this);
        this.wait = new WaitUtils();
        wait.untilClickable(checkoutButton);
    }

    public boolean isInCart(String name){
        return cartItems.stream()
                .anyMatch(item -> item.findElement(By.cssSelector(".inventory_item_name"))
                        .getText().equalsIgnoreCase(name));
    }

    public CheckoutPage clickCheckout(){
        checkoutButton.click();
        return new CheckoutPage(driver);
    }
}
