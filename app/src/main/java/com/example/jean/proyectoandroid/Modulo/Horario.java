package com.example.jean.proyectoandroid.Modulo;

import java.util.ArrayList;

public class Horario {

    private int idHorario;

    private int idUsuario;

    private String dia;

    private ArrayList<Tarea> tareas;


    public Horario() {
    }


    public Horario(int idHorario, int idUsuario, String dia, ArrayList<Tarea> tareas) {
        this.idHorario = idHorario;
        this.idUsuario = idUsuario;
        this.dia = dia;
        this.tareas = tareas;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public ArrayList<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(ArrayList<Tarea> tareas) {
        this.tareas = tareas;
    }


}
