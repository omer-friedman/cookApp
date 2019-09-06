package com.example.bishuliko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipesSelection extends AppCompatActivity {
    JSONArray json = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_selection);

        final ListView listView = findViewById(R.id.list_view);

        ArrayList<String> arrayList = new ArrayList<>();

        if(getIntent().hasExtra("json")) {
            try {
                json = new JSONArray (getIntent().getStringExtra("json"));
                arrayList = get_meals(json, arrayList);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        display_recipe(position);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> get_meals(JSONArray json, ArrayList<String> arrayList){
        for(int i=0;i<json.length();i++){
            try {
                arrayList.add(json.getJSONObject(i).getString("strMeal"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    public void display_recipe(int pos){
        try {
            String json_str = json.getJSONObject(pos).toString();
            Intent intent = new Intent(RecipesSelection.this, DisplayRecipe.class);
            intent.putExtra("json", json_str);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
