package listener;

import lombok.extern.log4j.Log4j2;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

@Log4j2
public class TestListener implements ITestListener {

    public void onTestStart(ITestResult result) {
        log.info("Test was START! -> " + result.getName());
    }

    public void onTestSuccess(ITestResult result) {
        log.info("Test was PASS! -> " + result.getName());
    }

    public void onTestFailure(ITestResult result) {
        log.fatal("Test was FAIL! -> " + result.getName());
    }

    public void onTestSkipped(ITestResult result) {
        log.warn("Test was SKIP! -> " + result.getName());
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public void onTestFailedWithTimeout(ITestResult result) {
        this.onTestFailure(result);
    }

    public void onStart(ITestContext context) {
        log.info("Started -> " + context);
    }

    public void onFinish(ITestContext context) {
        log.info("Finished -> " + context);
    }
}
