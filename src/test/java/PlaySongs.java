import POM.BasePage;
import POM.LoginPage;
import POM.SongPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlaySongs extends BaseTest {

    @Test
    public void playSong() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(myEmail, myLogin);
        SongPage songPage = new SongPage(driver);
        BasePage basePage = new BasePage(driver);
        songPage.goToAllSongsTub();
        // Play the song

        songPage.playSongWithPlayBtn();

        basePage.isEqualizerDisplayed();
        basePage.isPauseBtnDisplayed();
    }

    @Test
    public void playSongOverPlayBtn() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(myEmail,myLogin);
        SongPage songPage = new SongPage(driver);
        BasePage basePage = new BasePage(driver);
        songPage.goToAllSongsTub();
        songPage.selectSongFromAllSongs();
        songPage.playSongWithPlayBtn();
        Thread.sleep(3000);// to check the sound
        basePage.isEqualizerDisplayed();
    }


    @Test
    public void playSongFromListTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(myEmail, myLogin);
        SongPage songPage = new SongPage(driver);
        BasePage basePage = new BasePage(driver);
        songPage.goToAllSongsTub();
        songPage.rightClickOnSong();
        songPage.playbackSongFromDropMenu();
        basePage.isEqualizerDisplayed();
    }


}