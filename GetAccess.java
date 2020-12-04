package SpotifyMusicAdvisor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GetAccess {
    static final String SERVER_PATH = "https://accounts.spotify.com";
    static final String REDIRECT_URI = "http://localhost:8000";
    static final String CLIENT_ID = "*****************************";
    static final String CLIENT_SECRET = "********************";

    private static String accessToken = "";
    private static String accessCode = "";
    private boolean authorized = false;

    public boolean getAuthorized() {
        return authorized;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    void getAccessCode() {
        String uri = SERVER_PATH + "/authorize"
                + "?client_id=" + CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URI
                + "&response_type=code";
        System.out.println(uri);

        try {
            HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(8000), 0);
            server.createContext("/",
                    exchange -> {
                        String query = exchange.getRequestURI().getQuery();
                        String request;
                        if (query != null && query.contains("code")) {
                            accessCode = query.substring(5);
                            request = "Thank you for logging in. Please return back to the app.";
                            authorized = true;
                        } else {
                            request = "Login failed. Try again.";
                        }
                        exchange.sendResponseHeaders(200, request.length());
                        exchange.getResponseBody().write(request.getBytes());
                        exchange.getResponseBody().close();
                    });
            server.start();
            while (accessCode.length() == 0) {
                Thread.sleep(10);
            }
            server.stop(5);
        } catch (IOException | InterruptedException e) {
            System.out.println("Server error");
        }
    }

    void accessToken() {
        System.out.println("Now, please hold on while we make a http request for access_token...");
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(SERVER_PATH + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=authorization_code"
                                + "&code=" + accessCode
                                + "&client_id=" + CLIENT_ID
                                + "&client_secret=" + CLIENT_SECRET
                                + "&redirect_uri=" + REDIRECT_URI))
                .build();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response != null) {
                accessToken(response.body());
            }
            System.out.println("Success!");
        } catch (InterruptedException | IOException e) {
            System.out.println("Error response");
        }
    }

    void accessToken(String _response){
        JsonObject jsonObject = JsonParser.parseString(_response).getAsJsonObject();
        accessToken = jsonObject.get("access_token").getAsString();
    }
}