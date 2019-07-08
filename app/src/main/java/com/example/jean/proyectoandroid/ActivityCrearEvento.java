package com.example.jean.proyectoandroid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;

public class ActivityCrearEvento extends AppCompatActivity {


    String fecha ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);

        Intent recibir = getIntent();
        final String idUsuario = recibir.getStringExtra("idUsuario");


        ImageButton siguenteEvento = (ImageButton) findViewById(R.id.siguienteEvento);
        ImageButton cancelar = (ImageButton) findViewById(R.id.cancelarEvento);
        ImageButton atras = (ImageButton) findViewById(R.id.atrasEvento);


        CalendarView calendario = (CalendarView)findViewById(R.id.calendarioEvento);

        fecha = "";//yyyy/mm/dd

        final Spinner tipoEvento = (Spinner) findViewById(R.id.tipoEvento);


        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

               fecha = year+"-"+month+"-"+dayOfMonth;
            }
        });

        siguenteEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tipoSeteo = tipoEvento.getSelectedItem().toString();

                if(tipoEvento.getSelectedItem().toString().equals("Cumpleaños")){
                    tipoSeteo = "Cumpleanos";
                }

                Intent intent = new Intent(ActivityCrearEvento.this, ActivityCrearEventoFinal.class);
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("fecha", fecha);
                intent.putExtra("tipo", tipoSeteo);
                startActivity(intent);
            }
        });


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityCrearEvento.this, ActivityEvento.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityCrearEvento.this, ActivityEvento.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
        });

    }


}
