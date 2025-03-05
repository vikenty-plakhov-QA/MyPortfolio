import org.testng.asserts.SoftAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

public class Assertions extends BaseTest {

    // Soft assert example
/*    SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(songsNumberBefore == songsNumberAfter,
            "=== Songs number before should be equal songs number after ===");
        softAssert.assertEquals(driver.getCurrentUrl(), "https://bbb.testpro.io/#!/queue");
        System.out.println("Hello world");
        softAssert.assertAll();*/
    public static Boolean onlyOneSongIsInSearchResult(List<WebElement> searchResult) {

        if (searchResult.size() <= 1) {
            return true;

        }
        return false;
    }
}
