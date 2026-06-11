package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import utils.WaitUtils; 

public class HomePage {
    private WebDriver driver;

    // Locators
    private By departureDropdown = By.name("fromPort");
    private By destinationDropdown = By.name("toPort");
    private By findFlightsBtn = By.xpath("//input[@value='Find Flights']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void searchFlight(String departure, String destination) {
        try {
            System.out.println("\n=======================================================");
            System.out.println("[STEP] Initiating Flight Search: " + departure + " -> " + destination);
            
            WebElement depElem = WaitUtils.waitForElementVisible(departureDropdown);
            new Select(depElem).selectByVisibleText(departure); 
            
            WebElement destElem = WaitUtils.waitForElementVisible(destinationDropdown);
            new Select(destElem).selectByVisibleText(destination);
            
            WaitUtils.waitForElementClickable(findFlightsBtn).click();
            System.out.println("[STEP] Clicked on 'Find Flights' button.");
        } catch (Exception e) {
            Assert.fail("Failed to search flights! Exception: " + e.getMessage());
        }
    }
}