package controller;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import setup.Setup;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class DepositToCustomer extends Setup {

    public DepositToCustomer() throws IOException {
        initConfig();
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void successfullDepositToCustomer(){

        RestAssured.baseURI = prop.getProperty("BASE_URL");

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("TOKEN"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body("{\n" +
                                "    \"from_account\":\""+prop.getProperty("Agent_Phone_Number")+"\",\n" +
                                "    \"to_account\":\"" + prop.getProperty("Customer_Phone_Number") + "\",\n" +

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

    }

    public void depositToInvalidCustomer(){

        RestAssured.baseURI = prop.getProperty("BASE_URL");

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("TOKEN"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body("{\n" +
                                "    \"from_account\":\""+prop.getProperty("Agent_Phone_Number")+"\",\n" +
                                "    \"to_account\":\" 01521428605 \",\n" +

                                "    \"amount\":100\n" +
                                "}")
                        .when()
                        .post("/transaction/deposit")
                        .then()
                        .assertThat().statusCode(404).extract().response();

        JsonPath jsonpath = res.jsonPath();
        String message = jsonpath.get("message");
        setMessage(message);

    }


}
