package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public WebDriver init_driver(String browser) {
        System.out.println("Starting Test Execution on Local Browser (Headless): " + browser);

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless=new"); 
            chromeOptions.addArguments("--window-size=1920,1080"); 
            tlDriver.set(new ChromeDriver(chromeOptions));
            
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments("--headless"); 
            tlDriver.set(new FirefoxDriver(firefoxOptions));
            
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.addArguments("--headless"); 
            tlDriver.set(new EdgeDriver(edgeOptions));
            
        } else {
            System.out.println("Please pass the correct browser value: " + browser);
        }

        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        
        return getDriver();
    }

    public static synchronized WebDriver getDriver() {
        return tlDriver.get();
    }

    public static synchronized void removeDriver() {
        if (tlDriver.get() != null) {
            tlDriver.get().quit();
            tlDriver.remove();
        }
    }
}