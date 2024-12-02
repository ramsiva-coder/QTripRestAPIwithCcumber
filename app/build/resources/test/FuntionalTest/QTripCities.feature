Feature: Test Cities displayed on Search

    Cities should be displayed based on serach item
Scenario: Test correct cities are displayed
 Given Setup Base URl 
 When Send the Get request with endpoint 
 Then Validate Response Statucode 
 And  Validate Response body details