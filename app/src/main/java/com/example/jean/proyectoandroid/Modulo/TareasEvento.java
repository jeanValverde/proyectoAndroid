package com.example.jean.proyectoandroid.Modulo;

public class TareasEvento {

    private int idTareaEvento;
    private int idEvento;
    private String idTarea;


    public TareasEvento() {
    }

    public TareasEvento(int idTareaEvento, int idEvento, String idTarea) {
        this.idTareaEvento = idTareaEvento;
        this.idEvento = idEvento;
        this.idTarea = idTarea;
    }


    public int getIdTareaEvento() {
        return idTareaEvento;
    }

    public void setIdTareaEvento(int idTareaEvento) {
        this.idTareaEvento = idTareaEvento;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(String idTarea) {
        this.idTarea = idTarea;
    }
}
