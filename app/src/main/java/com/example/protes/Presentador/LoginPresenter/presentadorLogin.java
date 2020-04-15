package com.example.protes.Presentador.LoginPresenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.protes.Vista.LoginView.vistaLogin;
import com.example.protes.Vista.PrincipalView.VistaPrincipal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class presentadorLogin {
    private String TAG = "presentadorLogin";
    private Context context;
    private FirebaseAuth mAuth;
    private DatabaseReference dataBase;
    private vistaLogin vistaLogin;

    public presentadorLogin(Context context, FirebaseAuth mAuth, DatabaseReference dataBase) {
        this.context = context;
        this.mAuth = mAuth;
        this.dataBase = dataBase;
    }

    public void singInUser(String email, String password) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(context, "Ingrese un E-mail", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "Ingrese una Contraseña", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setTitle("Realizando Consulta");
        progressDialog.setMessage("ingresando....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:Exitoso");
                            progressDialog.dismiss();

                            Intent intent = new Intent(context, VistaPrincipal.class);
                            context.startActivity(intent);
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);


                        } else {
                            progressDialog.dismiss();
                            // If sign in fails, display a message to the user.

                            Toast.makeText(context, "los datos son incorrectos", Toast.LENGTH_SHORT).show();
                            updateUI(null);

                        }

                        // ...
                    }
                });


    }
    public void updateUI(FirebaseUser currentUser) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(context, VistaPrincipal.class);
            context.startActivity(intent);
        }
    }




}
