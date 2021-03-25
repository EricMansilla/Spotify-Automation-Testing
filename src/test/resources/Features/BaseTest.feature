Feature: SpotifyRegisterTest

  Background:
    Given I set UserEmail value in Data Scenario

  @test
  Scenario: Get Sites
    Given I am in App main site
    Then I load the DOM Information Spotify_registro.json
    And I Save text of Title as Scenario Context
    And I set Email with key value Title.text

  @test
  Scenario: I click an object
    Given I navigate to https://www.spotify.com/py/signup/
    Then I load the DOM Information Spotify_registro.json
    And I click on element Email
    And I set Email with text prueba@prueba.com
  
  @test
  Scenario: Handle Dropdown
    Given I am in App main site
    Then I load the DOM Information Spotify_registro.json
    And I wait for element Birth Month to be present
    And I set text Febrero in dropdown Birth Month
    And I set index 03 in dropdown Birth Month

  @test
  Scenario: I check if Email is registered
    Given I am in App main site
    Then I load the DOM Information Spotify_registro.json
    And I click on element Email
    And I set Email with text prueba@prueba.com
    And I click on element Confirmation Email
    Then I check if Email Error error message is displayed=true

  @frames
  Scenario: Handle various functions
    Given I navigate to https://chercher.tech/practice/frames-example-selenium-webdriver
    Then I load the DOM Information Frames.json
    And I switch to Frame: Frame2
    And I set text Avatar in dropdown Frame2 Select
    And I switch to parent frame
    And I switch to Frame: Frame1
    And I set Frame1 input with text Esto es una prueba
    Then I switch to Frame: Frame3

  @CheckBoxes
  Scenario: Handle CheckBoxes
    Given I am in App main site
    Then I load the DOM Information Spotify_registro.json
    And I check the checkbox Thirdparty
    And I check the checkbox Male
    And I check the checkbox Female
    And I check the checkbox Non-Binary
    And I uncheck the checkbox Thirdparty

  @test
  Scenario: Click on JS element
    Given I navigate to https://www.amazon.es/
    Then I load the DOM Information Amazon.json
    And I click in JS element Account
    And I wait for element My Orders to be present

  @test
  Scenario: Scroll to
    Given I navigate to https://www.amazon.es/
    Then I load the DOM Information Amazon.json
    And I scroll to element Top Sales
    And I scroll to bottom of page
    And I scroll to top of page

  @test
  Scenario: Open New Tab
    Given I am in App main site
    And I open new tab with URL https://chercher.tech/practice/frames-example-selenium-webdriver
    And I go to Practice window
    Then I load the DOM Information Frames.json
    And I switch to Frame: Frame2
    And I set text Avatar in dropdown Frame2 Select
    And I go to Principal window
    And I open new tab with URL https://www.google.com
    And I go to Google window
    And I open new tab with URL https://www.amazon.es/
    And I go to Amazon window
    And I go to Practice window
    And I go to Principal window
    And I go to Amazon window
    Then I load the DOM Information Amazon.json
    And I scroll to element Top Sales
    And I click in JS element Account
    And I wait for element My Orders to be present

  @test
  Scenario: Handle Alerts
    Given I navigate to https://www.w3schools.com/jsref/tryit.asp?filename=tryjsref_alert
    Then I load the DOM Information Alert.json
    And I switch to Frame: Alert Frame
    And I click on element Alert
    And I wait 8 seconds
    Then I accept alert

  @test
  Scenario: Take a ScreenShot
    Given I am in App main site
    And I wait 5 seconds
    And I take screenshot: HolyScreen