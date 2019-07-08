package com.example.jean.proyectoandroid.Modulo;

import android.security.keystore.UserPresenceUnavailableException;
import android.util.EventLog;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;

public class Controller {

    public ArrayList<Usuario> obtenerUsuarios(String contenido){

        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        try {

            JSONArray json = new JSONArray(contenido);

            for (int i = 0; i < json.length(); i++) {
                JSONArray jsonData = json.getJSONArray(i);
                Usuario usuario1 = new Usuario();
                usuario1.setIdUsuario(Integer.parseInt(jsonData.get(0).toString()));
                usuario1.setNombre(jsonData.get(1).toString());
                usuario1.setCorreo(jsonData.get(2).toString());
                usuario1.setContrasena(jsonData.get(3).toString());
                usuario1.setFoto(jsonData.get(4).toString());
                usuarios.add(usuario1);
            }


        } catch (JSONException e) {
            Log.e("ERROR", e.getMessage() + " == " + e.getCause());
        }

        return usuarios;
    }

    public Usuario obtenerUsuario(String  contenido , int idUsuario){

        Usuario usuario = new Usuario();

        for (Usuario usu : obtenerUsuarios(contenido)) {
            if(idUsuario == usu.getIdUsuario()){
                usu.setContrasena("");
                return usu;
            }else{
                usuario.setCorreo("noExiste");
            }
        }
        return usuario;

    }

    public boolean iniciarSesion(Usuario usuario , String correo, String contrasena){

        if(usuario.getCorreo().equals(correo) && usuario.getContrasena().equals(contrasena)){
            return true;
        }else{
            return false;
        }

    }

    public String registrarUsuario(Usuario usuario){
        String parametros = "";

        parametros = "nombre="+usuario.getNombre() + "&correo=" + usuario.getCorreo() + "&contrasena=" + usuario.getContrasena() + "&foto="+usuario.getFoto();

        return parametros;

    }


    public String registrarTarea(Tarea tarea){
        String parametros = "";

        //INSERT INTO `ordanizadordb`.`tarea`
        // (`nombre`, `descripcion`, `hora_inicio`, `hora_termino`, `tipo_tarea`, `estado_tarea`, `monitor`, `sub_tipo`, `seccion`, `sala`)
        // VALUES ('nombre', 'des', '12:32', '12:53', 'horario', 'iniciado', 'saraa', 'hora', 'fds', '32');

        parametros = "id_tarea=" + tarea.getIdTarea() +  "&nombre="+tarea.getNombre() + "&descripcion=" + tarea.getDescripcion() + "&hora_inicio=" + tarea.getHoraInicio() + "&hora_termino="+tarea.getHoraFin()
                + "&estado_tarea=" +tarea.getEstado() + "&monitor=" + tarea.getMonitor() + "&sub_tipo=" + tarea.getSubTipo() + "&seccion=" + tarea.getSeccion() + "&sala="+ tarea.getSala() + "&tipo_tarea=" + tarea.getTipo();


        return parametros;
    }


    public String registrarTareaHorario(int idHorario , String idTarea){
        String parametros = "";

        //INSERT INTO `ordanizadordb`.`tareas_horario` (`id_horario`, `id_tarea`) VALUES ('23', '32');

        parametros = "id_horario=" + idHorario + "&id_tarea=" + idTarea;

        return parametros;

    }

    public String registrarHorario(Horario horario){
        String parametros = "";

        return parametros = "dia=" + horario.getDia() + "&idUsuario=" + horario.getIdUsuario();
    }



    public String registrarEvento(Evento evento){
        String parametros = "";

        //INSERT INTO `ordanizadordb`.`eventos` (`id_eventos`, `nombre`, `descripcion`, `fecha_evento`, `usuario_id_usuario`, `tipoEvento`) VALUES ('231', 'df', 'sdf', 'sdf', 'sd', 'sdf');


        return parametros = "nombre=" + evento.getNombre() + "&descripcion=" + evento.getDescripcion() + "&fecha_evento=" + evento.getFecha() + "&usuario_id_usuario=" +
                evento.getIdUsuario() + "&tipoEvento=" + evento.getTipoEvento();
    }


    public String registrarProyecto(Proyecto proyecto){
        String parametros = "";

        //INSERT INTO `ordanizadordb`.`proyecto` (`nombre`, `descripcion`, `foto`, `fecha_inicio`, `fecha_termino`, `usuario_id_usuario`) VALUES ('fg', 'fgd', 'fdg', 'g', 'fg', 'fdg');

        return parametros = "nombre=" + proyecto.getNombre() + "&descripcion=" + proyecto.getDescripcion() + "&foto=" + proyecto.getFoto() +
                "&fecha_inicio=" + proyecto.getFechaInicio() + "&fecha_termino=" + proyecto.getFechaTermino() + "&usuario_id_usuario=" + proyecto.getIdUsuario();
    }

    public String registrarListaTarea(ListaTarea listaTarea){
        String parametros = "";

        //INSERT INTO `ordanizadordb`.`listas` (`descripcion`, `estado`, `id_tarea`) VALUES ('s', 'd', 'f');

        return parametros = "descripcion=" + listaTarea.getDescripcion() + "&estado=" + listaTarea.getEstado() + "&id_tarea=" + listaTarea.getIdTarea();

    }


    public String registrarTareaEvento(TareasEvento tareasEvento){
        String parametros = "";

        //INSERT INTO `ordanizadordb`.`tarea_evento` (`eventos_id_eventos`, `id_tarea`) VALUES ('4', '4sdf');

        return parametros = "eventos_id_eventos=" + tareasEvento.getIdEvento() + "&id_tarea=" + tareasEvento.getIdTarea();

    }

    public String registrarTareaProyecto(TareasProyecto tareasProyecto){
        String parametros = "";

        //INSERT INTO `ordanizadordb`.`tarea_proyecto` (`id_proyecto`, `id_tarea`) VALUES ('3', '3');

        return parametros = "id_proyecto=" + tareasProyecto.getIdProyecto() + "&id_tarea=" + tareasProyecto.getIdTarea();

    }



    public ArrayList<Horario> crearHorariosSemanal(int id){

        ArrayList<Horario> horarios = new ArrayList<Horario>();

        Horario horario1 = new Horario();
        horario1.setDia("lunes");
        horario1.setIdUsuario(id);
        horarios.add(horario1);

        Horario horario2 = new Horario();
        horario2.setDia("martes");
        horario2.setIdUsuario(id);
        horarios.add(horario2);

        Horario horario3 = new Horario();
        horario3.setDia("miercoles");
        horario3.setIdUsuario(id);
        horarios.add(horario3);

        Horario horario4 = new Horario();
        horario4.setDia("jueves");
        horario4.setIdUsuario(id);
        horarios.add(horario4);

        Horario horario5 = new Horario();
        horario5.setDia("viernes");
        horario5.setIdUsuario(id);
        horarios.add(horario5);

        Horario horario6 = new Horario();
        horario6.setDia("sabado");
        horario6.setIdUsuario(id);
        horarios.add(horario6);

        Horario horario7 = new Horario();
        horario7.setDia("domingo");
        horario7.setIdUsuario(id);
        horarios.add(horario7);

        return horarios;

    }




}
