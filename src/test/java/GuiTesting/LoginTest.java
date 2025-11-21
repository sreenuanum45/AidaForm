package GuiTesting;

import Base.BaseClass;
import ObjectRepository.Loginpage;
import Utility.Utilites_1;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class LoginTest extends BaseClass {
    Loginpage loginpage;
    RemoteWebDriver driver;
    FluentWait<RemoteWebDriver> wait;
    public LoginTest() {
        super();
    }
    @BeforeMethod
    public void setup() throws ElementClickInterceptedException {
        driver=intialzineBrowserandOpenApplication(p.getProperty("browserName"));
        wait = new FluentWait<RemoteWebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        driver.get(p.getProperty("loginurl"));

    }
    @Test(priority = 1,dataProviderClass = Utilites_1.class ,dataProvider = "testdata",invocationCount = 2, successPercentage = 50,description = "Verify the login functionality",groups = {"smoke","regression"})
    public void verifyTheLoginFunctionality(String email, String password) throws IOException, InterruptedException {
        loginpage = new Loginpage(driver, wait);
        loginpage.enterEmail(email);
        loginpage.enterPassword(password);
        loginpage.clickLogin();
    }
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
