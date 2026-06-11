package tests;

import base.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import pages.ConfirmationPage;
import pages.FlightResultsPage;
import pages.HomePage;
import pages.PurchasePage;
import utils.ExcelReader;

import java.util.List;
import java.util.Map;

public class BlazeDemoE2ETest extends BaseTest {
    private Map<String, String> testData;
    private Map<String, String> extractedFlightDetails;

    private HomePage homePage;
    private FlightResultsPage resultsPage;
    private PurchasePage purchasePage;
    private ConfirmationPage confirmationPage;

    @DataProvider(name = "blazeDemoData")
    public static Object[][] getPassengerData() {
        ExcelReader excelReader = new ExcelReader();
        List<Map<String, String>> dataList = excelReader.getData("./src/test/resources/testdata/BlazeDemoData.xlsx", "PassengerData");
        
        Object[][] data = new Object[dataList.size()][1];
        for (int i = 0; i < dataList.size(); i++) {
            data[i][0] = dataList.get(i);
        }
        return data;
    }

    @Factory(dataProvider = "blazeDemoData")
    public BlazeDemoE2ETest(Map<String, String> testData) {
        this.testData = testData;
    }


    @Test(priority = 1, groups = {"Smoke", "Search"})
    public void testFlightSearch() {
        System.out.println("Executing for Passenger: " + testData.get("Name"));
        homePage = new HomePage(driver);
        String depCity = testData.get("Departure");
        String destCity = testData.get("Destination");
        homePage.searchFlight(depCity, destCity);
    }

    @Test(priority = 2, dependsOnMethods = "testFlightSearch", groups = {"Smoke", "Selection"})
    public void testSelectCheapestFlight() {
        resultsPage = new FlightResultsPage(driver);
        extractedFlightDetails = resultsPage.selectCheapestFlight();
    }

    @Test(priority = 3, dependsOnMethods = "testSelectCheapestFlight", groups = {"Regression", "Purchase"})
    public void testPurchaseFlight() {
        purchasePage = new PurchasePage(driver);
        purchasePage.fillDetailsAndPurchase(testData, extractedFlightDetails);
    }

    @Test(priority = 4, dependsOnMethods = "testPurchaseFlight", groups = {"Regression", "E2E"}, retryAnalyzer = listeners.RetryAnalyzer.class)
    public void testVerifyBookingConfirmation() {
        confirmationPage = new ConfirmationPage(driver);
        confirmationPage.verifySuccess();
    }
}