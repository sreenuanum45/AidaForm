package ObjectRepository;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class SignupPage {
    RemoteWebDriver driver;
    FluentWait<RemoteWebDriver> wait;
    @FindBy(name = "nickname")
    private org.openqa.selenium.WebElement name;
    @FindBy(name = "email")
    private org.openqa.selenium.WebElement email;
    @FindBy(name = "password")
    private org.openqa.selenium.WebElement password;
    @FindBy(name = "confirm")
    private org.openqa.selenium.WebElement confirmPassword;
    @FindBy(xpath = "//input[@name='terms-of-service']")
    private WebElement agree;
    @FindBy(xpath = "//button[@type='submit']")
    private org.openqa.selenium.WebElement register;

    public SignupPage(RemoteWebDriver driver, FluentWait<RemoteWebDriver> wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void enterName(String name) {
        wait.until(ExpectedConditions.visibilityOf(this.name)).sendKeys(name);
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(this.email)).sendKeys(email);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(this.password)).sendKeys(password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        wait.until(ExpectedConditions.visibilityOf(this.confirmPassword)).sendKeys(confirmPassword);
    }

    public void clickAgree() throws InterruptedException {
        driver.executeScript("arguments[0].click();", agree);
    }

    public void clickRegister() {
        wait.until(ExpectedConditions.elementToBeClickable(this.register)).click();
    }
}