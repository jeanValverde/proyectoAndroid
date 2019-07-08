package com.example.jean.proyectoandroid;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.CpuUsageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.jean.proyectoandroid.Modulo.Usuario;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ActivityIniciarSesion extends AppCompatActivity {


    ArrayList<Usuario> usuarios = new ArrayList<Usuario>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        Button iniciar = (Button) findViewById(R.id.iniciar);

        final EditText correo = (EditText) findViewById(R.id.correo);
        final EditText contrasena = (EditText) findViewById(R.id.contrasena);

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(correo.getText().toString().equals("") || contrasena.getText().equals("")){
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Correo y contraseña obligatoio", Toast.LENGTH_SHORT);
                    toast1.show();
                }else {
                    Usuario usuario = new Usuario();

                    GetDataLocal getData = new GetDataLocal();

                    getData.correo = correo.getText().toString();
                    getData.contrasena = contrasena.getText().toString();

                    getData.execute(new String[]{"http://10.0.2.2/webService/iniciarSesion.php"});


                }

                }
        });

    }



    private class GetDataLocal extends AsyncTask<String, Void, Boolean> {

        // ProgressDialog dialog = new ProgressDialog(ActivityIniciarSesion.this);
        public String contenido = "";

        public String correo;

        public String contrasena;

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

                   iniciarSesion(usuarios, correo, contrasena);

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

        public void iniciarSesion(ArrayList<Usuario> usuarios, String correo , String contrasena){

            Usuario usuario = new Usuario();

            boolean usuarioValido = false;

            for (Usuario usu : usuarios) {
                if (usu.getCorreo().equals(correo) && usu.getContrasena().equals(contrasena)) {
                    usuarioValido  = true;
                    usuario = usu;
                    break;
                } else {
                    usuarioValido  = false;
                }
            }

            if(usuarioValido){
                Intent intent = new Intent(ActivityIniciarSesion.this, ActivityPrincipal.class);
                intent.putExtra("idUsuario", String.valueOf(usuario.getIdUsuario()));
                startActivity(intent);
            }else{
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Usuario o clave erronea", Toast.LENGTH_SHORT);
                toast1.show();
            }



        }


    }



}
