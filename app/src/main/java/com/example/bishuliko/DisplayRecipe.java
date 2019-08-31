package com.example.bishuliko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe);
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
        TextView tv_ing = findViewById(R.id.ingredients);
        TextView tv_ins = findViewById(R.id.instructions);
        try {
            String img_url = json.getString("strMealThumb");
            String ingredients = get_ingredients(json);
            String instructions = json.getString("strInstructions");
            load_image(img_url);
            tv_ing.setText(ingredients);
            tv_ins.setText(instructions);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void load_image(String url){
        ImageView iv = findViewById(R.id.image_recipe);
        Picasso.get().load(url).into(iv);
    }

    public String get_ingredients(JSONObject json){
        String ingredients = "";
        String prefix_ing = "strIngredient";
        String prefix_mea = "strMeasure";
        for(int i=1;i<30;i++){
            try {
                String ingredient = json.getString(prefix_ing+i);
                String measure = json.getString(prefix_mea+i);
                if(ingredient.length() == 0)
                    break;
                ingredients += ingredient + " " + measure + "\n";
            } catch (JSONException e) {
                return ingredients;
            }
        }
        return ingredients;
    }

}
