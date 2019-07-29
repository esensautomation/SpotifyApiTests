package com.spotify.automation.api.runner.v1;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features/spotifyApi/v1/"},
        glue = {
                "com.esens.automationFwk.steps",
                "com.esens.automationFwk.api.steps",
                "com.spotify.automation.api.steps"
        },
        tags = { "@Smoke" }
)
public class SmokeTestRunner {
}
