package ObjectRepository;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class Loginpage {
    RemoteWebDriver driver;
    FluentWait<RemoteWebDriver> wait;
    @FindBy(name="email")
    private org.openqa.selenium.WebElement email;

    @FindBy(name="password")
    private org.openqa.selenium.WebElement password;
    @FindBy(xpath = "//button[@type='submit']")
    private org.openqa.selenium.WebElement login;

    public Loginpage(RemoteWebDriver driver, FluentWait<RemoteWebDriver> wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(this.email)).sendKeys(email);
    }
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(this.password)).sendKeys(password);
    }
    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(this.login)).click();
    }

}
