package com.example.invictusapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.invictusapp.view.CoursesActivity;
import com.example.invictusapp.view.GiftcardActivity;
import com.example.invictusapp.view.NewsActivity;
import com.example.invictusapp.view.ProfileActivity;
import com.example.invictusapp.view.RequestsActivity;
import com.example.invictusapp.view.ResetPassword;
import com.example.invictusapp.view.Storage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {

    private static final String TAG = "WelcomeActivity";
    private TextView tvUserDetail;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private TextView btnSignOut;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tvUserDetail = (TextView) findViewById(R.id.tvUserDetail);
        btnSignOut = (TextView) findViewById(R.id.btnSignOut);
        progressDialog = new ProgressDialog(this);
        inicialize();

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                progressDialog.setMessage("Cerrando sesion, Espere...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void signOut()
    {
        firebaseAuth.signOut();
    }

    public void inicialize() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    tvUserDetail.setText("Bienvenid@ a Invictus App");
                } else {
                    Log.w(TAG, "onAuthStateChanged - signed_out ");
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public void goProfile(View view) {
        Intent intent = new Intent(WelcomeActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void goNews(View view) {
        Intent intent = new Intent(WelcomeActivity.this, NewsActivity.class);
        startActivity(intent);
    }

    public void goRequests(View view) {
        Intent intent = new Intent(WelcomeActivity.this, RequestsActivity.class);
        startActivity(intent);
    }

    public void goStorage(View view) {
        Intent intent = new Intent(WelcomeActivity.this, Storage.class);
        startActivity(intent);
    }

    public void goCourses(View view) {
        Intent intent = new Intent(WelcomeActivity.this, CoursesActivity.class);
        startActivity(intent);
    }

    public void goGiftcards(View view) {
        Intent intent = new Intent(WelcomeActivity.this, GiftcardActivity.class);
        startActivity(intent);
    }

    public void traerUser(){

    }
}