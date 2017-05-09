package com.herce.examenfinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.herce.examenfinal.Servicios.CONTACTOS_API;

public class ContactoDetailActivity extends AppCompatActivity {

    EditText nombreTxt, imagenURL, latitudTxt, longitudTxt;
    Button editBtn, deleteBtn;
    NetworkImageView image;
    ImageLoader imageLoader;
    String id;

    ProgressDialog progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto_detail);

        Bundle myIntent = getIntent().getExtras();

        if(myIntent != null) {id = myIntent.getString("id");}

        imageLoader = AppController.getInstance().getImageLoader();

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        nombreTxt = (EditText)  findViewById(R.id.nameDetailText);
        latitudTxt = (EditText) findViewById(R.id.latitudDetailText);
        longitudTxt = (EditText) findViewById(R.id.longitudDetailText);
        imagenURL = (EditText) findViewById(R.id.imagenDetailText);
        image = (NetworkImageView) findViewById(R.id.thumbnailDetailText);
        editBtn = (Button) findViewById(R.id.editDetailButton);
        deleteBtn = (Button) findViewById(R.id.deleteDetailButton);

        progress_bar = new ProgressDialog(this);
        progress_bar.setMessage("Please wait...");
        progress_bar.setCancelable(false);
        progress_bar.show();

        StringRequest productsReq = new StringRequest(Request.Method.GET, CONTACTOS_API + "?id=" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress_bar.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if (res.getString("code").equals("01"))
                            {
                                JSONObject contacto = res.getJSONObject("contact_directory");
                                Log.d("Test", contacto.getString("nombre"));
                                nombreTxt.setText(contacto.getString("nombre"), TextView.BufferType.EDITABLE);
                                latitudTxt.setText(contacto.getString("latitud"), TextView.BufferType.EDITABLE);
                                longitudTxt.setText(contacto.getString("longitud"), TextView.BufferType.EDITABLE);
                                imagenURL.setText(contacto.getString("imagen"), TextView.BufferType.EDITABLE);
                                image.setImageUrl(contacto.getString("imagen"), imageLoader);

                            } else if (res.getString("code").equals("04"))
                            {
                                Toast.makeText(ContactoDetailActivity.this, "Error en el query" , Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ContactoDetailActivity.this, "Respuesta no reconocida" , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ContactoDetailActivity.this, "Error! " + e.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress_bar.cancel();
                        Toast.makeText(ContactoDetailActivity.this, "Error: " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        Volley.newRequestQueue(this).add(productsReq);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editContact();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContact();
            }
        });
    }

    void editContact() {

        progress_bar.setMessage("Please wait...");
        progress_bar.setCancelable(false);
        progress_bar.show();
        StringRequest crearContactoReq = new StringRequest(Request.Method.PUT, CONTACTOS_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress_bar.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if(res.getString("code").equals("01"))
                            {
                                Toast.makeText(ContactoDetailActivity.this, "Contacto actualizado" , Toast.LENGTH_SHORT).show();
                                Intent actividad = new Intent(ContactoDetailActivity.this, DirectorioActivity.class);
                                startActivity(actividad);
                            } else if (res.getString("code").equals("02"))
                            {
                                Toast.makeText(ContactoDetailActivity.this, "Faltan valores obligatorios" , Toast.LENGTH_SHORT).show();
                            } else if (res.getString("code").equals("04"))
                            {
                                Toast.makeText(ContactoDetailActivity.this, "Error en query" , Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ContactoDetailActivity.this, "Respuesta desconocida" , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(ContactoDetailActivity.this, "Error! " + e.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress_bar.cancel();
                        Toast.makeText(ContactoDetailActivity.this, "Error: " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }
                }){

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",id);
                params.put("nombre",nombreTxt.getText().toString());
                params.put("imagen",imagenURL.getText().toString());
                params.put("latitud",latitudTxt.getText().toString());
                params.put("longitud",longitudTxt.getText().toString());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(crearContactoReq);

    }

    void deleteContact() {

        progress_bar.setMessage("Please wait...");
        progress_bar.setCancelable(false);
        progress_bar.show();
        StringRequest crearContactoReq = new StringRequest(Request.Method.PUT, CONTACTOS_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress_bar.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if(res.getString("code").equals("01"))
                            {
                                Toast.makeText(ContactoDetailActivity.this, "Contacto borrado" , Toast.LENGTH_SHORT).show();
                                Intent actividad = new Intent(ContactoDetailActivity.this, DirectorioActivity.class);
                                startActivity(actividad);
                            } else if (res.getString("code").equals("02"))
                            {
                                Toast.makeText(ContactoDetailActivity.this, "Faltan valores obligatorios" , Toast.LENGTH_SHORT).show();
                            } else if (res.getString("code").equals("04"))
                            {
                                Toast.makeText(ContactoDetailActivity.this, "Error en query" , Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ContactoDetailActivity.this, "Respuesta desconocida" , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(ContactoDetailActivity.this, "Error! " + e.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress_bar.cancel();
                        Toast.makeText(ContactoDetailActivity.this, "Error: " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }
                }){

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",id);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(crearContactoReq);

    }
}
