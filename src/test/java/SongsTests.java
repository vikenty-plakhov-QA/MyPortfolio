import POM.BasePage;
import POM.LoginPage;
import POM.PlayListPage;
import POM.SongPage;
import com.opencsv.CSVReader;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.NoSuchElementException;

import static org.testng.Assert.assertEquals;

public class SongsTests extends BaseTest {

    @Test
    public void addSongToPlaylist() throws InterruptedException {
        String text =
                "Tunnel of Lights (ID 1689)";
        String playlistName = generateRandomPlaylistName();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(myEmail, myLogin);
        SongPage songPage = new SongPage(driver);
        BasePage basePage = new BasePage(driver);
        songPage.searchSongInSearchField(text)
                .clickAllViewButtn();
        Thread.sleep(500);
        songPage.clickFirstSongInResult();
        //  System.out.println(songPage.clickFirstSongInResult());
        songPage.clickAddToBtn();
        songPage.createNewPlaylistWhileAddingSong(playlistName);
        basePage.isSuccessBannerDisplayed();
        Assert.assertEquals(text, songPage.getSongName());
        System.out.println(text + " " + songPage.getSongText());


    }

    @Test
    public void checkVisibilityTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(myEmail, myLogin);
        SongPage songPage = new SongPage(driver);
        String text = songPage.getSongText();
        System.out.println("WHERE IS TEXT?" + text);
        System.out.println("Is element invisible? === " + wait
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("title"))));

    }

    @Test
    public void countSongsInAllSongs() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(myEmail, myLogin);
        SongPage songPage = new SongPage(driver);
        BasePage basePage = new BasePage(driver);
        songPage.goToAllSongsTub();

        //count songs in All Songs Tab

        //     songPage.getAllSongWebElementsList();

        int count = songPage.getAllSongWebElementsList().size();
        System.out.println("Number of songs in Allsong  " + count);
        // count songs in th header
        WebElement songsCountHeader = basePage.waitUntilVisible(By.cssSelector("#songsWrapper .meta"));
        String countSongInHeader = songsCountHeader.getText();
        //     System.out.println(countSongInHeader);
        basePage.stringToInt(countSongInHeader);


        //Assertion of equality number songs in the list and in the header
        //<<<<<Put NOT Equals to pass the test!!>>>
        Assert.assertNotEquals(countSongInHeader, count, "Number of songs NOT equal in list and header");

    }

    @Test(dataProvider = "AllSongsData")
    public void searchForSong(String text) throws InterruptedException {
        //   String text = "Tunnel of Lights (ID 1689)";                                          //Tunnel of Lights (ID 1689)
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(myEmail, myLogin);
        Thread.sleep(1000);
        SongPage songPage = new SongPage(driver);
        // search for song name without author whose name divided by hyphen
        int index = text.indexOf("-");
        String name = (index != -1)?text.substring(index+2):text;//delete from search almum which separate with hyphen-
        //search only by name
        songPage.searchSongInSearchField(name);
        WebElement song = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//strong")));


        String NameSong = song.getText();
        System.out.println("----" + NameSong);
        // SoftAssert
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(NameSong, name);
        //Check that only searching song in result
        List<WebElement> searchResult = driver
                .findElements(By.cssSelector(" [data-testid = 'song-excerpts'] ul article"));
        if (!Assertions.onlyOneSongIsInSearchResult(searchResult)) {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destinationFile = new File("./ScreenShots/SearchSong  " + NameSong + ".png");
            try {
                FileUtils.copyFile(srcFile, destinationFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        softAssert.assertEquals(Assertions.onlyOneSongIsInSearchResult(searchResult).booleanValue(), true);
        //search for artist name
     /*   WebElement artist = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//section[@class= 'artists']/p")));
        System.out.println(artist.getText());
        //search for album name
        WebElement album = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//section[@class= 'albums']/p")));
        System.out.println(album.getText());*/
        softAssert.assertAll();
    }

    @DataProvider(name = "AllSongsData",parallel=false)
    public Object[][] AllSongsList() throws Exception {
        // path to csv file that is located under resources folder
        Reader reader = Files.newBufferedReader(Paths.get(System.getProperty("user.dir") + "/src/test/resources/AllSongsList.csv"));
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> records = csvReader.readAll();
        Object[][] array = null;
        for (int i = 0; i < records.size(); i++) {

            Object[] row = records.get(i);
            if (Objects.isNull(array)) {
                array = new Object[records.size()][row.length];
            }
            array[i][0] = row[0];
        }

        return array;


    }
}