package com.example.bishuliko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListImages extends AppCompatActivity {
    int[] images = {R.drawable.beef, R.drawable.breakfast, R.drawable.chicken, R.drawable.dessert,
            R.drawable.goat, R.drawable.lamb, R.drawable.miscellaneous, R.drawable.pasta,
            R.drawable.pork, R.drawable.seafood, R.drawable.side, R.drawable.starter,
            R.drawable.vegan, R.drawable.vegetarian};
    String[] categories = {"Beef", "Breakfast", "Chicken", "Dessert", "Goat", "Lamb", "Miscellaneous",
            "Pasta", "Pork", "Seafood", "Side", "Starter", "Vegan", "Vegetarian"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_images);
        ListView listView = findViewById(R.id.list_categories);
        ArrayList<ListCategoryView> arrayList = new ArrayList<>();
        for(int i=0;i<images.length;i++)
            arrayList.add(new ListCategoryView(images[i],categories[i]));
        CustomAdapter adapter = new CustomAdapter(this, R.layout.activity_list_images, arrayList);
        listView.setAdapter(adapter);

    }
}



//
//    CustomAdapter customAdapter = new CustomAdapter();
//    ListView listView = findViewById(R.id.list_categories);
//        listView.setAdapter(customAdapter);
//
//}
//
//class CustomAdapter extends BaseAdapter {
//
//    @Override
//    public int getCount() {
//        return images.length;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        convertView = getLayoutInflater().inflate(R.layout.custom_layout,null);
//        ImageView imageView = findViewById(R.id.list_img);
//        TextView textView = findViewById(R.id.list_text);
//        imageView.setImageResource(images[position]);
//        textView.setText(categories[position]);
//        return null;
//    }
//}