package controller;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import setup.Setup;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class GetCustomerId extends Setup {

    public GetCustomerId() throws IOException {
        initConfig();
    }

    public void getCustomerId(){

        RestAssured.baseURI = prop.getProperty("BASE_URL");

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("TOKEN"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .when().get("/user/search?id=" + prop.getProperty("Customer-id"))
                        .then().assertThat().statusCode(200).extract().response();
        JsonPath jsonpath = res.jsonPath();

        System.out.println(jsonpath.get("user.id").toString());
        Assert.assertEquals(jsonpath.get("user.id").toString(), prop.getProperty("Customer-id"));

    }


    public void getInvalidCustomerId(){

        RestAssured.baseURI = prop.getProperty("BASE_URL");

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("TOKEN"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .when().get("/user/search?id=" + "123456789")
                        .then().assertThat().statusCode(200).extract().response();
        JsonPath jsonpath = res.jsonPath();
        System.out.println(jsonpath.get("user.id") == null);
        Assert.assertTrue(jsonpath.get("user.id") == null);
    }

}
