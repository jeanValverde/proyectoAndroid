package com.example.jean.proyectoandroid.Modulo;

public class Usuario {

    private int idUsuario;
    private String nombre;
    private String correo;
    private String contrasena;


    public Usuario(){

    }

    public Usuario(int idUsuario, String nombre, String correo, String foto) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.foto = foto;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    private String foto;




}
