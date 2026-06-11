package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.WaitUtils; 
import java.util.List;

public class FlightResultsPage {
    private WebDriver driver;

    // Locators
    private By flightsTable = By.cssSelector("table.table");
    private By allRows = By.xpath("//table[@class='table']/tbody/tr");

    public FlightResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    public java.util.Map<String, String> selectCheapestFlight() {
        java.util.Map<String, String> selectedFlightData = new java.util.HashMap<>();
        try {
            System.out.println("[STEP] Analyzing all available flights to find the cheapest option...");
            
            WaitUtils.waitForElementVisible(flightsTable);
            
            List<WebElement> rows = driver.findElements(allRows);
            double minPrice = Double.MAX_VALUE;
            WebElement cheapestRow = null;
            WebElement cheapestFlightBtn = null;

            for (WebElement row : rows) {
                String priceText = row.findElement(By.xpath("./td[6]")).getText();
                double currentPrice = Double.parseDouble(priceText.replace("$", "").trim());
                
                if (currentPrice < minPrice) {
                    minPrice = currentPrice;
                    cheapestRow = row;
                    cheapestFlightBtn = row.findElement(By.xpath(".//input[@value='Choose This Flight']"));
                }
            }

            if (cheapestFlightBtn != null && cheapestRow != null) {
                selectedFlightData.put("FlightNumber", cheapestRow.findElement(By.xpath("./td[2]")).getText());
                selectedFlightData.put("Airline", cheapestRow.findElement(By.xpath("./td[3]")).getText());
                selectedFlightData.put("Price", String.valueOf(minPrice));

                System.out.println("[LOGIC] Cheapest flight found! Airline: " + selectedFlightData.get("Airline") 
                                    + " | Price: $" + minPrice);
                
                cheapestFlightBtn.click();
            } else {
                Assert.fail("Could not find any flight buttons in the table!");
            }

        } catch (Exception e) {
            Assert.fail("Failed to process flights table: " + e.getMessage());
        }
        return selectedFlightData; 
    }
}