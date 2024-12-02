Feature: Test Login Functionality

    Login with Username and Password through API
Scenario: Successfull Login
 Given Enter Username and Password
 When Send Post Request with Valid Endpoint
 Then Validate Statucode Should be 201
 And Get the Token details

Scenario: Login with Unregistered User 
 Given Enter Unregistered Username and Password
 |email            |password  |
 |kal235@gmail.com|Test@123  |
 When Send Post Request with Valid Endpoint
 Then Validate Statucode Should be 404
 And Validate Response Body