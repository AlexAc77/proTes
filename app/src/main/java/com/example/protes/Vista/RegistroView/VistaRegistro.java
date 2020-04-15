package com.example.protes.Vista.RegistroView;

import android.os.Bundle;

import com.example.protes.Presentador.RegistroPresenter.PresentadorRegistro;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.protes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VistaRegistro extends AppCompatActivity implements View.OnClickListener {

    private EditText txtEmail,txtNombre,txtUser,txtPasswor,txtRePasswor;
    private PresentadorRegistro presentadorRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_registro);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference();
        presentadorRegistro = new PresentadorRegistro(this,mAuth,dataBase);
        txtNombre=findViewById(R.id.txtNombre);
        txtUser=findViewById(R.id.txtUser);
        txtEmail=findViewById(R.id.txtEmail);
        txtPasswor=findViewById(R.id.txtPasswor);
        txtRePasswor=findViewById(R.id.txtRePasswor);
        Button btnRegistrar=findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegistrar:
                String nombre=txtNombre.getText().toString().trim();
                String user=txtUser.getText().toString().trim();
                String email=txtEmail.getText().toString().trim();
                String password=txtPasswor.getText().toString().trim();
                String confirPassword=txtRePasswor.getText().toString().trim();
                if (password.equals(confirPassword)){
                presentadorRegistro.SingUpUser(email,password,nombre,user);
                }else{
                    Toast.makeText(this,"Error las Contraseñas no coinciden",Toast.LENGTH_LONG).show();
                }
            break;
        }

    }
}
