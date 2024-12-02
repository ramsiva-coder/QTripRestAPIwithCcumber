@Exclude
Feature: Collections of books library

    

Background: Generate token for Authorisation
  Given Iam an Authorised user

Scenario: Authorised user can add or remove books
  Given List of books available
  When a book can be add to reading List
  Then book added successfully
  When a book can be removed from List
  Then book removed successfully 