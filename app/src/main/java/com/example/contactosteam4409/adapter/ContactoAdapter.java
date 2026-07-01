package com.example.contactosteam4409.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactosteam4409.R;
import com.example.contactosteam4409.models.Contacto;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.ViewHolder> {

    public interface OnContactoListener {

        void onEditar(Contacto contacto);

        void onEliminar(Contacto contacto);

        void onLlamar(Contacto contacto);

    }

    private final List<Contacto> listaContactos;

    private final OnContactoListener listener;

    public ContactoAdapter(
            List<Contacto> listaContactos,
            OnContactoListener listener) {

        this.listaContactos = listaContactos;

        this.listener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contacto, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Contacto contacto = listaContactos.get(position);

        holder.txtNombre.setText(contacto.getNombre());
        holder.txtTelefono.setText(contacto.getTelefono());
        holder.txtPais.setText(contacto.getPais());

        if (contacto.getFotoBase64() != null &&
                !contacto.getFotoBase64().isEmpty()) {

            byte[] bytes = Base64.decode(
                    contacto.getFotoBase64(),
                    Base64.DEFAULT
            );

            Bitmap bitmap = BitmapFactory.decodeByteArray(
                    bytes,
                    0,
                    bytes.length
            );

            holder.imgFoto.setImageBitmap(bitmap);

        } else {

            holder.imgFoto.setImageResource(
                    android.R.drawable.ic_menu_camera
            );

        }

        holder.btnEditar.setOnClickListener(v -> {

            if (listener != null) {

                listener.onEditar(contacto);

            }

        });

        holder.btnEliminar.setOnClickListener(v -> {

            if (listener != null) {

                listener.onEliminar(contacto);

            }

        });

        holder.btnLlamar.setOnClickListener(v -> {

            if (listener != null) {

                listener.onLlamar(contacto);

            }

        });

    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFoto;

        TextView txtNombre;
        TextView txtTelefono;
        TextView txtPais;

        LinearLayout btnEditar;
        LinearLayout btnEliminar;
        LinearLayout btnLlamar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFoto = itemView.findViewById(R.id.imgFoto);

            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
            txtPais = itemView.findViewById(R.id.txtPais);

            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            btnLlamar = itemView.findViewById(R.id.btnLlamar);
        }
    }
}