package com.example.bishuliko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SearchRandom extends AppCompatActivity {
    AsyncHttpClient client = new AsyncHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_random);
        final TextView tv = findViewById(R.id.recipe_view_random);
        client.get("https://www.themealdb.com/api/json/v1/1/random.php", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray recipes = response.getJSONArray("meals");
                    String json_str = recipes.getJSONObject(0).toString();
                    Intent intent = new Intent(SearchRandom.this, DisplayRecipe.class);
                    intent.putExtra("json", json_str);
                    startActivity(intent);
                } catch (JSONException e) {
                    tv.setText("No Recipes Found! Sorry..");
                }
            }
        });

    }
}
