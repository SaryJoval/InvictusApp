package com.example.invictusapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.invictusapp.MainActivity;
import com.example.invictusapp.R;
import com.example.invictusapp.WelcomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class ResetPassword extends AppCompatActivity {

    private EditText edtEmail;
    private Button btnSignIn;
    private FirebaseAuth firebaseAuth;
    private String email="";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = edtEmail.getText().toString().trim();
                if(!validarEmail(email) || email.isEmpty())
                {
                    edtEmail.setError("Favor ingrese email valido");
                }
                else {
                    progressDialog.setMessage("Espere un momento...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    resetPassword();
                }

            }
            private void resetPassword()
            {
                    firebaseAuth.setLanguageCode("es");
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(ResetPassword.this, "Se ha enviado un correo para reestablecer contraseña", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ResetPassword.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(ResetPassword.this, "Es posible que el correo ingresado no este registrado o  este mal escrito", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
            }

            private boolean validarEmail(String email) {
                Pattern patterns = Patterns.EMAIL_ADDRESS;
                return patterns.matcher(email).matches();
            }
        });

    }
}
