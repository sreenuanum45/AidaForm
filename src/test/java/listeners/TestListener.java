package listeners;

import Base.BaseClass;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reporting.AllureHelper;
import reporting.ExtentManager;
import reporting.ExtentTestManager;

public class TestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getExtent();

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentTest test = extent.createTest(testName);
        ExtentTestManager.startTest(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.getTest().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            Object instance = result.getInstance();
            if (instance instanceof BaseClass) {
                BaseClass base = (BaseClass) instance;
                String name = result.getMethod().getMethodName();
                // Allure attach
                byte[] b = base.takeScreenshotAsBytes(name);
                AllureHelper.attachScreenshot(b, name);
                // Extent attach (save screenshot file and attach)
                String relativePath = base.takeScreenshotToFile(name);
                if (relativePath != null) {
                    ExtentTestManager.getTest().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(relativePath).build());
                } else {
                    ExtentTestManager.getTest().fail(result.getThrowable());
                }
            } else {
                ExtentTestManager.getTest().fail(result.getThrowable());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}

