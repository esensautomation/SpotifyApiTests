@Spotify @v1 @Playlist @IntegrationTest @Smoke
Feature: [Spotify][v1][IntegrationTests] Read a Playlist

  @NominalCase
  Scenario: Nominal Case
    Given I ask for Spotify Api token with scope "playlist-modify-private"
    Given I prepare Spotify Api request with token from scenario
    When I send Spotify Api read playlist request with playlist id "1cZirM1Yv7bcx9nj2ZDNOo"
    Then validate Api response status code is 200
    And validate Api response contains json attributes with string values
      | jsonPath      | expectedValue                                            |
      | name          | [DoNotTouch] My Persistent Playlist                      |
      | description   | Playlist for automation tests. Do not delete or modify ! |
      | public        | false                                                    |
      | collaborative | false                                                    |

  @ErrorCase
  Scenario: Error Case - no auth token
    When I send Spotify Api read playlist request with playlist id "1cZirM1Yv7bcx9nj2ZDNOo"
    Then validate Api response status code is 401
    And validate Api response contains json attributes with string values
      | jsonPath      | expectedValue     |
      | error.message | No token provided |
