package com.example.contactosteam4409.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Base de datos aqui para conectar
    public static final String DATABASE_NAME = "contactos.db";
    public static final int DATABASE_VERSION = 2;

    // Tabla
    public static final String TABLA_CONTACTOS = "contactos";

    // Campos
    public static final String COL_ID = "id";
    public static final String COL_PAIS = "pais";
    public static final String COL_NOMBRE = "nombre";
    public static final String COL_TELEFONO = "telefono";
    public static final String COL_NOTA = "nota";
    public static final String COL_FOTO = "foto";
    public static final String COL_FECHA_CREACION = "fecha_creacion";
    public static final String COL_FECHA_MODIFICACION = "fecha_modificacion";

    public DatabaseHelper(Context context) {

        super(
                context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql =

                "CREATE TABLE " + TABLA_CONTACTOS + " ("

                        + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"

                        + COL_PAIS + " TEXT NOT NULL,"

                        + COL_NOMBRE + " TEXT NOT NULL,"

                        + COL_TELEFONO + " TEXT NOT NULL,"

                        + COL_NOTA + " TEXT NOT NULL,"

                        + COL_FOTO + " TEXT NOT NULL,"

                        + COL_FECHA_CREACION + " TEXT NOT NULL,"

                        + COL_FECHA_MODIFICACION + " TEXT NOT NULL"

                        + ");";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion) {

        db.execSQL(
                "DROP TABLE IF EXISTS "
                        + TABLA_CONTACTOS
        );

        onCreate(db);

    }

}