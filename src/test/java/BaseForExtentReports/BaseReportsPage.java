package BaseForExtentReports;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class BaseReportsPage {
    public String takeScreenShot(String testName, WebDriver driver) throws IOException {
        File srcScreenShotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(System
                .getProperty("user.dir")+"/src/test/java/KoelScreenShots/"+testName +".png");
        FileUtils.copyFile(srcScreenShotFile,destinationFile);
        return destinationFile.getAbsolutePath();

    }
}
