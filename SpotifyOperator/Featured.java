package SpotifyMusicAdvisor.SpotifyOperator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Iterator;

public class Featured extends SpotifyOperator {
    String response = makeRequest(RESOURCE + "/v1/browse/featured-playlists");

    public String getFeatured() {
        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
        JsonObject playlistsJson = jsonResponse.getAsJsonObject("playlists");
        JsonArray items = playlistsJson.getAsJsonArray("items");
        Iterator<JsonElement> iterator = items.iterator();
        StringBuilder builder = new StringBuilder();

        while (iterator.hasNext()) {
            JsonObject item = iterator.next().getAsJsonObject();
            builder.append(item.get("name").getAsString());
            builder.append("\n");
            builder.append(item.get("external_urls").getAsJsonObject().get("spotify").getAsString());
            builder.append("\n\n");
        }
        return builder.toString();
    }
}
