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

    //private DatabaseHandler db;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        EVENTS_FILE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/events.txt";

        mDatabase = FirebaseDatabase.getInstance().getReference("Departments");
        final Department [] departments = new Department[10] ;

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    // Department department = singleSnapshot.getValue(Department.class);
                    Department department = singleSnapshot.getValue(Department.class);
                    //departments[i] = department;
                    // Log.i("DEPARTMENT", department.toString());
                    generateDepartmentButtons(department, i);
                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        /*
        mDatabase.child("users").child("0001").child("userID").setValue("user0001");
        mDatabase.child("users").child("0001").child("userName").setValue("name01");
        mDatabase.child("users").child("0001").child("userFirstName").setValue("firstname01");
        mDatabase.child("users").child("0001").child("deptID").setValue("dept01");
        */
        Log.i("testFire","Fiiiiiire ! ");
        // Ask for permission.
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST);

                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                // app-defined int constant. The callback method gets the
                // result of the request.

            }

        } else {

            // Permission already granted, Do the
            // storage-related task you need to do.
            if (!new File(EVENTS_FILE_PATH).exists()) {
                downloadEvents();
            } else {
                printEvents();
                listEvents();
            }
        }
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

                String srcRepository = "com.delkappa.manos.myapplication.";
                String departmentName = department.toString()+"Activity";
                String activityToStart = srcRepository + departmentName;
                Intent intent;
                try {
                    Class<?> c = Class.forName(activityToStart);
                    intent = new Intent(ThirdActivity.this, c);
                    Log.i("testNAME", activityToStart);
                    startActivity(intent);
                } catch (ClassNotFoundException ignored) {
                    Log.i("EXCEPTIONOFDEATH", activityToStart);
                }
            }
        });
        //this.findViewById(android.R.id.content);
        LinearLayout parent = findViewById(R.id.mainLinearLayout);
        parent.addView(myButton,j);
    }
    @Override
    public void onResume() {
        super.onResume();

        // Register the BroadcastReceiver
        LocalBroadcastManager.getInstance(this).registerReceiver(eventReceiver,
                new IntentFilter(INTENT_FILTER));

    }

    @Override
    public void onPause() {
        super.onPause();

        // Register the BroadcastReceiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(eventReceiver);

    }

    private BroadcastReceiver eventReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Event received...");

            ////////////////////////////////////////////////////////////////////////////////////////
            // TODO                                                                               //
            // The activity is notified by the DownloadService.                                   //
            // Run the post download related functions here:                                      //
            // - parseEvents();                                                                   //
            // - printEvents();                                                                   //
            // - listEvents();                                                                    //
            ////////////////////////////////////////////////////////////////////////////////////////

        }

    };

    @Override
    public void onRequestPermissionsResult(int request, String permissions[], int[] results) {
        switch (request) {
            case PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // storage-related task you need to do.
                    downloadEvents();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permission denied to read your external storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void downloadEvents() {
        Log.d(TAG, "Downloading events...");
        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra(REQUEST_URL, URL_PATH);
        startService(intent);
    }

    private void parseEvents() {
        Log.d(TAG, "Parsing...");

        ////////////////////////////////////////////////////////////////////////////////////////////
        // TODO                                                                                   //
        // Parse the downloaded file line by line and add all the parsed data to the database.    //
        ////////////////////////////////////////////////////////////////////////////////////////////

    }

    private void printEvents() {
        Log.d(TAG, "Printing...");

        ////////////////////////////////////////////////////////////////////////////////////////////
        // TODO                                                                                   //
        // Get all the rows from the database and print the data in the console.                  //
        ////////////////////////////////////////////////////////////////////////////////////////////

    }

    private void listEvents() {
        Log.d(TAG, "Listing...");

        ////////////////////////////////////////////////////////////////////////////////////////////
        // TODO                                                                                   //
        // Visualize the database data with a ListView:                                           //
        // - Get the list from database                                                           //
        // - Copy to a new list                                                                   //
        // - Create the ListView                                                                  //
        // - Handle onClick event                                                                 //
        ////////////////////////////////////////////////////////////////////////////////////////////

    }

    public void firstDepartment(View view) {
        // Create an intent for the activity
        Intent i = new Intent(this, DirectionActivity.class);

        // Start the activity
        startActivity(i);
    }
/*
    public void secondDepartment(View view) {
        // Create an intent for the activity
        Intent i = new Intent(this, secondDepartmentActivity.class);

        // Start the activity
        startActivity(i);
    }

    public void thirdDepartment(View view) {
        // Create an intent for the activity
        Intent i = new Intent(this, thirdDepartmentActivity.class);

        // Start the activity
        startActivity(i);
    }
*/
}
