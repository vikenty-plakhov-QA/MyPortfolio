package POM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver givenDriver) {
        super(givenDriver);
    }

    //   @FindBy(css = "[type='password']")
//   WebElement passwordInput;
    // private   By emailField = By.cssSelector("[type='email']");
    @FindBy(css = "[type='email']")
    WebElement emailInput;
    @FindBy(css = "[type='password']")
    WebElement passwordInput;
    //  By passwordField = By.cssSelector("[type='password']");
    @FindBy(xpath = "//button[@type='submit']")
    WebElement submitLogin;

    public By loginBtn = By.cssSelector("button[type='submit']");

    public void clickLoginBtn() {
        WebElement submitLogin = wait.until(ExpectedConditions
                .visibilityOfElementLocated(loginBtn));
        submitLogin.click();
    }

    public LoginPage enterPassword(String password) {
//       WebElement passwordInput = wait.until(ExpectedConditions
        //               .visibilityOfElementLocated(passwordField));
        passwordInput.click();
        passwordInput.clear();
        passwordInput.sendKeys(password);
        return this;
    }

    public LoginPage enterEmail(String email) {
          emailInput.click();
        emailInput.clear();
        emailInput.sendKeys(email);
        return this;
    }

    public void login(String email, String password) {

        enterEmail(email);
        enterPassword(password);
        clickLoginBtn();


    }

    public boolean isSubmitLoginBtnDisplayed() {

        return findElement(loginBtn).isDisplayed();
    }
}
