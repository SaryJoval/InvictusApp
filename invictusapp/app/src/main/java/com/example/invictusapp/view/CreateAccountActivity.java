package com.example.invictusapp.view;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.ProgressDialog;
import com.example.invictusapp.MainActivity;
import com.example.invictusapp.R;
import com.example.invictusapp.modelo.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {

    private static final String USUARIOS_NODE = "Usuario";
    private DatabaseReference databaseReference;
    private static final String TAG = "MainActivity";
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

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

        //Base de datos
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Autorizacion
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        edtRutRef = (EditText) findViewById((R.id.edtRut));
        edtNombreRef = (EditText) findViewById(R.id.edtNombre);
        edtMailRef = (EditText) findViewById(R.id.edtEmail);
        edtTelefonoRef = (EditText) findViewById(R.id.edtTelefono);
        edtPasswordRef = (EditText) findViewById(R.id.edtPassword);
        edtPassword2Ref = (EditText) findViewById(R.id.edtPassword2);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String rut = edtRutRef.getText().toString();
                String nombre = edtNombreRef.getText().toString().trim();
                String email = edtMailRef.getText().toString().trim();
                if(edtTelefonoRef.getText().toString().length() == 0){
                    edtTelefonoRef.setText("0");
                }
                int telefono = Integer.parseInt(edtTelefonoRef.getText().toString());
                int longitud = String.valueOf(telefono).length();
                String pass = edtPasswordRef.getText().toString().trim();
                String pass2 = edtPassword2Ref.getText().toString();
                int contador= 0;


                if(!validarRut(rut)){
                    edtRutRef.setError("El rut no es valido");
                }
                else {
                    //Asignar variable a parametro en tabla
                    contador=contador+1;
                }
                if(nombre.isEmpty())
                {
                    edtNombreRef.setError("Favor Ingresar Nombre Completo");
                }
                else {
                    //Asignar variable a parametro en tabla
                    contador=contador+1;
                }
                if(!validarEmail(email) || email.isEmpty())
                {
                    edtMailRef.setError("El email no es valido");
                }
                else {
                    //Asignar variable a parametro en tabla
                    contador=contador+1;
                }
                if(longitud!=9)
                {
                    edtTelefonoRef.setError("Ingrese telefono de 9 digitos");
                }
                else {
                    //Asignar variable a parametro en tabla
                    contador=contador+1;
                }
                if(!validarPass(pass))
                {
                    Toast.makeText(CreateAccountActivity.this,"Error en la contraseña" , Toast.LENGTH_SHORT).show();
                }
                else
                {
                    contador=contador+1;
                }
                if(pass.isEmpty())
                {
                    edtPasswordRef.setError("Ingrese contraseña");
                }
                else if(pass2.isEmpty())
                {
                    edtPassword2Ref.setError("Repita contraseña");
                }
                else
                    {
                    contador=contador+1;
                }
                if(pass.equals(pass2))
                {
                    //Toast.makeText(CreateAccountActivity.this,"las contraseñas coinciden" , Toast.LENGTH_SHORT).show();
                    contador=contador+1;
                }
                else
                {
                    Toast.makeText(CreateAccountActivity.this,"las contraseñas no coinciden" , Toast.LENGTH_SHORT).show();
                }
                if(contador==7)
                {
                    progressDialog.setMessage("Realizando registro en linea...");
                    progressDialog.show();
                    createAccount(email,pass);
                    createUser();
                    Intent i = new Intent(CreateAccountActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
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
    private boolean validarPass(String Password)
    {
        boolean validacion = true;

        if (!Password.matches(".*[!@#$%^&*+=?-].*")){
            edtPasswordRef.setError("Ingrese almenos un caracter especial");
            validacion=false;
        }

        if (!Password.matches(".*\\d.*")){
            edtPasswordRef.setError("Ingrese almenos un numero");
            validacion=false;
        }

        if (!Password.matches(".*[a-z].*")){
            edtPasswordRef.setError("Ingrese almenos una minuscula");
            validacion=false;
        }

        if (!Password.matches(".*[A-Z].*")){
            edtPasswordRef.setError("Ingrese almenos una mayuscula");
            validacion=false;
        }

        if (!Password.matches(".{8,15}")){
            edtPasswordRef.setError("Ingrese contraseña de 8 a 15 caracteres");
            validacion=false;
        }

        if (Password.matches(".*\\s.*")){
            edtPasswordRef.setError("No ingrese espacios");
            validacion=false;
        }
        return validacion;
    }

    private void createAccount(String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(CreateAccountActivity.this, "Se ha creado una cuenta", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(CreateAccountActivity.this, "No se ha podido crear cuenta", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    //Creación usuario
    public void createUser(){
        String rut = edtRutRef.getText().toString();
        String nombre = edtNombreRef.getText().toString();
        String email = edtMailRef.getText().toString().trim();
        int telefono = Integer.parseInt(edtTelefonoRef.getText().toString());
        int longitud = String.valueOf(telefono).length();
        String pass = edtPasswordRef.getText().toString().trim();

        Usuario usuario = new Usuario(databaseReference.push().getKey(), rut, nombre, telefono, true, "","", email);
        databaseReference.child(USUARIOS_NODE).child(usuario.getId()).setValue(usuario);
    }
}
