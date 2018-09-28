package com.example.android.sandwichapp.utils;

import android.util.Log;

import com.example.android.sandwichapp.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject sandwichJson = new JSONObject(json);
            JSONObject nameJson = sandwichJson.getJSONObject("name");
            String mainName = nameJson.getString("mainName");
            Log.v("asdf", "mainname " + mainName);
            JSONArray alsoKnownAsJsonArray = nameJson.getJSONArray("alsoKnownAs");
            List<String> alsoKnownList = jsonArrayToStringList(alsoKnownAsJsonArray);
            String placeOfOrigin = sandwichJson.getString("placeOfOrigin");
            String description = sandwichJson.getString("description");
            String image = sandwichJson.getString("image");
            JSONArray ingredientJsonArray = sandwichJson.getJSONArray("ingredients");
            List<String> ingredients = jsonArrayToStringList(ingredientJsonArray);

            Sandwich sandwich = new Sandwich(mainName, alsoKnownList, placeOfOrigin, description, image, ingredients);

            Log.v("json", "sandwiches " + sandwich);

            return sandwich;

            } catch (JSONException e) {
            e.printStackTrace();
            return null;

    }
    }

    public static List<String> jsonArrayToStringList(JSONArray jsonArray) throws JSONException {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }
}

