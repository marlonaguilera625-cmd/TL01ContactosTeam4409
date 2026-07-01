package com.example.contactosteam4409.views;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contactosteam4409.R;
import com.example.contactosteam4409.dao.ContactoDAO;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

import android.net.Uri;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;

import android.Manifest;

import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Base64;

import java.io.ByteArrayOutputStream;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.widget.Toast;

import com.example.contactosteam4409.models.Contacto;

import android.graphics.ImageDecoder;
import android.os.Build;
import android.provider.MediaStore;

import java.io.IOException;

import android.content.Intent;

import android.graphics.BitmapFactory;

public class RegistroActivity extends AppCompatActivity {

    // Controles
    private ImageView imgFoto;

    private Spinner spnPais;

    private TextInputEditText txtNombre;
    private TextInputEditText txtTelefono;
    private TextInputEditText txtNota;

    private MaterialButton btnCamara;
    private MaterialButton btnGaleria;
    private MaterialButton btnGuardar;
    private MaterialButton btnVerContactos;

    // Imagen seleccionada
    private Bitmap bitmapFoto;

    // Base de datos
    private ContactoDAO contactoDAO;

    // Id para edición
    private int idContacto = 0;

    // Lanzador para seleccionar imagen desde la galería
    private ActivityResultLauncher<PickVisualMediaRequest> launcherGaleria;

    // Lanzador para tomar fotografía
    private ActivityResultLauncher<Void> launcherCamaraPreview;

    private static final int PERMISO_CAMARA = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Inicializar controles

        imgFoto = findViewById(R.id.imgFoto);

        spnPais = findViewById(R.id.spnPais);

        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtNota = findViewById(R.id.txtNota);

        btnCamara = findViewById(R.id.btnCamara);
        btnGaleria = findViewById(R.id.btnGaleria);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVerContactos = findViewById(R.id.btnVerContactos);

        //==========================
// Inicializar DAO
//==========================

        contactoDAO = new ContactoDAO(this);

//==========================
// Cargar Spinner
//==========================

        cargarPaises();

//==========================
// Verificar si viene en modo edición
//==========================

        idContacto = getIntent().getIntExtra("ID_CONTACTO", 0);

        if (idContacto > 0) {

            btnGuardar.setText("Actualizar Contacto");

            cargarContacto();

        }
        launcherGaleria =
                registerForActivityResult(
                        new ActivityResultContracts.PickVisualMedia(),
                        uri -> {

                            if (uri != null) {

                                try {

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

                                        ImageDecoder.Source source =
                                                ImageDecoder.createSource(
                                                        getContentResolver(),
                                                        uri
                                                );

                                        bitmapFoto =
                                                ImageDecoder.decodeBitmap(source);

                                    } else {

                                        bitmapFoto =
                                                MediaStore.Images.Media.getBitmap(
                                                        getContentResolver(),
                                                        uri
                                                );

                                    }

                                    imgFoto.setImageBitmap(bitmapFoto);

                                } catch (IOException e) {

                                    e.printStackTrace();

                                    Toast.makeText(
                                            this,
                                            "Error al cargar la imagen",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                }

                            }

                        });

        launcherCamaraPreview =
                registerForActivityResult(
                        new ActivityResultContracts.TakePicturePreview(),
                        bitmap -> {

                            if (bitmap != null) {

                                bitmapFoto = bitmap;

                                imgFoto.setImageBitmap(bitmapFoto);

                            }

                        });

