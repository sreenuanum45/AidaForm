package reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.nio.file.Paths;

public class ExtentManager {
    private static ExtentReports extent;

    public synchronized static ExtentReports getExtent() {
        if (extent == null) {
            String reportPath = Paths.get(System.getProperty("user.dir"), "target", "extent-report", "extent.html").toString();
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setDocumentTitle("Automation Test Results");
            spark.config().setReportName("Extent Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }
}

