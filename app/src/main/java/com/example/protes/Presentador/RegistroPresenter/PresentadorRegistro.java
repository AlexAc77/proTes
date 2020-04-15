package com.example.protes.Presentador.RegistroPresenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.protes.Vista.PrincipalView.VistaPrincipal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

public class PresentadorRegistro {
    private String TAG ="presentadorRegistro";
    private Context context;
    private FirebaseAuth mAuth;
    private DatabaseReference dataBase;

    public PresentadorRegistro(Context context, FirebaseAuth mAuth, DatabaseReference dataBase) {
        this.context = context;
        this.mAuth = mAuth;
        this.dataBase = dataBase;
    }
    public void SingUpUser(final String email, String password, final String nombreCompleto, final String userName){
        final  ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Ingresando Datos");
        progressDialog.setMessage("Registrando....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            progressDialog.dismiss();
                            Map<String,Object> crearUsuario = new HashMap<>();
                            crearUsuario.put("nombre",nombreCompleto);
                            crearUsuario.put("userName",userName);
                            crearUsuario.put("email",email);
                            dataBase.child("Usuario").child(task.getResult().getUser().getUid()).updateChildren(crearUsuario);
                            Intent intent = new Intent(context, VistaPrincipal.class);
                            context.startActivity(intent);


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            progressDialog.dismiss();
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
}
