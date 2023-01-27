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

public class CreateCustomer extends Setup {

    public CreateCustomer() throws IOException {
        initConfig();
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void createCustomer() throws ConfigurationException {

        Faker faker = new Faker();

        String name = faker.name().firstName();
        String email = name + "@gmail.com";
        String password = faker.internet().password();
        String phoneNumber = "0167"+ Utils.generateRandomNumber(1000000, 9999999);
        String nid = "19" + Utils.generateRandomNumber(10000000, 99999999);
        String role = "Customer";

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
                                "    \"role\":\"Customer\"\n" +
                                "}")
                        .when()
                        .post("/user/create")
                        .then()
                        .assertThat().statusCode(201).extract().response();

        JsonPath jsonpath = res.jsonPath();
        String message = jsonpath.get("message");
        setMessage(message);

        String id = jsonpath.get("user.id").toString();
        System.out.println(id);

        String cutomerNumber = jsonpath.get("user.phone_number").toString();
        System.out.println(cutomerNumber);

        String customerEmail = jsonpath.get("user.email").toString();
        System.out.println(customerEmail);

        String customerPassword = jsonpath.get("user.password").toString();
        System.out.println(customerPassword);

        String customerName = jsonpath.get("user.name").toString();
        System.out.println(customerName);

        String customerNid = jsonpath.get("user.nid").toString();
        System.out.println(customerNid);

        Utils.setEnvVariable("Customer-id", id);
        Utils.setEnvVariable("Customer_Phone_Number", cutomerNumber);
        Utils.setEnvVariable("Customer_Email", customerEmail);
        Utils.setEnvVariable("Customer_Password", customerPassword);
        Utils.setEnvVariable("Customer_Name", customerName);
        Utils.setEnvVariable("Customer_Nid", customerNid);

    }

    public void alreadyCreatedCustomer(){

        RestAssured.baseURI = prop.getProperty("BASE_URL");

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("TOKEN"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body("{\n" +
                                "    \"name\":\"" + "Carly" + "\",\n" +
                                "    \"email\":\"" +  "Carly@gmail.com" + "\",\n" +
                                "    \"password\":\"" + "tnjqujds93c2iv1" + "\",\n" +
                                "    \"phone_number\":\"" + "01675219856" + "\",\n" +
                                "    \"nid\":\"" + "1966930000" + "\",\n" +
                                "    \"role\":\"Customer\"\n" +
                                "}")
                        .when()
                        .post("/user/create")
                        .then().assertThat().statusCode(208).extract().response();

        JsonPath jsonpath = res.jsonPath();
        String message = jsonpath.get("message");
        setMessage(message);
    }
}
