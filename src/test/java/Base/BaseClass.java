package Base;

import Utility.Utilites_1;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
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



}


