package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject sandwich = new JSONObject(json);

            JSONObject nameFields = sandwich.getJSONObject("name");
            JSONArray listOfOtherNames = nameFields.getJSONArray("alsoKnownAs");

            String mainName = nameFields.getString("mainName");
            List<String> akaList = buildTheList(listOfOtherNames);
            String placeOfOrigin = sandwich.getString("placeOfOrigin");
            String description = sandwich.getString("description");
            String image = sandwich.getString("image");

            JSONArray ingredientsArray = sandwich.getJSONArray("ingredients");
            List<String> ingredientsList = buildTheList(ingredientsArray);

            return new Sandwich(
                    mainName, akaList, placeOfOrigin, description, image, ingredientsList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> buildTheList(JSONArray data) {

        List<String> listData = new ArrayList<>(data.length());
        for (int i = 0; i < data.length(); i++) {
            try {
                listData.add(data.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return listData;
    }
}
