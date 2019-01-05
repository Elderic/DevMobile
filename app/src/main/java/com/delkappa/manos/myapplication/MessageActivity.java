package com.delkappa.manos.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;

public class MessageActivity extends AppCompatActivity {
    TextInputEditText mTextInputLayoutMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        mTextInputLayoutMessage = findViewById(R.id.messageInput);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Bundle b = getIntent().getExtras();
        String data = b.getString("Number");
        Log.i("TESTBUNDLEVALUE", data);
    }

    public void sendMessage( View view) {
        SmsManager smsManager = SmsManager.getDefault();

        Bundle b = getIntent().getExtras();
        String data = b.getString("Number");
        String message = mTextInputLayoutMessage.getText().toString();
        smsManager.sendTextMessage("Number", null, message, null, null);
    }
}
