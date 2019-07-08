package com.example.jean.proyectoandroid;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jean.proyectoandroid.Modulo.ListaTarea;
import com.example.jean.proyectoandroid.Modulo.SetData;
import com.example.jean.proyectoandroid.Modulo.Tarea;
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

public class ActivityTareaInto extends AppCompatActivity {

    Tarea tarea;
    TextView dia ;
    TextView descripcion ;
    TextView incio ;
    TextView fin ;
    String idUsuario ;
    String idTarea;
    String idEvento;

    private SwipeListView listView;
    private ActivityTareaInto.ListAdapter listAdapter;
    private ArrayList<ActivityTareaInto.Info> listData = new ArrayList<ActivityTareaInto.Info>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea_into);


        Intent recibir = getIntent();
        idUsuario = recibir.getStringExtra("idUsuario");
        idTarea = recibir.getStringExtra("idTarea");
        idEvento = recibir.getStringExtra("idEvento");


        dia = (TextView) findViewById(R.id.tareaIntoNombre);
        descripcion = (TextView) findViewById(R.id.tareaIntoDescripcion);
        incio = (TextView) findViewById(R.id.tareaIntoInicio);
        fin = (TextView) findViewById(R.id.tareaIntoFin);

        ImageButton atras = (ImageButton) findViewById(R.id.atrasIntoTarea);
        ImageButton crearLista = (ImageButton) findViewById(R.id.crearIntoTareaLista);



        listView = (SwipeListView) findViewById(R.id.listView);


        GetDataLocal getDataLocal = new GetDataLocal();
        //

        getDataLocal.tipoGet = "tarea";

        getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getTarea.php"});

        listView.setListener(new OnSwipeListItemClickListener() {

            @Override
            public void OnClick(View view, int index) {

                /**
                 * activityHorarioInto.Info tareaInfo = (activityHorarioInto.Info) listData.get(index); //se obtiene el objeto
                 *
                 *                 //se envia el id de la tarea y el id Usuario en un intent
                 *
                 *                 Intent intent = new Intent(activityHorarioInto.this, activityHorarioInto.class);
                 *                 intent.putExtra("idUsuario", idUsuario);
                 *                 intent.putExtra("idTarea", tareaInfo.tarea.getIdTarea());
                 *                 startActivity(intent);
                 */

            }

            @Override
            public boolean OnLongClick(View view, int index) {
                AlertDialog.Builder ab = new AlertDialog.Builder(ActivityTareaInto.this);
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

                        ActivityTareaInto.Info lista = (ActivityTareaInto.Info) listData.get(index); //se obtiene el objeto*


                        SetData setData = new SetData();

                        String parametros = "id_listas=" + lista.listaTarea.getIdLita();

                        setData.setParametros(parametros);

                        setData.execute(new String[]{"http://10.0.2.2/webService/desactivarLista.php"});


                        listData.clear();

                        if(listData.isEmpty()){
                            GetDataLocal getDataLocal = new GetDataLocal();
                            //

                            getDataLocal.tipoGet = "tarea";

                            getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getTarea.php"});
                        }

                        break;
                    case R.id.delete:
                        ab = new AlertDialog.Builder(ActivityTareaInto.this);
                        ab.setTitle("Delete");
                        ab.setMessage("You will delete item "+index);
                        ab.create().show();
                        break;
                }
            }

        },new int[]{R.id.modify,R.id.delete});





        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityTareaInto.this, ActivityEventoInto.class);
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("idEvento", idEvento);
                startActivity(intent);
            }
        });

        crearLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityTareaInto.this, ActivityCrearListaTarea.class);
                intent.putExtra("tipo", "evento");
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("idEvento", idEvento);
                intent.putExtra("idTarea", idTarea);
                startActivity(intent);
            }
        });



    }


    //get
    private class GetDataLocal extends AsyncTask<String, Void, Boolean> {

        // ProgressDialog dialog = new ProgressDialog(ActivityIniciarSesion.this);
        public String contenido = "";

        public String tipoGet = "";


        protected void onPostExecute(Boolean result) {

            if (result) {
                JSONArray json = null;
                try {
                    ArrayList<ListaTarea> listaTareas = new ArrayList<ListaTarea>();

                    ArrayList<Tarea> tareas = new ArrayList<Tarea>();

                    if(tipoGet.equals("tarea")){
                        tarea = new Tarea();
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


                        for (Tarea tareaV : tareas) {
                            if(idTarea.equals(tareaV.getIdTarea())){
                                tarea = tareaV;
                                break;
                            }
                        }

                        despacharVista();

                    }else if(tipoGet.equals("actividades")){
                        json = new JSONArray(contenido);
                        for (int i = 0; i < json.length(); i++) {
                            JSONArray jsonData = json.getJSONArray(i);
                            ListaTarea listaTarea = new ListaTarea();

                            listaTarea.setIdLita(Integer.parseInt(jsonData.get(0).toString()));
                            listaTarea.setDescripcion(jsonData.get(1).toString());
                            listaTarea.setEstado(jsonData.get(2).toString());
                            listaTarea.setIdTarea(jsonData.get(3).toString());

                            listaTareas.add(listaTarea);
                        }

                        ArrayList<ListaTarea> seteoListaTarea = new ArrayList<ListaTarea>();

                        for (ListaTarea listaT :listaTareas) {
                            // if(listaT.getIdTarea().equals(idTarea)){
                            seteoListaTarea.add(listaT);
                            //}
                        }

                        rellenarDatos(seteoListaTarea);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // dialog.dismiss();
        }

        public void despacharVista() {


            dia.setText(tarea.getNombre());
            descripcion.setText(tarea.getDescripcion());
            incio.setText(tarea.getHoraInicio());
            fin.setText(tarea.getHoraFin());


            GetDataLocal getDataLocal = new GetDataLocal();


            getDataLocal.tipoGet = "actividades";

            getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getListaTarea.php"});




        }




        public void rellenarDatos(ArrayList<ListaTarea> listaTareas){


            for (ListaTarea lista : listaTareas ) {
                ActivityTareaInto.Info info = new ActivityTareaInto.Info();

                if(lista.getIdTarea().equals(idTarea)){
                    info.listaTarea.setIdLita(lista.getIdLita());
                    info.listaTarea.setDescripcion(lista.getDescripcion());
                    info.listaTarea.setEstado(lista.getEstado());
                    info.listaTarea.setIdTarea(lista.getIdTarea());
                    listData.add(info);
                }
            }


            listAdapter = new ActivityTareaInto.ListAdapter(listData);

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



    //swipe list

    @Override
    protected void onResume() {
        super.onResume();
    }

    class Info{
        public ListaTarea listaTarea = new ListaTarea();
    }

    class ViewHolder{

        public TextView desc;
        public Button modify;
        public Button delete;
        public Button imagen;
    }



    class ListAdapter extends com.example.jean.proyectoandroid.SwipeList.SwipeListAdapter {
        private ArrayList<ActivityTareaInto.Info> listData;
        public ListAdapter(ArrayList<ActivityTareaInto.Info> listData){
            this.listData= (ArrayList<ActivityTareaInto.Info>) listData.clone();
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
            ActivityTareaInto.ViewHolder viewHolder = new ActivityTareaInto.ViewHolder();
            if(convertView == null){
                convertView = View.inflate(getBaseContext(),R.layout.layout_lista_tarea,null);
                viewHolder.desc = (TextView) convertView.findViewById(R.id.descripcionListaTarea);
                viewHolder.modify = (Button) convertView.findViewById(R.id.modify);
                viewHolder.delete = (Button) convertView.findViewById(R.id.delete);
                viewHolder.imagen = (Button) convertView.findViewById(R.id.estadoListaTarea);


                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ActivityTareaInto.ViewHolder) convertView.getTag();
            }

            viewHolder.desc.setText(listData.get(position).listaTarea.getDescripcion());


            if (listData.get(position).listaTarea.getEstado().equals("activo")) {
                viewHolder.imagen.setBackground(AppCompatResources.getDrawable(ActivityTareaInto.this , R.drawable.iconcancel));
                viewHolder.imagen.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.rojo));



            }else{
                viewHolder.imagen.setBackground( AppCompatResources.getDrawable(ActivityTareaInto.this, R.drawable.iconcheck));
            }


            return super.bindView(position, convertView);
        }
    }




    //fin swipe list


}
