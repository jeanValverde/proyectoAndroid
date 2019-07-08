package com.example.jean.proyectoandroid;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jean.proyectoandroid.Modulo.Controller;
import com.example.jean.proyectoandroid.Modulo.Evento;
import com.example.jean.proyectoandroid.Modulo.Horario;
import com.example.jean.proyectoandroid.Modulo.HorarioTarea;
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
import java.util.IdentityHashMap;
import java.util.Scanner;

public class ActivityEvento extends AppCompatActivity {

    BottomNavigationView navigationView;

    private SwipeListView listView;
    private ActivityEvento.ListAdapter listAdapter;
    private ArrayList<ActivityEvento.Info> listData = new ArrayList<ActivityEvento.Info>();

    String idUsuario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

        Intent recibir = getIntent();
        idUsuario = recibir.getStringExtra("idUsuario");


        navigationView= (BottomNavigationView) findViewById(R.id.bottomNavigationView);


        navigationView.getMenu().getItem(2).setChecked(true);


        GetDataLocal getDataLocal = new GetDataLocal();

        getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getEvento.php"});


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.recienteMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityEvento.this, ActivityPrincipal.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }else  if (item.getItemId() == R.id.horarioMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityEvento.this, ActivityHorario.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }else  if (item.getItemId() == R.id.eventoMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityEvento.this, ActivityEvento.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }else if (item.getItemId() == R.id.proyectoMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityEvento.this, ActivityProyecto.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }else  if (item.getItemId() == R.id.perfilMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityEvento.this, ActivityPerfil.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });




        listView = (SwipeListView) findViewById(R.id.listViewEvento);





        listView.setListener(new OnSwipeListItemClickListener() {

            @Override
            public void OnClick(View view, int index) {

                ActivityEvento.Info eventoInfo = (ActivityEvento.Info) listData.get(index); //se obtiene el objeto

                //se envia el id de la tarea y el id Usuario en un intent

                Intent intent = new Intent(ActivityEvento.this, ActivityEventoInto.class);
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("idEvento", String.valueOf(eventoInfo.evento.getId()));
                startActivity(intent);

            }

            @Override
            public boolean OnLongClick(View view, int index) {
                AlertDialog.Builder ab = new AlertDialog.Builder(ActivityEvento.this);
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
                        ab = new AlertDialog.Builder(ActivityEvento.this);
                        ab.setTitle("Modify");
                        ab.setMessage("You will modify item "+index);
                        ab.create().show();
                        break;
                    case R.id.delete:
                        ab = new AlertDialog.Builder(ActivityEvento.this);
                        ab.setTitle("Delete");
                        ab.setMessage("You will delete item "+index);
                        ab.create().show();
                        break;
                }
            }

        },new int[]{R.id.modify,R.id.delete});



        //fin swipe list


        ImageButton crearEvento = (ImageButton) findViewById(R.id.crearEvento);

        crearEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityEvento.this, ActivityCrearEvento.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
        });



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

                        rellenarDatos(eventos);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // dialog.dismiss();
        }



        public void rellenarDatos(ArrayList<Evento> eventos){


            for (Evento evento : eventos ) {
                ActivityEvento.Info info = new ActivityEvento.Info();

                if(Integer.parseInt(evento.getIdUsuario()) == Integer.parseInt(idUsuario)){
                    info.evento.setId(evento.getId());
                    info.evento.setNombre(evento.getNombre());
                    info.evento.setDescripcion(evento.getDescripcion());
                    info.evento.setFecha(evento.getFecha());
                    info.evento.setTipoEvento(evento.getTipoEvento());
                    info.evento.setIdUsuario(evento.getIdUsuario());
                    listData.add(info);
                }
            }




            listAdapter = new ActivityEvento.ListAdapter(listData);

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
        public Evento evento = new Evento();
    }

    class ViewHolder{
        public TextView name;
        public TextView desc;
        public TextView horaInicio;
        public TextView nombre;
        public Button modify;
        public Button delete;
        public Button imagen;
    }


    class ListAdapter extends com.example.jean.proyectoandroid.SwipeList.SwipeListAdapter {
        private ArrayList<ActivityEvento.Info> listData;
        public ListAdapter(ArrayList<ActivityEvento.Info> listData){
            this.listData= (ArrayList<ActivityEvento.Info>) listData.clone();
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
            ActivityEvento.ViewHolder viewHolder = new ActivityEvento.ViewHolder();
            if(convertView == null){
                convertView = View.inflate(getBaseContext(),R.layout.layout_evento,null);
                viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                viewHolder.desc = (TextView) convertView.findViewById(R.id.desc);
                viewHolder.modify = (Button) convertView.findViewById(R.id.modify);
                viewHolder.delete = (Button) convertView.findViewById(R.id.delete);
                viewHolder.nombre = (TextView) convertView.findViewById(R.id.nombreEventoView);
                viewHolder.horaInicio = (TextView) convertView.findViewById(R.id.horaInicio);
                viewHolder.imagen = (Button) convertView.findViewById(R.id.imagen);


                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ActivityEvento.ViewHolder) convertView.getTag();
            }
            viewHolder.horaInicio.setText(listData.get(position).evento.getNombre());
            viewHolder.nombre.setText(listData.get(position).evento.getNombre());
            viewHolder.name.setText(listData.get(position).evento.getFecha());
            viewHolder.desc.setText(listData.get(position).evento.getDescripcion());



            if (listData.get(position).evento.getTipoEvento().equals("Matrimonio")) {
                viewHolder.imagen.setBackground(AppCompatResources.getDrawable(ActivityEvento.this , R.drawable.matrimonio));

            }else{
                viewHolder.imagen.setBackground( AppCompatResources.getDrawable(ActivityEvento.this, R.drawable.trabajo));
            }


            return super.bindView(position, convertView);
        }
    }



    //fin swipe list




}
