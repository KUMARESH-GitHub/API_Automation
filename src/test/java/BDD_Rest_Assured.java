import io.cucumber.java.en.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.IOException;

import static io.restassured.RestAssured.*;

public class BDD_Rest_Assured {
    Response response;
    int statusCode;

    @Given("Checking the GET method")
    public void httpGetMethod() {
        response = get("https://reqres.in/api/users?page=2"); /* Get Method */
        JsonPath path = response.jsonPath(); /* Json Path */
        System.out.println("JSON Response: " + path);
    }

    @And("status code of GET method occured")
    public void getStatusCode(){
        statusCode = response.getStatusCode(); /* Get Status Code */
        System.out.println("Status Code: " + statusCode);
    }

    @When("Check the status code")
    public void validatestatusCode(){
        Assert.assertEquals(statusCode , 200, "Failed with incorrect matches!!"); /* Assertion Validation */
    }
}