package com.example.bishuliko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayRecipe extends AppCompatActivity {
    LinearLayout linearLayout;
    CheckBox checkBox;
    ArrayList<String> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe);
        linearLayout = findViewById(R.id.linear_ingredients);
        al = new ArrayList<String>();
        JSONObject json = null;
        if(getIntent().hasExtra("json")) {
            try {
                json = new JSONObject(getIntent().getStringExtra("json"));
                parse_json_args(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
          }
        }

    public void parse_json_args(JSONObject json){
        TextView tv_title = findViewById(R.id.meal_title);
        TextView tv_instructions = findViewById(R.id.instructions);
        try {
            String meal_title = json.getString("strMeal");
            String img_url = json.getString("strMealThumb");
            get_ingredients(json);
            String instructions = json.getString("strInstructions");
            instructions = modify_instructions(instructions);
            load_image(img_url);
            tv_title.setText(meal_title);
            tv_instructions.setText(instructions);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String modify_instructions(String instructions){
        String[] parts = instructions.split("\r\n");
        String ret_str = String.join("\r\n\r\n", parts);
        return ret_str;
    }

    public void load_image(String url){
        ImageView iv = findViewById(R.id.image_recipe);
        Picasso.get().load(url).into(iv);
    }

    public void get_ingredients(JSONObject json){
        String ingredients = "";
        String prefix_ing = "strIngredient";
        String prefix_mea = "strMeasure";
        for(int i=1;i<30;i++){
            try {
                String ingredient = json.getString(prefix_ing+i);
                String measure = json.getString(prefix_mea+i);
                if(ingredient.length() == 0 || ingredient.equals("null"))
                    break;
                ingredients = ingredient + " - " + measure;
                al.add(ingredients);
                checkBox = new CheckBox(this);
                checkBox.setId(i);
                checkBox.setText(ingredients);
                checkBox.setTextSize(30);
                checkBox.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#CC0000")));
                linearLayout.addView(checkBox);
            } catch (JSONException e) {}
        }
    }

}
