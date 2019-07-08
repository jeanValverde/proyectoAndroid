package com.example.jean.proyectoandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.jean.proyectoandroid.Modulo.Controller;
import com.example.jean.proyectoandroid.Modulo.ListaTarea;
import com.example.jean.proyectoandroid.Modulo.SetData;

public class ActivityCrearListaTarea extends AppCompatActivity {

    String idUsuario;
    String idTarea;
    String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_lista_tarea);

        final Intent recibir = getIntent();
        idUsuario = recibir.getStringExtra("idUsuario");
        idTarea = recibir.getStringExtra("idTarea");
        tipo = recibir.getStringExtra("tipo");

        ImageButton atras = (ImageButton) findViewById(R.id.atrasListaTarea);
        ImageButton crear = (ImageButton) findViewById(R.id.crearListaTarea);
        ImageButton cancelar = (ImageButton) findViewById(R.id.cancelarListaTarea);

        final EditText descripcion = (EditText) findViewById(R.id.descripcionListaTarea);



        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListaTarea listaTarea = new ListaTarea();

                listaTarea.setDescripcion(descripcion.getText().toString());
                listaTarea.setEstado("activo");
                listaTarea.setIdTarea(idTarea);

                SetData setData = new SetData();

                Controller controller = new Controller();

                String parametros = controller.registrarListaTarea(listaTarea);

                setData.setParametros(parametros);

                setData.execute(new String[]{"http://10.0.2.2/webService/crearListaTarea.php"});



                if(tipo.equals("evento")){
                    String idEvento = recibir.getStringExtra("idEvento");
                    Intent intent = new Intent(ActivityCrearListaTarea.this, ActivityTareaInto.class);
                    intent.putExtra("idUsuario", idUsuario);
                    intent.putExtra("idTarea", idTarea);
                    intent.putExtra("idEvento", idEvento);
                    startActivity(intent);
                }else if(tipo.equals("horario")){
                    Intent intent = new Intent(ActivityCrearListaTarea.this, activityHorarioInto.class);
                    intent.putExtra("idUsuario", idUsuario);
                    intent.putExtra("idTarea", idTarea);
                    startActivity(intent);
                }else if(tipo.equals("proyecto")){
                    String idProyecto = recibir.getStringExtra("idProyecto");
                    Intent intent = new Intent(ActivityCrearListaTarea.this, activityHorarioInto.class);
                    intent.putExtra("idUsuario", idUsuario);
                    intent.putExtra("idTarea", idTarea);
                    intent.putExtra("idProyecto", idProyecto);
                    startActivity(intent);
                }

            }
        });


        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tipo.equals("evento")){
                    String idEvento = recibir.getStringExtra("idEvento");
                    Intent intent = new Intent(ActivityCrearListaTarea.this, ActivityTareaInto.class);
                    intent.putExtra("idUsuario", idUsuario);
                    intent.putExtra("idTarea", idTarea);
                    intent.putExtra("idEvento", idEvento);
                    startActivity(intent);
                }else if(tipo.equals("horario")){
                    Intent intent = new Intent(ActivityCrearListaTarea.this, activityHorarioInto.class);
                    intent.putExtra("idUsuario", idUsuario);
                    intent.putExtra("idTarea", idTarea);
                    startActivity(intent);
                }else if(tipo.equals("proyecto")){
                    String idProyecto = recibir.getStringExtra("idProyecto");
                    Intent intent = new Intent(ActivityCrearListaTarea.this, activityHorarioInto.class);
                    intent.putExtra("idUsuario", idUsuario);
                    intent.putExtra("idTarea", idTarea);
                    intent.putExtra("idProyecto", idProyecto);
                    startActivity(intent);
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tipo.equals("evento")){
                    String idEvento = recibir.getStringExtra("idEvento");
                    Intent intent = new Intent(ActivityCrearListaTarea.this, ActivityTareaInto.class);
                    intent.putExtra("idUsuario", idUsuario);
                    intent.putExtra("idTarea", idTarea);
                    intent.putExtra("idEvento", idEvento);
                    startActivity(intent);
                }else if(tipo.equals("horario")){
                    Intent intent = new Intent(ActivityCrearListaTarea.this, activityHorarioInto.class);
                    intent.putExtra("idUsuario", idUsuario);
                    intent.putExtra("idTarea", idTarea);
                    startActivity(intent);
                }else if(tipo.equals("proyecto")){
                    String idProyecto = recibir.getStringExtra("idProyecto");
                    Intent intent = new Intent(ActivityCrearListaTarea.this, activityHorarioInto.class);
                    intent.putExtra("idUsuario", idUsuario);
                    intent.putExtra("idTarea", idTarea);
                    intent.putExtra("idProyecto", idProyecto);
                    startActivity(intent);
                }
            }
        });







    }
}
