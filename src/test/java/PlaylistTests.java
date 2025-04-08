import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static io.restassured.path.json.JsonPath.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaylistTests extends BaseTest {
    int pList_id;
    @Epic("Playlist management")
    @Feature("Create PlayList")
    @Test
    @Description("Auth User can get their playlists data")
    @DisplayName("Playlist's names of the AuthUser")
    public void getAuthUserPlists() {
        Response getUserPlists = RestAssured.given()
                .headers(getAuthHeaders())
                .get(urlPlaylist)
                .andReturn();
        JsonPath jsonPath = getUserPlists.jsonPath();
        jsonPath.prettyPrint();
        System.out.println(jsonPath.getString("[1].rules[0].rules[0].model"));
        getUserPlists.then().assertThat().body("[0]", hasKey("id"));
        assertEquals(getUserPlists.statusCode(), 200, "Unexpected status code");
    }

    @Test
    @Description("Auth user can create new Playlist")
    @DisplayName("Auth user creates playlist")
    public void createPlistByAuthUser() {
        Map<String, String> body = new HashMap<>();
        body.put("name", "Once upon a time");
        JsonPath response = RestAssured.given()
                .body(body)
                .headers(getAuthHeaders())
                .post(urlPlaylist)
                .jsonPath();
        String pListName = response.getString("name");
         pList_id = response.get("id");
        System.out.println(pListName + "-----" + pList_id);
        Assertions.assertEquals(pListName, "Once upon a time", "PlistName in response doesn't match to entered ");


    }

    @Test
    @Description("Check response schema of the create playlist call")
    @DisplayName("Response return schema is correct")
    public void playListResponseSchemaValidator() {

        Map<String, String> body = new HashMap<>();
        body.put("name", "Once upon a time");
        Response response = RestAssured.given()
                .body(body)
                .headers(getAuthHeaders())
                .post(urlPlaylist).andReturn();
        response.prettyPrint();
        Assertions.assertEquals(200, response.statusCode());
        //response schema validation
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/playListsSchemaResponse.json"));
        response.then().assertThat().statusCode(200);

    }

    @Test
    @Description("Check if authUser can rename created PlayList")
    @DisplayName("PlayList renaming by auth User")
    public void renamePlayList() {
        Map<String, String> body = new HashMap<>();
        body.put("name", "Once upon a time");
        JsonPath response = RestAssured.given()
                .body(body)
                .headers(getAuthHeaders())
                .post(urlPlaylist)
                .jsonPath();
         pList_id = response.get("id");
        Map<String, String> bodyNew = new HashMap<>();
        bodyNew.put("name", "Urban Killer");
        Response responseForNew = RestAssured.given()
                .headers(getAuthHeaders())
                .body(bodyNew)
                .put(urlPlaylist +(pList_id))
                .andReturn();
        Assertions.assertEquals(200, responseForNew.statusCode(), "Unexpected Status Code");
        Assertions.assertEquals("Urban Killer", responseForNew.jsonPath().getString("name"));

    }
    @Test
    public void replacePlistContent(){
        //create plist
        Map<String, String> body = new HashMap<>();
        body.put("name", "Once upon a time");
        JsonPath response = RestAssured.given()
                .body(body)
                .headers(getAuthHeaders())
                .post(urlPlaylist)
                .jsonPath();

        pList_id = response.get("id");
        System.out.println(pList_id);
        //add song to th pList
        Map<String,List<String>> songs = new HashMap<>();
        songs.put("songs", Arrays.asList("da98f9eb65971e2d76d9ec3640bab317","cf03e66adbdf8380a2e993fdbf93b3fe"));
        RestAssured.given()
                .headers(getAuthHeaders())
                .body(songs)
                .put(urlPlaylist+pList_id+"/sync")
                .andReturn();
        //Check this song in the pList
        List<String>songList = new ArrayList<>();
        Response responseForCheck = RestAssured.given()
                .headers(getAuthHeaders())
                .get(urlPlaylist+pList_id+"/songs")
                .andReturn();
        songList = responseForCheck.jsonPath().getList("",String.class);
        responseForCheck.then().assertThat().body("$",hasItem("da98f9eb65971e2d76d9ec3640bab317"));
        //Just for double check - delete before prod!
        System.out.println("songs");
        for (String song:songList){
            System.out.println(song);
        }

    }
}
