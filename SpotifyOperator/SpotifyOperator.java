package SpotifyMusicAdvisor.SpotifyOperator;

import SpotifyMusicAdvisor.GetAccess;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

abstract class SpotifyOperator {

    static String RESOURCE = "https://api.spotify.com";
    private String token = GetAccess.getAccessToken();

    public String makeRequest(String _path) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + GetAccess.getAccessToken())
                .uri(URI.create(_path))
                .GET()
                .build();
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            assert response != null;
            return response.body();
        } catch (InterruptedException | IOException e) {
            return "Interrupted response";
        }
    }
}