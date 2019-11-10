package com.udacity.sandwichclub.utils;

import android.util.Log;

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
    public static final String FALL_BACK_STRING = "?";

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject sandwich = new JSONObject(json);

            JSONObject nameFields = sandwich.optJSONObject(KEY_NAME);
            JSONArray listOfOtherNames = nameFields.optJSONArray(KEY_ALSO_KNOWN_AS);

            String mainName = nameFields.optString(KEY_MAIN_NAME, FALL_BACK_STRING);
            List<String> akaList = buildTheList(listOfOtherNames);
            String placeOfOrigin = sandwich.optString(KEY_PLACE_OF_ORIGIN, FALL_BACK_STRING);
            String description = sandwich.optString(KEY_DESCRIPTION, FALL_BACK_STRING);
            String image = sandwich.optString(KEY_IMAGE, FALL_BACK_STRING);

            JSONArray ingredientsArray = sandwich.optJSONArray(KEY_INGREDIENTS);
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
                listData.add(data.optString(i, FALL_BACK_STRING));
        }
        return listData;
    }
}
