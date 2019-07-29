package com.spotify.automation.api.exceptions;

public class SpotifyAuthorizationTokenNotFound extends Throwable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2022594823121803844L;

	public SpotifyAuthorizationTokenNotFound(String currentUrl) {
        super(currentUrl);
    }
}
