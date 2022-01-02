package fr.poweroff.web.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.poweroff.web.util.JsonFromUrl;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Place {
    public static @NotNull JsonElement search(@NotNull String query) throws IOException {
        JsonObject jsonResult = JsonFromUrl.read(
                String.format("https://api-adresse.data.gouv.fr/search?q=%s", query.replaceAll(" ", "%20"))
        ).getAsJsonObject();
        return read(jsonResult);
    }

    public static @NotNull JsonElement reverse(@NotNull JsonArray query) throws IOException {
        JsonObject jsonResult = JsonFromUrl.read(
                String.format("https://api-adresse.data.gouv.fr/reverse/?lon=%s&lat=%s", query.get(0).getAsFloat(), query.get(1).getAsFloat())
        ).getAsJsonObject();
        return read(jsonResult);
    }

    public static @NotNull JsonElement read(@NotNull JsonObject jsonResult) {
        JsonArray output = new JsonArray();
        jsonResult.get("features").getAsJsonArray().forEach(jsonElement -> {
            JsonObject currentElement = new JsonObject();
            currentElement.add("name", jsonElement.getAsJsonObject().get("properties").getAsJsonObject().get("label"));
            currentElement.add("coordinates", jsonElement.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray());
            output.add(currentElement);
        });
        return output;
    }
}
