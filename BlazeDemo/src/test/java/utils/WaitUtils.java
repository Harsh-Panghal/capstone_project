package utils;

import base.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    // Default timeout duration (10 seconds)
    private static final int DEFAULT_TIMEOUT = 10;

    private static WebDriverWait getWait() {
        return new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    private static WebDriverWait getWait(int timeoutInSeconds) {
        return new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeoutInSeconds));
    }

    /**
     * Waits for an element to be completely visible on the DOM and screen.
     */
    public static WebElement waitForElementVisible(By locator) {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits for an element to be visible with a custom timeout.
     */
    public static WebElement waitForElementVisible(By locator, int timeoutInSeconds) {
        return getWait(timeoutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits until the element is clickable (visible and enabled).
     */
    public static WebElement waitForElementClickable(By locator) {
        return getWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Checks if an element is present in the DOM (even if hidden).
     */
    public static WebElement waitForElementPresence(By locator) {
        return getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Waits for the complete page to load using JavaScript Document Ready State.
     */
    public static void waitForPageLoad() {
        WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(20));
        wait.until(driver -> String
                .valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
                .equals("complete"));
    }
}