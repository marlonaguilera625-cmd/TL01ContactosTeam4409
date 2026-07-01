package com.example.contactosteam4409.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactosteam4409.R;
import com.example.contactosteam4409.adapter.ContactoAdapter;
import com.example.contactosteam4409.dao.ContactoDAO;
import com.example.contactosteam4409.models.Contacto;

import java.util.List;

public class ListaContactosActivity extends AppCompatActivity {

    private RecyclerView rvContactos;
    private ContactoAdapter adapter;
    private ContactoDAO contactoDAO;
    private List<Contacto> listaContactos;

    // Contacto seleccionado para llamar
    private Contacto contactoLlamada;

    // Solicitud del permiso CALL_PHONE
    private final ActivityResultLauncher<String> permisoLlamada =
            registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    isGranted -> {

                        if (isGranted) {

                            realizarLlamada();

                        } else {

                            Toast.makeText(
                                    ListaContactosActivity.this,
                                    "Permiso de llamadas denegado.",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);

        rvContactos = findViewById(R.id.rvContactos);
        rvContactos.setLayoutManager(new LinearLayoutManager(this));

        contactoDAO = new ContactoDAO(this);

        cargarContactos();
    }

    @Override
    protected void onResume() {
        super.onResume();

        cargarContactos();
    }

    private void cargarContactos() {

        listaContactos = contactoDAO.obtenerContactos();

        adapter = new ContactoAdapter(listaContactos, new ContactoAdapter.OnContactoListener() {

            @Override
            public void onEditar(Contacto contacto) {

                Intent intent = new Intent(
                        ListaContactosActivity.this,
                        RegistroActivity.class
                );

                intent.putExtra("ID_CONTACTO", contacto.getId());

                startActivity(intent);

            }

            @Override
            public void onEliminar(Contacto contacto) {

                new AlertDialog.Builder(ListaContactosActivity.this)
                        .setTitle("Eliminar contacto")
                        .setMessage("¿Desea eliminar el contacto \"" + contacto.getNombre() + "\"?")
                        .setPositiveButton("Sí", (dialog, which) -> {

                            contactoDAO.eliminarContacto(contacto.getId());

                            cargarContactos();

                        })
                        .setNegativeButton("No", null)
                        .show();

            }

            @Override
            public void onLlamar(Contacto contacto) {

                contactoLlamada = contacto;

                if (ContextCompat.checkSelfPermission(
                        ListaContactosActivity.this,
                        Manifest.permission.CALL_PHONE)
                        == PackageManager.PERMISSION_GRANTED) {

                    realizarLlamada();

                } else {

                    permisoLlamada.launch(
                            Manifest.permission.CALL_PHONE
                    );

                }

            }

        });

        rvContactos.setAdapter(adapter);

    }

    private void realizarLlamada() {

        if (contactoLlamada == null) {
            return;
        }

        Intent intent = new Intent(
                Intent.ACTION_CALL,
                Uri.parse("tel:" + contactoLlamada.getTelefono())
        );

        startActivity(intent);

    }

}