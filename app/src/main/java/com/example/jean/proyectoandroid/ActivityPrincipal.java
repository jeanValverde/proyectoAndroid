package com.example.jean.proyectoandroid;

/* Main para mostrar las tareas pendientes tanto eventos - proyectos - horarios */

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DrawableUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.jean.proyectoandroid.Modulo.Evento;
import com.example.jean.proyectoandroid.Modulo.Horario;
import com.example.jean.proyectoandroid.Modulo.HorarioTarea;
import com.example.jean.proyectoandroid.Modulo.Proyecto;
import com.example.jean.proyectoandroid.Modulo.Tarea;
import com.example.jean.proyectoandroid.Modulo.TareasEvento;
import com.example.jean.proyectoandroid.Modulo.TareasProyecto;
import com.example.jean.proyectoandroid.SwipeList.OnSwipeListItemClickListener;
import com.example.jean.proyectoandroid.SwipeList.SwipeListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class ActivityPrincipal extends AppCompatActivity {



    TextView diaTxt;
    String dia = "";

    private SwipeListView listView;
    private ActivityPrincipal.ListAdapter listAdapter;
    private ArrayList<ActivityPrincipal.Info> listData = new ArrayList<ActivityPrincipal.Info>();

    String idUsuario = "";
    //todas las tareas
    ArrayList<Tarea> tareas = new ArrayList<Tarea>();
    //horarios
    ArrayList<Horario> horarios = new ArrayList<Horario>();
    ArrayList<HorarioTarea> horarioTareas = new ArrayList<HorarioTarea>();

    //proyectos
    ArrayList<Proyecto> proyectos = new ArrayList<Proyecto>();
    ArrayList<TareasProyecto> tareasProyectos = new ArrayList<TareasProyecto>();

    //eventos
    ArrayList<Evento> eventos = new ArrayList<Evento>();
    ArrayList<TareasEvento> tareasEventos = new ArrayList<TareasEvento>();

    //tareas Dia y usuario
    ArrayList<Tarea> tareasUsuario = new ArrayList<Tarea>();

    // android.support.design.widget.BottomNavigationView bottomNavigationView = (android.support.design.widget.BottomNavigationView) findViewById(R.id.bottomNavigationView);

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Intent recibir = getIntent();
        idUsuario = recibir.getStringExtra("idUsuario");

        Calendar now = Calendar.getInstance();

        // Array con los dias de la semana
        String[] strDays = new String[]{
                "Domingo",
                "Lunes",
                "Martes",
                "Miercoles",
                "Jueves",
                "Viernes",
                "Sabado"};

        // El dia de la semana inicia en el 1 mientras que el array empieza en el 0
        dia =  strDays[now.get(Calendar.DAY_OF_WEEK) - 1];

        diaTxt = (TextView) findViewById(R.id.dia);

        diaTxt.setText(dia);


        //inicio de swipe list
        listView = (SwipeListView) findViewById(R.id.listView);

        ArrayList<ActivityPrincipal.Info> informacion = new ArrayList<>();

        GetDataLocal getDataLocal = new GetDataLocal();
        //


        getDataLocal.tipoGetData = "horario";
        getDataLocal.hoy = dia;

        getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getHorario.php"});



        listView.setListener(new OnSwipeListItemClickListener() {

            @Override
            public void OnClick(View view, int index) {

                ActivityPrincipal.Info tareaInfo = (ActivityPrincipal.Info) listData.get(index); //se obtiene el objeto

                //se envia el id de la tarea y el id Usuario en un intent

               /**
                *
                *
                *  Intent intent = new Intent(ActivityHorario.this, activityHorarioInto.class);
                *                 intent.putExtra("idUsuario", idUsuario);
                *                 intent.putExtra("idTarea", tareaInfo.tarea.getIdTarea());
                *                 startActivity(intent);
                *
                *
                * */

            }

            @Override
            public boolean OnLongClick(View view, int index) {
                AlertDialog.Builder ab = new AlertDialog.Builder(ActivityPrincipal.this);
                ab.setTitle("LongClick");
                ab.setMessage("long click item "+index);
                ab.create().show();
                return false;
            }

            @Override
            public void OnControlClick(int rid, View view, int index) {
                AlertDialog.Builder ab;
                switch (rid){
                    case R.id.modify:
                        ab = new AlertDialog.Builder(ActivityPrincipal.this);
                        ab.setTitle("Modify");
                        ab.setMessage("You will modify item "+index);
                        ab.create().show();
                        break;
                    case R.id.delete:
                        ab = new AlertDialog.Builder(ActivityPrincipal.this);
                        ab.setTitle("Delete");
                        ab.setMessage("You will delete item "+index);
                        ab.create().show();
                        break;
                }
            }

        },new int[]{R.id.modify,R.id.delete});




        //fin swipe list





        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
// ...
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.recienteMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityPrincipal.this, ActivityPrincipal.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }else  if (item.getItemId() == R.id.horarioMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityPrincipal.this, ActivityHorario.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }else  if (item.getItemId() == R.id.eventoMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityPrincipal.this, ActivityEvento.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }else if (item.getItemId() == R.id.proyectoMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityPrincipal.this, ActivityProyecto.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }else  if (item.getItemId() == R.id.perfilMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityPrincipal.this, ActivityPerfil.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });


    }






    //swipe list

    @Override
    protected void onResume() {
        super.onResume();
    }

    class Info{
        public Tarea tarea = new Tarea();
    }

    class ViewHolder{
        public TextView name;
        public TextView desc;
        public TextView horaInicio;
        public TextView horaFin;
        public Button modify;
        public Button delete;
    }


    class ListAdapter extends com.example.jean.proyectoandroid.SwipeList.SwipeListAdapter {
        private ArrayList<ActivityPrincipal.Info> listData;
        public ListAdapter(ArrayList<ActivityPrincipal.Info> listData){
            this.listData= (ArrayList<ActivityPrincipal.Info>) listData.clone();
        }
        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ActivityPrincipal.ViewHolder viewHolder = new ActivityPrincipal.ViewHolder();
            if(convertView == null){
                convertView = View.inflate(getBaseContext(),R.layout.layout_horario,null);
                viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                viewHolder.desc = (TextView) convertView.findViewById(R.id.desc);
                viewHolder.modify = (Button) convertView.findViewById(R.id.modify);
                viewHolder.delete = (Button) convertView.findViewById(R.id.delete);

                viewHolder.horaFin = (TextView) convertView.findViewById(R.id.horaFin);
                viewHolder.horaInicio = (TextView) convertView.findViewById(R.id.horaInicio);

                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ActivityPrincipal.ViewHolder) convertView.getTag();
            }
            viewHolder.horaInicio.setText(listData.get(position).tarea.getHoraInicio());
            viewHolder.horaFin.setText(listData.get(position).tarea.getHoraFin());
            viewHolder.name.setText(listData.get(position).tarea.getNombre());
            viewHolder.desc.setText(listData.get(position).tarea.getDescripcion());
            return super.bindView(position, convertView);
        }
    }



    //fin swipe list







    private class GetDataLocal extends AsyncTask<String, Void, Boolean> {

        // ProgressDialog dialog = new ProgressDialog(ActivityIniciarSesion.this);
        public String contenido = "";

        public String tipoGetData;

        public String hoy;


        protected void onPostExecute(Boolean result) {

            if (result) {
                JSONArray json = null;
                try {
                    if(tipoGetData.equalsIgnoreCase("tarea")){

                        json = new JSONArray(contenido);
                        //ArrayList<Tarea> tareas = new ArrayList<Tarea>();
                        for (int i = 0; i < json.length(); i++) {
                            JSONArray jsonData = json.getJSONArray(i);
                            Tarea tarea = new Tarea();
                            tarea.setIdTarea(jsonData.get(0).toString());
                            tarea.setNombre(jsonData.get(1).toString());
                            tarea.setDescripcion(jsonData.get(2).toString());
                            tarea.setHoraInicio(jsonData.get(3).toString());
                            tarea.setHoraFin(jsonData.get(4).toString());
                            tarea.setTipo(jsonData.get(5).toString());
                            tarea.setEstado(jsonData.get(6).toString());
                            tarea.setMonitor(jsonData.get(7).toString());
                            tarea.setSubTipo(jsonData.get(8).toString());
                            tarea.setSeccion(jsonData.get(9).toString());
                            tarea.setSala(jsonData.get(10).toString());
                            tareas.add(tarea);
                        }


                        Horario horariosP = new Horario();
                        for (Horario horario : horarios ){
                            if(horario.getIdUsuario() == Integer.parseInt(idUsuario)){
                                if(horario.getDia().equalsIgnoreCase(hoy.toLowerCase())){
                                    horariosP.setIdHorario(horario.getIdHorario());
                                    horariosP.setDia(horario.getDia());
                                    horariosP.setIdUsuario(horario.getIdUsuario());
                                    break;
                                }
                            }
                        }

                        ArrayList<HorarioTarea> horarioTareasP = new ArrayList<HorarioTarea>();

                        for (HorarioTarea horarioTarea : horarioTareas) {

                            if(horarioTarea.getIdHorario() == horariosP.getIdHorario()){
                                HorarioTarea horarioTarea1 = new HorarioTarea();
                                horarioTarea1.setIdHorarioTarea(horarioTarea.getIdHorarioTarea());
                                horarioTarea1.setIdHorario(horarioTarea.getIdHorario());
                                horarioTarea1.setIdTarea(horarioTarea.getIdTarea());
                                horarioTareasP.add(horarioTarea1);
                            }

                        }

                        //tareasUsuario = new ArrayList<Tarea>();

                        for (HorarioTarea horarioTarea : horarioTareasP) {
                            for (Tarea tarea : tareas) {
                                if(tarea.getIdTarea().equals(horarioTarea.getIdTarea())){
                                    Tarea tarea1 = new Tarea();
                                    tarea1 = tarea;
                                    tareasUsuario.add(tarea1);
                                }
                            }
                        }

                        //eventos

                        ArrayList<Evento> eventosUsu = new ArrayList<Evento>();

                        for (Evento evento : eventos) {
                            if(Integer.parseInt(evento.getIdUsuario()) == Integer.parseInt(idUsuario)){
                                    eventosUsu.add(evento);
                            }
                        }

                        for (Evento evento : eventosUsu) {
                            for (TareasEvento tareasEvento : tareasEventos) {
                                if(evento.getId() == tareasEvento.getIdEvento()){
                                    for (Tarea tarea : tareas) {
                                        if(tarea.getIdTarea().equals(tareasEvento.getIdTarea())){
                                            tareasUsuario.add(tarea);
                                        }
                                    }
                                }
                            }
                        }

                        //proyectos


                        ArrayList<Proyecto> proyectosUsu = new ArrayList<Proyecto>();

                        for (Proyecto proyecto : proyectos) {
                            if(proyecto.getIdProyecto() == Integer.parseInt(idUsuario)){
                                proyectosUsu.add(proyecto);
                            }
                        }

                        for (Proyecto proyecto : proyectosUsu) {
                            for (TareasProyecto tareasProyecto : tareasProyectos) {
                                if(proyecto.getIdProyecto() == tareasProyecto.getIdProyecto()){
                                    for (Tarea tarea : tareas) {
                                        if(tarea.getIdTarea().equals(tareasProyecto.getIdTarea())){
                                            tareasUsuario.add(tarea);
                                        }
                                    }
                                }
                            }
                        }

                        rellenarDatos(tareasUsuario);


                    }else if (tipoGetData.equalsIgnoreCase("horario")){

                        //ArrayList<Horario> horarios = new ArrayList<Horario>();
                        json = new JSONArray(contenido);
                        for (int i = 0; i < json.length(); i++) {
                            JSONArray jsonData = json.getJSONArray(i);
                            Horario horario = new Horario();
                            horario.setIdHorario(Integer.parseInt(jsonData.get(0).toString()));
                            horario.setDia(jsonData.get(1).toString());
                            horario.setIdUsuario(Integer.parseInt(jsonData.get(2).toString()));
                            horarios.add(horario);
                        }


                        ejecutarEvento();

                    }else if (tipoGetData.equalsIgnoreCase("horarioTareas")){

                        //ArrayList<HorarioTarea> horarioTareas = new ArrayList<HorarioTarea>();
                        json = new JSONArray(contenido);
                        for (int i = 0; i < json.length(); i++) {
                            JSONArray jsonData = json.getJSONArray(i);
                            HorarioTarea horarioTarea = new HorarioTarea();
                            horarioTarea.setIdHorarioTarea(Integer.parseInt(jsonData.get(0).toString()));
                            horarioTarea.setIdHorario(Integer.parseInt(jsonData.get(1).toString()));
                            horarioTarea.setIdTarea(jsonData.get(2).toString());
                            horarioTareas.add(horarioTarea);
                        }

                        ejecutarEventoTarea();


                    }else if(tipoGetData.equalsIgnoreCase("eventoTareas")){

                       // ArrayList<TareasEvento> tareasEventosSe = new ArrayList<TareasEvento>();
                        json = new JSONArray(contenido);
                        for (int i = 0; i < json.length(); i++) {
                            JSONArray jsonData = json.getJSONArray(i);
                            TareasEvento tareasEvento = new TareasEvento();
                            tareasEvento.setIdTareaEvento(Integer.parseInt(jsonData.get(0).toString()));
                            tareasEvento.setIdEvento(Integer.parseInt(jsonData.get(1).toString()));
                            tareasEvento.setIdTarea(jsonData.get(2).toString());
                            tareasEventos.add(tareasEvento);
                        }

                        ejecutarProyectoTarea();




                    }else if(tipoGetData.equalsIgnoreCase("proyectoTareas")){

                        //ArrayList<TareasProyecto> tareasProyectos = new ArrayList<TareasProyecto>();

                        json = new JSONArray(contenido);
                        for (int i = 0; i < json.length(); i++) {
                            JSONArray jsonData = json.getJSONArray(i);
                            TareasProyecto tareasProyecto = new TareasProyecto();
                            tareasProyecto.setIdTareaProyecto(Integer.parseInt(jsonData.get(0).toString()));
                            tareasProyecto.setIdProyecto(Integer.parseInt(jsonData.get(1).toString()));
                            tareasProyecto.setIdTarea(jsonData.get(2).toString());
                            tareasProyectos.add(tareasProyecto);
                        }


                        ejecutarTarea();


                    }else if(tipoGetData.equalsIgnoreCase("proyectos")){

                        json = new JSONArray(contenido);
                        //ArrayList<Proyecto> proyectos = new ArrayList<Proyecto>();
                        for (int i = 0; i < json.length(); i++) {
                            JSONArray jsonData = json.getJSONArray(i);
                            Proyecto proyecto = new Proyecto();
                            proyecto.setIdProyecto(Integer.parseInt(jsonData.get(0).toString()));
                            proyecto.setNombre(jsonData.get(1).toString());
                            proyecto.setDescripcion(jsonData.get(2).toString());
                            proyecto.setFoto(jsonData.get(3).toString());
                            proyecto.setFechaInicio(jsonData.get(4).toString());
                            proyecto.setFechaTermino(jsonData.get(5).toString());
                            proyecto.setIdUsuario(Integer.parseInt(jsonData.get(6).toString()));
                            proyectos.add(proyecto);
                        }


                        ejecutarHorarioTarea();

                    }else if(tipoGetData.equalsIgnoreCase("eventos")){


                        json = new JSONArray(contenido);
                        //ArrayList<Evento> eventos = new ArrayList<Evento>();
                        for (int i = 0; i < json.length(); i++) {
                            JSONArray jsonData = json.getJSONArray(i);
                            Evento evento = new Evento();
                            evento.setId(Integer.parseInt(jsonData.get(0).toString()));
                            evento.setNombre(jsonData.get(1).toString());
                            evento.setDescripcion(jsonData.get(2).toString());
                            evento.setFecha(jsonData.get(3).toString());
                            evento.setIdUsuario(jsonData.get(4).toString());
                            evento.setTipoEvento(jsonData.get(5).toString());
                            eventos.add(evento);
                        }

                        ejecutarProyecto();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // dialog.dismiss();
        }



        public void ejecutarEvento(){

            GetDataLocal getDataLocal = new GetDataLocal();

            getDataLocal.tipoGetData = "eventos";
            getDataLocal.hoy = dia;

            getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getEvento.php"});

        }

        public void ejecutarProyecto(){

            GetDataLocal getDataLocal = new GetDataLocal();

            getDataLocal.tipoGetData = "proyectos";
            getDataLocal.hoy = dia;

            getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getProyecto.php"});

        }

        public void ejecutarHorarioTarea(){

            GetDataLocal getDataLocal = new GetDataLocal();

            getDataLocal.tipoGetData = "horarioTareas";
            getDataLocal.hoy = dia;

            getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getHorarioTarea.php"});

        }

        public void ejecutarEventoTarea(){

            GetDataLocal getDataLocal = new GetDataLocal();

            getDataLocal.tipoGetData = "eventoTareas";
            getDataLocal.hoy = dia;

            getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getTareaEvento.php"});

        }

        public void ejecutarProyectoTarea(){

            GetDataLocal getDataLocal = new GetDataLocal();

            getDataLocal.tipoGetData = "proyectoTareas";
            getDataLocal.hoy = dia;

            getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getTareaProyecto.php"});

        }

        public void ejecutarTarea(){

            GetDataLocal getDataLocal = new GetDataLocal();

            getDataLocal.tipoGetData = "tarea";
            getDataLocal.hoy = dia;

            getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getTarea.php"});


        }






        public void rellenarDatos(ArrayList<Tarea> tareas){


            for (Tarea tarea : tareas) {
                if(tarea.getSubTipo().equalsIgnoreCase(dia)){
                    ActivityPrincipal.Info info = new ActivityPrincipal.Info();

                    info.tarea.setNombre(tarea.getNombre());
                    info.tarea.setDescripcion("De " + tarea.getTipo());
                    info.tarea.setHoraInicio(tarea.getHoraInicio());
                    info.tarea.setHoraFin(tarea.getHoraFin());
                    info.tarea.setMonitor(tarea.getMonitor());
                    info.tarea.setEstado(tarea.getEstado());
                    info.tarea.setIdTarea(tarea.getIdTarea());
                    info.tarea.setSubTipo(tarea.getSubTipo());
                    info.tarea.setSeccion(tarea.getSeccion());
                    info.tarea.setSala(tarea.getSala());
                    info.tarea.setTipo(tarea.getTipo());

                    listData.add(info);
                }
            }


            listAdapter = new ActivityPrincipal.ListAdapter(listData);

            listView.setAdapter(listAdapter); //aca se inicializa la lista (despacho)


        }


        protected void onPreExecute() {
            // dialog.setMessage("Leyendo datos de la BD remota");
            // dialog.show();

        }

        protected Boolean doInBackground(String... urls) {
            InputStream inputStream = null;
            for (String url1 : urls) {
                try {
                    URL url = new URL(url1);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milisegundos */);
                    conn.setConnectTimeout(15000 /* milisegundos */);
                    // Método para enviar los datos
                    conn.setRequestMethod("GET");
                    // Si se requiere obtener un resultado de la página
                    // se coloca setDoInput(true);
                    conn.setDoInput(true);
                    // Recupera la página
                    conn.connect();
                    int response = conn.getResponseCode();
                    Log.d("SERVIDOR", "La respuesta del servidor es: " + response);
                    inputStream = conn.getInputStream();
                    contenido = new Scanner(inputStream).useDelimiter("\\A").next();
                    Log.i("CONTENIDO", contenido);
                } catch (Exception ex) {
                    Log.e("ERROR", ex.toString());
                    return false;
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return true;
        }
    }




}
