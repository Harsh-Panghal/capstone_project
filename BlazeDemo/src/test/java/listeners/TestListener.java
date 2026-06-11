package listeners;

import base.DriverFactory;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ExtentReportManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {

    private ExtentReports extent;
    private ExtentTest test;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test Suite Execution Started!");
        extent = ExtentReportManager.getReportInstance();
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test Started: " + result.getMethod().getMethodName());
        test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test Passed: " + result.getMethod().getMethodName());
        extentTest.get().log(Status.PASS, "Test Passed Successfully");

        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "SUCCESS_" + result.getMethod().getMethodName() + "_" + timestamp + ".png";
            
            Path destinationPath = Paths.get(System.getProperty("user.dir"), "reports", "Screenshots", fileName);
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            
            try {
                Files.createDirectories(destinationPath.getParent());
                Files.copy(screenshotFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                extentTest.get().addScreenCaptureFromPath("Screenshots/" + fileName, "Booking Confirmation Proof");
                
                System.out.println("Success Proof saved and attached to report!");
            } catch (IOException e) {
                System.out.println("Failed to save success screenshot: " + e.getMessage());
            }
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test Failed: " + result.getMethod().getMethodName());

        extentTest.get().log(Status.FAIL, "Test Failed: " + result.getThrowable().getMessage());
        
        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = result.getMethod().getMethodName() + "_" + timestamp + ".png";
            
            Path destinationPath = Paths.get(System.getProperty("user.dir"), "reports", "Screenshots", fileName);
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            
            try {
                Files.createDirectories(destinationPath.getParent());
                Files.copy(screenshotFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                extentTest.get().addScreenCaptureFromPath("Screenshots/" + fileName, "Failed Step Screenshot");
                
                System.out.println("Screenshot saved and attached to report!");
            } catch (IOException e) {
                System.out.println("Failed to save screenshot: " + e.getMessage());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test Skipped: " + result.getMethod().getMethodName());
        extentTest.get().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test Suite Execution Finished! Generating Report...");
        if (extent != null) {
            extent.flush();
        }
    }
}