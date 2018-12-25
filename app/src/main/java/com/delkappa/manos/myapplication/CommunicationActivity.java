package com.delkappa.manos.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CommunicationActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private GUIManager guiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        guiManager = GUIManager.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference("Communication");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;

                // finish();
                // startActivity(getIntent());

                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    // Department department = singleSnapshot.getValue(Department.class);
                    User user = singleSnapshot.getValue(User.class);
                    //departments[i] = department;
                    Log.i("DEPARTMENT", user.toString());

                    guiManager.generateUsersButtons(user, i, CommunicationActivity.this, findViewById(android.R.id.content).getRootView());
                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
