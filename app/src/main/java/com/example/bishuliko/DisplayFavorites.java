package com.example.bishuliko;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
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

public class DisplayFavorites extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_favorites);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        TableLayout tb_favorite = findViewById(R.id.favorites_table);
        tb_favorite.removeAllViews();
        populate_favotries();
    }

    private void populate_favotries(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("user").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TableLayout tbl_favorites = findViewById(R.id.favorites_table);
                TableRow first_sep_row = new TableRow(DisplayFavorites.this);
                ImageView first_sep_image = new ImageView(DisplayFavorites.this);
                setSepParams(first_sep_image);
                tbl_favorites.addView(first_sep_row);
                first_sep_row.addView(first_sep_image);
                if(!dataSnapshot.hasChildren()){
                    TableRow tr_no_favorites = new TableRow(DisplayFavorites.this);
                    ImageView iv_no_favorites = new ImageView(DisplayFavorites.this);
                    iv_no_favorites.setBackgroundResource(R.drawable.no_favorites);
                    TableRow.LayoutParams params = new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT
                    );
                    params.gravity = Gravity.CENTER;
                    iv_no_favorites.setLayoutParams(params);
                    tr_no_favorites.setGravity(Gravity.CENTER);
                    tbl_favorites.addView(tr_no_favorites);
                    tr_no_favorites.addView(iv_no_favorites);

                }
                for(DataSnapshot recipe : dataSnapshot.getChildren()){
                    try {
                        String recipe_title = recipe.getKey();
                        final String recipe_json_str = recipe.getValue().toString();
                        JSONObject recipe_json = new JSONObject(recipe_json_str);
                        String recipe_image_url = recipe_json.getString("strMealThumb");
                        TableRow cur_row = new TableRow(DisplayFavorites.this);
                        cur_row.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(DisplayFavorites.this, DisplayRecipe.class);
                                intent.putExtra("json", recipe_json_str);
                                startActivity(intent);
                            }
                        });
                        TableRow sep_line = new TableRow(DisplayFavorites.this);
                        TextView tv_recipe_name = new TextView(DisplayFavorites.this);
                        tv_recipe_name.setText(recipe_title);
                        tv_recipe_name.setLayoutParams(new TableRow.LayoutParams(
                                TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT,
                                1.0f
                        ));
                        tv_recipe_name.setGravity(Gravity.LEFT | Gravity.CENTER);
                        ImageView iv_recipe_image = new ImageView(DisplayFavorites.this);
                        ImageView sep_image = new ImageView(DisplayFavorites.this);
                        setSepParams(sep_image);
                        Picasso.get().load(recipe_image_url)
                                .error(R.mipmap.ic_launcher)
                                .resize(200,200)
                                .into(iv_recipe_image, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess(){}
                                    @Override
                                    public void onError(Exception e){}
                                });
                        tbl_favorites.addView(cur_row);
                        tbl_favorites.addView(sep_line);
                        sep_line.addView(sep_image);
                        cur_row.addView(iv_recipe_image);
                        cur_row.addView(tv_recipe_name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            private ImageView setSepParams(ImageView sep_image) {
                sep_image.setImageResource(R.drawable.seperator);
                TableRow.LayoutParams params = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                );
                params.gravity = Gravity.CENTER;
                sep_image.setLayoutParams(params);
                return sep_image;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
