import POM.BasePage;
import POM.LoginPage;
import POM.PlayListPage;
import POM.SongPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.Keys.ENTER;

public class PlayListTests extends BaseTest {

    @Test
    public void deletePlaylistTest() throws InterruptedException {
        String playlistName = generateRandomPlaylistBookName();
        System.out.println(playlistName);
        PlayListPage playListPage = new PlayListPage(driver);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(myEmail, myLogin);
        playListPage.plusBtnClick();
        playListPage.goToPlayListField();
        playListPage.createNewPlaylist(playlistName);
        //Assertions of playlist name
        playListPage.checkPlayListName(playlistName);
        playListPage.isSuccessBannerDisplayed();
        //delete playlist
        playListPage.deleteCreatedPlaylist();
        //Assertions
        Thread.sleep(1000);//left it because of instability
        playListPage.isPlayListDeleted(playlistName);

    }


    @Test
    public void renamePlayList() {
        String newPlayLIstName = "Mermaid";
        PlayListPage playListPage = new PlayListPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(myEmail, myLogin);

        playListPage.choosePlayListToDelete();
        playListPage.enterPlaylistName(newPlayLIstName);
        System.out.println(newPlayLIstName);
        System.out.println(playListPage.getPlaylistName());
        Assert.assertEquals(newPlayLIstName, playListPage.getPlaylistName());
    }

    @Test
    public void renamePlistWithEditBtn() {
        String newName = "Sausage Dog";
        PlayListPage playListPage = new PlayListPage(driver);
        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(myEmail, myLogin);
        playListPage.rightClickToEditPlistName();
        playListPage.enterPlaylistName(newName);
        basePage.isSuccessBannerDisplayed();
        Assert.assertEquals(newName, playListPage.getPlaylistName());
        System.out.println(newName + "  " + playListPage.getPlaylistName());

    }

    @Test
    public void deletePlaylistAddingSongsByDragging() throws InterruptedException {
        //  String playlistName = generateRandomPlaylistBookName();
        String playlistName = "00000001";
        System.out.println(playlistName);
        //create playlist
        PlayListPage playListPage = new PlayListPage(driver);
        SongPage songPage = new SongPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(myEmail, myLogin);
        playListPage.plusBtnClick();
        playListPage.goToPlayListField();
        playListPage.createNewPlaylist(playlistName);
        playListPage.isSuccessBannerDisplayed();
        //chose the song to drag to playlist
        songPage.goToAllSongsTub();
        //add song to playlist with dragging it from Allsongs

        playListPage.dragSongToPlaylist();

        playListPage.isSuccessBannerDisplayed();
        //delete playlist

        playListPage.deleteCreatedPlaylist();
        playListPage.clickOKbuttonToDeletePlist();


        //Assertions
        Thread.sleep(1000);//left it because of instability

        playListPage.isPlayListDeleted(playlistName);
        playListPage.isSuccessBannerDisplayed();


    }
}
//#playlists li:nth-child(3)