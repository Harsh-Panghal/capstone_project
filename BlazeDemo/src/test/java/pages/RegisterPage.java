package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class RegisterPage {
    private WebDriver driver;

    private By nameInput = By.id("name");
    private By companyInput = By.id("company");
    private By emailInput = By.id("email");
    private By passwordInput = By.id("password");
    private By confirmPasswordInput = By.id("password-confirm");
    private By registerBtn = By.xpath("//button[contains(normalize-space(text()), 'Register')]");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillRegistrationForm(String name, String company, String email, String password) {
        WaitUtils.waitForElementVisible(nameInput).sendKeys(name);
        WaitUtils.waitForElementVisible(companyInput).sendKeys(company);
        WaitUtils.waitForElementVisible(emailInput).sendKeys(email);
        WaitUtils.waitForElementVisible(passwordInput).sendKeys(password);
        WaitUtils.waitForElementVisible(confirmPasswordInput).sendKeys(password);
        WaitUtils.waitForElementClickable(registerBtn).click();
    }
}