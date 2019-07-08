package com.example.jean.proyectoandroid.Modulo;

public class HorarioTarea {

    private int idHorarioTarea;
    private int idHorario;
    private String idTarea;

    public HorarioTarea() {
    }

    public HorarioTarea(int idHorarioTarea, int idHorario, String idTarea) {
        this.idHorarioTarea = idHorarioTarea;
        this.idHorario = idHorario;
        this.idTarea = idTarea;
    }



    public int getIdHorarioTarea() {
        return idHorarioTarea;
    }

    public void setIdHorarioTarea(int idHorarioTarea) {
        this.idHorarioTarea = idHorarioTarea;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public String getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(String idTarea) {
        this.idTarea = idTarea;
    }
}
