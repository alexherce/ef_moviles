package com.herce.examenfinal;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.herce.examenfinal.Servicios.CONTACTOS_API;

public class DirectorioActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Contactos> contactList;
    private ContactosAdapter adaptador;
    ProgressDialog progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directorio);

        contactList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.directorio_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        progress_bar = new ProgressDialog(this);
        progress_bar.setMessage("Please wait...");
        progress_bar.setCancelable(false);
        progress_bar.show();

        StringRequest productsReq = new StringRequest(Request.Method.GET, CONTACTOS_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress_bar.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if (res.getString("code").equals("01"))
                            {
                                JSONArray contactos = res.getJSONArray("contact_directory");
                                contactList = ParserJSON.parseaArregloContactos(contactos);
                                adaptador = new ContactosAdapter(DirectorioActivity.this, contactList);
                                recyclerView.setAdapter(adaptador);
                                adaptador.notifyDataSetChanged();

                            } else if (res.getString("code").equals("04"))
                            {
                                Toast.makeText(DirectorioActivity.this, "Error en el query" , Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(DirectorioActivity.this, "Respuesta no reconocida" , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DirectorioActivity.this, "Error! " + e.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress_bar.cancel();
                        Toast.makeText(DirectorioActivity.this, "Error: " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        Volley.newRequestQueue(this).add(productsReq);

    }
}
