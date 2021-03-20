Feature: SpotifyRegisterTest

  @test
  Scenario: Get Sites
    Given I am in App main site
  
  @test
  Scenario: I click an object
    Given I navigate to https://www.spotify.com/py/signup/
    Then I load the DOM Information Spotify_registro.json
    And I click on element Email
    And I set Email with text prueba@prueba.com
    Then I close the window