package com.jr.gochef;

import android.util.Log;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.IOException;
        import java.util.ArrayList;

        import okhttp3.Callback;
        import okhttp3.Call;

        import okhttp3.HttpUrl;
        import okhttp3.OkHttpClient;
        import okhttp3.Request;
        import okhttp3.Response;

public class YummlyService {

    public static final String YUMMLY_API_KEY = "5443b0900f2ec4b050ac04c63a39aae7";
    public static final String YUMMLY_APP_ID = "6ac28980";
    public static final String YUMMLY_BASE_URL = "http://api.yummly.com/v1/api/recipes?";
    public static final String SEARCH_QUERY_INGREDIENT = "allowedIngredient[]";
    public static final String API_ID_QUERY_PARAMETER = "X-Yummly-App-ID";
    public static final String API_KEY_QUERY_PARAMETER = "X-Yummly-App-Key";

    public static void findRecipes(String ingredient, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(YUMMLY_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(SEARCH_QUERY_INGREDIENT, ingredient);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .header(API_ID_QUERY_PARAMETER, YUMMLY_APP_ID)
                .header(API_KEY_QUERY_PARAMETER, YUMMLY_API_KEY)
                .build();

        Log.d("url", url);

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Recipe> processResults(Response response) {
        ArrayList<Recipe> recipes = new ArrayList<>();

        try {
            if (response.isSuccessful()) {
                String jsonData = response.body().string();

                JSONObject yummlyJSON = new JSONObject(jsonData);
                JSONArray matchesJSON = yummlyJSON.getJSONArray("matches");
                for (int i = 0; i < matchesJSON.length(); i++) {
                    JSONObject recipeJSON = matchesJSON.getJSONObject(i);
                    String recipeName = recipeJSON.getString("recipeName");

                    ArrayList<String> ingredients = new ArrayList<>();
                    JSONArray ingredientsJSON = recipeJSON.getJSONArray("ingredients");

                    for (int y = 0; y < ingredientsJSON.length(); y++) {
                        ingredients.add(ingredientsJSON.get(y).toString());
                    }

                    String imageUrl = recipeJSON.getJSONObject("imageUrlsBySize").getString("90");
                    String rating = recipeJSON.getString("rating");
                    String source = recipeJSON.getString("sourceDisplayName");
                    String id = recipeJSON.getString("id");

                    Recipe recipe = new Recipe(recipeName, ingredients, imageUrl, rating, source, id);
                    recipes.add(recipe);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }

}