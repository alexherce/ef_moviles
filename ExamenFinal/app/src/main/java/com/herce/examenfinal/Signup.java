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

import static com.herce.examenfinal.Servicios.AUTH_API;

public class Signup extends AppCompatActivity {

    Button loginBtn, signupBtn;
    EditText nombreTxt, appaternoTxt, apmaternoText, usuarioTxt, passwordTxt;
    ProgressDialog progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        loginBtn = (Button)findViewById(R.id.loginButtonSignup);
        signupBtn = (Button)findViewById(R.id.signupButton);
        nombreTxt = (EditText)findViewById(R.id.nameTextField);
        appaternoTxt = (EditText)findViewById(R.id.apellidopatTextField);
        apmaternoText = (EditText)findViewById(R.id.apellidomatTextField);
        usuarioTxt = (EditText)findViewById(R.id.usernameTextFieldSignup);
        passwordTxt = (EditText)findViewById(R.id.passwordTextField);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actividad = new Intent(Signup.this, Login.class);
                startActivity(actividad);
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    signup();
            }
        });
    }

    private void signup()
    {
        final ProgressDialog progress_bar = new ProgressDialog(Signup.this);
        progress_bar.setMessage("Please wait");
        progress_bar.setCancelable(false);
        progress_bar.show();

        StringRequest signupReq = new StringRequest(Request.Method.POST, AUTH_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress_bar.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if(res.getString("code").equals("01"))
                            {
                                Toast.makeText(Signup.this, "Usuario registrado" , Toast.LENGTH_SHORT).show();
                                Intent actividad = new Intent(Signup.this, Login.class);
                                startActivity(actividad);
                            } else if (res.getString("code").equals("02"))
                            {
                                Toast.makeText(Signup.this, "Faltan valores obligatorios" , Toast.LENGTH_SHORT).show();
                            } else if (res.getString("code").equals("04"))
                            {
                                Toast.makeText(Signup.this, "Error en query" , Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Signup.this, "Respuesta desconocida" , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(Signup.this, "Error! " + e.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress_bar.cancel();
                        Toast.makeText(Signup.this, "Error: " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("nombre",nombreTxt.getText().toString());
                params.put("appaterno",appaternoTxt.getText().toString());
                params.put("apmaterno",apmaternoText.getText().toString());
                params.put("usuario",usuarioTxt.getText().toString());
                params.put("password",passwordTxt.getText().toString());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(signupReq);
    }
}
