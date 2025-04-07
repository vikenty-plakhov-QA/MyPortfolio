import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class userRegTest extends BaseTest {
    public  String token;
    @Test
    public void userAuthTest() {
        Response responseForAuth = loginUserSuccess();
        this.token = responseForAuth.jsonPath().getString("token");
        System.out.println(token);
        responseForAuth.then().assertThat().body("$", hasKey("token"));

    }

    @Test
    public void getAuthUserDataTest() {
          Response responseGetUserData = RestAssured.given()
                .headers(getAuthHeaders())
                .get(urlAppData)
                .andReturn();
        responseGetUserData.then().assertThat().body("$", hasKey("albums"));
        assertEquals(responseGetUserData.statusCode(),200,"Unexpected Status Code");
    }
    @Test
    public void logOutAuthUserSuccess(){

        Response responseLogOutUser = RestAssured.given()
                .headers(getAuthHeaders())
                .delete(urlAuth)
                .andReturn();
        System.out.println(responseLogOutUser.asString());
        assertEquals(responseLogOutUser.statusCode(),204,"Unexpected Status Code");

    }
    @ParameterizedTest
    @CsvSource({"vikenty.plakhov@testpro.io,200",//"longlonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglong@gmail.com,401",
    /*"gmail.com,422",*/"\"\",302",/*"null,302"*/})

    public void negativeAuthWithInvalidEmail(String email,int code){
        Map<String, String> userData = new HashMap<>();
        userData.put("email",email);
        userData.put("password",password);
        Map<String,String>headers = new HashMap<>();

        headers.put("Content-Type","application/json");
        Response response= RestAssured.given()
                .body(userData)
                .headers(headers)
                .post(urlAuth)
                .andReturn();
       int token = response.getStatusCode();
        System.out.println(token);
        response.prettyPrint();
    //   Assertions.assertEquals(code, response.statusCode());


    }

}
