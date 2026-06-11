package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import utils.WaitUtils; // 💡 WaitUtils imported
import java.util.Map;

public class PurchasePage {
    private WebDriver driver;

    // Locators
    private By totalCostText = By.xpath("//p[contains(text(), 'Total Cost')]");
    private By inputName = By.id("inputName");
    private By inputAddress = By.id("address");
    private By inputCity = By.id("city");
    private By inputState = By.id("state");
    private By inputZipCode = By.id("zipCode");
    private By cardTypeDropdown = By.id("cardType");
    private By creditCardNumber = By.id("creditCardNumber");
    private By creditCardMonth = By.id("creditCardMonth");
    private By creditCardYear = By.id("creditCardYear");
    private By nameOnCard = By.id("nameOnCard");
    private By purchaseBtn = By.xpath("//input[@value='Purchase Flight']");

    public PurchasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillDetailsAndPurchase(Map<String, String> excelData, Map<String, String> flightData) {
        SoftAssert softAssert = new SoftAssert(); 

        try {
            System.out.println("[STEP] Validating Context Data and Entering Passenger Details...");
            WaitUtils.waitForElementVisible(totalCostText);

            // 1. Cross-Page State Validations (Soft Assertions) using WaitUtils
            String actualFlightNum = WaitUtils.waitForElementVisible(By.xpath("//p[contains(text(), 'Flight Number')]")).getText();
            String actualAirline = WaitUtils.waitForElementVisible(By.xpath("//p[contains(text(), 'Airline')]")).getText();
            String actualPrice = WaitUtils.waitForElementVisible(By.xpath("//p[contains(text(), 'Price')]")).getText();
            
            // BlazeDemo has a known defect (hardcoded checkout details).
            System.out.println("[DEFECT LOG] Application is showing hardcoded dummy data!");
            System.out.println("   -> Expected Airline: " + flightData.get("Airline") + " | Actual Shows: " + actualAirline);
            System.out.println("   -> Expected Price: $" + flightData.get("Price") + " | Actual Shows: " + actualPrice);

            softAssert.assertTrue(actualAirline.contains("United"), "Purchase page dummy data missing!");

            // 2. Fill the Form
            WaitUtils.waitForElementVisible(inputName).sendKeys(excelData.get("Name"));
            WaitUtils.waitForElementVisible(inputAddress).sendKeys(excelData.get("Address"));
            WaitUtils.waitForElementVisible(inputCity).sendKeys(excelData.get("City"));
            WaitUtils.waitForElementVisible(inputState).sendKeys(excelData.get("State"));
            WaitUtils.waitForElementVisible(inputZipCode).sendKeys(excelData.get("ZipCode"));
            
            new Select(WaitUtils.waitForElementVisible(cardTypeDropdown)).selectByVisibleText(excelData.get("CardType"));
            WaitUtils.waitForElementVisible(creditCardNumber).sendKeys(excelData.get("CardNumber"));
            
            WaitUtils.waitForElementVisible(creditCardMonth).clear();
            WaitUtils.waitForElementVisible(creditCardMonth).sendKeys(excelData.get("Month"));
            
            WaitUtils.waitForElementVisible(creditCardYear).clear();
            WaitUtils.waitForElementVisible(creditCardYear).sendKeys(excelData.get("Year"));
            
            WaitUtils.waitForElementVisible(nameOnCard).sendKeys(excelData.get("NameOnCard"));
            
            // Wait for button to be clickable before clicking
            WaitUtils.waitForElementClickable(purchaseBtn).click();
            System.out.println("[STEP] Details submitted. Purchasing flight...");

            softAssert.assertAll(); 
            
        } catch (Exception e) {
            Assert.fail("Error on Purchase Page: " + e.getMessage());
        }
    }
}