package com.example.jean.proyectoandroid.Modulo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Tarea {

    private String idTarea;
    private String estado;
    private String tipo;
    private String subTipo;
    private String horaInicio ;
    private String  horaFin;
    private String nombre;
    private String descripcion;
    private String monitor;
    private String sala;
    private String seccion;

    public Tarea() {
    }


    public Tarea(String idTarea, String estado, String tipo, String subTipo, String horaInicio, String horaFin, String nombre, String descripcion, String monitor, String sala, String seccion) {
        this.idTarea = idTarea;
        this.estado = estado;
        this.tipo = tipo;
        this.subTipo = subTipo;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.monitor = monitor;
        this.sala = sala;
        this.seccion = seccion;
    }

    public String getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(String idTarea) {
        this.idTarea = idTarea;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSubTipo() {
        return subTipo;
    }

    public void setSubTipo(String subTipo) {
        this.subTipo = subTipo;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
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

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }
}
