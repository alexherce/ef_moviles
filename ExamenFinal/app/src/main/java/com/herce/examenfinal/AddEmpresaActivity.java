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

import static com.herce.examenfinal.Servicios.EMPRESAS_API;

public class AddEmpresaActivity extends AppCompatActivity {

    EditText nombreTxt, sloganTxt, oficinaTxt, telefonoTxt, faxTxt, calleTxt, ciudadTxt, estadoTxt, codigopostalTxt;
    Button crearBtn;
    ProgressDialog progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_empresa);

        // Enseña progress bar
        progress_bar = new ProgressDialog(AddEmpresaActivity.this);


        // Obtiene text de los fields
        nombreTxt = (EditText)findViewById(R.id.nombreEmpresaField);
        sloganTxt = (EditText)findViewById(R.id.sloganField);
        oficinaTxt = (EditText)findViewById(R.id.oficinaField);
        telefonoTxt = (EditText)findViewById(R.id.telefonoField);
        faxTxt = (EditText)findViewById(R.id.faxField);
        calleTxt = (EditText)findViewById(R.id.calleField);
        ciudadTxt = (EditText)findViewById(R.id.ciudadField);
        estadoTxt = (EditText)findViewById(R.id.estadoField);
        codigopostalTxt = (EditText)findViewById(R.id.codigopostalField);

        crearBtn = (Button) findViewById(R.id.crearButton);

        crearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearEmpresa();
            }
        });

    }

    void crearEmpresa() {
        // Peticion volley
        progress_bar.setMessage("Please wait...");
        progress_bar.setCancelable(false);
        progress_bar.show();

        StringRequest crearEmpresaReq = new StringRequest(Request.Method.POST, EMPRESAS_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress_bar.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if(res.getString("code").equals("01"))
                            {
                                Toast.makeText(AddEmpresaActivity.this, "Empresa creada" , Toast.LENGTH_SHORT).show();
                                Intent actividad = new Intent(AddEmpresaActivity.this, MainActivity.class);
                                startActivity(actividad);
                            } else if (res.getString("code").equals("02"))
                            {
                                Toast.makeText(AddEmpresaActivity.this, "Faltan valores obligatorios" , Toast.LENGTH_SHORT).show();
                            } else if (res.getString("code").equals("04"))
                            {
                                Toast.makeText(AddEmpresaActivity.this, "Error en query" , Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddEmpresaActivity.this, "Respuesta desconocida" , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(AddEmpresaActivity.this, "Error! " + e.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress_bar.cancel();
                        Toast.makeText(AddEmpresaActivity.this, "Error: " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("nombre",nombreTxt.getText().toString());
                params.put("slogan",sloganTxt.getText().toString());
                params.put("oficina",oficinaTxt.getText().toString());
                params.put("telefono",telefonoTxt.getText().toString());
                params.put("fax",faxTxt.getText().toString());
                params.put("calle",calleTxt.getText().toString());
                params.put("ciudad",ciudadTxt.getText().toString());
                params.put("estado",estadoTxt.getText().toString());
                params.put("codigopostal",codigopostalTxt.getText().toString());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(crearEmpresaReq);
    }
}
