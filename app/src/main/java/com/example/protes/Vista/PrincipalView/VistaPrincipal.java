package com.example.protes.Vista.PrincipalView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.example.protes.Presentador.vPrincipalPresenter.PresentadorVistaPrincipal;
import com.example.protes.Vista.LoginView.vistaLogin;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.protes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class VistaPrincipal extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private DatabaseReference dataBase;
    private StorageReference storageReference;
    private PresentadorVistaPrincipal presentadorVistaPrincipal;
    private Dialog dialog;
    private ProgressDialog progressDialog;
    private final int PICK_PHOTO = 1;
    private Uri filePath;
    private ImageView imagenProduct;
    //private Button btnSalir;
    private EditText txtNombrePro, txtDescripcionPro, txtPrecioPro;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_principal);


        mAuth = FirebaseAuth.getInstance();
        dataBase = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        FloatingActionButton floatingActionButton =findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
        Button btnSalir = findViewById(R.id.btnSalir);
        btnSalir.setOnClickListener(this);
        presentadorVistaPrincipal = new PresentadorVistaPrincipal(this,mAuth,dataBase,storageReference);
        presentadorVistaPrincipal.Wellcome();
        initRecycle();




    }
    public void initRecycle(){
        RecyclerView recyclerView=findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        presentadorVistaPrincipal.CargarRecycleView(recyclerView);

    }
    public void cargarProducto() {


        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogrow);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        txtNombrePro = dialog.findViewById(R.id.txtNombreProducto);
        txtDescripcionPro = dialog.findViewById(R.id.txtDescripcionProducto);
        txtPrecioPro = dialog.findViewById(R.id.txtPrecioProducto);

        Button btnProducto = dialog.findViewById(R.id.btnProducto);
        ImageView btnFoto = dialog.findViewById(R.id.imgFoto);
        btnProducto.setOnClickListener(this);
        btnFoto.setOnClickListener(this);
        imagenProduct = dialog.findViewById(R.id.imgFoto);
        dialog.show();


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSalir:
                presentadorVistaPrincipal.salir();

            break;
            case R.id.fab:
                cargarProducto();
                break;
            case R.id.btnProducto:
                progressDialog=new ProgressDialog(this);
                progressDialog.setTitle("Cargando....");
                progressDialog.setMessage("Añadiendo Producto...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                String nombrePro=txtNombrePro.getText().toString().trim();
                String descripcionPro=txtDescripcionPro.getText().toString().trim();
                float precioPro= Float.parseFloat(txtPrecioPro.getText().toString().trim());
                presentadorVistaPrincipal.cargarProductoFireBase( nombrePro,  descripcionPro,  precioPro,dialog,progressDialog,filePath);
                break;

            case R.id.imgFoto:
                abrirGaleria();
                break;
        }
    }
    private void abrirGaleria(){
        Intent intent = new Intent();
        intent.setType("image/jpeg");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Seleccione una imagen"),PICK_PHOTO );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_PHOTO && resultCode == RESULT_OK && data !=null  && data.getData()!=null){
            filePath = data.getData();
            try {
                Bitmap bitmapImagen = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imagenProduct.setImageBitmap(bitmapImagen);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
