package com.example.bishuliko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
public class SearchName extends AppCompatActivity {
    AsyncHttpClient client = new AsyncHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_name);
    }


    public void get_json(View view) {
        final EditText et = findViewById(R.id.input);
        final Button b = findViewById(R.id.get_recipe);
        client.get("https://www.themealdb.com/api/json/v1/1/search.php?s=" + et.getText(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                TextView tv = findViewById(R.id.recipe_view_name);
                super.onSuccess(statusCode, headers, response);
                try {
                    et.setVisibility(View.GONE);
                    b.setVisibility(View.GONE);
                    JSONArray recipes = response.getJSONArray("meals");
                    tv.setText(recipes.getJSONObject(0).toString());
                } catch (JSONException e) {
                    tv.setText("No Recipes Found! Sorry..");
                }
            }
        });
    }
}
