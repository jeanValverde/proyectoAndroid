package com.example.jean.proyectoandroid.Modulo;

public class TareasProyecto {

    private int idTareaProyecto;
    private int idProyecto;
    private String idTarea;

    public TareasProyecto() {
    }

    public TareasProyecto(int idTareaProyecto, int idProyecto, String idTarea) {
        this.idTareaProyecto = idTareaProyecto;
        this.idProyecto = idProyecto;
        this.idTarea = idTarea;
    }

    public int getIdTareaProyecto() {
        return idTareaProyecto;
    }

    public void setIdTareaProyecto(int idTareaProyecto) {
        this.idTareaProyecto = idTareaProyecto;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(String idTarea) {
        this.idTarea = idTarea;
    }
}
