package com.example.bishuliko;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
//    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mAuth = FirebaseAuth.getInstance();
    }

    public void go_to_menu(View view) {
        Intent myIntent = new Intent(MainActivity.this, MenuWindow.class);
        MainActivity.this.startActivity(myIntent);
    }

}
