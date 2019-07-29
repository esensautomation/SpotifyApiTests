package com.spotify.automation.api.steps;

import com.esens.automationFwk.api.steps.ApiScenarioContext;

public class SpotifyApiScenarioContext extends ApiScenarioContext {

    private String authorizationRedirectionUrl;
    private String authorizationToken;

    private static SpotifyApiScenarioContext instance = new SpotifyApiScenarioContext();

    protected SpotifyApiScenarioContext(){
        super();
    }

    public static SpotifyApiScenarioContext getInstance(){
        return instance;
    }

    public String getAuthorizationRedirectionUrl() {
        return authorizationRedirectionUrl;
    }

    public void setAuthorizationRedirectionUrl(String authorizationRedirectionUrl) {
        this.authorizationRedirectionUrl = authorizationRedirectionUrl;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }
}
