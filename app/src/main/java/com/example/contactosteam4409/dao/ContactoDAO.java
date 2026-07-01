package com.example.contactosteam4409.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.contactosteam4409.models.Contacto;

import android.content.Context;

import com.example.contactosteam4409.database.DatabaseHelper;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class ContactoDAO {

    private final DatabaseHelper databaseHelper;

    public ContactoDAO(Context context) {

        databaseHelper = new DatabaseHelper(context);

    }

    public long insertarContacto(Contacto contacto) {

        SQLiteDatabase db =
                databaseHelper.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                DatabaseHelper.COL_PAIS,
                contacto.getPais()
        );

        values.put(
                DatabaseHelper.COL_NOMBRE,
                contacto.getNombre()
        );

        values.put(
                DatabaseHelper.COL_TELEFONO,
                contacto.getTelefono()
        );

        values.put(
                DatabaseHelper.COL_NOTA,
                contacto.getNota()
        );

        values.put(
                DatabaseHelper.COL_FOTO,
                contacto.getFotoBase64()
        );

        values.put(
                DatabaseHelper.COL_FECHA_CREACION,
                contacto.getFechaCreacion()
        );

        values.put(
                DatabaseHelper.COL_FECHA_MODIFICACION,
                contacto.getFechaModificacion()
        );

        long resultado =
                db.insert(
                        DatabaseHelper.TABLA_CONTACTOS,
                        null,
                        values
                );

        db.close();

        return resultado;
    }

    public List<Contacto> obtenerContactos() {

        List<Contacto> lista =
                new ArrayList<>();

        SQLiteDatabase db =
                databaseHelper.getReadableDatabase();

        Cursor cursor =
                db.query(
                        DatabaseHelper.TABLA_CONTACTOS,
                        null,
                        null,
                        null,
                        null,
                        null,
                        DatabaseHelper.COL_NOMBRE + " ASC"
                );

        if (cursor.moveToFirst()) {

            do {

                Contacto contacto =
                        new Contacto();

                contacto.setId(
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                        DatabaseHelper.COL_ID
                                )
                        )
                );

                contacto.setPais(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        DatabaseHelper.COL_PAIS
                                )
                        )
                );

                contacto.setNombre(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        DatabaseHelper.COL_NOMBRE
                                )
                        )
                );

                contacto.setTelefono(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        DatabaseHelper.COL_TELEFONO
                                )
                        )
                );

                contacto.setNota(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        DatabaseHelper.COL_NOTA
                                )
                        )
                );

                contacto.setFotoBase64(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        DatabaseHelper.COL_FOTO
                                )
                        )
                );

                contacto.setFechaCreacion(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        DatabaseHelper.COL_FECHA_CREACION
                                )
                        )
                );

                contacto.setFechaModificacion(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        DatabaseHelper.COL_FECHA_MODIFICACION
                                )
                        )
                );

                lista.add(contacto);

            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();

        return lista;
    }

    public Contacto obtenerContactoPorId(int id) {

        SQLiteDatabase db =
                databaseHelper.getReadableDatabase();

        Cursor cursor =
                db.query(
                        DatabaseHelper.TABLA_CONTACTOS,
                        null,
                        DatabaseHelper.COL_ID + "=?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        null
                );

        Contacto contacto = null;

        if (cursor.moveToFirst()) {

            contacto = new Contacto();

            contacto.setId(
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COL_ID
                            )
                    )
            );

            contacto.setPais(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COL_PAIS
                            )
                    )
            );

            contacto.setNombre(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COL_NOMBRE
                            )
                    )
            );

            contacto.setTelefono(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COL_TELEFONO
                            )
                    )
            );

            contacto.setNota(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COL_NOTA
                            )
                    )
            );

            contacto.setFotoBase64(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COL_FOTO
                            )
                    )
            );

            contacto.setFechaCreacion(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COL_FECHA_CREACION
                            )
                    )
            );

            contacto.setFechaModificacion(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COL_FECHA_MODIFICACION
                            )
                    )
            );

        }

        cursor.close();
        db.close();

        return contacto;
    }

    public int actualizarContacto(Contacto contacto) {

        SQLiteDatabase db =
                databaseHelper.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                DatabaseHelper.COL_PAIS,
                contacto.getPais()
        );

        values.put(
                DatabaseHelper.COL_NOMBRE,
                contacto.getNombre()
        );

        values.put(
                DatabaseHelper.COL_TELEFONO,
                contacto.getTelefono()
        );

        values.put(
                DatabaseHelper.COL_NOTA,
                contacto.getNota()
        );

        values.put(
                DatabaseHelper.COL_FOTO,
                contacto.getFotoBase64()
        );

        // Mantener la fecha original de creación
        values.put(
                DatabaseHelper.COL_FECHA_CREACION,
                contacto.getFechaCreacion()
        );

        // Actualizar únicamente la fecha de modificación
        values.put(
                DatabaseHelper.COL_FECHA_MODIFICACION,
                contacto.getFechaModificacion()
        );

        int resultado =
                db.update(
                        DatabaseHelper.TABLA_CONTACTOS,
                        values,
                        DatabaseHelper.COL_ID + "=?",
                        new String[]{
                                String.valueOf(contacto.getId())
                        }
                );

        db.close();

        return resultado;
    }

    public int eliminarContacto(int id) {

        SQLiteDatabase db =
                databaseHelper.getWritableDatabase();

        int resultado =
                db.delete(
                        DatabaseHelper.TABLA_CONTACTOS,
                        DatabaseHelper.COL_ID + "=?",
                        new String[]{
                                String.valueOf(id)
                        }
                );

        db.close();

        return resultado;
    }

}