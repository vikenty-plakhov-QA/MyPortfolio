package ListenersPackage;

import BaseForExtentReports.BaseReportsPage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MyListeners extends BaseReportsPage implements ITestListener {
// Call method to generate extent report

   ExtentReports report = ExtentReportGenerator.getExtendReport();
    ExtentTest eTest;
    @Override
    public void onTestStart(ITestResult result) {
     String testName =  result.getName();
     // Create a test with report
       eTest =  report.createTest(testName);
       eTest.log(Status.INFO,testName+"started");

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName =  result.getName();
        eTest.log(Status.PASS,testName + "passed successfully").log(Status.INFO, MarkupHelper
                .createLabel("<h1> CONGRATS!", ExtentColor.GREEN));
    }


    @Override
    public void onTestFailure(ITestResult result) {
        String testName =  result.getName();
        eTest.log(Status.FAIL,testName+ "   GOT FAILED");
        //Let's add a ScreenShot
        //Implement driver
        WebDriver driver = null;

        try {

           driver = (WebDriver)result.getTestClass().getRealClass()
                         .getField("driver").get(result.getInstance());

        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        //Create Class BaseReportPage and method takeScreenShot


        try {
            eTest.addScreenCaptureFromPath(takeScreenShot(testName,driver),testName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         eTest.log(Status.INFO,result.getThrowable());
        eTest.assignAuthor("Vikenty Plakhov");
        eTest.assignCategory("Smoke");
        eTest.assignDevice("Windows 10 Chrome131");
        eTest.log(Status.FAIL,"<b>Test  failed</b>");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName =  result.getName();
        eTest.log(Status.INFO,testName+ "  skipped");

    }

    @Override
    public void onFinish(ITestContext context) {
        report.flush();//Otherwise nothing works
        File eRportFile = new File(System.getProperty("user.dir")+"/src/test/java/Koel/eReport.html");
        try {
            Desktop.getDesktop().browse(eRportFile.toURI());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
