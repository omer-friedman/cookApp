package com.example.bishuliko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_window);
    }

    public void search_name(View view) {
        Intent myIntent = new Intent(MenuWindow.this, SearchName.class);
        MenuWindow.this.startActivity(myIntent);
    }

    public void search_random(View view) {
        Intent myIntent = new Intent(MenuWindow.this, SearchRandom.class);
        MenuWindow.this.startActivity(myIntent);
    }

    public void search_category(View view) {
        Intent myIntent = new Intent(MenuWindow.this, SearchCategory.class);
        MenuWindow.this.startActivity(myIntent);
    }
}
