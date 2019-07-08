package com.example.jean.proyectoandroid;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jean.proyectoandroid.Modulo.Controller;
import com.example.jean.proyectoandroid.Modulo.Horario;
import com.example.jean.proyectoandroid.Modulo.SetData;
import com.example.jean.proyectoandroid.Modulo.Tarea;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class ActivityCrearHoaraio extends AppCompatActivity {


    String idTarea = "";
    String diaSelect = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_hoaraio);

        Intent recibir = getIntent();
        final String idUsuario = recibir.getStringExtra("idUsuario");

        ImageButton atrasBtn = (ImageButton) findViewById(R.id.atrasHorario);
        ImageButton cancelarHorarioBtn = (ImageButton) findViewById(R.id.cancelarHorario);
        ImageButton crearHorarioBtn = (ImageButton) findViewById(R.id.crearHorario);

        atrasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityCrearHoaraio.this, ActivityHorario.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
        });

        cancelarHorarioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityCrearHoaraio.this, ActivityHorario.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
        });


        final Tarea tarea = new Tarea();

        //crear horario input

        final String tipoTarea = "horario";

        crearHorarioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText curso = (EditText) findViewById(R.id.cursoHorario);
                EditText inicio = (EditText) findViewById(R.id.inicioHorario);
                EditText fin = (EditText) findViewById(R.id.finHorario);
                EditText monitor = (EditText) findViewById(R.id.monitorHorario);
                EditText seccion = (EditText) findViewById(R.id.sessionHorario);
                EditText sala = (EditText) findViewById(R.id.salaHorario);
                EditText descripcion = (EditText) findViewById(R.id.descripcionTareaView);
                Spinner dia = (Spinner) findViewById(R.id.diasHorario);

                UUID uuid = UUID.randomUUID();

                tarea.setEstado("Iniciado");
                tarea.setIdTarea(uuid.toString());
                tarea.setTipo(tipoTarea);
                tarea.setNombre(curso.getText().toString());
                tarea.setSala(sala.getText().toString());
                tarea.setDescripcion(descripcion.getText().toString());
                tarea.setSeccion(seccion.getText().toString());
                tarea.setMonitor(monitor.getText().toString());
                tarea.setHoraInicio(inicio.getText().toString());
                tarea.setHoraFin(fin.getText().toString());
                tarea.setSubTipo(dia.getSelectedItem().toString());

                diaSelect = dia.getSelectedItem().toString();

                Controller controller = new Controller();

                SetData setData = new SetData();

                setData.setParametros(controller.registrarTarea(tarea));

                setData.execute(new String[]{"http://10.0.2.2/webService/crearTarea.php"});

                GetDataLocal getDataLocal = new GetDataLocal();

                getDataLocal.idUsuario = idUsuario;
                getDataLocal.idTarea = tarea.getIdTarea();

                getDataLocal.execute(new String[]{"http://10.0.2.2/webService/getHorario.php"});


            }
        });


    }



    private class GetDataLocal extends AsyncTask<String, Void, Boolean> {

        // ProgressDialog dialog = new ProgressDialog(ActivityIniciarSesion.this);
        public String contenido = "";

        //Usuario usuario = null;

        public String idUsuario;

        public String idTarea;

        protected void onPostExecute(Boolean result) {

            if (result) {
                JSONArray json = null;
                try {
                    ArrayList<Horario> horarios = new ArrayList<Horario>();
                    json = new JSONArray(contenido);
                    for (int i = 0; i < json.length(); i++) {
                        JSONArray jsonData = json.getJSONArray(i);
                        Horario horario = new Horario();
                        horario.setIdHorario(Integer.parseInt(jsonData.get(0).toString()));
                        horario.setDia(jsonData.get(1).toString());
                        horario.setIdUsuario(Integer.parseInt(jsonData.get(2).toString()));
                        horarios.add(horario);
                    }


                    Horario horarioSelect = new Horario();

                    for (Horario horario : horarios) {
                        if(horario.getIdUsuario() == Integer.parseInt(idUsuario)){
                            if(horario.getDia().equalsIgnoreCase(diaSelect)){
                                horarioSelect = horario;
                                break;
                            }
                        }
                    }

                    registrarTareaHorario(horarioSelect, idTarea, idUsuario);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // dialog.dismiss();
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



    public void registrarTareaHorario(Horario horario, String id, String idUsuario){

        SetData setData = new SetData();

        Controller controller = new Controller();

        String parametros = controller.registrarTareaHorario(horario.getIdHorario(), id);

        setData.setParametros(parametros);

        setData.execute(new String[]{"http://10.0.2.2/webService/crearHorarioTarea.php"});

        Toast toast1 =
                Toast.makeText(getApplicationContext(),
                        "Tarea creada con exito!", Toast.LENGTH_LONG);

        Intent intent = new Intent(ActivityCrearHoaraio.this, ActivityHorario.class);
        intent.putExtra("idUsuario", idUsuario);
        startActivity(intent);


    }


}
