import POM.BasePage;
import POM.LoginPage;
import POM.SongPage;
import com.opencsv.CSVReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class CSVDataProviderTest extends BaseTest {

    @DataProvider(name = "getSongsData")
    // Method to read the data from .csv file and return it as array
    public Object[][] getData() throws Exception {
        // path to csv file that is located under resources folder
        Reader reader = Files.newBufferedReader(Paths.get(System.getProperty("user.dir") + "/src/test/resources/Songs.csv"));
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> records = csvReader.readAll();
        Object[][] array = null;
        for (int i = 0; i < records.size(); i++) {

            Object[] row = records.get(i);
            if (Objects.isNull(array)) {
                array = new Object[records.size()][row.length];
            }
            array[i][0] = row[0];
            //      array[i][1] = row[1];
            //       array[i][2] = row[3];
        }
        return array;


    }

    @Test(dataProvider = "getSongsData")
    public void searchForSong(String song) {
        LoginPage loginPage = new LoginPage(driver);
        SongPage songPage = new SongPage(driver);
        loginPage.login(myEmail, myLogin);
        songPage.clickSearchField();
        songPage.enterNameInSearchField(song);
        songPage.checkSearchResult();
        System.out.println(songPage.checkSearchResult());
        Assert.assertEquals(songPage.checkSearchResult(), song);


    }
}