package com.example.jean.proyectoandroid.Modulo;

public class ListaTarea {


    private int idLita;
    private String descripcion;
    private String estado;
    private String idTarea;

    public ListaTarea() {
    }

    public ListaTarea(int idLita, String descripcion, String estado, String idTarea) {
        this.idLita = idLita;
        this.descripcion = descripcion;
        this.estado = estado;
        this.idTarea = idTarea;
    }

    public int getIdLita() {
        return idLita;
    }

    public void setIdLita(int idLita) {
        this.idLita = idLita;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(String idTarea) {
        this.idTarea = idTarea;
    }
}
