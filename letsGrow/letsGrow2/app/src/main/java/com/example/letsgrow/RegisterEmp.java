package com.example.letsgrow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class RegisterEmp extends AppCompatActivity {

    EditText name,email,pass,mobile,exper,skills;
    ImageView reg;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_emp);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        name = findViewById(R.id.empname);
        email = findViewById(R.id.empemail);
        pass = findViewById(R.id.emppass);
        mobile = findViewById(R.id.empmobile);
        exper = findViewById(R.id.empexperi);
        skills = findViewById(R.id.empskills);
        reg = findViewById(R.id.empregister);

        firebaseAuth = FirebaseAuth.getInstance();

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createemp();
            }
        });




    }
    String nameemp,emailemp,passemp,mobileemp,skillemp,experiemp;
    String user = "Employee";

    private void createemp() {
        emailemp = email.getText().toString().trim();
        passemp = pass.getText().toString().trim();

        firebaseAuth.createUserWithEmailAndPassword(emailemp,passemp).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(RegisterEmp.this, "User Created", Toast.LENGTH_SHORT).show();
                save();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterEmp.this, "Not Created", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void save(){

        nameemp = name.getText().toString().trim();
        emailemp = email.getText().toString().trim();
        passemp = pass.getText().toString().trim();
        mobileemp = mobile.getText().toString().trim();
        skillemp = skills.getText().toString().trim();
        experiemp = exper.getText().toString().trim();

        //firebaseAuth.createUserWithEmailAndPassword(emailemp,passemp);



        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("Name",nameemp);
        hashMap.put("User",user);
        hashMap.put("Email",emailemp);
        hashMap.put("Mobile Number",mobileemp);
        hashMap.put("Skills",skillemp);
        hashMap.put("Experience",experiemp);

        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("Employee");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseDatabase.child(firebaseAuth.getUid()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(RegisterEmp.this, "Data Saved", Toast.LENGTH_SHORT).show();
                databaseReference.child(firebaseAuth.getUid()).setValue(hashMap);
                startActivity(new Intent(RegisterEmp.this,AfterRegistration.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterEmp.this, "Not Save", Toast.LENGTH_SHORT).show();

            }
        });
    }
}