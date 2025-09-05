package com.ecommerce.automation.pages;

import com.ecommerce.automation.core.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage {
    private final WebDriver driver;
    private final WaitUtils wait;

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id = "postal-code")
    private WebElement postalCodeInput;

    @FindBy(css = "[data-test='continue']")
    private WebElement continueButton;

    @FindBy(css = "[data-test='finish']")
    private WebElement finishButton;

    @FindBy(css = ".complete-header")
    private WebElement confirmationHeader;

    public CheckoutPage(WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver,this);
        this.wait = new WaitUtils();
    }

    public CheckoutPage enterUserInfo(String first, String last, String postal){
        wait.untilVisible(firstNameInput).sendKeys(first);
        lastNameInput.sendKeys(last);
        postalCodeInput.sendKeys(postal);
        return this;
    }

    public CheckoutPage clickContinue(){
        continueButton.click();
        return this;
    }

    public CheckoutPage clickFinish(){
        finishButton.click();
        return this;
    }

    public String getConfirmation(){
        return wait.untilVisible(confirmationHeader).getText();
    }
}
