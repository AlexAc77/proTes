package com.example.protes.Presentador.vPrincipalPresenter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protes.Adaptadores.RecycleProductoAdapter;
import com.example.protes.Modelo.ProductoModel;
import com.example.protes.Modelo.UserModel;
import com.example.protes.R;
import com.example.protes.Vista.LoginView.vistaLogin;
import com.example.protes.Vista.PrincipalView.VistaPrincipal;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PresentadorVistaPrincipal {

    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private DatabaseReference dataBase;
    private Context context;
    private EditText txtNombrePro, txtDescripcionPro, txtPrecioPro;
    private RecycleProductoAdapter adapter;

    public PresentadorVistaPrincipal(Context context, FirebaseAuth mAuth, DatabaseReference dataBase,StorageReference storageReference) {
        this.context = context;
        this.mAuth = mAuth;
        this.dataBase = dataBase;
        this.storageReference =storageReference;
    }

    public void Wellcome() {
        dataBase.child("Usuario").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                Toast.makeText(context, "Bienvenido :  " + userModel.getUserName(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void cargarProductoFireBase(final String NombreProducto, final String DescripcionProducto, final float PrecioProducto, final Dialog dialog, final ProgressDialog progressDialog, Uri filePath) {
         if (filePath!=null) {
             final StorageReference fotoRef = storageReference.child("Fotos").child(mAuth.getCurrentUser().getUid()).child(filePath.getLastPathSegment());
            fotoRef.putFile(filePath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw new Exception();
                    }
                    return fotoRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri donloaLink= task.getResult();
                        Map<String, Object> producto = new HashMap<>();
                        producto.put("nombreProducto", NombreProducto);
                        producto.put("descripcionProducto", DescripcionProducto);
                        producto.put("precioProducto", PrecioProducto);
                        producto.put("imagen",donloaLink.toString());
                        dataBase.child("Usuario").child(mAuth.getCurrentUser().getUid()).child("Productos").push().updateChildren(producto).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                dialog.dismiss();
                                progressDialog.dismiss();
                                Toast.makeText(context, "Cargado Exitosamente", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            });
         }

    }

    public void CargarRecycleView(final RecyclerView recyclerView) {
        dataBase.child("Usuario").child(mAuth.getCurrentUser().getUid()).child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<ProductoModel> arrayListProductos = new ArrayList<>();

                for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {

                    ProductoModel productoModel = snapshot1.getValue(ProductoModel.class);
                    //obtenemos los datos de Firebase
                    String NombrePro = productoModel.getNombreProducto();
                    String descripcionPro = productoModel.getDescripcionProducto();
                    float precioPro = productoModel.getPrecioProducto();
                    String imagenPro = productoModel.getImagen();


                    //set capos
                    productoModel.setNombreProducto(NombrePro);
                    productoModel.setDescripcionProducto(descripcionPro);
                    productoModel.setPrecioProducto(precioPro);
                    productoModel.setImagen(imagenPro);

                    arrayListProductos.add(productoModel);
                }
                adapter = new RecycleProductoAdapter(context, R.layout.productorow, arrayListProductos);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void salir(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(context, vistaLogin.class);
        context.startActivity(intent);

    }


}
