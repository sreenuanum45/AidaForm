package reporting;

import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static synchronized ExtentTest getTest() {
        return extentTest.get();
    }

    public static synchronized void startTest(ExtentTest test) {
        extentTest.set(test);
    }

    public static synchronized void endTest() {
        extentTest.remove();
    }
}

