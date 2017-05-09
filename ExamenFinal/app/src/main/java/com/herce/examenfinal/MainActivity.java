package com.herce.examenfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button directorioBtn, addContactBtn, addEmpresaBtn, verMapaBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        directorioBtn = (Button)findViewById(R.id.directorioButton);
        addContactBtn = (Button)findViewById(R.id.addContactoButton);
        addEmpresaBtn = (Button)findViewById(R.id.addEmpresaButton);
        verMapaBtn = (Button)findViewById(R.id.verMapaButton);

        directorioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actividad = new Intent(MainActivity.this, DirectorioActivity.class);
                startActivity(actividad);
            }
        });

        addEmpresaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actividad = new Intent(MainActivity.this, AddEmpresaActivity.class);
                startActivity(actividad);
            }
        });

        addContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actividad = new Intent(MainActivity.this, AddContactoActivity.class);
                startActivity(actividad);
            }
        });

        verMapaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actividad = new Intent(MainActivity.this, MapActivity.class);
                startActivity(actividad);
            }
        });
    }
}
