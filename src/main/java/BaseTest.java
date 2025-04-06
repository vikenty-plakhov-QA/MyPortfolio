import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {
public String urlAuth = "https://qa.koel.app/api/me";
public String urlAppData = "https://qa.koel.app/api/data";
public String urlPlaylist = "https://qa.koel.app/api/playlist";
public String email = "vikenty.plakhov@testpro.io";
public String password = "MEGAdelta06";


public  Response loginUserSuccess(){
    Map<String, String> userData = new HashMap<>();
    userData.put("email",email);
    userData.put("password",password);
    Map<String,String>headers = new HashMap<>();

    headers.put("Content-Type","application/json");
    return RestAssured.given()
            .body(userData)
            .headers(headers)
            .post(urlAuth)
            .andReturn();
}
public HashMap<String,String>getAuthHeaders(){
    Response response =loginUserSuccess();
    String token = response.jsonPath().getString("token");
    HashMap<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");
    headers.put("Authorization", "Bearer " + token);
    return headers;


}

}
