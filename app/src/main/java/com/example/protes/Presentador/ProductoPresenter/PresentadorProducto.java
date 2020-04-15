package com.example.protes.Presentador.ProductoPresenter;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.protes.Modelo.ProductoModel;
import com.example.protes.Modelo.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class PresentadorProducto {
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private DatabaseReference dataBase;
    private Context context;

    public PresentadorProducto(FirebaseAuth mAuth, StorageReference storageReference, DatabaseReference dataBase, Context context) {
        this.mAuth = mAuth;
        this.storageReference = storageReference;
        this.dataBase = dataBase;
        this.context = context;
    }
    public void DetalleProducto() {
        dataBase.child("Usuario").child("Productos").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProductoModel productoModel = dataSnapshot.getValue(ProductoModel.class);
                Toast.makeText(context, "Producto :  " + productoModel.getNombreProducto(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