        btnCamara.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {

                launcherCamaraPreview.launch(null);

            } else {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.CAMERA},
                        PERMISO_CAMARA);

            }

        });

        btnGaleria.setOnClickListener(v -> {

            launcherGaleria.launch(

                    new PickVisualMediaRequest.Builder()

                            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)

                            .build()

            );

        });

        btnGuardar.setOnClickListener(v -> {

            guardarContacto();

        });

        btnVerContactos.setOnClickListener(v -> {

            Intent intent = new Intent(
                    RegistroActivity.this,
                    ListaContactosActivity.class
            );

            startActivity(intent);

        });

    }

    private void cargarPaises() {

        ArrayList<String> paises = new ArrayList<>();

        paises.add("+504 Honduras");
        paises.add("+502 Guatemala");
        paises.add("+503 El Salvador");
        paises.add("+505 Nicaragua");
        paises.add("+506 Costa Rica");
        paises.add("+507 Panamá");
        paises.add("+52 México");
        paises.add("+1 Estados Unidos");
        paises.add("+57 Colombia");
        paises.add("+34 España");

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        paises
                );

        spnPais.setAdapter(adapter);

    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {

        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults);

        if (requestCode == PERMISO_CAMARA) {

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                launcherCamaraPreview.launch(null);

            }

        }

    }

    private String bitmapToBase64(Bitmap bitmap) {

        if (bitmap == null) {
            return "";
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(
                Bitmap.CompressFormat.JPEG,
                80,
                baos
        );

        byte[] imageBytes = baos.toByteArray();

        return Base64.encodeToString(
                imageBytes,
                Base64.NO_WRAP
        );
    }

    private void guardarContacto() {

        // Validar fotografía
        if (bitmapFoto == null) {

            Toast.makeText(
                    this,
                    "Seleccione una fotografía.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // Obtener datos del formulario

        String pais =
                spnPais.getSelectedItem().toString().trim();

        String nombre =
                txtNombre.getText().toString().trim();

        String telefono =
                txtTelefono.getText().toString().trim();

        String nota =
                txtNota.getText().toString().trim();

        // Validaciones

        if (nombre.isEmpty()) {

            txtNombre.setError("Ingrese el nombre");

            txtNombre.requestFocus();

            return;
        }

        if (telefono.isEmpty()) {

            txtTelefono.setError("Ingrese el teléfono");

            txtTelefono.requestFocus();

            return;
        }

        if (nota.isEmpty()) {

            txtNota.setError("Ingrese una nota");

            txtNota.requestFocus();

            return;
        }

        // Fecha actual

        String fechaActual =
                new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss",
                        Locale.getDefault()
                ).format(new Date());

        // Crear objeto

        Contacto contacto = new Contacto();

        contacto.setPais(pais);
        contacto.setNombre(nombre);
        contacto.setTelefono(telefono);
        contacto.setNota(nota);

        contacto.setFotoBase64(
                bitmapToBase64(bitmapFoto)
        );

        contacto.setFechaCreacion(fechaActual);
        contacto.setFechaModificacion(fechaActual);

        // Si estamos editando, conservar el ID del contacto
        if (idContacto > 0) {

            contacto.setId(idContacto);

        }
// Guardar o actualizar

        long resultado;

        if (idContacto == 0) {

            resultado = contactoDAO.insertarContacto(contacto);

        } else {

            resultado = contactoDAO.actualizarContacto(contacto);

        }

        if (resultado > 0) {

            if (idContacto == 0) {

                Toast.makeText(
                        this,
                        "Contacto guardado correctamente",
                        Toast.LENGTH_SHORT
                ).show();

                limpiarFormulario();

            } else {

                Toast.makeText(
                        this,
                        "Contacto actualizado correctamente",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            }

        } else {

            Toast.makeText(
                    this,
                    "No fue posible guardar el contacto",
                    Toast.LENGTH_SHORT
            ).show();

        }

    }

    private void cargarContacto() {

        Contacto contacto = contactoDAO.obtenerContactoPorId(idContacto);

        if (contacto == null) {
            return;
        }

        txtNombre.setText(contacto.getNombre());

        txtTelefono.setText(contacto.getTelefono());

        txtNota.setText(contacto.getNota());

        // Seleccionar el país
        for (int i = 0; i < spnPais.getCount(); i++) {

            if (spnPais.getItemAtPosition(i)
                    .toString()
                    .equals(contacto.getPais())) {

                spnPais.setSelection(i);

                break;

            }

        }

        // ==========================
        // Cargar fotografía
        // ==========================

        if (contacto.getFotoBase64() != null &&
                !contacto.getFotoBase64().isEmpty()) {

            byte[] bytes = Base64.decode(
                    contacto.getFotoBase64(),
                    Base64.DEFAULT
            );

            bitmapFoto = BitmapFactory.decodeByteArray(
                    bytes,
                    0,
                    bytes.length
            );

            imgFoto.setImageBitmap(bitmapFoto);

        }

    }
    private void limpiarFormulario() {

        imgFoto.setImageResource(android.R.drawable.ic_menu_camera);

        bitmapFoto = null;

        spnPais.setSelection(0);

        txtNombre.setText("");

        txtTelefono.setText("");

        txtNota.setText("");

        txtNombre.requestFocus();

    }

}