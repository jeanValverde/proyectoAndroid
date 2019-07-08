package com.example.jean.proyectoandroid.Modulo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SetData extends AsyncTask<String, Void, Boolean>
{
   // ProgressDialog dialog = new ProgressDialog(Main2Activity.this);

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public String parametros;



    protected void onPreExecute()
    {
        super.onPreExecute();
        //dialog.setMessage("Almacenando datos...");
        //dialog.show();
    }
    protected void onPostExecute(Boolean result)
    {
        super.onPostExecute(result);
        if(result)
        {

        }else{

        }
        //dialog.dismiss();
    }
    protected Boolean doInBackground(String... urls) {
        InputStream inputStream = null;

        for(String url1 : urls) {
            try {
                URL url = new URL(url1);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milisegundos */);
                conn.setConnectTimeout(15000 /* milisegundos */);
                conn.setRequestMethod("GET");
                // Si se requiere enviar datos a la página se coloca
                // setDoOutput(true) este ejemplo tiene ambos por cuestión de
                // depuración. Si analizas el código setData.php, observarás que
                // se regresa -1 en caso que no se envíen todos los campos.
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream out = conn.getOutputStream();
                Log.d("PARAMS", getParametros());
                out.write(getParametros().getBytes());
                out.flush();
                out.close();
                // Recupera la página
                conn.connect();
                int response = conn.getResponseCode();
                Log.d("SERVIDOR", "La respuesta del servidor es: " + response);
                inputStream = conn.getInputStream();
                // Convertir inputstream a string
                String contenido = new Scanner(inputStream).useDelimiter("\\A").next();
                Log.i("CONTENIDO",contenido);
            }
            catch (Exception ex) {
                Log.e("ERROR", ex.toString());
                return false; }
        } return true;
    }
}