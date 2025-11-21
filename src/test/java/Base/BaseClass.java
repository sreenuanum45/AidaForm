package Base;

import Utility.Utilites_1;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.function.Function;

public class BaseClass {
    public RemoteWebDriver driver;
    public  FluentWait<RemoteWebDriver> wait;
    public Properties p;
    public  Properties dataProperties;

    public BaseClass(){
        File f=new File(System.getProperty("user.dir")+"/src/test/resources/config.properties");
        File f1=new File(System.getProperty("user.dir")+"/src/test/resources/testdata.properties");

        dataProperties=new Properties();
        try {
            FileInputStream fis1=new FileInputStream(f1);
            dataProperties.load(fis1);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            FileInputStream fis=new FileInputStream(f);
            p=new Properties();
            p.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public <T> T fluentWait(Function<WebDriver, T> condition) {
        return wait.until(condition);
    }
    public  RemoteWebDriver  intialzineBrowserandOpenApplication(String browserChoice){
        if (browserChoice.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browserChoice.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browserChoice.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else {
            System.out.println("Invalid browser choice. Please choose 'chrome', 'firefox', or 'edge'.");
            return  null;
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Utilites_1.wait_Time));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Utilites_1.page_Loading_Time));
        return this.driver;
    }

    // Provide a safe getter for the driver so listeners/helpers can access it
    public RemoteWebDriver getDriver() {
        return this.driver;
    }

    // Capture screenshot as bytes (for Allure attachments)
    public byte[] takeScreenshotAsBytes(String name) {
        try {
            if (driver == null) return null;
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Save screenshot to target/extent-report/screenshots and return the relative path used by Extent
    public String takeScreenshotToFile(String name) {
        try {
            if (driver == null) return null;
            byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
            String safeName = name.replaceAll("[^a-zA-Z0-9-_]", "_");
            Path screenshotsDir = Paths.get(System.getProperty("user.dir"), "target", "extent-report", "screenshots");
            Files.createDirectories(screenshotsDir);
            Path img = screenshotsDir.resolve(safeName + "_" + timestamp + ".png");
            Files.write(img, bytes);
            // Return path relative to extent report file
            return "screenshots/" + img.getFileName().toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
