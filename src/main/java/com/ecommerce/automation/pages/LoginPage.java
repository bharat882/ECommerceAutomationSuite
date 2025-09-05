package com.ecommerce.automation.pages;

import com.ecommerce.automation.core.WaitUtils;
import org.openqa.selenium.WebDriver;
import  org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private WebDriver driver = null;
    private WaitUtils wait = null;

    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver){
        this.driver =driver;
        PageFactory.initElements(driver,this);
        this.wait =new WaitUtils();
    }

    public ProductsPage loginValid (String user, String pass)
    {
        wait.untilVisible(usernameInput).sendKeys(user);
        passwordInput.sendKeys(pass);
        loginButton.click();
        return new ProductsPage(driver);
    }

    // Invalid login stays on the same page
    public LoginPage loginInvalid(String user, String pass)
    {
        wait.untilVisible(usernameInput).sendKeys(user);
        passwordInput.sendKeys(pass);
        loginButton.click();
        return this;
    }

    public String getErrorText(){
        return wait.untilVisible(errorMessage).getText();
    }


}
