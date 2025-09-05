package com.ecommerce.automation.pages;

import com.ecommerce.automation.core.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductsPage {
    private final WebDriver driver;
    private final WaitUtils wait;

    @FindBy(css = ".inventory_item")
    private List<WebElement> products;

    @FindBy(css = ".shopping_cart_link")
    private WebElement cartIcon;

    public ProductsPage(WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver,this);
        this.wait = new WaitUtils();
        wait.untilVisible(cartIcon);
    }

    public void addProductToCart(String name)
    {
        for(WebElement item : products)
        {
            String title = item.findElement(By.cssSelector(".inventory_item_name")).getText();
            if(title.equalsIgnoreCase(name)){
                item.findElement(By.tagName("button")).click();
                break;
            }

        }
    }

    public CartPage goToCart(){
        cartIcon.click();
        return new CartPage(driver);
    }

}
