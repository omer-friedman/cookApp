package com.example.bishuliko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

public class SearchCategory extends AppCompatActivity {
    String [] categories = {"Beef", "Breakfast", "Chicken", "Dessert", "Goat", "Lamb", "Miscellaneous",
            "Pasta", "Pork", "Seafood", "Side", "Starter", "Vegan", "Vegetarian"};
    AsyncHttpClient client = new AsyncHttpClient();
    JSONArray json = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_catagory);
        final ListView listView = findViewById(R.id.category_list);
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(categories));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                display_recipe(position);
            }
        });
    }

    private void display_recipe(int pos) {
        client.get("https://www.themealdb.com/api/json/v1/1/filter.php?c=" + categories[pos], new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONArray recipes = null;
                try {
                    recipes = response.getJSONArray("meals");
                    Intent intent = new Intent(SearchCategory.this, RecipesSelection.class);
                    intent.putExtra("from_category", "");
                    intent.putExtra("json", recipes.toString());
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
