package com.ecommerce.automation.tests;
import org.testng.annotations.DataProvider;

public class TestData {

    @DataProvider(name = "loginCredentials")
    public static Object[][] loginCredentials(){
        return new Object[][]{
                //{ username, password, expectedOutcome }

                {"standard_user" ,"secret_sauce" , "VALID"},
                {"locked_out_user" , "secret_sauce", "ERROR_LOCKED"},
                {
                    "invalid_user","wrong_pass","ERROR_CREDENTIALS"
                }


        };
    }
}
