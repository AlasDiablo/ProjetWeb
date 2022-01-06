package fr.poweroff.web.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.poweroff.web.util.JsonFromUrl;
import fr.poweroff.web.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Place {
    private static final Map<String, Pair<JsonElement, Integer>>    CACHE_SEARCH  = new HashMap<>();
    private static final Map<JsonArray, Pair<JsonElement, Integer>> CACHE_REVERSE = new HashMap<>();

    private static void cleanupCache() {
        if (CACHE_SEARCH.size() > 1000) {
            List<String> toRemove = new ArrayList<>();
            CACHE_SEARCH.forEach((query, result) -> {
                if (result.getRight() <= CACHE_SEARCH.size() / 100) {
                    toRemove.add(query);
                }
            });
            toRemove.forEach(CACHE_SEARCH::remove);
        }
        if (CACHE_REVERSE.size() > 1000) {
            List<JsonArray> toRemove = new ArrayList<>();
            CACHE_REVERSE.forEach((query, result) -> {
                if (result.getRight() <= CACHE_REVERSE.size() / 100) {
                    toRemove.add(query);
                }
            });
            toRemove.forEach(CACHE_REVERSE::remove);
        }
    }


    public static @NotNull JsonElement search(@NotNull String query) throws IOException {
        if (CACHE_SEARCH.containsKey(query)) {
            Pair<JsonElement, Integer> result = CACHE_SEARCH.get(query);
            result.setRight(result.getRight() + 1);
            CACHE_SEARCH.replace(query, result);
            return result.getLeft();
        }
        JsonObject jsonResult = JsonFromUrl.read(
                String.format("https://api-adresse.data.gouv.fr/search?q=%s", query.replaceAll(" ", "%20"))
        ).getAsJsonObject();
        JsonElement read = read(jsonResult);
        cleanupCache();
        CACHE_SEARCH.put(query, new Pair<>(read, 1));
        return read;
    }

    public static @NotNull JsonElement reverse(@NotNull JsonArray query) throws IOException {
        if (CACHE_REVERSE.containsKey(query)) {
            Pair<JsonElement, Integer> result = CACHE_REVERSE.get(query);
            result.setRight(result.getRight() + 1);
            CACHE_REVERSE.replace(query, result);
            return result.getLeft();
        }
        JsonObject jsonResult = JsonFromUrl.read(
                String.format("https://api-adresse.data.gouv.fr/reverse/?lon=%s&lat=%s", query.get(0).getAsFloat(), query.get(1).getAsFloat())
        ).getAsJsonObject();
        JsonElement read = read(jsonResult);
        cleanupCache();
        CACHE_REVERSE.put(query, new Pair<>(read, 1));
        return read;
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
