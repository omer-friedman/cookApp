package com.example.bishuliko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
public class SearchName extends AppCompatActivity {
    AsyncHttpClient client = new AsyncHttpClient();
    ImageView msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_name);
        msg = findViewById(R.id.rcp_not_found);
        msg.setVisibility(View.INVISIBLE);
    }


    public void get_json(View view) {
        final EditText et = findViewById(R.id.input);
        client.get("https://www.themealdb.com/api/json/v1/1/search.php?s=" + et.getText(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray recipes = response.getJSONArray("meals");
                    msg.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(SearchName.this, RecipesSelection.class);
                    intent.putExtra("json", recipes.toString());
                    startActivity(intent);
                } catch (JSONException e) {
                    msg.setVisibility(View.VISIBLE);
                    EditText et_search_recipe = findViewById(R.id.input);
                    et_search_recipe.setText("");
                }
            }
        });
    }
}
