package com.example.jean.proyectoandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.jean.proyectoandroid.Modulo.Controller;
import com.example.jean.proyectoandroid.Modulo.Evento;
import com.example.jean.proyectoandroid.Modulo.SetData;

import java.util.Date;

public class ActivityCrearEventoFinal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento_final);


        Intent recibir = getIntent();
        final String idUsuario = recibir.getStringExtra("idUsuario");
        final String fecha = recibir.getStringExtra("fecha");
        final String tipoEvento = recibir.getStringExtra("tipo");


        final EditText nombre = (EditText) findViewById(R.id.nombreEvento);
        final EditText descripcionEvento = (EditText) findViewById(R.id.descripcionEvento);

        ImageButton crearEvento = (ImageButton) findViewById(R.id.crearEventoFinal);

        ImageButton cancelar = (ImageButton) findViewById(R.id.cancelarEventoFinal);

        ImageButton atras = (ImageButton) findViewById(R.id.atrasEventoFinal);



        crearEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Evento evento = new Evento();

                evento.setNombre(nombre.getText().toString());
                evento.setDescripcion(descripcionEvento.getText().toString());
                evento.setTipoEvento(tipoEvento);
                evento.setFecha(fecha);
                evento.setIdUsuario(idUsuario);


                Controller controller = new Controller();

                String parametros = controller.registrarEvento(evento);


                SetData setData = new SetData();

                setData.setParametros(parametros);

                setData.execute(new String[]{"http://10.0.2.2/webService/crearEvento.php"});

                Intent intent = new Intent(ActivityCrearEventoFinal.this, ActivityEvento.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);

            }
        });



        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityCrearEventoFinal.this, ActivityEvento.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityCrearEventoFinal.this, ActivityCrearEvento.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
        });



    }
}
