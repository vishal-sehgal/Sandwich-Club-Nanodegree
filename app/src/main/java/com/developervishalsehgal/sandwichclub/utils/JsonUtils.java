package com.developervishalsehgal.sandwichclub.utils;


import com.developervishalsehgal.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        // Intially Declaring a sandwich object as null.
        Sandwich sandwich = null;

        /**
         * According to JSON structure we've 2 JsonObjects(including root object), 4 Key-Value Pairs, 2 JsonArrays.
         */

        // All KEY names.
        final String KEY_NAME = "name",
                KEY_MAIN_NAME = "mainName",
                KEY_ALSO_KNOWN_AS = "alsoKnownAs",
                KEY_PLACE_OF_ORIGIN = "placeOfOrigin",
                KEY_DESCRIPTION = "description",
                KEY_IMAGE_URL = "image",
                KEY_INGREDIENTS = "ingredients";


        try {

            //rootJsonObject is the root of the JSON.
            JSONObject rootJsonObject, nameObject;
            String mainName, placeOfOrigin, description, imageURL;
            List<String> alsoKnownAsArray, ingredientsArray;

            rootJsonObject = new JSONObject(json);

            nameObject = rootJsonObject.getJSONObject(KEY_NAME);

            mainName = nameObject.optString(KEY_MAIN_NAME);

            alsoKnownAsArray = jsonArrayValuesToList(nameObject.getJSONArray(KEY_ALSO_KNOWN_AS));

            placeOfOrigin = rootJsonObject.optString(KEY_PLACE_OF_ORIGIN);
            description = rootJsonObject.optString(KEY_DESCRIPTION);
            imageURL = rootJsonObject.optString(KEY_IMAGE_URL);

            ingredientsArray = jsonArrayValuesToList(rootJsonObject.getJSONArray(KEY_INGREDIENTS));

            sandwich = new Sandwich(mainName, alsoKnownAsArray, placeOfOrigin, description, imageURL, ingredientsArray);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }

    /**
     * This method adds the JsonArray Values in to a List<String> and returns that List.
     *
     * @param jsonArray
     * @return myList
     */
    private static List<String> jsonArrayValuesToList(JSONArray jsonArray) {
        List<String> myList = new ArrayList<String>();

        // Adding each JSONArrayValues into myList.
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                myList.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return myList;
    }
}
