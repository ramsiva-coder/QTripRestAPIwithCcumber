package cucumberapi.stepDefinitions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Steps {
    private static final String USER_ID = "87bfa745-0e24-41e4-9201-a49047ce042d";
    private static final String USERNAME = "kalyani2325@gmail.com";
    private static final String PASSWORD = "Test@2325";
    private static final String BASE_URL = "https://content-qtripdynamic-qa-backend.azurewebsites.net";

    private static String jsonString;
    private static Response response;
    private static String token;
    private static String bookId;
    static String endpoint ;
    public JSONObject jsonobject;
    
    
    // Helper method to configure the request with headers
    private RequestSpecification createRequest() {
        
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .baseUri(BASE_URL);
    }

    @Given("Iam an Authorised user")
    public void iam_an_authorised_user() {
        RequestSpecification request = createRequest();
        response = request.body("{ \"email\":\"" + USERNAME + "\", \"password\":\"" + PASSWORD + "\"}")
                          .post(BASE_URL+"/api/v1/login");
        System.out.println(response.getBody());
        Assert.assertEquals(201, response.getStatusCode());
        System.out.println(response.getStatusCode());
        jsonString = response.asString();
        System.out.println(jsonString);
        token = JsonPath.from(jsonString).getString("token");
        System.out.println(token);
        Assert.assertNotNull("Token should not be null", token);
        
    }

    @Given("List of books available")
    public void list_of_books_available() {
        RequestSpecification request = createRequest();
        response = request.get("/BookStore/v1/Books");

        Assert.assertEquals(200, response.getStatusCode());
        
        jsonString = response.asString();
        List<Map<String, String>> books = JsonPath.from(jsonString).getList("books");
        Assert.assertFalse("Book list should not be empty", books.isEmpty());

        bookId = books.get(0).get("isbn");
        System.out.println("BOOK ID is "+ bookId);
    }

    @When("a book can be add to reading List")
    public void a_book_can_be_add_to_reading_list() {
        RequestSpecification request = createRequest()
                                        .header("Authorization", "Bearer " + token);

        response = request.body("{\"userId\": \""+ USER_ID + "\", \"collectionOfIsbns\": [{\"isbn\": \""+ bookId + "\"}]}")
                          .post("/BookStore/v1/Books");
        int statusCode=response.getStatusCode();
        if(statusCode==201){
            System.out.println("Book is added successfully in reading list");
        }else if(statusCode==400 || statusCode == 409){
            System.out.println("Book is already added in reading list"); 
        }else{
            Assert.fail("Unexpected statuscode found" + statusCode);
        }
            
    }

    @Then("book added successfully")
    public void book_added_successfully() { 
        RequestSpecification request = createRequest();
        Response getResponse = request.get("/BookStore/v1/Books?userId=" + USER_ID);
        Assert.assertEquals(200, getResponse.getStatusCode());
        List<Map<String,String>> books = getResponse.jsonPath().getList("books");
        boolean bookfound = books.stream().anyMatch(book-> book.get("isbn").equals(bookId));
        Assert.assertTrue("The book with ISBN "+ bookId + "is not in reading list", bookfound);
    }

    @When("a book can be removed from List")
    public void a_book_can_be_removed_from_list() {
        RequestSpecification request = createRequest()
                                        .header("Authorization", "Bearer " + token);

        response = request.body("{\"isbn\": \""+ bookId + "\", \"userId\": \""+ USER_ID + "\"}")
                          .delete("/BookStore/v1/Book");

        Assert.assertEquals(204, response.getStatusCode());
    }

    @Then("book removed successfully")
    public void book_removed_successfully() {
        Assert.assertEquals(204, response.getStatusCode());
    }


// =======================QTRIP APPLICATION END TO END TESTING=================

//=====================Registation QTrip==================================

@Given("Valid Username and Password")
public void valid_username_and_password() {
        // Write code here that turns the phrase above into concrete actions
        // RequestSpecification request = createRequest();
        // 
        //response = request.body("{\"email\":\""+ USERNAME +"\",\"password\":\""+ PASSWORD +"\",\"confirmpassword\":\""+ PASSWORD +"\"}");
        jsonobject = new JSONObject();
        jsonobject.put("email", USERNAME);
        jsonobject.put("password", PASSWORD);
        jsonobject.put("confirmpassword", PASSWORD);

        
        
    }
    @When("The Post request send with endoint")
    public void the_post_request_send_with_endoint() {
        // Write code here that turns the phrase above into concrete actions
        RequestSpecification request = createRequest();
        endpoint="/api/v1/register";
       response= request.body(jsonobject.toString()).post(BASE_URL + endpoint);
    }

    @Then("Validate Statuscode")
    public void validate_statuscode() {
        // Write code here that turns the phrase above into concrete actions
        jsonString = response.asString();
        int statuscode = response.getStatusCode();
        if(statuscode==201){
            System.out.println("User Registerd Succesfully");
        }else{
            Assert.assertEquals("Email already exists",JsonPath.from(jsonString).getString("message"));
            System.out.println("Email already exist");
        }
        
    }
    @Then("Validate Response Body values")
    public void validate_response_body_values() {
        System.out.println(response.getBody().asString());
      if(JsonPath.from(jsonString).getBoolean("success")==true){
        Assert.assertEquals(201, response.getStatusCode());
      }else{
        System.out.println(response.getBody());
      }
    }

@Given("User enter  Username and Password")
    public void user_enter_username_and_password(DataTable datatable) {
        RequestSpecification request = createRequest();
        List<Map<String,String>> map = datatable.asMaps(String.class,String.class);
        for(Map<String,String> userdata : map){
            String Email = userdata.get("email");
            String Password = userdata.get("password");
            String confirmPwd = userdata.get("confirmpassword");

            if(!Password.equals(confirmPwd)){
                System.out.println("password and confirm password do not match for "+ Email);
                continue;
            }
            jsonobject = new JSONObject();
            jsonobject.put("email", Email);
            jsonobject.put("password", Password);
            jsonobject.put("confirmpassword", confirmPwd);
             
            // Map<String,String> payload = Map.of(
            //     // "email", Email,
            //     // "password", Password,
            //     // "confirmpassword", confirmPwd
                

                

            // );
            
           
    
        }

        // Write code here that turns the phrase above into concrete actions
        
    }

//  ==================QTRIP LOGIN API==============================================

@Given("Enter Username and Password")
    public void enter_username_and_password() {
        // Write code here that turns the phrase above into concrete actions
        jsonobject= new JSONObject();
        jsonobject.put("email", USERNAME);
        jsonobject.put("password", PASSWORD);
        
        
    }
    @When("Send Post Request with Valid Endpoint")
    public void send_post_request_with_valid_endpoint() {
        // Write code here that turns the phrase above into concrete actions
        endpoint = "/api/v1/login";
        RequestSpecification request = createRequest();
        jsonobject = new JSONObject();
        jsonobject.put("email", USERNAME);
        jsonobject.put("password",PASSWORD);
        response = request.body(jsonobject.toString()).post(BASE_URL+endpoint);
        
        
        

         
        
        
    }
    @Then("Validate Statucode Should be {int}")
    public void validate_statucode_should_be(Integer actual_statuscode) {
        // Write code here that turns the phrase above into concrete actions
        jsonString = response.asString();
        System.out.println(jsonString);
        System.out.println(response.getStatusCode());
        Assert.assertEquals(actual_statuscode.intValue(), response.getStatusCode());
        
    }
    @Then("Get the Token details")
    public void get_the_token_details() {
        // Write code here that turns the phrase above into concrete actions

        token = JsonPath.from(jsonString).getString("data.token");
        
        System.out.println(token);
        
    }
    @Given("Enter Unregistered Username and Password")
    public void enter_unregistered_username_and_password(io.cucumber.datatable.DataTable dataTable) {
        RequestSpecification request = createRequest();
        List<Map<String,String>> map = dataTable.asMaps(String.class,String.class);
        for(Map<String,String> userdata : map){
            String email = userdata.get("email");
            String password = userdata.get("password");

            jsonobject = new JSONObject();
            jsonobject.put("email", email);
            jsonobject.put("password",password);
            response = request.body(jsonobject.toString()).post(BASE_URL+endpoint);
            
        } 
    }
    @Then("Validate Response Body")
    public void validate_response_body() {
        // Write code here that turns the phrase above into concrete actions
        jsonString = response.asString();
        System.out.println(jsonString);
        System.out.println(JsonPath.from(jsonString).getString("success"));
        Assert.assertEquals(201, response.getStatusCode());
        

        
    }

    //======================Cities API for QTrip=================================================

    @Given("Setup Base URl")
    public void setup_base_u_rl() {
        // Write code here that turns the phrase above into concrete actions
        endpoint ="/api/v1/cities?q=beng";
    }
    @When("Send the Get request with endpoint")
    public void send_the_get_request_with_endpoint() {
        // Write code here that turns the phrase above into concrete actions
        RequestSpecification request = createRequest();
        response = request.header("Content-Type","application/json").get(BASE_URL+endpoint);
    }
    @Then("Validate Response Statucode")
    public void validate_response_statucode() {
        // Write code here that turns the phrase above into concrete actions
        
        int Statuscode = response.getStatusCode();
        System.out.println(Statuscode);
        Assert.assertEquals(200, Statuscode);
    }
    @Then("Validate Response body details")
    public void validate_response_body_details() {
        // Write code here that turns the phrase above into concrete action
        jsonString = response.asString();
        List<Map<String,String>> map = JsonPath.from(jsonString).get();
        for(Map<String,String> data : map){
            String Description= data.get("description");
            String City = data.get("city");
        
        System.out.println("City is " + City + " and description is " + Description);

        Assert.assertEquals("100+ Places", Description);
        }
    }
//=========================QTrip Reservation API========================================

@Given("Enter Required details for Reservation")
public void enter_required_details_for_reservation() {
    // Write code here that turns the phrase above into concrete actions
    endpoint="/api/v1/reservations/new";
    RequestSpecification request = createRequest().
    header("Authorization","Bearer "+"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InMxMTEyMzVAZ21haWwuY29tIiwiaWF0IjoxNzMzMTcwNjI5LCJleHAiOjE3MzMxOTIyMjl9.wCY1qpv6ukCfaztnOp4AOS9l3hvVYqvPPqXy-CJ-4lI");
    
    Map<String,String> data = new HashMap<>();
    
      data.put("userId","szhd1Ip2jARX9wsh");
        data.put("name","TestUser");
        data.put("date","2026-09-09");
        data.put("person","1");
        data.put("adventure","2447910730");
        jsonobject = new JSONObject(data);
        response = request.body(jsonobject.toString()).post(BASE_URL+endpoint);
        
     
    
}
@Then("Validate Successfull Response body")
public void validate_successfull_response_body() {
    // Write code here that turns the phrase above into concrete actions
   System.out.println(response.getBody().asPrettyString());
   Assert.assertTrue(JsonPath.from(jsonString).getBoolean("success"));
   
}


}