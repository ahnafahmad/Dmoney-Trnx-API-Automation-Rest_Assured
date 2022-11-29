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

    public void createCustomer() throws IOException, ConfigurationException {

        Faker faker = new Faker();

        String name = faker.name().firstName();
        String email = name + "@gmail.com";
        String password = faker.internet().password();
        String phoneNumber = "0167"+ Utils.generateRandomNumber(1000000, 9999999);
        String nid = "19" + Utils.generateRandomNumber(10000000, 99999999);
        String role = "Customer";

        RestAssured.baseURI = prop.getProperty("BASE_URL");
//        CreateCustomerModel createCustomerModel= new CreateCustomerModel(name,email, password,phoneNumber,nid,role);

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
//                        .body(createCustomerModel)
                        .when()
                        .post("/user/create")
                        .then().assertThat().statusCode(201).extract().response();

        JsonPath jsonpath = res.jsonPath();
        String message = jsonpath.get("message");
        setMessage(message);

        String id = jsonpath.get("user.id").toString();
        System.out.println(id);

        String cutomerNumber = jsonpath.get("user.phone_number").toString();
        System.out.println(cutomerNumber);

        Utils.setEnvVariable("Customer-id", id);
        Utils.setEnvVariable("Customer_Phone_Number", cutomerNumber);

    }

    public void alreadyCreatedCustomer() throws IOException, ConfigurationException {

        RestAssured.baseURI = prop.getProperty("BASE_URL");

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("TOKEN"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body("{\n" +
                                "    \"name\":\"" + "Lindsey" + "\",\n" +
                                "    \"email\":\"" +  "Lindsey@gmail.com" + "\",\n" +
                                "    \"password\":\"" + "o5fhu5wj2" + "\",\n" +
                                "    \"phone_number\":\"" + "01678959060" + "\",\n" +
                                "    \"nid\":\"" + "1933957246" + "\",\n" +
                                "    \"role\":\"Customer\"\n" +
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
