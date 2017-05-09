package com.herce.examenfinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.herce.examenfinal.Servicios.CONTACTOS_API;
import static com.herce.examenfinal.Servicios.EMPRESAS_API;

public class AddContactoActivity extends AppCompatActivity {

    EditText nombreTxt, telefonoTxt, imagenTxt, latitudTxt, longitudTxt, idempresaTxt;
    Button crearBtn;
    ProgressDialog progress_bar;
    String id;

    Bundle myIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacto);

        // Enseña progress bar
        progress_bar = new ProgressDialog(AddContactoActivity.this);

        // Obtiene text de los fields
        nombreTxt = (EditText)findViewById(R.id.nombreContactoField);
        telefonoTxt = (EditText)findViewById(R.id.telefonoContactoField);
        imagenTxt = (EditText)findViewById(R.id.imagenField);
        latitudTxt = (EditText)findViewById(R.id.latitudField);
        longitudTxt = (EditText)findViewById(R.id.longitudField);
        idempresaTxt = (EditText)findViewById(R.id.idcompanyField);

        crearBtn = (Button) findViewById(R.id.crearContactoButton);

        crearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearContacto();
            }
        });
        
    }

    void crearContacto() {
        // Peticion volley
        progress_bar.setMessage("Please wait...");
        progress_bar.setCancelable(false);
        progress_bar.show();
        StringRequest crearContactoReq = new StringRequest(Request.Method.POST, CONTACTOS_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress_bar.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if(res.getString("code").equals("01"))
                            {
                                Toast.makeText(AddContactoActivity.this, "Contacto creado" , Toast.LENGTH_SHORT).show();
                                Intent actividad = new Intent(AddContactoActivity.this, MainActivity.class);
                                startActivity(actividad);
                            } else if (res.getString("code").equals("02"))
                            {
                                Toast.makeText(AddContactoActivity.this, "Faltan valores obligatorios" , Toast.LENGTH_SHORT).show();
                            } else if (res.getString("code").equals("04"))
                            {
                                Toast.makeText(AddContactoActivity.this, "Error en query" , Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddContactoActivity.this, "Respuesta desconocida" , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(AddContactoActivity.this, "Error! " + e.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress_bar.cancel();
                        Toast.makeText(AddContactoActivity.this, "Error: " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("nombre",nombreTxt.getText().toString());
                params.put("telefono",telefonoTxt.getText().toString());
                params.put("imagen",imagenTxt.getText().toString());
                params.put("latitud",latitudTxt.getText().toString());
                params.put("longitud",longitudTxt.getText().toString());
                params.put("id_empresa",idempresaTxt.getText().toString());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(crearContactoReq);
    }
}
