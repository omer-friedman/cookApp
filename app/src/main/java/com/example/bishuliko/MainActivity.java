package com.example.bishuliko;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void get_json(View view) {
        client.get("https://www.themealdb.com/api/json/v1/1/search.php?s=Chicken", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                TextView tv = findViewById(R.id.out);
                Button b = findViewById(R.id.button);
                super.onSuccess(statusCode, headers, response);
                try {
                    b.setVisibility(View.GONE);
                    JSONArray recipes = response.getJSONArray("meals");
                    tv.setText(recipes.getJSONObject(0).toString());
                } catch (JSONException e) {
                    tv.setText("No Recipes Found! Sorry..");
                }
            }
        });
    }

    public void go_to_menu(View view) {
        Intent myIntent = new Intent(MainActivity.this, MenuWindow.class);
        MainActivity.this.startActivity(myIntent);
    }
}
