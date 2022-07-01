package com.example.letsgrow;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AfterRegistration extends AppCompatActivity {

    TextView sigm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_registration);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        sigm = findViewById(R.id.sign);
        sigm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AfterRegistration.this,MainActivity.class));
            }
        });
    }
}