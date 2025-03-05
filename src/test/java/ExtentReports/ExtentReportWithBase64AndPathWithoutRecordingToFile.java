package ExtentReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ExtentReportWithBase64AndPathWithoutRecordingToFile {
    public static WebDriver driver;

    public static void main(String[] args) throws IOException {
        ExtentReports extent = new ExtentReports();
        File path = new File(System.getProperty("user.dir")
                + "/src/test/java/ExtentReports/ReportsFolder/eReportTutorialNinja.html");
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(path);
        extent.attachReporter(sparkReporter);
        sparkReporter.loadJSONConfig(new File(System.getProperty("user.dir")+"/src/test/resources/Base64ScreenShotTrue.json"));
        WebDriverManager.chromedriver().clearDriverCache().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://tutorialsninja.com/demo/");
        ExtentTest eTest1 = extent.createTest("Test one");
        eTest1.log(Status.INFO,"Test one is started and navigating to Home Page");
        // taking base64 screenShot directly without recording into the file


     //   eTest1.addScreenCaptureFromBase64String(takeScreenShot(),"Home Page");
        // Make screenshot and bring the screenshot to the log level
        eTest1.log(Status.INFO, MediaEntityBuilder
                .createScreenCaptureFromBase64String(takeScreenShotBase64(),"TutorialNinjaHomePage").build());
        // Second ScreenShot Using path
        eTest1.log(Status.INFO,"HP name entered into the search field");
        driver.findElement(By.name("search")).sendKeys("HP");
       // eTest1.addScreenCaptureFromPath(takeScreenShotAndReturnPath("HP search"),"Hp product Search");


        //Make screenshot and bring the screenshot to the log level
        eTest1.log(Status.INFO,MediaEntityBuilder
                .createScreenCaptureFromPath(takeScreenShotAndReturnPath("HP search"),"HP product search").build());
        eTest1.log(Status.INFO,"Test one is completed");
        extent.flush();//to generate report!!!
        driver.quit();
        //To open generated eReport automatically
        try {
            Desktop.getDesktop().browse(path.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static String takeScreenShotBase64(){
        String base64ScreenShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        return base64ScreenShot;

    }
    public static String takeScreenShotAndReturnPath(String fileName) throws IOException {
        File srcScreenShotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(System.getProperty("usr.dir") + "ScreenShotsKoel/" + fileName + ".png");
        FileUtils.copyFile(srcScreenShotFile, destinationFile);
        return destinationFile.getAbsolutePath();


    }
}