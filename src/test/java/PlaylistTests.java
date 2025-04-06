import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static io.restassured.path.json.JsonPath.given;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaylistTests extends BaseTest {
    @Epic("Playlist management")
    @Feature("Create PlayList")
    @Test
    @Description("Auth User can get their playlists data")
    public void getAuthUserPlists() {
        Response getUserPlists = RestAssured.given()
                .headers(getAuthHeaders())
                .get(urlPlaylist)
                .andReturn();
        JsonPath jsonPath = getUserPlists.jsonPath();
        jsonPath.prettyPrint();
        System.out.println(jsonPath.getString("[1].rules[0].rules[0].model"));
        getUserPlists.then().assertThat().body("[0]",hasKey("id"));
        assertEquals(getUserPlists.statusCode(),200,"Unexpected status code");
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
        System.out.println(pListName);
        Assertions.assertEquals(pListName,"Once upon a time","PlistName in response doesn't match to entered ");





    }
    @Test
    @Description("Check response schema of the create playlist call")
    @DisplayName("Response return schema is correct")
    public void playListResponseSchemaValidator(){

        Map<String, String> body = new HashMap<>();
        body.put("name", "Once upon a time");
        Response response =  RestAssured.given()
                .body(body)
                .headers(getAuthHeaders())
                .post(urlPlaylist).andReturn();
        response.prettyPrint();
         Assertions.assertEquals(200,response.statusCode());
         //response schema validation
       response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/playListsSchemaResponse.json"));
        response.then().assertThat().statusCode(200);




    }
}
