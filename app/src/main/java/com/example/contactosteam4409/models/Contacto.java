package com.example.contactosteam4409.models;

public class Contacto {

    private int id;
    private String pais;
    private String nombre;
    private String telefono;
    private String nota;
    private String fotoBase64;
    private String fechaCreacion;
    private String fechaModificacion;

    public Contacto() {
    }

    public Contacto(int id,
                    String pais,
                    String nombre,
                    String telefono,
                    String nota,
                    String fotoBase64,
                    String fechaCreacion,
                    String fechaModificacion) {

        this.id = id;
        this.pais = pais;
        this.nombre = nombre;
        this.telefono = telefono;
        this.nota = nota;
        this.fotoBase64 = fotoBase64;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getFotoBase64() {
        return fotoBase64;
    }

    public void setFotoBase64(String fotoBase64) {
        this.fotoBase64 = fotoBase64;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}