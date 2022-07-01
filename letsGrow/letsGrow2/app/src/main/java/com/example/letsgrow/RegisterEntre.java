package com.example.letsgrow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterEntre extends AppCompatActivity {

    EditText name,email,pass,mobile,businame,location,noofemp;
    ImageView reg;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_entre);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        firebaseAuth = FirebaseAuth.getInstance();
        reg = findViewById(R.id.entregister);
        name = findViewById(R.id.entname);
        email = findViewById(R.id.entemail);
        pass = findViewById(R.id.entpass);
        location = findViewById(R.id.entlocation);
        mobile = findViewById(R.id.entmobile);
        businame = findViewById(R.id.entbusiname);
        noofemp = findViewById(R.id.entnoemp);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createent();
            }
        });
    }

    String entname,entemail,entpass,entmobile,entloc,entbusi,entnoof;

    String user = "Entrepreneur";

    private void createent() {

        entemail = email.getText().toString().trim();
        entpass = pass.getText().toString().trim();

        firebaseAuth.createUserWithEmailAndPassword(entemail,entpass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(RegisterEntre.this, "User Created", Toast.LENGTH_SHORT).show();
                save();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterEntre.this, "Not Created", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void save() {

        entname = name.getText().toString().trim();
        entemail = email.getText().toString().trim();
        entpass = pass.getText().toString().trim();
        entmobile = mobile.getText().toString().trim();
        entloc = location.getText().toString().trim();
        entbusi = businame.getText().toString().trim();
        entnoof = noofemp.getText().toString().trim();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("Name",entname);
        hashMap.put("User",user);
        hashMap.put("Email",entemail);
        hashMap.put("Mobile Number",entmobile);
        hashMap.put("Location",entloc);
        hashMap.put("Business Name",entbusi);
        hashMap.put("No of Employees",entnoof);

        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("Entrepreneur");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseDatabase.child(firebaseAuth.getUid()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(RegisterEntre.this, "Data Saved", Toast.LENGTH_SHORT).show();
                databaseReference.child(firebaseAuth.getUid()).setValue(hashMap);
                startActivity(new Intent(RegisterEntre.this,AfterRegistration.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterEntre.this, "Not Save", Toast.LENGTH_SHORT).show();

            }
        });
    }
}