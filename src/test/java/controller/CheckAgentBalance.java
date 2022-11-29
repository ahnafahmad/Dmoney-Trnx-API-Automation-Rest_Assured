package controller;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import setup.Setup;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class CheckAgentBalance extends Setup {

    public CheckAgentBalance() throws IOException {
        initConfig();
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void checkAgentBalance(){

        RestAssured.baseURI = prop.getProperty("BASE_URL");

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("TOKEN"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .when().get("/transaction/balance/" + prop.getProperty("Agent_Phone_Number"))
                        .then().assertThat().statusCode(200).extract().response();
        JsonPath jsonpath = res.jsonPath();

        System.out.println(jsonpath.get("balance").toString());

        String message = jsonpath.get("message");
        setMessage(message);

    }

    public void checkInvalidAgentBalance(){

        RestAssured.baseURI = prop.getProperty("BASE_URL");

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("TOKEN"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .when().get("/transaction/balance/01813528647")
                        .then().assertThat().statusCode(200).extract().response();
        JsonPath jsonpath = res.jsonPath();

        System.out.println(jsonpath.get("balance").toString());

        int balance = jsonpath.get("balance");

        Assert.assertEquals(balance,0);

    }

}
