package com.example.bishuliko;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchCategory extends AppCompatActivity {
    private static final int NCOLS = 3;
    AsyncHttpClient client = new AsyncHttpClient();
    JSONArray json = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_catagory);
        populate_categories();
    }

    private void display_recipes(String category) {
        client.get("https://www.themealdb.com/api/json/v1/1/filter.php?c=" + category, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONArray recipes;
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

    private void populate_categories(){
        client.get("https://www.themealdb.com/api/json/v1/1/categories.php", new JsonHttpResponseHandler() {
            ArrayList<ListCategoryView> categories  = new ArrayList<>();
            JSONArray categories_json;
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    categories_json = response.getJSONArray("categories");
                    for (int i = 0; i < categories_json.length(); i++) {
                        JSONObject item = categories_json.getJSONObject(i);
                        String name = item.getString("strCategory");
                        String image_url = item.getString("strCategoryThumb");
                        categories.add(new ListCategoryView(name, image_url));
                    }
                    fill_categories_table(categories);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void fill_categories_table(ArrayList<ListCategoryView> categories){
        int NROWS = (int)Math.ceil(categories.size()/NCOLS);
        int cnt_cur_category = 0;
        TableLayout tbl_categories = findViewById(R.id.categories_table);
        for(int row = 0; row < NROWS; row++){
            TableRow cur_row = new TableRow(this);
            TableLayout one_category = new TableLayout(this);
            TableRow tbr_category_name = new TableRow(this);
            TableRow tbr_category_image = new TableRow(this);
            tbl_categories.addView(cur_row);
            cur_row.addView(one_category);
            one_category.addView(tbr_category_name);
            one_category.addView(tbr_category_image);
            for(int col = 0; col < NCOLS ; col++, cnt_cur_category++){
                final String category;
                try {
                    category = categories.get(cnt_cur_category).getCategory();
                }catch (Exception e){
                    break;
                }
                String image_url = categories.get(cnt_cur_category).getImgUrl();
                String categorie_name = categories.get(cnt_cur_category).getCategory();
                TextView tv_category = new TextView(this);
                tv_category.setText(categorie_name);
                tv_category.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                ));
                tv_category.setGravity(Gravity.CENTER);
                ImageButton btn_category = new ImageButton(this);
                btn_category.setBackgroundResource(0);
                btn_category.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                ));
                Picasso.get().load(image_url)
                        .error(R.mipmap.ic_launcher)
                        .into(btn_category, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });

                btn_category.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        display_recipes(category);
                    }
                });
                tbr_category_name.addView(tv_category);
                tbr_category_image.addView(btn_category);
            }
        }
    }
}
