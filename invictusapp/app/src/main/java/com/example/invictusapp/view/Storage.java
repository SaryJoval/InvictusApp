package com.example.invictusapp.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.invictusapp.MainActivity;
import com.example.invictusapp.R;
import com.example.invictusapp.WelcomeActivity;
import com.example.invictusapp.modelo.Solicitud;
import com.example.invictusapp.modelo.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.lang.ref.SoftReference;
import java.util.concurrent.Executor;

public class Storage extends AppCompatActivity {

    private static final int CHOOSER_IMAGES = 1;
    private static final String TAG = "STORAGE";
    private static final String SOLICITUD_NODE = "Solicitud";
    private static final String USUARIO_NODE = "Usuario";
    private DatabaseReference databaseReference;
    private DatabaseReference dbUser;
    //private Button btnDownload;
    private Button btnUpload;
    private ImageView imvImage;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;
    private StorageReference documentoRef;
    private String downloadUri;
    private EditText edtAsunto;
    private EditText edtDescripcion;
    private String user;
    private String nombreuser;


    //Sesion usuario
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        storageReference = FirebaseStorage.getInstance().getReference();
        //btnDownload = (Button) findViewById(R.id.btnDownload);
        edtAsunto = (EditText) findViewById(R.id.edtAsunto);
        edtDescripcion = (EditText) findViewById(R.id.edtDescripcion);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        imvImage = (ImageView) findViewById(R.id.imvImage);
        progressDialog = new ProgressDialog(this);

        //Base de datos
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Sesion
        inicialize();

        imvImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"Seleccione una imagen"),CHOOSER_IMAGES);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String asunto = edtAsunto.getText().toString().trim();
                String descripcion = edtDescripcion.getText().toString().trim();

                int count =0;

                if (asunto.isEmpty()) {
                    edtAsunto.setError("Favor ingresar asunto");
                }
                if (descripcion.isEmpty()) {
                    edtDescripcion.setError("Favor ingresar descripcion del asunto");
                }
                //if (imvImage.getDrawable() == null) {
                  // Toast.makeText(Storage.this, "Favor ingresar imagen", Toast.LENGTH_SHORT).show();
                //}
                //else {
                    progressDialog.setTitle("Procesando...");
                    progressDialog.setMessage("Enviando solicitud de reembolso");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    //documentoRef = storageReference.child("documento.png");
                    imvImage.setDrawingCacheEnabled(true);
                    imvImage.buildDrawingCache();

                    Bitmap bitmap = imvImage.getDrawingCache();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                    byte[] documentoByte = baos.toByteArray();

                    final UploadTask uploadTask = documentoRef.putBytes(documentoByte);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Ocurrió un error en la subida del archivo");
                            e.printStackTrace();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(Storage.this, "Solicitud enviada exitosamente", Toast.LENGTH_LONG).show();
                            downloadUri = taskSnapshot.getUploadSessionUri().getPath();
                            //downloadUri = documentoRef.getDownloadUrl().toString();
                            Log.w(TAG, "image URL: " + downloadUri);
                            createRequest();
                            edtAsunto.setText("");
                            edtDescripcion.setText("");
                            
                            Intent i = new Intent(Storage.this, WelcomeActivity.class);
                            startActivity(i);
                            finish();


                        }
                    });
                //}
            }
        });
    }

    public void inicialize() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    user = firebaseUser.getEmail();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSER_IMAGES){
            Uri imageUri = data.getData();
            if(imageUri != null){
                imvImage.setImageURI(imageUri);
                documentoRef = storageReference.child(imageUri.getLastPathSegment());
                //downloadUri= documentoRef.getDownloadUrl().toString();
            }
        }
    }
    public void createRequest(){

        String asunto = edtAsunto.getText().toString().trim();
        String descripcion = edtDescripcion.getText().toString().trim();
        //nombreuser = databaseReference.child(USUARIO_NODE).orderByChild("mail").equalTo(user).addChildEventListener(new ChildEventListener()
        nombreuser ="-MBNY_IQmH4Ltogxoh5B";
        Solicitud solicitud = new Solicitud(databaseReference.push().getKey(),asunto, descripcion, downloadUri, nombreuser, "1", "1"  );
        databaseReference.child(SOLICITUD_NODE).child(solicitud.getId()).setValue(solicitud);
    }

    public void getUser(){
        nombreuser = databaseReference.child(USUARIO_NODE).child("Id").orderByChild("mail").equalTo(user).toString();
    }

}
