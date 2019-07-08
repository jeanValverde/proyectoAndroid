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

import com.example.jean.proyectoandroid.Modulo.Horario;
import com.example.jean.proyectoandroid.Modulo.HorarioTarea;
import com.example.jean.proyectoandroid.Modulo.Proyecto;
import com.example.jean.proyectoandroid.Modulo.Tarea;
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
import java.util.Scanner;

public class ActivityProyectoInto extends AppCompatActivity {

    String idUsuario = "";
    String idProyecto = "";

    TextView nombre;
    TextView descripcion;
    TextView fechaInicio;
    TextView fechaTermino;

    Proyecto proyecto;

    ArrayList<TareasProyecto> tareasProyectos = new ArrayList<TareasProyecto>();

    ArrayList<Tarea> tareasUsuario = new ArrayList<Tarea>();


    private SwipeListView listView;
    private ActivityProyectoInto.ListAdapter listAdapter;
    private ArrayList<ActivityProyectoInto.Info> listData = new ArrayList<ActivityProyectoInto.Info>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyecto_into);

        Intent recibir = getIntent();
        idUsuario = recibir.getStringExtra("idUsuario");
        idProyecto = recibir.getStringExtra("idProyecto");


        nombre = (TextView) findViewById(R.id.nombreProyecto);
        descripcion = (TextView) findViewById(R.id.descripcionProyecto);
        fechaInicio = (TextView) findViewById(R.id.fechaInicioProyecto);
        fechaTermino = (TextView) findViewById(R.id.fechaFinProyecto);

        ImageButton atras = (ImageButton) findViewById(R.id.atrasViewProyecto);
        ImageButton crear = (ImageButton) findViewById(R.id.crearTareaProyecto);


        //inicio de swipe list
        listView = (SwipeListView) findViewById(R.id.listView);


        GetDataLocal getDataLocal = new GetDataLocal();

        getDataLocal.tipoGetData = "proyecto";

        getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getProyecto.php"});



        listView.setListener(new OnSwipeListItemClickListener() {

            @Override
            public void OnClick(View view, int index) {

                ActivityProyectoInto.Info tareaInfo = (ActivityProyectoInto.Info) listData.get(index); //se obtiene el objeto

                //se envia el id de la tarea y el id Usuario en un intent


                Intent intent = new Intent(ActivityProyectoInto.this, ActivityProyectoIntoTarea.class);
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("idTarea", tareaInfo.tarea.getIdTarea());
                intent.putExtra("idProyecto", idProyecto);
                startActivity(intent);

            }

            @Override
            public boolean OnLongClick(View view, int index) {
                AlertDialog.Builder ab = new AlertDialog.Builder(ActivityProyectoInto.this);
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
                        ab = new AlertDialog.Builder(ActivityProyectoInto.this);
                        ab.setTitle("Modify");
                        ab.setMessage("You will modify item "+index);
                        ab.create().show();
                        break;
                    case R.id.delete:
                        ab = new AlertDialog.Builder(ActivityProyectoInto.this);
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

                Intent intent = new Intent(ActivityProyectoInto.this, ActivityProyecto.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);

            }
        });


        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ActivityProyectoInto.this, ActivityCrearTarea.class);
                intent.putExtra("tipo", "proyecto");
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("idProyecto", idProyecto);
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
        private ArrayList<ActivityProyectoInto.Info> listData;
        public ListAdapter(ArrayList<ActivityProyectoInto.Info> listData){
            this.listData= (ArrayList<ActivityProyectoInto.Info>) listData.clone();
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
            ActivityProyectoInto.ViewHolder viewHolder = new ActivityProyectoInto.ViewHolder();
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
                viewHolder = (ActivityProyectoInto.ViewHolder) convertView.getTag();
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
                    if(tipoGetData.equalsIgnoreCase("proyecto")){

                        json = new JSONArray(contenido);
                        ArrayList<Proyecto> proyectos = new ArrayList<Proyecto>();
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

                        //proyecto = new Proyecto();

                        ArrayList<Proyecto> proyectosS = new ArrayList<Proyecto>();

                        for (Proyecto proyecto : proyectos) {
                            if(proyecto.getIdUsuario() == Integer.parseInt(idUsuario)){
                                proyectosS.add(proyecto);
                            }
                        }

                        for (Proyecto proyectoTem : proyectosS) {
                            if(proyectoTem.getIdProyecto() == Integer.parseInt(idProyecto)){
                                proyecto = proyectoTem;
                                break;
                            }
                        }

                        setearDatos();



                    }else if (tipoGetData.equalsIgnoreCase("tareas")){

                        ArrayList<Tarea> tareas = new ArrayList<Tarea>();

                        json = new JSONArray(contenido);
                        for (int i = 0; i < json.length(); i++) {
                            JSONArray jsonData = json.getJSONArray(i);
                            Tarea tarea1 = new Tarea();
                            tarea1.setIdTarea(jsonData.get(0).toString());
                            tarea1.setNombre(jsonData.get(1).toString());
                            tarea1.setDescripcion(jsonData.get(2).toString());
                            tarea1.setHoraInicio(jsonData.get(3).toString());
                            tarea1.setHoraFin(jsonData.get(4).toString());
                            tarea1.setTipo(jsonData.get(5).toString());
                            tarea1.setEstado(jsonData.get(6).toString());
                            tarea1.setMonitor(jsonData.get(7).toString());
                            tarea1.setSubTipo(jsonData.get(8).toString());
                            tarea1.setSeccion(jsonData.get(9).toString());
                            tarea1.setSala(jsonData.get(10).toString());
                            tareas.add(tarea1);
                        }



                        for (TareasProyecto tareaProyec : tareasProyectos) {
                            for (Tarea tarea: tareas) {
                                if(tareaProyec.getIdTarea().equals(tarea.getIdTarea())){
                                    tareasUsuario.add(tarea);
                                }
                            }
                        }


                         despacharVista();


                    }else if (tipoGetData.equalsIgnoreCase("tareasProyecto")){

                        ArrayList<TareasProyecto> tareasProyectosS = new ArrayList<TareasProyecto>();


                        json = new JSONArray(contenido);
                        for (int i = 0; i < json.length(); i++) {
                            JSONArray jsonData = json.getJSONArray(i);
                            TareasProyecto tareasProyecto = new TareasProyecto();
                            tareasProyecto.setIdTareaProyecto(Integer.parseInt(jsonData.get(0).toString()));
                            tareasProyecto.setIdProyecto(Integer.parseInt(jsonData.get(1).toString()));
                            tareasProyecto.setIdTarea(jsonData.get(2).toString());
                            tareasProyectosS.add(tareasProyecto);
                        }

                        for (TareasProyecto tareasProyecti: tareasProyectosS) {
                            if (tareasProyecti.getIdProyecto() == Integer.parseInt(idProyecto)) {
                                tareasProyectos.add(tareasProyecti);
                            }
                        }


                        setearTareas();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // dialog.dismiss();
        }


        public void setearDatos(){

            nombre.setText(proyecto.getNombre());
            descripcion.setText(proyecto.getDescripcion());
            fechaInicio.setText(proyecto.getFechaInicio());
            fechaTermino.setText(proyecto.getFechaTermino());


            GetDataLocal getDataLocal = new GetDataLocal();
            getDataLocal.tipoGetData = "tareasProyecto";

            getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getTareaProyecto.php"});


        }

        public void setearTareas(){


            GetDataLocal getDataLocal = new GetDataLocal();
            getDataLocal.tipoGetData = "tareas";

            getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getTarea.php"});

        }


        public void despacharVista(){

            for (Tarea tarea : tareasUsuario) {
                         if(tarea.getTipo().equalsIgnoreCase("proyecto")){
                              ActivityProyectoInto.Info info = new ActivityProyectoInto.Info();
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

                      listAdapter = new ActivityProyectoInto.ListAdapter(listData);

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

    //fin get
}
