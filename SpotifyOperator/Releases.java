package SpotifyMusicAdvisor.SpotifyOperator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Iterator;

public class Releases extends SpotifyOperator {
     String response = makeRequest(RESOURCE + "/v1/browse/new-releases");

    public String parseNewReleases() {
        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
        JsonObject albumsJson = jsonResponse.getAsJsonObject("albums");
        JsonArray items = albumsJson.getAsJsonArray("items");
        Iterator<JsonElement> iterator = items.iterator();
        StringBuilder builder = new StringBuilder();

        while (iterator.hasNext()) {
            JsonObject item = iterator.next().getAsJsonObject();
            builder.append(item.get("name").getAsString());
            builder.append("\n");
            Iterator<JsonElement> artistsIterator = item.getAsJsonArray("artists").iterator();
            builder.append("[");
            while (artistsIterator.hasNext()) {
                JsonObject artist = artistsIterator.next().getAsJsonObject();
                builder.append(artist.get("name").getAsString());
                if (artistsIterator.hasNext()) {
                    builder.append(", ");
                }
            }
            builder.append("]\n");
            builder.append(item.get("external_urls").getAsJsonObject().get("spotify").getAsString());
            builder.append("\n\n");
        }
        return builder.toString();
    }
}