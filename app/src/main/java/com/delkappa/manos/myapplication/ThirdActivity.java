package com.delkappa.manos.myapplication;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Button;
import java.io.File;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ThirdActivity extends AppCompatActivity {

    public static String TAG = "ThirdActivity";
    public static final int PERMISSIONS_REQUEST = 0;
    public static final String REQUEST_URL = "url";
    public static final String URL_PATH = "https://goo.gl/WQQfyP";
    public static final String INTENT_FILTER = "filter";
    public static String EVENTS_FILE_PATH = "";
    public static String EVENT_EXTRA = "event";

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        EVENTS_FILE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/events.txt";

        mDatabase = FirebaseDatabase.getInstance().getReference("Departments");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    Department department = singleSnapshot.getValue(Department.class);
                    generateDepartmentButtons(department, i);
                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void generateDepartmentButtons(final Department department, int j) {
        Log.i("ttt","dze");
        Log.i("posttEST",department.toString());
        Button myButton = new Button(this);
        myButton.setText(department.toString());

        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i("MessageButton","Yeah");

                // String srcRepository = "com.delkappa.manos.myapplication.";
                String departmentName = department.toString()+"Activity";
                // String activityToStart = srcRepository + departmentName;
                Intent intent;
                intent = new Intent(ThirdActivity.this, DirectionActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putString("DepartmentName", department.toString());
                intent.putExtras(mBundle);

                Log.i("testNAME", departmentName);
                startActivity(intent);

            }
        });
        //this.findViewById(android.R.id.content);
        LinearLayout parent = findViewById(R.id.mainLinearLayout);
        parent.addView(myButton,j);
    }
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        // Create an intent for the activity
        Intent i = new Intent(this, ConnexionActivity.class);

        // Start the activity
        startActivity(i);
    }
}
