package Tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.Duration;


public class BaseTest {
    public static WebDriver driver = null;
    public static String url = "https://quicklyn.com/";
    ;
    public static WebDriverWait wait = null;


    @BeforeSuite
    static void setupDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    void setBrowser()  {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().window().maximize();
       driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
       driver.manage().timeouts().getScriptTimeout();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get(url);
        By closeAdd = By.xpath("//img[@alt = 'Close']");
        WebElement close = wait.until(ExpectedConditions.visibilityOfElementLocated(closeAdd));
        close.click();
    }

   @AfterMethod(alwaysRun = true)
    public void TearDown() {
        driver.quit();
    }

    @Test
    public void checkUrl() throws InterruptedException {
        By closeAdd = By.xpath("//img[@alt = 'Close']");
         Thread.sleep(1000);
         wait.until(ExpectedConditions.visibilityOfElementLocated(closeAdd)).click();
        Assertions.assertEquals(driver.getCurrentUrl(), url);
        System.out.println(driver.getCurrentUrl());

    }
 @Test
    public void datePicker() throws InterruptedException {

     By bookXml = By.cssSelector("li[id='menu-item-122']");
     By iframeXpath = By.xpath("//iframe[@data-lazyloaded = '1']");
    // By dataCss = By.cssSelector("[data-id= '181']>input");//[id=dp1706116425190]
      By dataCss = By.id("dp1706116425190");
     By monthXpath = By.xpath("//span[@class ='ui-datepicker-month']");
     By iframeCalendCss = By.cssSelector("[name='__privateStripeMetricsController6540']");

     JavascriptExecutor js =(JavascriptExecutor)driver;
     WebElement bookNowBtn = wait.until(ExpectedConditions
             .visibilityOfElementLocated(bookXml));
     bookNowBtn.click();
     System.out.println("Before");
     Thread.sleep(3000);
     WebElement iframe = wait.until(ExpectedConditions
             .visibilityOfElementLocated(iframeXpath));
     driver.switchTo().frame(iframe);

        WebElement calendarField = wait.until(ExpectedConditions
                .elementToBeClickable(dataCss));
  //   js.executeScript("window.scrollBy(0,document.body.scrollHeight)");

        js.executeScript("arguments[0].scrollIntoView(true);", calendarField);
   //  js.executeScript("window.scrollBy(0,500)");
     js.executeScript("arguments[0].click();",calendarField);
     js.executeScript("arguments[0].click();",calendarField);
        //    calendarField.click();

        WebElement monthPicker = wait.until(ExpectedConditions
                .elementToBeClickable(monthXpath));
     js.executeScript("arguments[0].scrollIntoView(true);", monthPicker);
        String month = monthPicker.getText();
     System.out.println(month);
       while ((!month.contains("May"))) {
           Thread.sleep(3000);
       WebElement next = wait.until(ExpectedConditions
               .elementToBeClickable(By.xpath("//span[@class ='ui-icon ui-icon-circle-triangle-e']")));
           js.executeScript("arguments[0].click();", next);
           Thread.sleep(3000);
       }
 }
}
