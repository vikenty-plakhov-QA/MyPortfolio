package ListenersPackage;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

public class ExtentReportGenerator {
    public static ExtentReports getExtendReport(){
        ExtentReports report = new ExtentReports();
        File file = new File(System.getProperty("user.dir")+"./src/test/java/Koel/eReport.html");
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(file);
        // Adjust ExtentReport view
        sparkReporter.config().setTheme(Theme.DARK);
        //name
        sparkReporter.config().setReportName("Koel Tests execution result");
        // title of the report page
        sparkReporter.config().setDocumentTitle("QA Koel Report");
        // Change timeStamp format in the US style
        sparkReporter.config().setTimeStampFormat("MM-dd-yyyy h:mm:ss a");// a --> PM/AM
        report.attachReporter(sparkReporter);
         return report;
    }
}
