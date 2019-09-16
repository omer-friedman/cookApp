package com.example.bishuliko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MenuWindow extends AppCompatActivity {
    AsyncHttpClient client = new AsyncHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_window);
    }

    public void search_name(View view) {
        Intent myIntent = new Intent(MenuWindow.this, SearchName.class);
        MenuWindow.this.startActivity(myIntent);
    }

    public void search_random(View view) {
        client.get("https://www.themealdb.com/api/json/v1/1/random.php", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray recipes = response.getJSONArray("meals");
                    String json_str = recipes.getJSONObject(0).toString();
                    Intent intent = new Intent(MenuWindow.this, DisplayRecipe.class);
                    intent.putExtra("json", json_str);
                    startActivity(intent);
                } catch (JSONException e) {}
            }
        });
    }

    public void search_category(View view) {
        Intent myIntent = new Intent(MenuWindow.this, SearchCategory.class);
        MenuWindow.this.startActivity(myIntent);
    }
}
