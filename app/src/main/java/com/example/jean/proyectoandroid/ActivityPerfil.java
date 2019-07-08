package com.example.jean.proyectoandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jean.proyectoandroid.Modulo.Evento;
import com.example.jean.proyectoandroid.Modulo.Horario;
import com.example.jean.proyectoandroid.Modulo.HorarioTarea;
import com.example.jean.proyectoandroid.Modulo.Proyecto;
import com.example.jean.proyectoandroid.Modulo.Tarea;
import com.example.jean.proyectoandroid.Modulo.TareasEvento;
import com.example.jean.proyectoandroid.Modulo.TareasProyecto;
import com.example.jean.proyectoandroid.Modulo.Usuario;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ActivityPerfil extends AppCompatActivity {

    BottomNavigationView navigationView;

    ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

    private PieChart pieChart;
    private BarChart barChart;
    private String[] eventos = new String[]{"Horarios", "Eventos", "Proyectos"};
    private int[] numEventos = new int[]{45,25, 8};
    private int[] color = new int[]{Color.rgb(0, 238, 197), Color.rgb(30,138, 248), Color.rgb(224, 78, 204)};

    TextView nombreUsuario;

    TextView cantidadProyecto;

    TextView cantidadTareasAll;

    String idUsuario;

    int id;


    //todas las tareas
    ArrayList<Tarea> tareas = new ArrayList<Tarea>();
    //horarios
    ArrayList<Horario> horarios = new ArrayList<Horario>();
    ArrayList<HorarioTarea> horarioTareas = new ArrayList<HorarioTarea>();

    //proyectos
    ArrayList<Proyecto> proyectos = new ArrayList<Proyecto>();
    ArrayList<TareasProyecto> tareasProyectos = new ArrayList<TareasProyecto>();

    //eventos
    ArrayList<Evento> eventosM = new ArrayList<Evento>();
    ArrayList<TareasEvento> tareasEventos = new ArrayList<TareasEvento>();

    //tareas Dia y usuario
    ArrayList<Tarea> tareasUsuario = new ArrayList<Tarea>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Intent recibir = getIntent();
        idUsuario = recibir.getStringExtra("idUsuario");

        nombreUsuario = (TextView) findViewById(R.id.usuarioPerfil) ;

        cantidadProyecto = (TextView) findViewById(R.id.textView11);

        cantidadTareasAll = (TextView) findViewById(R.id.textView7);

        id = Integer.parseInt(idUsuario);

        Usuario usuario = new Usuario();

        GetDataLocal getData = new GetDataLocal();

        getData.accion = "usuario";

        getData.execute(new String[]{"http://10.0.2.2/webService/iniciarSesion.php"});

        Boolean usuarioValido = false;

        navigationView= (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        navigationView.getMenu().getItem(4).setChecked(true);


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.recienteMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityPerfil.this, ActivityPrincipal.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }else  if (item.getItemId() == R.id.horarioMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityPerfil.this, ActivityHorario.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }else  if (item.getItemId() == R.id.eventoMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityPerfil.this, ActivityEvento.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }else if (item.getItemId() == R.id.proyectoMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityPerfil.this, ActivityProyecto.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }else  if (item.getItemId() == R.id.perfilMenu) {
                    // on favorites clicked
                    Intent intent = new Intent(ActivityPerfil.this, ActivityPerfil.class);
                    intent.putExtra("idUsuario", idUsuario);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });


        ImageButton cerrarSesion = (ImageButton) findViewById(R.id.cerrarSesionBtn);

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityPerfil.this, MainActivity.class);
                startActivity(intent);
            }
        });


        //extraemos el drawable en un bitmap
        ImageView imageView = (ImageView) findViewById(R.id.avatar);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.avatarwomen);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);
        imageView.setImageDrawable(roundedBitmapDrawable);


        //graficos
        pieChart = (PieChart)findViewById(R.id.piechart);

        barChart = (BarChart) findViewById(R.id.barchart);







    }

    private Chart getSameChart(Chart chart, String descripcion, int colorText, int backgraound, int animateY){

        chart.getDescription().setText(descripcion);
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(backgraound);
        chart.animateY(animateY);

        return chart;

    }

    private void legend(Chart chart){
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);


        ArrayList<LegendEntry> entries = new ArrayList<>();

        for (int i = 0; i < eventos.length ; i++){
            LegendEntry entry = new LegendEntry();
            entry.formColor = color[i];
            entry.label = eventos[i];
            entries.add(entry);
        }

        legend.setCustom(entries);
    }


    private ArrayList<BarEntry> getBarEntries(){
        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < numEventos.length ; i++){
            entries.add(new BarEntry(i, numEventos[i]));
        }

        return entries;

    }

    private ArrayList<PieEntry> getPieEntries(int[] datosSet){
        ArrayList<PieEntry> entries = new ArrayList<>();

        int con = 1;

        for (int i = 0; i < datosSet.length ; i++){

            PieEntry pie = new PieEntry(datosSet[i], con);

            con++;

            entries.add(pie);
        }

        return entries;

    }

    private void axixX(XAxis axis){

        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(eventos));
        axis.setEnabled(false);

    }

    private void axisLeft(YAxis axis){
         axis.setSpaceTop(30);
         axis.setAxisMinimum(0);
    }

    private void axisRight(YAxis axis){
        axis.setEnabled(false);
    }

    public void createChart(int[] datosSet ){
        barChart=(BarChart)getSameChart(barChart, "Series", Color.RED, Color.WHITE, 3000);

        barChart.setDrawGridBackground(true);
        barChart.setDrawBarShadow(true);
        barChart.setData(getBarData());
        barChart.invalidate();
        axixX(barChart.getXAxis());
        axisLeft(barChart.getAxisLeft());
        axisRight(barChart.getAxisRight());


        pieChart = (PieChart)getSameChart(pieChart, "Tareas", Color.BLACK, Color.WHITE, 3000);
        pieChart.setHoleRadius(20);//radio del centro
        pieChart.setTransparentCircleRadius(12);
        pieChart.setData(getPieData(datosSet));
        pieChart.invalidate();

    }

    private DataSet getData(DataSet dataSet){
           dataSet.setColors(color);
           dataSet.setValueTextColor(Color.BLACK);
           dataSet.setValueTextSize(30);
           return dataSet;
    }

    private BarData getBarData(){
        BarDataSet barDataSet =(BarDataSet)getData(new BarDataSet(getBarEntries(), ""));
        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.45f);
        return barData;
    }



    private PieData getPieData(int[] datosSet){
        PieDataSet pieDataSet =(PieDataSet) getData(new PieDataSet(getPieEntries(datosSet), ""));
        pieDataSet.setSliceSpace(12);
        return new PieData(pieDataSet);
    }




    private class GetDataLocal extends AsyncTask<String, Void, Boolean> {

        // ProgressDialog dialog = new ProgressDialog(ActivityIniciarSesion.this);
        public String contenido = "";

        public String accion = "";

        protected void onPostExecute(Boolean result) {
            if (result) {
                JSONArray json = null;
                try {
                    if(accion.equals("usuario")){
                        json = new JSONArray(contenido);
                        for (int i = 0; i < json.length(); i++) {
                            JSONArray jsonData = json.getJSONArray(i);
                            Usuario usuario = new Usuario();
                            usuario.setIdUsuario(Integer.parseInt(jsonData.get(0).toString()));
                            usuario.setNombre(jsonData.get(1).toString());
                            usuario.setCorreo(jsonData.get(2).toString());
                            usuario.setContrasena(jsonData.get(3).toString());
                            usuario.setFoto(jsonData.get(4).toString());
                            usuarios.add(usuario);
                        }


                        for (Usuario usu : usuarios) {
                            if (usu.getIdUsuario() == id) {
                                usu.setContrasena("");
                                nombreUsuario.setText("@" + usu.getNombre());
                                break;
                            } else {
                            }
                        }

                        ejecutarCantidadProyectos();

                    }else if(accion.equals("proyecto")){

                        json = new JSONArray(contenido);

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

                        int cantidad = 0;
                        for (Proyecto proyecto : proyectos) {
                            if(proyecto.getIdUsuario() == Integer.parseInt(idUsuario)){
                                  cantidad++;
                            }
                        }

                        cantidadProyecto.setText(cantidad + "");

                        ejecutarEvento();



                    }else if(accion.equals("evento")){

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
                            eventosM.add(evento);
                        }

                        ejecutarHorario();

                    }else if(accion.equals("horario")){

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

                        ejecutarHorarioTareas();

                    }else if (accion.equals("horarioTareas")){

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


                    }else if(accion.equals("eventoTareas")){

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

                    }else if(accion.equals("proyectoTareas")){

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


                    }if(accion.equals("tarea")){

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


                        ArrayList<Horario> horariosSemana = new ArrayList<Horario>();

                        for (Horario horario : horarios ){
                            if(horario.getIdUsuario() == Integer.parseInt(idUsuario)){
                                    horariosSemana.add(horario);
                            }
                        }

                        ArrayList<HorarioTarea> horarioTareasP = new ArrayList<HorarioTarea>();

                        ArrayList<HorarioTarea> horarioTareasSemana = new ArrayList<HorarioTarea>();

                        for (HorarioTarea horarioTarea : horarioTareas) {

                            for (Horario horario : horariosSemana) {
                                if(horarioTarea.getIdHorario() == horario.getIdHorario()){
                                    HorarioTarea horarioTarea1 = new HorarioTarea();
                                    horarioTarea1.setIdHorarioTarea(horarioTarea.getIdHorarioTarea());
                                    horarioTarea1.setIdHorario(horarioTarea.getIdHorario());
                                    horarioTarea1.setIdTarea(horarioTarea.getIdTarea());
                                    horarioTareasP.add(horarioTarea1);
                                }
                            }
                        }

                        //tareasUsuario = new ArrayList<Tarea>();

                        int cantidadHorario = 0;


                        for (HorarioTarea horarioTarea : horarioTareasP) {
                            for (Tarea tarea : tareas) {
                                if(tarea.getIdTarea().equals(horarioTarea.getIdTarea())){
                                   cantidadHorario++;
                                }
                            }
                        }


                                //eventos

                        ArrayList<Evento> eventosUsu = new ArrayList<Evento>();

                        for (Evento evento : eventosM) {
                            if(Integer.parseInt(evento.getIdUsuario()) == Integer.parseInt(idUsuario)){
                                eventosUsu.add(evento);
                            }
                        }

                        int cantidadEventos = 0;
                        for (Evento evento : eventosUsu) {
                            for (TareasEvento tareasEvento : tareasEventos) {
                                if(evento.getId() == tareasEvento.getIdEvento()){
                                    for (Tarea tarea : tareas) {
                                        if(tarea.getIdTarea().equals(tareasEvento.getIdTarea())){
                                           cantidadEventos ++;
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

                        int cantidadProyectos = 0;
                        for (Proyecto proyecto : proyectosUsu) {
                            for (TareasProyecto tareasProyecto : tareasProyectos) {
                                if(proyecto.getIdProyecto() == tareasProyecto.getIdProyecto()){
                                    for (Tarea tarea : tareas) {
                                        if(tarea.getIdTarea().equals(tareasProyecto.getIdTarea())){
                                            cantidadProyectos++;
                                        }
                                    }
                                }
                            }
                        }

                        //rellenarDatos(tareasUsuario);

                        int total = cantidadHorario + cantidadEventos + cantidadProyectos;

                        cantidadTareasAll.setText(total + "");

                        int[] datosSet = new int[]{cantidadHorario,cantidadEventos, cantidadProyectos};
                        createChart(datosSet);


                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            // dialog.dismiss();
        }

        public void ejecutarCantidadProyectos(){

            GetDataLocal getData = new GetDataLocal();

            getData.accion = "proyecto";

            getData.execute(new String[]{"http://10.0.2.2/webService/getProyecto.php"});

        }

        public void ejecutarEvento(){

            GetDataLocal getData = new GetDataLocal();

            getData.accion = "evento";

            getData.execute(new String[]{"http://10.0.2.2/webService/getEvento.php"});


        }

        public void ejecutarHorario(){

            GetDataLocal getData = new GetDataLocal();

            getData.accion = "horario";

            getData.execute(new String[]{"http://10.0.2.2/webService/getHorario.php"});

        }

        public void ejecutarEventoTarea(){

            GetDataLocal getData = new GetDataLocal();

            getData.accion = "eventoTareas";

            getData.execute(new String[]{"http://10.0.2.2/webService/getTareaEvento.php"});

        }


        public void ejecutarProyectoTarea(){

            GetDataLocal getData = new GetDataLocal();

            getData.accion = "proyectoTareas";

            getData.execute(new String[]{"http://10.0.2.2/webService/getTareaProyecto.php"});
        }

        public void ejecutarTarea(){
            GetDataLocal getData = new GetDataLocal();

            getData.accion = "tarea";

            getData.execute(new String[]{"http://10.0.2.2/webService/getTarea.php"});
        }

        public void ejecutarHorarioTareas(){
            GetDataLocal getData = new GetDataLocal();

            getData.accion = "horarioTareas";

            getData.execute(new String[]{"http://10.0.2.2/webService/getHorarioTarea.php"});
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
