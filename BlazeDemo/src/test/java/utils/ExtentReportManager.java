package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

public class ExtentReportManager {
    
    private static ExtentReports extent;

    public static ExtentReports getReportInstance() {
        if (extent == null) {
            // 1. Set Report folder and file path 
            String reportPath = System.getProperty("user.dir") + "/reports/SparkReport_BlazeDemo.html";
            
            // 2. Spark Reporter initialize
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            
            // 3. Report Dashboard and Look/Feel design 
            sparkReporter.config().setDocumentTitle("BlazeDemo Automation Report");
            sparkReporter.config().setReportName("End-to-End Flight Booking Execution");
            sparkReporter.config().setTheme(Theme.DARK);
            
            // 4. Attach the spark in Extent Reports engine
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            
            // 5. System/Environment details add 
            extent.setSystemInfo("Project", "BlazeDemo Capstone");
            extent.setSystemInfo("SDET Engineer", "Harsh Choudhary");
            extent.setSystemInfo("Operating System", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }
        return extent;
    }
}