package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import utils.WaitUtils; 

public class ConfirmationPage {
    private WebDriver driver;

    // Locators
    private By successHeading = By.xpath("//h1[contains(text(), 'Thank you for your purchase today!')]");
    private By bookingIdLocator = By.xpath("//td[text()='Id']/following-sibling::td");

    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void verifySuccess() {
        try {
            // WaitUtils checking visibility of success heading
            Assert.assertTrue(WaitUtils.waitForElementVisible(successHeading).isDisplayed(), "Confirmation text missing!");
            
            // WaitUtils directly fetching the Booking ID
            String bookingId = WaitUtils.waitForElementVisible(bookingIdLocator).getText();
            
            System.out.println("[SUCCESS] BOOKING CONFIRMED!");
            System.out.println("[TICKET INFO] Confirmation ID is: " + bookingId);
            System.out.println("=======================================================\n");
        } catch (Exception e) {
            Assert.fail("Failed to verify booking confirmation: " + e.getMessage());
        }
    }
}