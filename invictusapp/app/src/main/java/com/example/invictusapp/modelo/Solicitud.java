package com.example.invictusapp.modelo;

public class Solicitud {

    private String id;
    private String asunto;
    private String descripcion;
    private String archivo;
    private String usuario_id;
    private String tipo_id;
    private String estado_id;

    public Solicitud() {
    }

    public Solicitud(String id, String asunto, String descripcion, String archivo, String usuario_id, String tipo_id, String estado_id) {
        this.id = id;
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.archivo = archivo;
        this.usuario_id = usuario_id;
        this.tipo_id = tipo_id;
        this.estado_id= estado_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getTipo_id() {
        return tipo_id;
    }

    public void setTipo_id(String tipo_id) {
        this.tipo_id = tipo_id;
    }

    public String getEstado_id() {
        return estado_id;
    }

    public void setEstado_id(String estado_id) {
        this.estado_id = estado_id;
    }
}
