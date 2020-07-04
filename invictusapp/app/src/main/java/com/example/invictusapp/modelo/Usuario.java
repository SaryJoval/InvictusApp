package com.example.invictusapp.modelo;

import android.widget.ScrollView;

public class Usuario {

    private String id;
    private String rut;
    private String nombre;
    private String password;
    private int telefono;
    private Boolean estado;
    private String banco;
    private String cuenta;
    private String mail;

    public Usuario() {
    }

    public Usuario(String id, String rut, String nombre, int telefono, Boolean estado, String banco, String cuenta, String mail) {
        this.id = id;
        this.rut = rut;
        this.nombre = nombre;
        this.telefono = telefono;
        this.estado = estado;
        this.banco = banco;
        this.cuenta = cuenta;
        this.mail = mail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    /*public int getPerfil_id() {
        return perfil_id;
    }

    public void setPerfil_id(int perfil_id) {
        this.perfil_id = perfil_id;
    }*/

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
