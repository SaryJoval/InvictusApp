package com.example.invictusapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.invictusapp.view.CreateAccountActivity;
import com.example.invictusapp.view.NewsActivity;
import com.example.invictusapp.view.ResetPassword;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.logging.LoggingMXBean;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    //private Button btnCreateAccount;
    private Button btnSignIn;

    private EditText edtEmail;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        inicialize();

        /*btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(edtEmail.getText().toString(), edtPassword.getText().toString());
            }
        });*/

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                if(!validarEmail(email) || email.isEmpty())
                {
                    edtEmail.setError("Favor ingrese mail valido");
                }
                else if(password.isEmpty())
                {
                    edtPassword.setError("Favor ingrese contraseña");
                }
                else
                {
                    signIn(email,password);
                }

            }
        });
    }

    public void goRegister(View view){
        Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
        startActivity(intent);
    }
    public void goResetPassword(View view){
        Intent intent = new Intent(MainActivity.this, ResetPassword.class);
        startActivity(intent);
    }

    public void inicialize(){
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    Log.w(TAG, "onAuthStateChanged - signed_in " + firebaseUser.getUid());
                    Log.w(TAG, "onAuthStateChanged - signed_in " + firebaseUser.getEmail());
                }else{
                    Log.w(TAG, "onAuthStateChanged - signed_out " );
                }
            }
        };
    }
    private void signIn(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Autentificacion Exitosa", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MainActivity.this, WelcomeActivity.class);
                            startActivity(i);
                            finish();
                        }else {
                            Toast.makeText(MainActivity.this, "No es posible iniciar sesion, favor validar sus datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    private boolean validarEmail(String email) {
        Pattern patterns = Patterns.EMAIL_ADDRESS;
        return patterns.matcher(email).matches();
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
}
