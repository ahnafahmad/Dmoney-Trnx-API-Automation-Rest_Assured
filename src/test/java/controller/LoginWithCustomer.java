package controller;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.LoginModel;
import org.apache.commons.configuration.ConfigurationException;
import setup.Setup;
import utils.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class LoginWithCustomer extends Setup {

    public LoginWithCustomer() throws IOException {
        initConfig();
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void callingLoginAPI() throws ConfigurationException {
        RestAssured.baseURI = prop.getProperty("BASE_URL");

        Response res =
                given()
                        .contentType("application/json")
                        .body("{\n" +
                                "    \"email\":\"" + prop.getProperty("Customer_Email") + "\",\n" +
                                "    \"password\":\"" + prop.getProperty("Customer_Password") + "\"\n" +
                                "}")
                        .when()
                        .post("/user/login")
                        .then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath jsonpath = res.jsonPath();
        String token = jsonpath.get("token");
        String message = jsonpath.get("message");
        setMessage(message);
        Utils.setEnvVariable("TOKEN", token);
    }

    public void loginApiWithWrongEmail(String email, String password){
        RestAssured.baseURI = prop.getProperty("BASE_URL");
        LoginModel loginModel = new LoginModel(email, password);
        Response res =
                given()
                        .contentType("application/json")
                        .body(loginModel)
                        .when()
                        .post("/user/login")
                        .then()
                        .assertThat().statusCode(404).extract().response();

        JsonPath jsonpath = res.jsonPath();
        String message = jsonpath.get("message");
        setMessage(message);
    }

}
