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
    And I wait for element Mes de Nacimiento to be present
    And I set text Febrero in dropdown Mes de Nacimiento
    And I set index 03 in dropdown Mes de Nacimiento