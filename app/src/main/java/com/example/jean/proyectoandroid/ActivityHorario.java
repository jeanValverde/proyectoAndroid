package com.example.jean.proyectoandroid;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jean.proyectoandroid.Modulo.Controller;
import com.example.jean.proyectoandroid.Modulo.Horario;
import com.example.jean.proyectoandroid.Modulo.HorarioTarea;
import com.example.jean.proyectoandroid.Modulo.Tarea;
import com.example.jean.proyectoandroid.Modulo.Usuario;
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
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ActivityHorario extends AppCompatActivity {

    BottomNavigationView navigationView;

    TextView diaTxt;

    private SwipeListView listView;
    private ActivityHorario.ListAdapter listAdapter;
    private ArrayList<ActivityHorario.Info> listData = new ArrayList<ActivityHorario.Info>();

    String idUsuario = "";
    ArrayList<Tarea> tareas = new ArrayList<Tarea>();
    ArrayList<Horario> horarios = new ArrayList<Horario>();
    ArrayList<HorarioTarea> horarioTareas = new ArrayList<HorarioTarea>();


    ArrayList<Tarea> tareasUsuario = new ArrayList<Tarea>();

    String dia = "";

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);

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

        //aca es el tema

        //inicio de swipe list
        listView = (SwipeListView) findViewById(R.id.listView);

        ArrayList<ActivityHorario.Info> informacion = new ArrayList<>();

        GetDataLocal getDataLocal = new GetDataLocal();
        //

        getDataLocal.tipoGetData = "horario";
        getDataLocal.hoy = dia;

        getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getHorario.php"});


        listView.setListener(new OnSwipeListItemClickListener() {

            @Override
            public void OnClick(View view, int index) {

                ActivityHorario.Info tareaInfo = (ActivityHorario.Info) listData.get(index); //se obtiene el objeto

                //se envia el id de la tarea y el id Usuario en un intent

                Intent intent = new Intent(ActivityHorario.this, activityHorarioInto.class);
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("idTarea", tareaInfo.tarea.getIdTarea());
                startActivity(intent);

            }

            @Override
            public boolean OnLongClick(View view, int index) {
                AlertDialog.Builder ab = new AlertDialog.Builder(ActivityHorario.this);
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
                        ab = new AlertDialog.Builder(ActivityHorario.this);
                        ab.setTitle("Modify");
                        ab.setMessage("You will modify item "+index);
                        ab.create().show();
                        break;
                    case R.id.delete:
                        ab = new AlertDialog.Builder(ActivityHorario.this);
                        ab.setTitle("Delete");
                        ab.setMessage("You will delete item "+index);
                        ab.create().show();
                        break;
                }
            }

        },new int[]{R.id.modify,R.id.delete});




        //fin swipe list


        navigationView= (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navigationView.getMenu().getItem(1).setChecked(true);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.recienteMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityHorario.this, ActivityPrincipal.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }else  if (item.getItemId() == R.id.horarioMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityHorario.this, ActivityHorario.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }else  if (item.getItemId() == R.id.eventoMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityHorario.this, ActivityEvento.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }else if (item.getItemId() == R.id.proyectoMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityHorario.this, ActivityProyecto.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }else  if (item.getItemId() == R.id.perfilMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityHorario.this, ActivityPerfil.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });


        ImageButton crearHoaraio = (ImageButton) findViewById(R.id.plusHorario);

        crearHoaraio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityHorario.this, ActivityCrearHoaraio.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
        });


        //domingo
        com.getbase.floatingactionbutton.FloatingActionButton domingoBtn = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.menuFlotanteDomingo);


        domingoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                diaTxt.setText("Domingo");
                horarios.clear();
                horarioTareas.clear();
                tareasUsuario.clear();
                tareas.clear();
                listData.clear();


                GetDataLocal getDataLocal = new GetDataLocal();

                getDataLocal.tipoGetData = "horario";
                getDataLocal.hoy = "domingo";
                dia = "domingo";

                getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getHorario.php"});


            }
        });


        //sabado
        com.getbase.floatingactionbutton.FloatingActionButton sabadoBtn = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.menuFlotanteSabado);

        sabadoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                diaTxt.setText("Sabado");
                horarios.clear();
                horarioTareas.clear();
                tareasUsuario.clear();
                tareas.clear();
                listData.clear();


                GetDataLocal getDataLocal = new GetDataLocal();

                getDataLocal.tipoGetData = "horario";
                getDataLocal.hoy = "sabado";
                dia = "sabado";

                getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getHorario.php"});
            }
        });


        com.getbase.floatingactionbutton.FloatingActionButton viernesBtn = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.menuFlotanteViernes);

        viernesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diaTxt.setText("Viernes");
                horarios.clear();
                horarioTareas.clear();
                tareasUsuario.clear();
                tareas.clear();
                listData.clear();


                GetDataLocal getDataLocal = new GetDataLocal();

                getDataLocal.tipoGetData = "horario";
                getDataLocal.hoy = "viernes";
                dia = "viernes";

                getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getHorario.php"});
            }
        });



        com.getbase.floatingactionbutton.FloatingActionButton juevesBtn = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.menuFlotanteJueves);

        juevesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diaTxt.setText("Jueves");
                horarios.clear();
                horarioTareas.clear();
                tareasUsuario.clear();
                tareas.clear();
                listData.clear();


                GetDataLocal getDataLocal = new GetDataLocal();

                getDataLocal.tipoGetData = "horario";
                getDataLocal.hoy = "jueves";
                dia = "jueves";

                getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getHorario.php"});
            }
        });



        com.getbase.floatingactionbutton.FloatingActionButton miercolesBtn = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.menuFlotanteMiercoles);

        miercolesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diaTxt.setText("Miercoles");
                horarios.clear();
                horarioTareas.clear();
                tareasUsuario.clear();
                tareas.clear();
                listData.clear();


                GetDataLocal getDataLocal = new GetDataLocal();

                getDataLocal.tipoGetData = "horario";
                getDataLocal.hoy = "miercoles";
                dia = "miercoles";

                getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getHorario.php"});
            }
        });

        com.getbase.floatingactionbutton.FloatingActionButton martesBtn = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.menuFlotanteMartes);

        martesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diaTxt.setText("Martes");
                horarios.clear();
                horarioTareas.clear();
                tareasUsuario.clear();
                tareas.clear();
                listData.clear();


                GetDataLocal getDataLocal = new GetDataLocal();

                getDataLocal.tipoGetData = "horario";
                getDataLocal.hoy = "martes";
                dia = "martes";

                getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getHorario.php"});
            }
        });

        com.getbase.floatingactionbutton.FloatingActionButton lunesBtn = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.menuFlotanteLunes);

        lunesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diaTxt.setText("Lunes");
                horarios.clear();
                horarioTareas.clear();
                tareasUsuario.clear();
                tareas.clear();
                listData.clear();


                GetDataLocal getDataLocal = new GetDataLocal();

                getDataLocal.tipoGetData = "horario";
                getDataLocal.hoy = "lunes";
                dia = "lunes";

                getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getHorario.php"});
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
        private ArrayList<Info> listData;
        public ListAdapter(ArrayList<Info> listData){
            this.listData= (ArrayList<Info>) listData.clone();
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
            ViewHolder viewHolder = new ViewHolder();
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
                viewHolder = (ViewHolder) convertView.getTag();
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


                        iniciarHorarioTareas();


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

                        iniciarTareas();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // dialog.dismiss();
        }

        public void iniciarHorarioTareas(){
            GetDataLocal getDataLocal = new GetDataLocal();
            getDataLocal.tipoGetData = "horarioTareas";
            getDataLocal.hoy = dia;

            getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getHorarioTarea.php"});

        }

        public void iniciarTareas(){
            GetDataLocal getDataLocal = new GetDataLocal();
            getDataLocal.tipoGetData = "tarea";
            getDataLocal.hoy = dia;

            getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getTarea.php"});
        }

        public void rellenarDatos(ArrayList<Tarea> tareas){


            for (Tarea tarea : tareas) {
                if(tarea.getTipo().equalsIgnoreCase("horario")){
                    ActivityHorario.Info info = new ActivityHorario.Info();

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


            listAdapter = new ActivityHorario.ListAdapter(listData);

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
