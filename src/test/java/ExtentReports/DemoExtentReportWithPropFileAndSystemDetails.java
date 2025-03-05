package ExtentReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DemoExtentReportWithPropFileAndSystemDetails {
   static WebDriver driver;
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        Properties prop = new Properties();

        File file= new File(System.getProperty("user.dir")+"/src/test/resources/data.properties");
        FileInputStream propFile = new FileInputStream(file);
        try {
            prop.load(propFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.get(prop.getProperty("url"));

        ExtentReports eReport = new ExtentReports();
        File eReportFile = new File(System
                .getProperty("user.dir") + "/src/test/java/ExtentReports/ReportsFolder/eReportWithSystemData.html");
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(eReportFile);
        sparkReporter.config().setTimeStampFormat("MM-dd-yyyy h:mm:ss a");
        eReport.attachReporter(sparkReporter);


        //Set app url in eReport
       eReport.setSystemInfo("Application URL:  ", prop.getProperty("url"));
       // Add System and Browser info to the eReport
        Capabilities cap = ((RemoteWebDriver)driver).getCapabilities();
        eReport.setSystemInfo("Browser Name:  ",cap.getBrowserName());
        eReport.setSystemInfo("Browser version:  ", cap.getBrowserVersion());
        // add info from the System
       // System.getProperties().list(System.out);// All System properties list
        eReport.setSystemInfo("Operating System:  ",System.getProperty("os.name"));
        eReport.setSystemInfo("Java version: ",System.getProperty("java.version"));

        driver.findElement(By.cssSelector("[type='email']")).sendKeys(prop.getProperty("myEmail"));
        driver.findElement(By.cssSelector("[type='password']")).sendKeys(prop.getProperty("myLogin" ));
        String ScreenPath = null;
        try {
            ScreenPath = takeScreenShot("LoginPageKoel");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(1000);
        String HomeScreenPath = null;
        try {
          HomeScreenPath = takeScreenShot("HomePageKoel");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        driver.quit();

        ExtentTest eTest1 = eReport.createTest("Test One","This is the test description");
        eTest1.log(Status.INFO,"<h1>Chrome is started</h1>");//HEADER
        eTest1.log(Status.INFO,"<b>Browser got closed</b>");//bold
        eTest1.log(Status.WARNING,"Waiting is too long");
        eTest1.log(Status.PASS,"<i>Test One passed</i>");//italic
        // Assign author and tech INFO test one
        eTest1.assignAuthor("Vikenty Plakhov");
        eTest1.assignCategory("Smoke");
        eTest1.assignDevice("Windows 10 Chrome131");
        eTest1.log(Status.FAIL,"<u>Test one failed</u.");//1st level <u> -> underlined

        ExtentTest eTest2 = eReport.createTest("Test two","<h1>This is KoelHomePage</h1>")
                .log(Status.INFO, MarkupHelper.createLabel("<h1>KoelHomePage</h1>", ExtentColor.GREEN));//description of th test added
        eTest2.addScreenCaptureFromPath(ScreenPath,"<b>Login Page</b>");
        eTest2.addScreenCaptureFromPath(HomeScreenPath,"<b>HomePageKoel</b> ");//Screenshot in ExtentReport
        eTest2.log(Status.INFO,"<b>KoelHomePage</b>");
        eTest2.log(Status.FAIL,"Test two got failed");
        eTest2.assignAuthor("Vikenty Plakhov");
        eTest2.assignCategory("Sanity");
        eTest2.assignDevice("Windows 10 FireFox");

        ExtentTest eTest3= eReport.createTest("Test Three","This is a description of Test Three");
        eTest3.log(Status.INFO,"Test Three is started");
        eTest3.log(Status.SKIP,"Test three got skipped");
        eTest3.assignAuthor("Vikenty Plakhov");
        eTest3.assignCategory("Regression");
        eTest3.assignDevice("Windows 10 Edge");

        ExtentTest eTest4 = eReport.createTest("Test Four","This is a description of Test Four");
        eTest4.log(Status.INFO,"Test Four is started");
        eTest4.log(Status.PASS,"Test three got passed");
        eTest4.log(Status.INFO, MarkupHelper.createLabel("<h1>KoelHomePage</h1>", ExtentColor.BLUE));//highlight text
        eTest4.assignAuthor("VIP");
        eTest4.assignCategory("Regression");
        eTest4.assignDevice("Windows 10 Chrome 131");

        ExtentTest eTest5 = eReport.createTest("Test Five","This is a description of Test Four");
        eTest5.log(Status.INFO,"Test Five is started");
        eTest5.log(Status.PASS,"Test Five got passed");
        eTest5.assignAuthor("VIP","Oleg","Aarun");
        eTest5.assignCategory("Regression","Smoke");
        eTest5.assignDevice("Windows 10 Firefox");

        ExtentTest eTest7 = eReport.createTest("TestSeven","This is a description of Test Four");
        eTest7.log(Status.INFO,"Test Five is started");
        eTest7.log(Status.PASS,"Test Five got passed");
        eTest7.assignAuthor(new String[]{"VIP","Oleg","Aarun"});
        eTest7.assignCategory(new String[]{"Regression"," Smoke"," Sanity"});
        eTest7.assignDevice("Windows 10   Firefox");

        eReport.flush();//to generate report!!!

        //To open generated eReport automatically
        try {
            Desktop.getDesktop().browse(eReportFile.toURI());
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

