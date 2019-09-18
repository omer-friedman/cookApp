package com.example.bishuliko;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 7117;
    ArrayList<AuthUI.IdpConfig> providers;
    TextView logout_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logout_textview = findViewById(R.id.logout);
        logout_textview.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   AuthUI.getInstance()
                           .signOut(MainActivity.this)
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   logout_textview.setEnabled(false);
                                   showSignInOptions();
                               }
                           }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, ""+e.getMessage(),Toast.LENGTH_SHORT).show();
                       }
                   });
               }
        });
        providers = new ArrayList<AuthUI.IdpConfig>(Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(), //Email Builder
                new AuthUI.IdpConfig.PhoneBuilder().build(), //Phone Builder
                new AuthUI.IdpConfig.FacebookBuilder().build(), //Facebook Builder
                new AuthUI.IdpConfig.GoogleBuilder().build() //Google Builder
        ));

        showSignInOptions();
    }

    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.MyTheme)
                .build(), MY_REQUEST_CODE
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==MY_REQUEST_CODE){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(this,""+user.getEmail(),Toast.LENGTH_SHORT).show();
                logout_textview.setEnabled(true );
            }
            else{
                Toast.makeText(this, ""+response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void go_to_menu(View view) {
        Intent myIntent = new Intent(MainActivity.this, MenuWindow.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void logout(View view){

    }
}
