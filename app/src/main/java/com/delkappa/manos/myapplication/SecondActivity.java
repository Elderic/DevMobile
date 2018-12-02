package com.delkappa.manos.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private SharedPreferences sharedPref = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // Get the information from the intent
        String name = sharedPref.getString(getString(R.string.user_name), "");
        String email = sharedPref.getString(getString(R.string.user_email), "");

        // Create the text view
        TextView textView = findViewById(R.id.name);

        // Show the user's name on the screen
        textView.setText(name);

        // Print the user's email
        Log.d("SecondActivity", "The user's email is: " + email);
    }

    public void next(View view) {
        // Create an intent for the activity
        Intent i = new Intent(this, ThirdActivity.class);

        // Start the activity
        startActivity(i);
    }

}
