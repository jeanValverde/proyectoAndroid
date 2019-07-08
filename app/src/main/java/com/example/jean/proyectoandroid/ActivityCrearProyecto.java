package com.example.jean.proyectoandroid;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;

import com.example.jean.proyectoandroid.Modulo.Controller;
import com.example.jean.proyectoandroid.Modulo.Proyecto;
import com.example.jean.proyectoandroid.Modulo.SetData;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class ActivityCrearProyecto extends AppCompatActivity {

    String fecha = "" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_proyecto);


        Intent recibir = getIntent();
        final String idUsuario = recibir.getStringExtra("idUsuario");
        final String nombreProyecto = recibir.getStringExtra("nombreProyecto");
        final String descripcionProyecto = recibir.getStringExtra("descripcionProyecto");

        CalendarView calendario = (CalendarView)findViewById(R.id.calendarioProyecto);

        fecha = "";//yyyy/mm/dd


        ImageButton crearProyecto = (ImageButton) findViewById(R.id.crearProyectoFin);
        ImageButton atras = (ImageButton) findViewById(R.id.atrasProyectoFin);
        ImageButton cancelar = (ImageButton) findViewById(R.id.cancelarProyectoFin);


        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                fecha = year+"-"+month+"-"+dayOfMonth;
            }
        });


        crearProyecto.setOnClickListener(new View.OnClickListener() {
            //@RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                Date date = new Date();


                Proyecto proyecto = new Proyecto();

                proyecto.setIdUsuario(Integer.parseInt(idUsuario));
                proyecto.setNombre(nombreProyecto);
                proyecto.setDescripcion(descripcionProyecto);
                proyecto.setFechaInicio(new SimpleDateFormat("yyyy-MM-dd").format(date));
                proyecto.setFechaTermino(fecha);
                proyecto.setFoto("none.jpg");


                Controller controller = new Controller();

                String parametros = controller.registrarProyecto(proyecto);

                SetData setData = new SetData();

                setData.setParametros(parametros);

                setData.execute(new String[]{"http://10.0.2.2/webService/crearProyecto.php"});

                Intent intent = new Intent(ActivityCrearProyecto.this, ActivityProyecto.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);


            }
        });


        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityCrearProyecto.this, ActivityCrearProyectoFinal.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
        });


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityCrearProyecto.this, ActivityProyecto.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
        });











    }


}
