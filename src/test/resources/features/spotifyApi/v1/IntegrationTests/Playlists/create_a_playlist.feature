@Spotify @v1 @Playlist @IntegrationTest @Smoke
Feature: [Spotify][v1][IntegrationTests] Create a Playlist

  @NominalCase
  Scenario: Nominal Case
    Given I ask for Spotify Api token with scope "playlist-modify-public"
    And I prepare Spotify Api request with token from scenario
    And I prepare Api request with "Content-Type" header as "application/json"
    And I prepare Api request with json body from data file "v1/IntegrationTests/Playlists/Request/create_playlist_OK.json"
    When I send Spotify Api create playlist request with user id "2g5o4vq6ayxp9eo5xlqw6os2k"
    Then validate Api response status code is 201
    And validate Api response contains json attributes with string values
      | jsonPath      | expectedValue                 |
      | name          | My Training Playlist          |
      | description   | Playlist for automation tests |
      | public        | false                         |
      | collaborative | false                         |
    And validate Api response json attributes exists
      | jsonPath      |
      | external_urls |
      | followers     |
      | href          |
      | images        |
      | owner         |
      | tracks        |

  @ErrorCase
  Scenario: Error Case - no auth token
    Given I prepare Api request with "Content-Type" header as "application/json"
    And I prepare Api request with json body from data file "v1/IntegrationTests/Playlists/Request/create_playlist_OK.json"
    When I send Spotify Api create playlist request with user id "2g5o4vq6ayxp9eo5xlqw6os2k"
    Then validate Api response status code is 401
    And validate Api response contains json attributes with string values
      | jsonPath      | expectedValue     |
      | error.message | No token provided |
