#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Login functionality

Background:
 Given I am on the login page

Scenario: Verify title (of tab) of the page
  Then The tab title should be correct

@run
  Scenario: Verify successful login
    When I enter email "prasanna.inamdar@zenken.co.jp" and password "Password_12"
    Then I redirect to profile page
  
  Scenario: Verify 見出し of the page
  Then I find expected text for 見出し
  
  Scenario: Verify Forgot your password? link
  When I click on Forgot your password? link
  Then I get redirected to forgot password page
  
  Scenario: Verify Create an Account link
  When I click on Create an Account link
  Then I get redirected to register page

  Scenario: Verify validation error for invalid email
  When I enter invalid email "hey"
  And I enter password "Password_1"
  And I click on LogIn button on login page
  But I get error for invalid email
  
  Scenario: Verify validation error for blank email
  And I enter password "Password_1"
  And I click on LogIn button on login page
  But I get error for blank email
  
  Scenario: Verify validation error for blank password
  When I enter email "23e51ed7-f1d2-405c-8380-67b86ca0956b@mailslurp.net" on login page
  And I click on LogIn button on login page
  But I get error for blank password
  
  Scenario: Verify validation error for invalid credentials
  When I enter email "2e51ed7-f1d2-405c-8380-67b86ca0956b@mailslurp.net" on login page
  And I enter password "Password_1"
  And I click on LogIn button on login page
  But I get error for credentials mismatch
  
  Scenario: Verify title of forgot password page
  When I click on Forgot your password? link
  Then I find expected title name
  
  Scenario: Verify headline of forgot password page
  When I click on Forgot your password? link
  Then I find expected headline text
    
  Scenario: Verify that password reset mail is sent
  When I click on Forgot your password? link
  And I enter email "23e51ed7-f1d2-405c-8380-67b86ca0956b@mailslurp.net"
  And I click on Send button
  Then I see the success alert
  
  Scenario: Verfiy that same mail cannot be sent within one minute
  When I click on Forgot your password? link
  And I enter email "23e51ed7-f1d2-405c-8380-67b86ca0956b@mailslurp.net"
  And I click on Send button
  But I see the error alert
  
  Scenario: Verify that Reset Password mail is delivered
  When I check inbox
  Then I find email with correct subject
  
  Scenario: Verify that password reset link in the mail works
  When I open link from the mail
  Then I check 見出し of the page
  
  @SkipAfterHook
  Scenario: Verify that password can be reset
  When I open link from the mail
  And I enter new password "Password_1"
  And I click on Change Password button
  Then I redirect to reset password complete page
  
  @SkipBeforeHook @SkipAfterHook
  Scenario: Verify that page displayed is correct after password reset
  Then I check 見出し of password reset complete page
  
  @SkipBeforeHook
  Scenario: Verify redirection to login page after password is reset
  When I click on LogIn button
  Then I redirect to login page
  
  Scenario: Verify successful login after password is reset
  When I enter email "23e51ed7-f1d2-405c-8380-67b86ca0956b@mailslurp.net" on login page
  And I enter password "Password_1"
  And I click on LogIn button on login page
  Then I redirect to profile page with new password

  Scenario: Verify validation error for blank email on forgot password page
  When I click on Forgot your password? link
  And I click on Send button
  But I get error for blank email

  Scenario: Verify validation error for non-existent email on forgot password page
  When I click on Forgot your password? link
  And I enter email "sample123@orkut.com"
  And I click on Send button
  But I get error for non-existent email

  Scenario: Verify validation error for invalid email on forgot password page
  When I click on Forgot your password? link
  And I enter email "hey"
  And I click on Send button
  But I get error for invalid email

  Scenario: Verify validation error for invalid password: less than 8 characters
  When I open reset password page
  And I enter new password "Passw_1"
  And I click on Change Password button
  But I get error for password: less than 8 characters

  Scenario: Verify validation error for invalid password: No numbers
  When I open reset password page
  And I enter new password "Password_#"
  And I click on Change Password button
  But I get error for password: No numbers

  Scenario: Verify validation error for invalid password: No upper case characters
  When I open reset password page
  And I enter new password "password_1"
  And I click on Change Password button
  But I get error for password: No upper case characters

  Scenario: Verify validation error for invalid password: No special characters
  When I open reset password page
  And I enter new password "Password12"
  And I click on Change Password button
  But I get error for password: No special characters

  Scenario: Verify validation error for invalid token
  When I open reset password page
  And I enter new password "Password_189"
  And I click on Change Password button
  But I get error for invalid token

  Scenario: Verify validation error for setting same password as previous
  When I open reset password page
  And I enter new password "Password_1"
  And I click on Change Password button
  But I get error for same password as previous
