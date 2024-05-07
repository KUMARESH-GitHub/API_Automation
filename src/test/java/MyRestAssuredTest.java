import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import io.cucumber.java.en.*;

public class MyRestAssuredTest {
    Response response;

    @Test(enabled = true)
    public void rest_Assured_Methods() {

        response = get("https://api.thecatapi.com/v1/images/0XYvRd7oD"); /* Get Method */

        String id = response.getSessionId(); /* Get Session ID */
        System.out.println("Session ID: " + id);

        String contentType = response.getContentType(); /* Get Content Type */
        System.out.println("Content-Type value: " + contentType);

        int statusCode = response.getStatusCode(); /* Get Status Code */
        System.out.println("Status Code: " + statusCode);

        String statusLine = response.getStatusLine(); /* Get Status Line */
        System.out.println("Status Line: " + statusLine);

        Headers allHeaders = response.getHeaders(); /* Get Headers */
        for (Header header : allHeaders) {
            System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
        }

        String contentType1 = response.header("Content-Type");
        System.out.println("Content-Type value: " + contentType1);
        String serverType = response.header("Server");
        System.out.println("Server value: " + serverType);
        String date = response.header("Date");
        System.out.println("Date value: " + date);
        String contentEnco = response.header("Content-Encoding");
        System.out.println("Content-Encoding: " + contentEnco);
        String transferEnco = response.header("Transfer-Encoding");
        System.out.println("Transfer-Encoding value: " + transferEnco);

        JsonPath path = response.jsonPath(); /* Json Path */
        String value = path.get("id");
        System.out.println("JSON Response parameter value is: " + value);
        System.out.println("JSON Response parameter value is: " + path.getString("id"));
        Assert.assertEquals(value /* actual value */, "0XYvRd7oD"/* Expected Value */, "Failed with incorrect matches!!"); /* Assertion Validation */

        List<String> list = new ArrayList(path.get("breeds")); /*we can also use array list to store values*/
        System.out.println("Values in array list :" + list);

        List<Object> values = path.getList("breeds"); /* 'getList' method is used to store values in a list*/
        System.out.println("Values in array list :" + values);

        String responseBodyAsString = response.getBody().asString(); /* asString Method Usage */
        System.out.println("Response Body is =>  " + responseBodyAsString);

        String responseBodyToString = response.getBody().toString(); /* toString Method Usage */
        System.out.println("Response Body is =>  " + responseBodyToString);
    }

    @Test(enabled = true, description = "Parameterization (File Operations)")
    public void fileOperation() throws IOException {
        String filePath = "C:\\Users\\KUMARESH S\\Automation\\RestAssuredPractice\\src\\test\\java\\Test Data\\testData.xlsx";
        FileInputStream fis = new FileInputStream(filePath);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
        for (int i = 1; i <= rowCount; i++) {
            String id = sheet.getRow(i).getCell(0).getStringCellValue();
            response = get("http://localhost:8080/InfyClinicV2/APP_V2/getDoctor/byId?doctorId=" + id + "");
            String responseBody = response.getBody().asString();
            System.out.println("Response Body is =>  " + responseBody);
        }
    }

    @Test(enabled = true, description = "Request Specification")
    public void requestSpecification() {
        baseURI = "https://api.thecatapi.com/";
        RequestSpecification httpRequest = given();
        Response response = httpRequest.request(Method.GET, "v1/images/search?size=med&mime_types=jpg&format=json&has_breeds=true&order=RANDOM&page=0&limit=1");
        String responseBody = response.getBody().asString();
        System.out.println("Response Body is =>  " + responseBody);
    }

    @Test
    public void test() {
        baseURI = "https://reqres.in/";
        given().get("/api/users?page=2").then().statusCode(200).body("data[1].id", equalTo(8));
    }

    @Test(enabled = true, description = "Json Nested Object")
    public void jsonObjectAndJsonArray() {
        JSONObject parameters = new JSONObject();
        parameters.put("name", "Ragavi");
        parameters.put("Designation", "Technical Test Lead");
        parameters.put("id", 990880770);

        JSONObject details = new JSONObject();  /* Create new JSON Object to store contact details */
        details.put("Official Number", 0422123456);
        details.put("Personal Number", 987654321);
        details.put("Land line", 0422654321);
        details.put("Email id", "ragavi@gmail.com");

        parameters.put("Contact", details); /* make details as value to "Contact" Key */

        System.out.println(parameters.toString()); /* print the JSON object as String in the console */

        JSONArray contactDetails = new JSONArray(); /* Create instance of JSONArray */
        contactDetails.put(details); /* Move the created JSONObject to JSONArray */
        parameters.put("Contact", contactDetails); /* Make contactDetails as value to "Contact" Key  */
        System.out.println(parameters.toString()); /* print the JSON object as String in the console */
    }
}