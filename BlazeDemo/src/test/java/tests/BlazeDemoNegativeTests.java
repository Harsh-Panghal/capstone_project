package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.PurchasePage;
import pages.RegisterPage;
import utils.WaitUtils;
import java.util.HashMap;

public class BlazeDemoNegativeTests extends BaseTest {

    @Test(priority = 1, description = "Jira Defect : Registration Blocked by 419 Error")
    public void testRegistrationThrows419Error() {
        System.out.println("Executing Negative Test: Registration 419 Error");
        driver.get(prop.getProperty("url") + "register"); 
        
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.fillRegistrationForm("Harsh", "Automation Inc", "test@gmail.com", "Password@123");
        
        // Validation: We expect the application to fail with 419
        boolean isErrorDisplayed = driver.getPageSource().contains("419");
        Assert.assertTrue(isErrorDisplayed, "Defect not reproduced: 419 Error page was expected but not found!");
    }

    @Test(priority = 2, description = "Jira Defect : Login Blocked by 419 Error")
    public void testLoginThrows419Error() {
        System.out.println("Executing Negative Test: Login 419 Error");
        driver.get(prop.getProperty("url") + "login");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginUser("test@gmail.com", "Password@123");
        
        // Validation: We expect the 419 Page Expired text
        boolean isErrorDisplayed = driver.getPageSource().contains("419");
        Assert.assertTrue(isErrorDisplayed, "Defect not reproduced: 419 Error page was expected but not found!");
    }

    @Test(priority = 3, description = "Authentication Defect: Invalid Login Credentials Rejection")
    public void testInvalidLoginCredentials() {
        System.out.println("Executing Negative Test: Invalid Login Credentials");
        driver.get(prop.getProperty("url") + "login"); 
        
        LoginPage loginPage = new LoginPage(driver);
        // Providing fake/unregistered credentials
        loginPage.loginUser("hacker_fake_email@test.com", "WrongPassword123!");
        
        String pageSource = driver.getPageSource();
        boolean isLoginRejected = pageSource.contains("These credentials do not match our records.") 
                               || pageSource.contains("419")
                               || pageSource.contains("Login"); // Still on login page
        
        Assert.assertTrue(isLoginRejected, "Critical Security Defect: System allowed login with invalid/fake credentials!");
    }

    @Test(priority = 4, description = "Form Validation Defect: Submitting Empty Purchase Form")
    public void testEmptyFormSubmissionOnPurchase() {
        System.out.println("Executing Negative Test: Empty Purchase Form Submission");
        driver.get(prop.getProperty("url") + "purchase.php"); // Directly access purchase page
        
        PurchasePage purchasePage = new PurchasePage(driver);
        
        // Passing completely empty data to force validation errors
        HashMap<String, String> emptyData = new HashMap<>();
        emptyData.put("Name", ""); emptyData.put("Address", ""); emptyData.put("City", "");
        emptyData.put("State", ""); emptyData.put("ZipCode", ""); emptyData.put("CardType", "Visa");
        emptyData.put("CardNumber", ""); emptyData.put("Month", ""); emptyData.put("Year", "");
        emptyData.put("NameOnCard", "");
        
        HashMap<String, String> dummyFlight = new HashMap<>();
        dummyFlight.put("Airline", "United"); dummyFlight.put("Price", "400");

        purchasePage.fillDetailsAndPurchase(emptyData, dummyFlight);
        boolean isSuccess = driver.getPageSource().contains("Thank you for your purchase today!");
        Assert.assertFalse(isSuccess, "Critical Security Bug: System confirmed booking with empty passenger details!");
    }
}