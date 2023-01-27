package controller;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import setup.Setup;
import utils.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class DepositToAgent extends Setup {

    public DepositToAgent() throws IOException {
        initConfig();
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void successfullDepositToAgent() throws ConfigurationException {
        RestAssured.baseURI = prop.getProperty("BASE_URL");

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("TOKEN"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body("{\n" +
                                "    \"from_account\":\"SYSTEM\",\n" +
                                "    \"to_account\":\"" + prop.getProperty("Agent_Phone_Number") + "\",\n" +
                                "    \"amount\":100\n" +
                                "}")
                        .when()
                        .post("/transaction/deposit")
                        .then()
                        .assertThat().statusCode(201).extract().response();

        JsonPath jsonpath = res.jsonPath();
        String message = jsonpath.get("message");
        setMessage(message);

        String currentBalance = jsonpath.get("currentBalance").toString();
        System.out.println(currentBalance);
        Utils.setEnvVariable("current_Balance", currentBalance);

    }

    public void InsufficientMoneyForDepositingToAgent() {
        RestAssured.baseURI = prop.getProperty("BASE_URL");

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("TOKEN"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body("{\n" +
                                "    \"from_account\":\"SYSTEM\",\n" +
                                "    \"to_account\":\"" + prop.getProperty("Agent_Phone_Number") + "\",\n" +

                                "    \"amount\":400005200\n" +
                                "}")
                        .when()
                        .post("/transaction/deposit")
                        .then()
                        .assertThat().statusCode(208).extract().response();

        JsonPath jsonpath = res.jsonPath();
        String message = jsonpath.get("message");
        setMessage(message);

    }

}
