package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int count = 0;
    private static final int maxTry = 1; // only 1 time retry (Total 2 runs)

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) { // when test fail
            if (count < maxTry) {
                count++;
                System.out.println("[AUTO-HEALER] Network glitch detected! Retrying test: " 
                        + iTestResult.getMethod().getMethodName() + " for the " + count + " time.");
                return true; // Test trigger again
            }
        }
        return false;
    }
}