package ExtentReports;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DemoGenerateReportWithStepScreenShot {
   public static WebDriver driver;
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();

        File file= new File(System.getProperty("user.dir")+"/src/test/resources/data.properties");
        FileInputStream propFile = new FileInputStream(file);
        try {
            prop.load(propFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        WebDriverManager.chromedriver().clearDriverCache().setup();
        driver = new ChromeDriver();
       driver.manage().window().maximize();
       driver.get(prop.getProperty("url"));


       //HERE WE ARE TAKING SCREENSHOT ON THE TEST LEVEL
      // Create a method to get KoelScreenShot
      /*File KoelHomeScreenShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String KoelScreenPath = System.getProperty("user.dir")+"./src/test/java/Koel/KoelHomeScreenShot.png";
        FileUtils.copyFile(KoelHomeScreenShot,new File(KoelScreenPath));*/


        String KoelScreenPath = takeScreenShot("HomePageKoel");
        ExtentReports extent = new ExtentReports();//extent is an engine
        // create object extentSparkReporter
        File path = new File(System.getProperty("user.dir") + "/src/test/java/ExtentReports/ReportsFolder/eReport.html");
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(path);
        sparkReporter.config().setTimeStampFormat("MM-dd-yyyy h:mm:ss a");

        // attach reporter to the engine
        extent.attachReporter(sparkReporter);
        //add Url into Report
        extent.setSystemInfo("App URL:  ",prop.getProperty("url"));
        //Add browser name (Selenium)
        Capabilities cap = ((RemoteWebDriver)driver).getCapabilities();
        extent.setSystemInfo("Browser Name:  ",cap.getBrowserName());
        extent.setSystemInfo("Browser version:  ", cap.getBrowserVersion());
        // add info from the System
        System.getProperties().list(System.out);// All System properties list
        extent.setSystemInfo("Operating System:  ",System.getProperty("os.name"));
        extent.setSystemInfo("Java version: ",System.getProperty("java.version"));



        ExtentTest eTest1 = extent.createTest("Test One","This is the test description");
        eTest1.log(Status.INFO,"<h1>Chrome is started</h1>");//HEADER
        eTest1.log(Status.INFO,"<b>Browser got closed</b>");//bold
        eTest1.log(Status.WARNING,"Waiting is too long");
        eTest1.log(Status.PASS,"<i>Test One passed</i>");//italic
        // Assign author and tech INFO test one
        eTest1.assignAuthor("Vikenty Plakhov");
        eTest1.assignCategory("Smoke");
        eTest1.assignDevice("Windows 10 Chrome131");
        eTest1.log(Status.FAIL,"<u>Test one failed</u.");//1st level <u> -> underlined

     ExtentTest eTest2 = extent.createTest("Test two","<h1>This is KoelHomePage</h1>")
             .log(Status.INFO, MarkupHelper.createLabel("<h1>KoelHomePage</h1>", ExtentColor.GREEN));//description of th test added
        eTest2.addScreenCaptureFromPath(KoelScreenPath,"<i><b>Koel Home Page</b></i> ");//Screenshot in ExtentReport
        eTest2.log(Status.INFO,"<b>KoelHomePage</b>");
        eTest2.log(Status.INFO, MarkupHelper.createLabel("<h1>KoelHomePage</h1>", ExtentColor.BLUE));//highlight text
        eTest2.log(Status.FAIL,"Test two got failed");
        eTest2.assignAuthor("Vikenty Plakhov");
        eTest2.assignCategory("Sanity");
        eTest2.assignDevice("Windows 10 FireFox");
        //another ScreenShot
        driver.findElement(By.cssSelector("[type='email']")).sendKeys("VIPlakh@mail.com");
        String KoelEmailField = takeScreenShot("KoelEmail");
       eTest2.addScreenCaptureFromPath(KoelEmailField,"<b>Koel Email Enter</b>");

        ExtentTest eTest3= extent.createTest("Test Three","This is a description of Test Three");
        eTest3.log(Status.INFO,"Test Three is started");
        eTest3.log(Status.SKIP,"Test three got skipped");
        eTest3.assignAuthor("Vikenty Plakhov");
        eTest3.assignCategory("Regression");
        eTest3.assignDevice("Windows 10 Edge");

        ExtentTest eTest4 = extent.createTest("Test Four","This is a description of Test Four");
        eTest4.log(Status.INFO,"Test Four is started");
        eTest4.log(Status.PASS,"Test three got passed");
        eTest4.assignAuthor("VIP");
        eTest4.assignCategory("Regression");
        eTest4.assignDevice("Windows 10 Chrome 131");

        ExtentTest eTest5 = extent.createTest("Test Five","This is a description of Test Four");
        eTest5.log(Status.INFO,"Test Five is started");
        eTest5.log(Status.PASS,"Test Five got passed");
        eTest5.assignAuthor("VIP","Oleg","Aarun");
        eTest5.assignCategory("Regression","Smoke");
        eTest5.assignDevice("Windows 10 Firefox");

        ExtentTest eTest7 = extent.createTest("TestSeven","This is a description of Test Four");
        eTest7.log(Status.INFO,"Test Five is started");
        eTest7.log(Status.PASS,"Test Five got passed");
        eTest7.assignAuthor(new String[]{"VIP","Oleg","Aarun"});
        eTest7.assignCategory(new String[]{"Regression"," Smoke"," Sanity"});
        eTest7.assignDevice("Windows 10   Firefox");

        extent.flush();//to generate report!!!
        driver.quit();
        //To open generated eReport automatically
        try {
            Desktop.getDesktop().browse(path.toURI());
        } catch (IOException e) {
           e.printStackTrace();
        }


        }
    public static String takeScreenShot(String fileName) throws IOException {
        File KoelHomeScreenShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String KoelScreenPath = System.getProperty("user.dir")+"./src/test/java/Koel/"+fileName+".png";
        FileUtils.copyFile(KoelHomeScreenShot,new File(KoelScreenPath));
        return KoelScreenPath;
    }
}
