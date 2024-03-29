package com.spotify.automation.api.runner.v1.integrationTests.playlists;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features/spotifyApi/v1/IntegrationTests/Playlists/create_a_playlist.feature"},
        glue = {
                "com.esens.automationFwk.steps",
                "com.esens.automationFwk.api.steps",
                "com.spotify.automation.api.steps"
        },
        plugin = {"json:target/cucumber-report/createAPlaylist.json"}
)
public class CreateAPlaylistFtRunner {
}
