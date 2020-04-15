package com.example.protes.Adaptadores;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.protes.Modelo.ProductoModel;
import com.example.protes.R;
import com.example.protes.Vista.ProductoView.VistaProductoDetalles;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class RecycleProductoAdapter extends RecyclerView.Adapter<RecycleProductoAdapter.ProductoViewHolder> {
    private Context context;
    private int layoutResource;
    private ArrayList<ProductoModel> arrayListProductos;


    public RecycleProductoAdapter(Context context, int layoutResource, ArrayList<ProductoModel> arrayListProductos) {
        this.context = context;
        this.layoutResource = layoutResource;
        this.arrayListProductos = arrayListProductos;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutResource, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductoViewHolder holder, int position) {

        ProductoModel productoModel = arrayListProductos.get(position);
        holder.blNombreProduc.setText("Nombre:" + productoModel.getNombreProducto());
        holder.blDescripcionProduc.setText("Descripcion:" + productoModel.getDescripcionProducto());
        holder.blPrecioProduc.setText("precio:" + (int) productoModel.getPrecioProducto());
        //holder.magenProduct.setImageResource(R.drawable.ic_insert_emoticon_black_24dp);
        //cargar imagen desde firebase
        Glide
                .with(context)
                .load(productoModel.getImagen() )
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.magenProduct.setVisibility(View.VISIBLE);
                        holder.magenProduct.setImageResource(R.drawable.ic_broken_image_black_24dp);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.magenProduct.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(holder.magenProduct);


    }

    @Override
    public int getItemCount() {
        if (arrayListProductos.size() > 0) {
            return arrayListProductos.size();
        }
        return 0;
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView blDescripcionProduc, blNombreProduc, blPrecioProduc;
        ImageView magenProduct;
        ProgressBar progressBar;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            blNombreProduc = itemView.findViewById(R.id.lblNombreProduc);
            blDescripcionProduc = itemView.findViewById(R.id.lblDescripcionProduc);
            blPrecioProduc = itemView.findViewById(R.id.lblPrecioProduc);
            magenProduct = itemView.findViewById(R.id.imagenProducto);
            progressBar = itemView.findViewById(R.id.progressBar);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(context, VistaProductoDetalles.class);
            //extras



            context.startActivity(intent);

        }
    }
}
