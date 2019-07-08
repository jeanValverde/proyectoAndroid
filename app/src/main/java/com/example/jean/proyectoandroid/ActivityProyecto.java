package com.example.jean.proyectoandroid;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jean.proyectoandroid.Modulo.Evento;
import com.example.jean.proyectoandroid.Modulo.Proyecto;
import com.example.jean.proyectoandroid.SwipeList.OnSwipeListItemClickListener;
import com.example.jean.proyectoandroid.SwipeList.SwipeListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class ActivityProyecto extends AppCompatActivity {

    BottomNavigationView navigationView;


    private SwipeListView listView;
    private ActivityProyecto.ListAdapter listAdapter;
    private ArrayList<ActivityProyecto.Info> listData = new ArrayList<ActivityProyecto.Info>();

    String idUsuario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyecto);

        Intent recibir = getIntent();
        idUsuario = recibir.getStringExtra("idUsuario");

        navigationView= (BottomNavigationView) findViewById(R.id.bottomNavigationView);


        navigationView.getMenu().getItem(3).setChecked(true);


        GetDataLocal getDataLocal = new GetDataLocal();

        getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getProyecto.php"});



        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.recienteMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityProyecto.this, ActivityPrincipal.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }else  if (item.getItemId() == R.id.horarioMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityProyecto.this, ActivityHorario.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }else  if (item.getItemId() == R.id.eventoMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityProyecto.this, ActivityEvento.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }else if (item.getItemId() == R.id.proyectoMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityProyecto.this, ActivityProyecto.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }else  if (item.getItemId() == R.id.perfilMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityProyecto.this, ActivityPerfil.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });


        ImageButton crearProyecto = (ImageButton) findViewById(R.id.crearProyecto);

        crearProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityProyecto.this, ActivityCrearProyectoFinal.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
        });



        //list


        listView = (SwipeListView) findViewById(R.id.listViewProyecto);

        listView.setListener(new OnSwipeListItemClickListener() {

            @Override
            public void OnClick(View view, int index) {


                ActivityProyecto.Info tareaInfo = (ActivityProyecto.Info) listData.get(index); //se obtiene el objeto

                //se envia el id de la tarea y el id Usuario en un intent

                Intent intent = new Intent(ActivityProyecto.this, ActivityProyectoInto.class);
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("idProyecto", String.valueOf(tareaInfo.proyecto.getIdProyecto()) );
                startActivity(intent);

            }

            @Override
            public boolean OnLongClick(View view, int index) {
                AlertDialog.Builder ab = new AlertDialog.Builder(ActivityProyecto.this);
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
                        ab = new AlertDialog.Builder(ActivityProyecto.this);
                        ab.setTitle("Modify");
                        ab.setMessage("You will modify item "+index);
                        ab.create().show();
                        break;
                    case R.id.delete:
                        ab = new AlertDialog.Builder(ActivityProyecto.this);
                        ab.setTitle("Delete");
                        ab.setMessage("You will delete item "+index);
                        ab.create().show();
                        break;
                }
            }

        },new int[]{R.id.modify,R.id.delete});







    }

    //get
    private class GetDataLocal extends AsyncTask<String, Void, Boolean> {

        // ProgressDialog dialog = new ProgressDialog(ActivityIniciarSesion.this);
        public String contenido = "";


        protected void onPostExecute(Boolean result) {

            if (result) {
                JSONArray json = null;
                try {


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


                    rellenarDatos(proyectos);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // dialog.dismiss();
        }



        public void rellenarDatos(ArrayList<Proyecto> proyectos){



            for (Proyecto proyecto : proyectos ) {
                if(proyecto.getIdUsuario() == Integer.parseInt(idUsuario)){
                    ActivityProyecto.Info info = new ActivityProyecto.Info();
                    info.proyecto.setNombre(proyecto.getNombre());
                    info.proyecto.setDescripcion(proyecto.getDescripcion());
                    info.proyecto.setFechaInicio(proyecto.getFechaInicio());
                    info.proyecto.setFechaTermino(proyecto.getFechaTermino());
                    info.proyecto.setIdUsuario(proyecto.getIdUsuario());
                    info.proyecto.setIdProyecto(proyecto.getIdProyecto());
                    info.proyecto.setFoto(proyecto.getFoto());
                    listData.add(info);
                }

            }


            listAdapter = new ActivityProyecto.ListAdapter(listData);

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
        public Proyecto proyecto = new Proyecto();
    }

    class ViewHolder{
        public TextView nombreProyecto;
        public TextView descripcionProyecto;
        public TextView fechaIncio;
        public TextView fechaTermino;
        public Button modify;
        public Button delete;
        public Button imagen;
    }


    class ListAdapter extends com.example.jean.proyectoandroid.SwipeList.SwipeListAdapter {
        private ArrayList<ActivityProyecto.Info> listData;
        public ListAdapter(ArrayList<ActivityProyecto.Info> listData){
            this.listData= (ArrayList<ActivityProyecto.Info>) listData.clone();
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
            ActivityProyecto.ViewHolder viewHolder = new ActivityProyecto.ViewHolder();
            if(convertView == null){
                convertView = View.inflate(getBaseContext(),R.layout.layout_proyecto,null);
                viewHolder.nombreProyecto = (TextView) convertView.findViewById(R.id.nombreProyectoView);
                viewHolder.descripcionProyecto = (TextView) convertView.findViewById(R.id.descripcionProyectoView);
                viewHolder.modify = (Button) convertView.findViewById(R.id.modify);
                viewHolder.delete = (Button) convertView.findViewById(R.id.delete);
                viewHolder.fechaIncio = (TextView) convertView.findViewById(R.id.fechaInicioProyectoView);
                viewHolder.fechaTermino = (TextView) convertView.findViewById(R.id.fechaTerminoProyectoView);
                viewHolder.imagen = (Button) convertView.findViewById(R.id.imagenProyectoView);


                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ActivityProyecto.ViewHolder) convertView.getTag();
            }
            viewHolder.nombreProyecto.setText(listData.get(position).proyecto.getNombre());
            viewHolder.descripcionProyecto.setText(listData.get(position).proyecto.getDescripcion());
            viewHolder.fechaIncio.setText(listData.get(position).proyecto.getFechaInicio());
            viewHolder.fechaTermino.setText(listData.get(position).proyecto.getFechaTermino());
            viewHolder.imagen.setBackground( AppCompatResources.getDrawable(ActivityProyecto.this, R.drawable.trabajo));


            return super.bindView(position, convertView);
        }
    }



    //fin swipe list


}
