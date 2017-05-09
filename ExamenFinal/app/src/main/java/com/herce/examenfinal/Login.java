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

public class Login extends AppCompatActivity {

    Button loginBtn, signupBtn;
    EditText userTxt, passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button)findViewById(R.id.loginButton);
        signupBtn = (Button)findViewById(R.id.signupButtonLogin);
        userTxt = (EditText)findViewById(R.id.emailTextField);
        passwordTxt = (EditText)findViewById(R.id.passwordTextField);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actividad = new Intent(Login.this, Signup.class);
                startActivity(actividad);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    private void login()
    {
        final ProgressDialog progress_bar = new ProgressDialog(Login.this);
        progress_bar.setMessage("Please wait...");
        progress_bar.setCancelable(false);
        progress_bar.show();

        String user, pass;

        user = userTxt.getText().toString();
        pass = passwordTxt.getText().toString();

        StringRequest loginReq = new StringRequest(Request.Method.GET, AUTH_API + "?usuario=" + user + "&password=" + pass,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress_bar.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if (res.getString("code").equals("01"))
                            {
                                    Intent actividad = new Intent(Login.this, MainActivity.class);
                                    startActivity(actividad);
                            } else if (res.getString("code").equals("02"))
                            {
                                Toast.makeText(Login.this, "Faltan valores obligatorios" , Toast.LENGTH_SHORT).show();
                            } else if (res.getString("code").equals("03"))
                            {
                                Toast.makeText(Login.this, "Datos incorrectos" , Toast.LENGTH_SHORT).show();
                            } else if (res.getString("code").equals("04"))
                            {
                                Toast.makeText(Login.this, "Error" , Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Login.this,"Respuesta desconocida" , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Login.this, "Error! " + e.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress_bar.cancel();
                        Toast.makeText(Login.this, "Error: " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(loginReq);
    }
}
