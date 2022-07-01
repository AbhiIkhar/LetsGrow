package com.example.letsgrow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Homepage extends AppCompatActivity {

    ImageView msg,post,home,search,info;
    FirebaseAuth firebaseAuth;
    String type;
    String ente,inve,emp;
    private Fragment HomeFragment,PostFragment,ProfileFragment,EnteProfileFragment,EmpProfileFragment,ShowMsgFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        firebaseAuth = FirebaseAuth.getInstance();
        msg = findViewById(R.id.msg);
        post = findViewById(R.id.post);
        home = findViewById(R.id.homec);
        search = findViewById(R.id.search);
        info = findViewById(R.id.info);
        
        HomeFragment = new HomeFragment();
        PostFragment = new PostFragment();
        ProfileFragment = new ProfileFragment();
        EnteProfileFragment = new EnteProfileFragment();
        EmpProfileFragment = new EmpProfileFragment();
        ShowMsgFragment = new ShowMsgFragment();
        loadFragment(HomeFragment);



        ente = "Entrepreneur";
        inve = "Investor";
        emp = "Employee";

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                type = snapshot.child("User").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(HomeFragment);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(PostFragment);
            }
        });

        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(ShowMsgFragment);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("UserID",firebaseAuth.getUid());
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("CurrentUser");
                databaseReference.setValue(hashMap);

                if (type.equals(inve)){
                    loadFragment(ProfileFragment);
                }
                else if (type.equals(ente)){
                    loadFragment(EnteProfileFragment);
                }else if (type.equals(emp)){
                    loadFragment(EmpProfileFragment);

                }else {
                    Toast.makeText(Homepage.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
                }


            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(Homepage.this,MainActivity.class));
            }
        });
    }

    private void loadFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home,fragment);
        transaction.commit();
    }
    String ustype;

    public void profile(String id){


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ustype = snapshot.child("User").getValue(String.class);
                if (ustype.equals(inve)){
                    loadFragment(ProfileFragment);
                }
                else if (ustype.equals(ente)){
                    loadFragment(EnteProfileFragment);
                }else {
                    Toast.makeText(Homepage.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}