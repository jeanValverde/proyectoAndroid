package com.example.jean.proyectoandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class ActivityCrearProyectoFinal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_proyecto_final);
        Intent recibir = getIntent();
        final String idUsuario = recibir.getStringExtra("idUsuario");


        final EditText nombreProyecto = (EditText) findViewById(R.id.nombreProyectoTex);
        final EditText descripcionProyecto = (EditText) findViewById(R.id.descripcionProyectoText);


        ImageButton crearProyecto = (ImageButton) findViewById(R.id.siguienteProyecto);
        ImageButton atras = (ImageButton) findViewById(R.id.atrasProyecto);
        ImageButton cancelar = (ImageButton) findViewById(R.id.cancelarProyecto);




        crearProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ActivityCrearProyectoFinal.this, ActivityCrearProyecto.class);
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("nombreProyecto", nombreProyecto.getText().toString());
                intent.putExtra("descripcionProyecto", descripcionProyecto.getText().toString());
                startActivity(intent);
            }
        });


        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityCrearProyectoFinal.this, ActivityProyecto.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityCrearProyectoFinal.this, ActivityProyecto.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
        });




    }
}
