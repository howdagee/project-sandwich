package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String KEY_NAME = "name";
    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject sandwich = new JSONObject(json);

            JSONObject nameFields = sandwich.getJSONObject(KEY_NAME);
            JSONArray listOfOtherNames = nameFields.getJSONArray(KEY_ALSO_KNOWN_AS);

            String mainName = nameFields.getString(KEY_MAIN_NAME);
            List<String> akaList = buildTheList(listOfOtherNames);
            String placeOfOrigin = sandwich.getString(KEY_PLACE_OF_ORIGIN);
            String description = sandwich.getString(KEY_DESCRIPTION);
            String image = sandwich.getString(KEY_IMAGE);

            JSONArray ingredientsArray = sandwich.getJSONArray(KEY_INGREDIENTS);
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
