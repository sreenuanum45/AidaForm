package GuiTesting;

import Base.BaseClass;
import ObjectRepository.SignupPage;
import Utility.Utilites_1;
import com.github.javafaker.Faker;
import org.openqa.selenium.ElementClickInterceptedException;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.*;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class Test1 extends BaseClass {
    SignupPage signupPage;
    RemoteWebDriver driver;
    FluentWait<RemoteWebDriver> wait;
    public Test1() {
        super();
    }
    @BeforeMethod
    public void setup() throws ElementClickInterceptedException, IOException, InterruptedException {
       driver=intialzineBrowserandOpenApplication(p.getProperty("browserName"));
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        driver.get(p.getProperty("url"));
    }
    @Test(priority = 1, dataProvider = "testdata",invocationCount = 4
            ,description = "Verify the registration functionality")
    public void verifyTheRegistrationFunctionality(String name, String email, String password) throws InterruptedException {
        signupPage=new SignupPage(driver,wait);
        signupPage.enterName(name);
        signupPage.enterEmail(email);
        signupPage.enterPassword(password);
        signupPage.enterConfirmPassword(password);
        signupPage.clickAgree();
        signupPage.clickRegister();
    }
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
    @DataProvider(name = "testdata")
    public Object[][] getData() {
        return new Object[][]{
                {new Faker().name().fullName(), new Faker().internet().emailAddress() , new Faker().internet().password()}
        };
    }
}
