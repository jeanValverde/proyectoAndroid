package com.example.jean.proyectoandroid.Modulo;

import java.util.Date;

public class Evento {


    private int id;
    private String nombre;
    private String descripcion;
    private String fecha;
    private String idUsuario;
    private String tipoEvento;

    public Evento() {
    }

    public Evento(int id, String nombre, String descripcion, String fecha, String idUsuario, String tipoEvento) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.idUsuario = idUsuario;
        this.tipoEvento = tipoEvento;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }
}

