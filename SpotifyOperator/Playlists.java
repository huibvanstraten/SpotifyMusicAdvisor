package SpotifyMusicAdvisor.SpotifyOperator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Iterator;

public class Playlists extends SpotifyOperator {
    private String categoryName;
    private String categoryID;
    String response = makeRequest(RESOURCE + "/v1/browse/categories/");

    public Playlists(String categoryName) {
        this.categoryName = categoryName;
    }
    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryIdByName() {
        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
        JsonObject categoriesJson = jsonResponse.getAsJsonObject("categories");
        JsonArray items = categoriesJson.getAsJsonArray("items");
        Iterator<JsonElement> iterator = items.iterator();

        while (iterator.hasNext()) {
            JsonObject category = iterator.next().getAsJsonObject();
            if (category.get("name").getAsString().equalsIgnoreCase(categoryName)) {
                return category.get("id").getAsString();
            }
        }
        return "Unknown category name.";
    }

    public String getPlaylist() {
        String responsePlaylist = makeRequest(RESOURCE + "/v1/browse/categories/" + categoryID + "/playlists");
        JsonObject jsonResponse = JsonParser.parseString(responsePlaylist).getAsJsonObject();
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