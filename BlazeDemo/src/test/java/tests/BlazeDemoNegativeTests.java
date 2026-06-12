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

    @Test(priority = 3, description = "Data Integrity Defect: Hardcoded Dummy Data Displayed")
    public void testDataIntegrityIssue() {
        System.out.println("Executing Negative Test: Data Integrity Issue (Hardcoded Data)");
       
        driver.get(prop.getProperty("url"));
        driver.findElement(By.cssSelector("input[type='submit']")).click(); // Clicks 'Find Flights'
        
        driver.findElement(By.cssSelector("input[type='submit']")).click(); // Clicks 'Choose This Flight'
        
        String pageText = driver.findElement(By.tagName("body")).getText();
        boolean isHardcodedDataPresent = pageText.contains("Airline: United") && pageText.contains("Price: 400");
        
        Assert.assertFalse(isHardcodedDataPresent, "Data Integrity Defect: System is displaying hardcoded dummy data (United/400) instead of the actual selected flight!");
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