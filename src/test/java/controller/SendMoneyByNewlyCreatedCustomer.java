package controller;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import setup.Setup;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class SendMoneyByNewlyCreatedCustomer extends Setup {

    public SendMoneyByNewlyCreatedCustomer() throws IOException {
        initConfig();

    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public void sendMoneyByNewlyCreatedCustomer() {

        RestAssured.baseURI = prop.getProperty("BASE_URL");

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("TOKEN"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body("{\n" +
                                "    \"from_account\":\"" + prop.getProperty("Customer_Phone_Number") + "\",\n" +
                                "    \"to_account\":\"01686606909\",\n" +

                                "    \"amount\":30\n" +
                                "}")
                        .when()
                        .post("/transaction/sendmoney")

                        .then()
                        .assertThat().statusCode(201).extract().response();

        JsonPath jsonpath = res.jsonPath();
        String message = jsonpath.get("message");
        setMessage(message);

        String currentBalance = jsonpath.get("currentBalance").toString();
        System.out.println(currentBalance);

    }

    public void sendMoneyByCustomerWithoutMoneyAmount(){

        RestAssured.baseURI = prop.getProperty("BASE_URL");

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("TOKEN"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body("{\n" +
                                "    \"from_account\":\"" + prop.getProperty("Customer_Phone_Number") + "\",\n" +
                                "    \"to_account\": \n" +

                                "    \"amount\":50\n" +
                                "}")
                        .when()
                        .post("/transaction/sendmoney")
                        .then()
                        .assertThat().statusCode(400).extract().response();

        JsonPath jsonpath = res.jsonPath();
        String message = jsonpath.get("error.message");
        setMessage(message);

    }


}
