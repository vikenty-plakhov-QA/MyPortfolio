package POM;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SongPage extends BasePage {
    public SongPage(WebDriver givenDriver) {
        super(givenDriver);
    }

    //"#songsWrapper .items > tr:nth-of-type(1) > .title"
    @FindBy(css = "#playlistWrapper .song-item .title")
    WebElement songName;
    @FindBy(css = "[id='songResultsWrapper'] [placeholder='Playlist name']")
    WebElement newPlaylistNameInput;
    By songListlocator = By.cssSelector(" .item-container .title");

    public SongPage searchSongInSearchField(String text) {
        WebElement searchInput = waitUntilClickable(By.cssSelector("[type='search']"));
        searchInput.click();
        searchInput.clear();
        searchInput.sendKeys(text);
        return this;
    }

    public void clickSearchField() {
        WebElement searchInput = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("[type='search']")));
        searchInput.click();
        searchInput.clear();

    }

    public void enterNameInSearchField(String name) {
        WebElement searchField = driver.findElement(By.cssSelector("input[type='search']"));
        searchField.click();
        searchField.clear();
        searchField.sendKeys(name);
    }


    public String getSongText() {
        WebElement title = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("title")));
        String text = title.getText();
        return text;

    }

    public String getSongName() {

        String songText = songName.getText();
        return songText;
    }

    public void createNewPlaylistWhileAddingSong(String playlistName) {

        newPlaylistNameInput.click();
        newPlaylistNameInput.clear();
        newPlaylistNameInput.sendKeys(playlistName);
        new Actions(driver)
                .keyDown(Keys.ENTER)
                .perform();

    }

    public String clickFirstSongInResult() {
        List<WebElement> songsInResults = driver
                .findElements(By.cssSelector(".search-results .song-item .title"));
        songsInResults.get(0).click();
        return songsInResults.get(0).getText();
    }

    public void clickAddToBtn() {
        WebElement addToButtn = driver.findElement(By.cssSelector(".btn-add-to"));
        addToButtn.click();
    }

    public SongPage clickAllViewButtn() {
        WebElement viewAll = wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//button[@data-test='view-all-songs-btn']")));
        viewAll.click();
        return this;
    }

    public void goToAllSongsTub() {
        WebElement allSongs = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector(".songs")));
        allSongs.click();
    }

    public void rightClickOnSong() {
        WebElement playSong = driver.findElement(By.cssSelector(".items>:nth-child(4)"));
        new Actions(driver).contextClick(playSong).perform();
    }

    public void playbackSongFromDropMenu() {
        driver.findElement(By.cssSelector(".playback")).click();
    }

    public void playSongWithPlayBtn() {

        WebElement plyBtn = driver.findElement(By.cssSelector("span.play .fa"));
        new Actions(driver)
                .moveToElement(plyBtn)
                .perform();
        plyBtn.click();
    }

    public void selectSongFromAllSongs() {

        WebElement selectedSong = waitUntilClickable(By.cssSelector("tr:nth-child(3)>.title"));
        selectedSong.click();
    }

    public String checkSearchResult() {
        WebElement findSong = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("strong")));
        String foundSong = findSong.getText();
        return foundSong;


    }

    public List<WebElement> getAllSongWebElementsList() {
        List<WebElement> songList = driver.findElements(songListlocator);
        return songList;
    }

}
