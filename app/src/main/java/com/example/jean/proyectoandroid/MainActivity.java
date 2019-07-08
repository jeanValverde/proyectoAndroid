package com.example.jean.proyectoandroid;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.jean.proyectoandroid.Modulo.Controller;
import com.example.jean.proyectoandroid.Modulo.Horario;
import com.example.jean.proyectoandroid.Modulo.SetData;
import com.example.jean.proyectoandroid.Modulo.Usuario;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    EditText correo;

    ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button iniciarSesion = (Button) findViewById(R.id.iniciarSesion);
        Button registrar = (Button) findViewById(R.id.registrar);

        final EditText nombre = (EditText) findViewById(R.id.newNombre);
        correo = (EditText) findViewById(R.id.newCorreo);
        final EditText contrasena = (EditText) findViewById(R.id.newContrasena);

        final CheckBox politica = (CheckBox) findViewById(R.id.politica);


        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityIniciarSesion.class);
                startActivity(intent);
            }
        });


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(politica.isChecked()){

                    if(!nombre.getText().toString().equals("") && !correo.getText().toString().equals("") && !contrasena.getText().toString().equals("") ){

                        Usuario usuarioNuevo = new Usuario();

                        usuarioNuevo.setNombre(nombre.getText().toString());
                        usuarioNuevo.setCorreo(correo.getText().toString());
                        usuarioNuevo.setContrasena(contrasena.getText().toString());
                        usuarioNuevo.setFoto("none.jpg");

                        Controller controller = new Controller();

                        String parametro = controller.registrarUsuario(usuarioNuevo);

                        SetData setData = new SetData();

                        setData.setParametros(parametro);

                        setData.execute(new String[]{"http://10.0.2.2/webService/registrarUsuario.php"});

                        GetDataLocal getData = new GetDataLocal();

                        getData.execute(new String[]{"http://10.0.2.2/webService/iniciarSesion.php"});



                    }
                }
            }
        });


    }


    private class GetDataLocal extends AsyncTask<String, Void, Boolean> {

        // ProgressDialog dialog = new ProgressDialog(ActivityIniciarSesion.this);
        public String contenido = "";

        Usuario usuario = null;

        protected void onPostExecute(Boolean result) {

            if (result) {
                JSONArray json = null;
                try {
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

                    String correoUsuario = correo.getText().toString();

                    for (Usuario usu : usuarios) {
                        if(correoUsuario.equals(usu.getCorreo())){
                            usuario = usu;
                        }
                    }

                    if(usuario != null){

                        cargarUsuarioNuevo(usuario.getIdUsuario());


                    }



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


    public void cargarUsuarioNuevo(int IdUsuario){
        Controller controller = new Controller();

        for (Horario horario : controller.crearHorariosSemanal(IdUsuario)) {
            SetData setData = new SetData();

            String parametro = controller.registrarHorario(horario);

            setData.setParametros(parametro);

            setData.execute(new String[]{"http://10.0.2.2/webService/crearHorario.php"});
        }

        Intent intent = new Intent(MainActivity.this, ActivityPrincipal.class);
        intent.putExtra("idUsuario", String.valueOf(IdUsuario));
        startActivity(intent);

    }
}
