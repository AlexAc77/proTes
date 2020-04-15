package com.example.protes.Vista.LoginView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.protes.Presentador.LoginPresenter.presentadorLogin;
import com.example.protes.R;
import com.example.protes.Vista.PrincipalView.VistaPrincipal;
import com.example.protes.Vista.RegistroView.VistaRegistro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class vistaLogin extends AppCompatActivity implements View.OnClickListener {

    private EditText txtEmail, txtPasswor;
    private presentadorLogin presentadorLogin;
    private static final String TAG = "vistaLogin";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         mAuth = FirebaseAuth.getInstance();
        DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference();
        presentadorLogin = new presentadorLogin(this, mAuth, dataBase);


        Button btnIngresar = findViewById(R.id.btnIngresar);
        Button btnRegistro = findViewById(R.id.btnRegistro);
        txtEmail = findViewById(R.id.txtEmail);
        txtPasswor = findViewById(R.id.txtPasswor);
        btnIngresar.setOnClickListener(this);
        btnRegistro.setOnClickListener(this);


    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        presentadorLogin.updateUI(currentUser);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnIngresar:
                String email = txtEmail.getText().toString().trim();
                String password = txtPasswor.getText().toString().trim();
                presentadorLogin.singInUser(email, password);

                break;
            case R.id.btnRegistro:
                Intent intent = new Intent(vistaLogin.this, VistaRegistro.class);
                startActivity(intent);
                break;
        }

    }
}
