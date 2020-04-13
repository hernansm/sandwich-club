package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        JSONObject sandwichJson = new JSONObject(json);
        JSONObject nameJson = sandwichJson.getJSONObject("name");
        String mainName = nameJson.getString("mainName");

        JSONArray test = (JSONArray) nameJson.get("alsoKnownAs");
        List<String> alsoKnownAsList = new ArrayList<>();

        for (int i = 0; i < test.length(); i++) {
            alsoKnownAsList.add(test.get(i).toString());
        }

        String placeOfOrigin = sandwichJson.getString("placeOfOrigin");
        String description = sandwichJson.getString("description");
        String image = sandwichJson.getString("image");

        JSONArray ingredientsArray = (JSONArray) sandwichJson.get("ingredients");
        List<String> ingredientsList = new ArrayList<>();

        for (int i = 0; i < ingredientsArray.length(); i++) {
            ingredientsList.add(ingredientsArray.get(i).toString());
        }

        Sandwich sw = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);

        return sw;
    }
}
