package controller;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import setup.Setup;
import utils.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class CreateAgent extends Setup {

    public CreateAgent() throws IOException {
        initConfig();
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void createAgent() throws ConfigurationException {

        Faker faker = new Faker();

        String name = faker.name().firstName();
        String email = name + "@gmail.com";
        String password = faker.internet().password();
        String phoneNumber = "0167"+ Utils.generateRandomNumber(1000000, 9999999);
        String nid = "19" + Utils.generateRandomNumber(10000000, 99999999);
        String role = "Agent";

        RestAssured.baseURI = prop.getProperty("BASE_URL");

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("TOKEN"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body("{\n" +
                                "    \"name\":\"" + name + "\",\n" +
                                "    \"email\":\"" + email + "\",\n" +
                                "    \"password\":\"" + password + "\",\n" +
                                "    \"phone_number\":\"" + phoneNumber + "\",\n" +
                                "    \"nid\":\"" + nid + "\",\n" +
                                "    \"role\":\"Agent\"\n" +
                                "}")
//                        .body(createAgentModel)
                        .when()
                        .post("/user/create")
                        .then().assertThat().statusCode(201).extract().response();

        JsonPath jsonpath = res.jsonPath();
        String message = jsonpath.get("message");
        setMessage(message);

        String agentPhoneNumber = jsonpath.get("user.phone_number").toString();
        System.out.println(agentPhoneNumber);
        Utils.setEnvVariable("Agent_Phone_Number", agentPhoneNumber);
    }

    public void alreadyCreatedAgent(){

        RestAssured.baseURI = prop.getProperty("BASE_URL");

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("TOKEN"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body("{\n" +
                                "    \"name\":\"" + "Gerald" + "\",\n" +
                                "    \"email\":\"" +  "Gerald@gmail.com" + "\",\n" +
                                "    \"password\":\"" + "1a09s1lz2g720x" + "\",\n" +
                                "    \"phone_number\":\"" + "01677936638" + "\",\n" +
                                "    \"nid\":\"" + "1938778373" + "\",\n" +
                                "    \"role\":\"Agent\"\n" +
                                "}")
//                        .body(createCustomerModel)
                        .when()
                        .post("/user/create")
                        .then().assertThat().statusCode(208).extract().response();

        JsonPath jsonpath = res.jsonPath();
        String message = jsonpath.get("message");
        setMessage(message);

    }




}
