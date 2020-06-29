package com.example.invictusapp.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.example.invictusapp.R;

import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText edtRutRef;
    private EditText edtNombreRef;
    private EditText edtMailRef;
    private EditText edtTelefonoRef;
    private EditText edtPasswordRef;
    private EditText edtPassword2Ref;

    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        //showToolbar(getResources().getString(R.string.toolbar_tittle_createaccount),false);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        edtRutRef = (EditText) findViewById((R.id.edtRut));
        edtNombreRef = (EditText) findViewById(R.id.edtNombre);
        edtMailRef = (EditText) findViewById(R.id.edtEmail);
        /* edtTelefonoRef = (EditText) findViewById(R.id.edtTelefono);
        edtPasswordRef = (EditText) findViewById(R.id.edtPassword);
        edtPassword2Ref = (EditText) findViewById(R.id.edtPassword2);
         */

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String rut = edtRutRef.getText().toString();
                String nombre = edtNombreRef.getText().toString();
                String email = edtMailRef.getText().toString();
                /*String telefono = edtTelefonoRef.getText().toString();
                int telefonou = Integer.parseInt(telefono);
                String pass = edtPasswordRef.getText().toString();
                String pass2 = edtPassword2Ref.getText().toString();
                */
                if(!validarRut(rut) || rut.isEmpty()){
                    edtRutRef.setError("El rut no es valido");
                }
                else {
                    //Asignar variable a parametro en tabla
                }
                if(nombre.isEmpty())
                {
                    edtNombreRef.setError("Favor Ingresar Nombre Completo");
                }
                if(!validarEmail(email) || email.isEmpty())
                {
                    edtMailRef.setError("El email no es valido");
                }
            }
        });
    }

    public static boolean validarRut(String rut) {
        boolean validacion = false;
        try {
            rut =  rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validacion;
    }

    private boolean validarEmail(String email) {
        Pattern patterns = Patterns.EMAIL_ADDRESS;
        return patterns.matcher(email).matches();
    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
}
