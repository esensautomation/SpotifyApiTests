package com.spotify.automation.api.steps;

import com.esens.automationFwk.api.steps.ApiScenarioContext;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;

public class SpotifyApiActionSteps {

    @When("I send Spotify Api create playlist request with user id {string}")
    public void iSendCreatePlaylistRequestWithUserId(String userId) {
        System.out.println("---------- STEP");
        System.out.println("'I send create playlist request with user id {" + userId + "}'");
        System.out.println("\n");
        System.out.println("- Sending API request for playlist creation ...");
        System.out.println("\n");
        RequestSpecification scnReqSpec = ApiScenarioContext.getInstance().getRequestSpecification();
        System.out.println("- Request & Response :");
        System.out.println("\n");
        Response response = scnReqSpec
                    .log().all()
                .when()
                    .post("https://api.spotify.com/v1/users/" + userId + "/playlists")
                .then()
                    .log().all()
                .and()
                    .extract().response();
        ApiScenarioContext.getInstance().setResponse(response);
        System.out.println("-> API request for playlist creation sent !");
        System.out.println("\n");
    }

    @When("I send Spotify Api read playlist request with playlist id {string}")
    public void iSendSpotifyApiReadPlaylistRequestWithPlaylistId(String playlistID) {
        System.out.println("---------- STEP");
        System.out.println("'I send read playlist request with playlist id {" + playlistID + "}'");
        System.out.println("\n");
        System.out.println("- Sending API request to read a playlist ...");
        System.out.println("\n");
        RequestSpecification scnReqSpec = ApiScenarioContext.getInstance().getRequestSpecification();
        System.out.println("- Request & Response :");
        System.out.println("\n");
        Response response = scnReqSpec
                    .log().all()
                .when()
                    .get("https://api.spotify.com/v1/playlists/" + playlistID)
                .then()
                    .log().all()
                .and()
                    .extract().response();
        ApiScenarioContext.getInstance().setResponse(response);
        System.out.println("-> API request for playlist sent !");
        System.out.println("\n");
    }

    @When("I send Spotify Api update playlist request with playlist id {string} and data from file {string}")
    public void iSendSpotifyApiUpdatePlaylistRequestWithPlaylistIdAndDataFromFile(String playlistId, String dataFilePath) {
        if(!dataFilePath.contains("src/test/resources/data/spotifyApi/")) {
            dataFilePath = "src/test/resources/data/spotifyApi/" + dataFilePath;
        }
        System.out.println("---------- STEP");
        System.out.println("'I send update playlist request with playlist id {" + playlistId + "} and data from file {" + dataFilePath + "}'");
        System.out.println("\n");
        System.out.println("- Sending API request to update a playlist ...");
        System.out.println("\n");
        RequestSpecification scnReqSpec = ApiScenarioContext.getInstance().getRequestSpecification();
        ApiScenarioContext.getInstance().setRequestSpecification(scnReqSpec.body(new File(dataFilePath)));
        System.out.println("- Request & Response :");
        System.out.println("\n");
        Response response = scnReqSpec
                    .log().all()
                .when()
                    .put("https://api.spotify.com/v1/playlists/" + playlistId)
                .then()
                    .log().all()
                .and()
                    .extract().response();
        ApiScenarioContext.getInstance().setResponse(response);
        System.out.println("-> API request for playlist update sent !");
        System.out.println("\n");
    }
}
