package com.example.jean.proyectoandroid;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jean.proyectoandroid.Modulo.Evento;
import com.example.jean.proyectoandroid.Modulo.Horario;
import com.example.jean.proyectoandroid.Modulo.HorarioTarea;
import com.example.jean.proyectoandroid.Modulo.Tarea;
import com.example.jean.proyectoandroid.Modulo.TareasEvento;
import com.example.jean.proyectoandroid.SwipeList.OnSwipeListItemClickListener;
import com.example.jean.proyectoandroid.SwipeList.SwipeListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ActivityEventoInto extends AppCompatActivity {

    String idUsuario = "";
    String idEvento = "";

    Evento evento;

    TextView diaEventoView;
    TextView diaEventoFecha;
    TextView diaEventoDescripcion;

    private SwipeListView listView;
    private ActivityEventoInto.ListAdapter listAdapter;
    private ArrayList<ActivityEventoInto.Info> listData = new ArrayList<ActivityEventoInto.Info>();



    ArrayList<TareasEvento> tareasEventos = new ArrayList<TareasEvento>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_into);

        Intent recibir = getIntent();
        idUsuario = recibir.getStringExtra("idUsuario");
        idEvento = recibir.getStringExtra("idEvento");


        diaEventoView = (TextView) findViewById(R.id.diaEventoView);
        diaEventoFecha = (TextView) findViewById(R.id.diaEventoFecha);
        diaEventoDescripcion = (TextView) findViewById(R.id.descripcionEventoView);


        ImageButton atras = (ImageButton) findViewById(R.id.atrasViewTareaEvento);
        ImageButton crear = (ImageButton) findViewById(R.id.crearTareaEvento);


        //inicio de swipe list
        listView = (SwipeListView) findViewById(R.id.listView);



        GetDataLocal getDataLocal = new GetDataLocal();
        getDataLocal.tipoGetData = "evento";

        getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getEvento.php"});



        listView.setListener(new OnSwipeListItemClickListener() {

            @Override
            public void OnClick(View view, int index) {

                ActivityEventoInto.Info tareaInfo = (ActivityEventoInto.Info) listData.get(index); //se obtiene el objeto

                //se envia el id de la tarea y el id Usuario en un intent


                Intent intent = new Intent(ActivityEventoInto.this, ActivityTareaInto.class);
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("idTarea", tareaInfo.tarea.getIdTarea());
                intent.putExtra("idEvento", idEvento);

                startActivity(intent);


            }

            @Override
            public boolean OnLongClick(View view, int index) {
                AlertDialog.Builder ab = new AlertDialog.Builder(ActivityEventoInto.this);
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
                        ab = new AlertDialog.Builder(ActivityEventoInto.this);
                        ab.setTitle("Modify");
                        ab.setMessage("You will modify item "+index);
                        ab.create().show();
                        break;
                    case R.id.delete:
                        ab = new AlertDialog.Builder(ActivityEventoInto.this);
                        ab.setTitle("Delete");
                        ab.setMessage("You will delete item "+index);
                        ab.create().show();
                        break;
                }
            }

        },new int[]{R.id.modify,R.id.delete});




        //fin swipe list


        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityEventoInto.this, ActivityEvento.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
        });


        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityEventoInto.this, ActivityCrearTarea.class);
                intent.putExtra("tipo", "evento");
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("idEvento", idEvento);
                startActivity(intent);
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
        private ArrayList<ActivityEventoInto.Info> listData;
        public ListAdapter(ArrayList<ActivityEventoInto.Info> listData){
            this.listData= (ArrayList<ActivityEventoInto.Info>) listData.clone();
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
            ActivityEventoInto.ViewHolder viewHolder = new ActivityEventoInto.ViewHolder();
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
                viewHolder = (ActivityEventoInto.ViewHolder) convertView.getTag();
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
                    if(tipoGetData.equalsIgnoreCase("evento")){

                        evento = new Evento();

                        json = new JSONArray(contenido);
                        ArrayList<Evento> eventos = new ArrayList<Evento>();
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



                        for (Evento eventoS : eventos ){
                            if(eventoS.getId() == Integer.parseInt(idEvento)){
                                evento = eventoS;
                                break;
                            }
                        }

                        despacharVista();



                    }else if (tipoGetData.equalsIgnoreCase("tareasEvento")){

                        ArrayList<TareasEvento> tareasEventosSe = new ArrayList<TareasEvento>();
                        json = new JSONArray(contenido);
                        for (int i = 0; i < json.length(); i++) {
                            JSONArray jsonData = json.getJSONArray(i);
                            TareasEvento tareasEvento = new TareasEvento();
                            tareasEvento.setIdTareaEvento(Integer.parseInt(jsonData.get(0).toString()));
                            tareasEvento.setIdEvento(Integer.parseInt(jsonData.get(1).toString()));
                            tareasEvento.setIdTarea(jsonData.get(2).toString());
                            tareasEventosSe.add(tareasEvento);
                        }


                        for (TareasEvento tareaEvento : tareasEventosSe) {

                            if(evento.getId() == tareaEvento.getIdEvento()){
                                tareasEventos.add(tareaEvento);
                            }

                        }



                        iniciarTareas();


                    }else if (tipoGetData.equalsIgnoreCase("tareas")){

                        json = new JSONArray(contenido);
                        ArrayList<Tarea> tareas = new ArrayList<Tarea>();
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


                        ArrayList<Tarea> tareaSeteo = new ArrayList<Tarea>();

                        for (TareasEvento tareaEvento : tareasEventos) {
                            for (Tarea tarea : tareas) {
                                if(tarea.getIdTarea().equals(tareaEvento.getIdTarea())){
                                    tareaSeteo.add(tarea);
                                }
                            }
                        }


                        rellenarDatos(tareaSeteo);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // dialog.dismiss();
        }

        public void despacharVista(){

            diaEventoView.setText(evento.getNombre());
            diaEventoFecha.setText(evento.getFecha());
            diaEventoDescripcion.setText(evento.getDescripcion());



            GetDataLocal getDataLocal = new GetDataLocal();
            getDataLocal.tipoGetData = "tareasEvento";

            getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getTareaEvento.php"});


        }

        public void iniciarTareas(){


            GetDataLocal getDataLocal = new GetDataLocal();
            getDataLocal.tipoGetData = "tareas";

            getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getTarea.php"});


        }

        public void rellenarDatos(ArrayList<Tarea> tareas){

            //inicializar y cargar la lista de tareas

            for (Tarea tarea : tareas) {
                if(tarea.getTipo().equalsIgnoreCase("evento")){
                    ActivityEventoInto.Info info = new ActivityEventoInto.Info();
                    info.tarea.setNombre(tarea.getNombre());
                    info.tarea.setDescripcion(tarea.getDescripcion());
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


            listAdapter = new ActivityEventoInto.ListAdapter(listData);

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
