package com.example.jean.proyectoandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.jean.proyectoandroid.Modulo.Controller;
import com.example.jean.proyectoandroid.Modulo.Proyecto;
import com.example.jean.proyectoandroid.Modulo.SetData;
import com.example.jean.proyectoandroid.Modulo.Tarea;
import com.example.jean.proyectoandroid.Modulo.TareasEvento;
import com.example.jean.proyectoandroid.Modulo.TareasProyecto;

import java.util.Calendar;
import java.util.UUID;

public class ActivityCrearTarea extends AppCompatActivity {

    String idUsuario = "";
    String idEvento = "";
    String tipo = "";
    String tipoTarea = "";
    String idProyecto = "";

    EditText nombre;
    EditText inicio;
    EditText fin;
    EditText ubicacion;
    EditText descripcion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);

        Intent recibir = getIntent();
        tipo = recibir.getStringExtra("tipo");
        idUsuario = recibir.getStringExtra("idUsuario");

        if(tipo.equals("evento")){
            idEvento = recibir.getStringExtra("idEvento");
        }else if(tipo.equals("proyecto")){
            idProyecto = recibir.getStringExtra("idProyecto");
        }



        nombre = (EditText) findViewById(R.id.nombreTareaCrear);
        inicio = (EditText) findViewById(R.id.inicioTareaCrear);
        fin = (EditText) findViewById(R.id.finTareaCrear);
        ubicacion = (EditText) findViewById(R.id.ubicacionTareaCrear);
        descripcion = (EditText) findViewById(R.id.descripcionTareaCrear);

        ImageButton crear = (ImageButton) findViewById(R.id.crearTareaCrear);
        ImageButton atras = (ImageButton) findViewById(R.id.atrasTareaCrear);
        ImageButton cancelar = (ImageButton) findViewById(R.id.cancelarTareaCrear);



        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                String dia =  strDays[now.get(Calendar.DAY_OF_WEEK) - 1];



                    Tarea tarea = new Tarea();


                    UUID uuid = UUID.randomUUID();

                    tarea.setEstado("Iniciado");
                    tarea.setIdTarea(uuid.toString());
                    tarea.setNombre(nombre.getText().toString());
                    tarea.setSala(ubicacion.getText().toString());
                    tarea.setDescripcion(descripcion.getText().toString());
                    tarea.setSeccion("No aplica");
                    tarea.setMonitor("No aplica");
                    tarea.setHoraInicio(inicio.getText().toString());
                    tarea.setHoraFin(fin.getText().toString());
                    tarea.setSubTipo(dia);

                if(tipo.equals("evento")){
                    tipoTarea = "evento";
                    tarea.setTipo(tipoTarea);

                    Controller controller = new Controller();

                    SetData setData = new SetData();

                    setData.setParametros(controller.registrarTarea(tarea));

                    setData.execute(new String[]{"http://10.0.2.2/webService/crearTarea.php"});

                    crearTareaEvento(tarea);

                } else if (tipo.equals("proyecto")) {

                    tipoTarea = "proyecto";
                    tarea.setTipo(tipoTarea);

                    Controller controller = new Controller();

                    SetData setData = new SetData();

                    setData.setParametros(controller.registrarTarea(tarea));

                    setData.execute(new String[]{"http://10.0.2.2/webService/crearTarea.php"});

                    crearTareaProyecto(tarea);


                }


            }
        });



        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tipo.equals("evento")){
                    Intent intent = new Intent(ActivityCrearTarea.this, ActivityEventoInto.class);
                    intent.putExtra("idUsuario", idUsuario);
                    intent.putExtra("idEvento", idEvento);
                    startActivity(intent);
                }else if(tipo.equals("proyecto")){

                    Intent intent = new Intent(ActivityCrearTarea.this, ActivityProyectoInto.class);
                    intent.putExtra("idUsuario", idUsuario);
                    intent.putExtra("idProyecto", idProyecto);
                    startActivity(intent);

                }


            }
        });


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tipo.equals("evento")){
                    Intent intent = new Intent(ActivityCrearTarea.this, ActivityEventoInto.class);
                    intent.putExtra("idUsuario", idUsuario);
                    intent.putExtra("idEvento", idEvento);
                    startActivity(intent);
                }else if(tipo.equals("proyecto")){
                    Intent intent = new Intent(ActivityCrearTarea.this, ActivityProyectoInto.class);
                    intent.putExtra("idUsuario", idUsuario);
                    intent.putExtra("idProyecto", idProyecto);
                    startActivity(intent);
                }

            }
        });




    }

    public void crearTareaEvento(Tarea tarea){

        TareasEvento tareasEvento = new TareasEvento();

        tareasEvento.setIdTarea(tarea.getIdTarea());
        tareasEvento.setIdEvento(Integer.parseInt(idEvento));

        Controller controller = new Controller();

        SetData setData = new SetData();

        setData.setParametros(controller.registrarTareaEvento(tareasEvento));

        setData.execute(new String[]{"http://10.0.2.2/webService/crearTareaEvento.php"});


        Intent intent = new Intent(ActivityCrearTarea.this, ActivityEventoInto.class);
        intent.putExtra("idUsuario", idUsuario);
        intent.putExtra("idEvento", idEvento);
        startActivity(intent);

    }


    public void crearTareaProyecto(Tarea tarea){

        TareasProyecto tareasProyecto = new TareasProyecto();

        tareasProyecto.setIdProyecto(Integer.parseInt(idProyecto));
        tareasProyecto.setIdTarea(tarea.getIdTarea());

        Controller controller = new Controller();

        SetData setData = new SetData();

        setData.setParametros(controller.registrarTareaProyecto(tareasProyecto));

        setData.execute(new String[]{"http://10.0.2.2/webService/crearTareaProyecto.php"});


        Intent intent = new Intent(ActivityCrearTarea.this, ActivityProyectoInto.class);
        intent.putExtra("idUsuario", idUsuario);
        intent.putExtra("idProyecto", idProyecto);
        startActivity(intent);

    }
}
