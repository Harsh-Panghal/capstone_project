package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class LoginPage {
    private WebDriver driver;

    private By emailInput = By.id("email");
    private By passwordInput = By.id("password");
    private By loginBtn = By.xpath("//button[contains(normalize-space(text()), 'Login')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void loginUser(String email, String password) {
        WaitUtils.waitForElementVisible(emailInput).sendKeys(email);
        WaitUtils.waitForElementVisible(passwordInput).sendKeys(password);
        WaitUtils.waitForElementClickable(loginBtn).click();
    }
}