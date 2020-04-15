package com.example.protes.Vista.ProductoView;

import android.os.Bundle;

import com.example.protes.Presentador.ProductoPresenter.PresentadorProducto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.protes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class VistaProductoDetalles extends AppCompatActivity {

    private PresentadorProducto presentadorProducto;
    private FirebaseAuth mAuth;
    private DatabaseReference dataBase;
    private StorageReference storageReference;
    private TextView lblProducto;

    private EditText txtNombrePro, txtDescripcionPro, txtPrecioPro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_producto_detalles);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lblProducto=findViewById(R.id.lblProducto);
        txtNombrePro = findViewById(R.id.txtNombreProductoDetalle);
        txtDescripcionPro = findViewById(R.id.txtDescripcionProductoDetalle);
        txtPrecioPro =findViewById(R.id.txtPrecioProductoDetaalle);
        Button btnProducto = findViewById(R.id.btnProductoDetalle);
        ImageView imagenProduct = findViewById(R.id.imgFotoProducto);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
