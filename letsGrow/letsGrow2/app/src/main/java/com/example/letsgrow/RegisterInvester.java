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

public class RegisterInvester extends AppCompatActivity {

    EditText name,email,pass,mobile,domain,firm,noofin;
    ImageView reg;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_invester);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        firebaseAuth = FirebaseAuth.getInstance();
        reg = findViewById(R.id.registerinve);
        name = findViewById(R.id.invename);
        email = findViewById(R.id.inveemail);
        pass = findViewById(R.id.invepass);
        domain = findViewById(R.id.invedomain);
        mobile = findViewById(R.id.invemobile);
        firm = findViewById(R.id.invefirm);
        noofin = findViewById(R.id.invenoofinve);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createinve();
            }
        });
    }

    String invename,inveemail,invepass,invemobile,invedomain,invefirm,invenoof;
    String user = "Investor";

    private void createinve() {

        inveemail = email.getText().toString().trim();
        invepass = pass.getText().toString().trim();

        firebaseAuth.createUserWithEmailAndPassword(inveemail,invepass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(RegisterInvester.this, "User Created", Toast.LENGTH_SHORT).show();
                save();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterInvester.this, "Not Created", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void save() {

        invename = name.getText().toString().trim();
        inveemail = email.getText().toString().trim();
        invepass = pass.getText().toString().trim();
        invemobile = mobile.getText().toString().trim();
        invedomain = domain.getText().toString().trim();
        invefirm = firm.getText().toString().trim();
        invenoof = noofin.getText().toString().trim();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("Name",invename);
        hashMap.put("User",user);
        hashMap.put("Email",inveemail);
        hashMap.put("Mobile Number",invemobile);
        hashMap.put("Domain",invedomain);
        hashMap.put("Firm",invefirm);
        hashMap.put("No Of Investments",invenoof);

        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("Investor");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseDatabase.child(firebaseAuth.getUid()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(RegisterInvester.this, "Data Saved", Toast.LENGTH_SHORT).show();
                databaseReference.child(firebaseAuth.getUid()).setValue(hashMap);
                startActivity(new Intent(RegisterInvester.this,AfterRegistration.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterInvester.this, "Not Save", Toast.LENGTH_SHORT).show();

            }
        });

    }
}