package com.example.elisa.projetapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.elisa.projetapplication.myrequest.MyRequest;


public class LoginActivity extends AppCompatActivity {

    private TextInputLayout til_pseudo, til_password;
    private Button btn_send;
    private RequestQueue queue;
    private MyRequest request;
    private ProgressBar pb_loader;
    private Handler handler;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();

        //Récupération du message de l'inscription
        if (intent.hasExtra("REGISTER")){
            Toast.makeText(this, intent.getStringExtra("REGISTER"), Toast.LENGTH_SHORT).show();
        }


        til_pseudo = (TextInputLayout) findViewById(R.id.til_pseudo);
        til_password = (TextInputLayout) findViewById(R.id.til_password);
        btn_send = (Button) findViewById(R.id.btn_send);
        pb_loader = (ProgressBar) findViewById(R.id.pb_loader);
        handler = new Handler();
        sessionManager = new SessionManager(this);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);

        btn_send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                final String pseudo = til_pseudo.getEditText().getText().toString().trim();
                final String password = til_password.getEditText().getText().toString().trim();
                pb_loader.setVisibility(View.VISIBLE);

                if (pseudo.length()> 0 && password.length() > 0) {

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            request.connexion(pseudo, password, new MyRequest.LoginCallback() {
                                @Override
                                public void onSuccess(String id, String pseudo) {

                                    pb_loader.setVisibility(View.GONE);
                                    sessionManager.insertUtilisateur(id, pseudo);
                                    Intent intent = new Intent(getApplicationContext(), PremiereActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                                @Override
                                public void onError(String message) {
                                    pb_loader.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }, 1000);

                }else {
                    Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }



}
