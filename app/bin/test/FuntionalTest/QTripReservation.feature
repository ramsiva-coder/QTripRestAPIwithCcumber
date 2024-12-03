Feature: Book New Reservation for Adventure

    Adventure or hiils station etc booking Reservation
# Background: Generate Token for authorisation
#  Given Iam an Authorised user

Scenario: Test the Reservation Functionality
 Given Enter Required details for Reservation
 When Send Post Request with Valid Endpoint
 Then Validate Statucode Should be 201
 And Validate Successfull Response body