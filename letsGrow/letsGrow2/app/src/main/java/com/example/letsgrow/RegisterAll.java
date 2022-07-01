package com.example.letsgrow;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class RegisterAll extends AppCompatActivity {

    ImageView inve,ente,emp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_all);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        inve = findViewById(R.id.invebut);
        ente = findViewById(R.id.entebut);
        emp = findViewById(R.id.empbut);

        inve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterAll.this,RegisterInvester.class));
            }
        });

        ente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterAll.this,RegisterEntre.class));
            }
        });

        emp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterAll.this,RegisterEmp.class));
            }
        });


    }
}