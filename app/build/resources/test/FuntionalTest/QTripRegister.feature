Feature: User Registration

    
 #Background: Set up Base Url
 # Given The Base Url Set up

Scenario: Successfull Registration
 Given Valid Username and Password
 When The Post request send with endoint
 Then Validate Statuscode 
 And Validate Response Body values

Scenario: Regisration With Existing User
 Given User enter  Username and Password
   |email                |password     |confirmpassword|
   |kalyani2325@gmail.com|Test@2325    |Test@2325      |
   |TOOLSQA.COM          |Test@@123    |Test@@123      |
 When The Post request send with endoint
 Then Validate Statuscode 
 And Validate Response Body values

 Scenario: Regisration with Invalid User 
  Given User enter  Username and Password
  |email                |password     |confirmpassword|
  |kalyani2325@gmail.com|Test@232    |Test@232    |
  When The Post request send with endoint
  Then Validate Statuscode
  And Validate Response Body values
  
  


