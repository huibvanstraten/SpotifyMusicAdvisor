package SpotifyMusicAdvisor.SpotifyOperator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Iterator;

public class Categories extends SpotifyOperator {
    String response = makeRequest(RESOURCE + "/v1/browse/categories");

    public String getCategories() {
        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
        JsonObject categoriesJson = jsonResponse.getAsJsonObject("categories");
        JsonArray items = categoriesJson.getAsJsonArray("items");
        Iterator<JsonElement> iterator = items.iterator();
        StringBuilder builder = new StringBuilder();

        while (iterator.hasNext()) {
            JsonObject category = iterator.next().getAsJsonObject();
            builder.append(category.get("name").getAsString());
            builder.append("\n");
        }
        System.out.print(builder.toString());
        return builder.toString();
    }
}
