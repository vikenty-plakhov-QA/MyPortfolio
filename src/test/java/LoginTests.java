import POM.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class LoginTests extends BaseTest {


    @Test//(groups = {"smoke1"})
    public void loginSucceedTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(myEmail,myLogin);
        HomePage homePage = new HomePage(driver);
              // find if avatar exists
        Assert.assertTrue(homePage.getAvatar(), " User is NOT Logged in");
        System.out.println("User logged successfully   " + homePage.getAvatar());
    }


    @Test
    public void loginEmptyPasswordTest() {
        LoginPage loginpage = new LoginPage(driver);
        loginpage.login(myEmail, "");
        System.out.println("Is Submit button is displayed?  " + loginpage.isSubmitLoginBtnDisplayed());
        Assert.assertTrue(loginpage.isSubmitLoginBtnDisplayed());
    }

    @Test(priority = 1)
    public void loginInvalidEmailTest() throws InterruptedException {
        LoginPage loginpage = new LoginPage(driver);
        String invalidEmail = "galy.o" + generateTimeStamp() + "@testpro.io";
          System.out.println(invalidEmail);
        loginpage.login(invalidEmail, myLogin);
        Thread.sleep(3000);
        System.out.println("Is Submit button is displayed?  " + loginpage.isSubmitLoginBtnDisplayed());
        Assert.assertTrue(loginpage.isSubmitLoginBtnDisplayed());
        //       Assert.assertFalse(homePage.getAvatar());
    }

    @Test(dataProvider = "IncorrectLoginProviders")
    public void negativeLoginTests(String email, String password) throws InterruptedException {
        LoginPage loginpage = new LoginPage(driver);

        SoftAssert softAssert = new SoftAssert();
        loginpage.login(email, password);
        Thread.sleep(500);
        System.out.println(driver.getCurrentUrl());
        softAssert.assertEquals(driver.getCurrentUrl(), url);
        softAssert.assertTrue(loginpage.isSubmitLoginBtnDisplayed());
        softAssert.assertAll();
    }

    //        Email("demo@class.com");
//        Password("te$t$tudent");
}
