package com.example.bishuliko;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayRecipe extends AppCompatActivity {
    private DatabaseReference databaseReference;
    LinearLayout linearLayout;
    CheckBox checkBox;
    ArrayList<String> al;
    String meal_json = "";
    String meal_title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_display_recipe);
        linearLayout = findViewById(R.id.linear_ingredients);
        al = new ArrayList<String>();
        JSONObject json = null;
        if(getIntent().hasExtra("json")) {
            try {
                meal_json = getIntent().getStringExtra("json");
                json = new JSONObject(meal_json);
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
            meal_title = json.getString("strMeal");
            checkMealFavorite(meal_title);
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
                checkBox.setTextSize(15);
                checkBox.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#424242")));
                linearLayout.addView(checkBox);
            } catch (JSONException e) {}
        }
    }

    public void changeTitleColor(View view){
        TextView meal_title = findViewById(R.id.meal_title);
        if(meal_title.getTag().equals("no_background")) {
            meal_title.setBackgroundColor(Color.parseColor("#000000"));
            meal_title.setTag("black_background");
        }
        else {
            meal_title.setBackgroundColor(0x00000000);
            meal_title.setTag("no_background");
        }
    }

    public void handle_favorite_click(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        boolean favorite_on = toggleFavoriteButton();
        if(favorite_on){
            try{
                databaseReference.child("user").child(user.getUid()).child(meal_title).setValue(meal_json);
                Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
            }catch(Exception e){
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else{
            try{
                databaseReference.child("user").child(user.getUid()).child(meal_title).removeValue();
                Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
            }catch(Exception e){
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean toggleFavoriteButton(){
        ImageView favorite_logo = findViewById(R.id.favorite);
        String favorite_tag = String.valueOf(favorite_logo.getTag());
        if(favorite_tag.equals("favorite_false")) {
            favorite_logo.setImageResource(R.drawable.favorite_true);
            toggleFavoriteVisability(true);
            favorite_logo.setTag("favorite_true");
            return true;
        }
        else {
            favorite_logo.setImageResource(R.drawable.favorite_false);
            toggleFavoriteVisability(true);
            favorite_logo.setTag("favorite_false");
            return false;
        }
    }

    private void toggleFavoriteVisability(boolean to_visible){
        ImageView favorite_logo = findViewById(R.id.favorite);
        if(to_visible)
            favorite_logo.setVisibility(View.VISIBLE);
        else
            favorite_logo.setVisibility(View.INVISIBLE);
    }

    private void checkMealFavorite(final String meal_title) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("user").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.hasChildren())
                    toggleFavoriteVisability(true);
                else
                    for(DataSnapshot data : snapshot.getChildren())
                        if(data.getKey().equals(meal_title))
                            toggleFavoriteButton();
                        else{
                            toggleFavoriteVisability(true);
                    }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}
