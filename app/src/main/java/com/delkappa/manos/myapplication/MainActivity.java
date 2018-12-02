package com.delkappa.manos.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPref = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if(!sharedPref.getString(getString(R.string.user_name), "").trim().equals("")) {
            // Create an intent for the second activity
            Intent i = new Intent(this, SecondActivity.class);

            // Kill this activity
            finish();

            // Start the activity
            startActivity(i);
        }

    }

    public void submit(View view) {
        // Create an intent for the second activity
        Intent intent = new Intent(this, SecondActivity.class);

        // Get the name and email values
        EditText eTname = findViewById(R.id.name);
        EditText eTemail = findViewById(R.id.email);

        // Extract the user information
        String name = eTname.getText().toString();
        String email = eTemail.getText().toString();

        if(name.trim().equals("")) {
            eTname.setError( "Name is required" );
            eTname.setHint("Please enter your name");
        } else if(email.trim().equals("")) {
            eTemail.setError( "Email is required" );
            eTemail.setHint("Please enter your email");
        } else {
            // Save user information
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.user_name), name);
            editor.putString(getString(R.string.user_email), email);
            editor.commit();

            // Start the activity
            startActivity(intent);
        }

    }
}
