@Spotify @v1 @Playlist @IntegrationTest @Smoke
Feature: [Spotify][v1][IntegrationTests] Update a Playlist

  @NominalCase
  Scenario: Nominal Case
    Given I ask for Spotify Api token with scope "playlist-modify-private"
    And I prepare Spotify Api request with token from scenario
    And I prepare Api request with "Content-Type" header as "application/json"
    When I send Spotify Api update playlist request with playlist id "00yGpKNuvWE2btkcNU2rJA" and data from file "v1/IntegrationTests/Playlists/Request/update_playlist.json"
    Then validate Api response status code is 200

  @ErrorCase
  Scenario: Error Case - no auth token
    Given I prepare Api request with "Content-Type" header as "application/json"
    When I send Spotify Api update playlist request with playlist id "00yGpKNuvWE2btkcNU2rJA" and data from file "v1/IntegrationTests/Playlists/Request/update_playlist.json"
    Then validate Api response status code is 401
    And verify Api response contains json attributes with string values
      | jsonPath      | expectedValue     |
      | error.message | No token provided |