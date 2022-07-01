package com.example.letsgrow;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class EnteProfileFragment extends Fragment {

    RecyclerView recyclerView;
    PostAdapter postAdapter;
    ArrayList<Post> list;
    FirebaseAuth firebaseAuth;
    String id,name;
    ImageView meet2;
    TextView enname;
    Fragment MessageFragment;




    public EnteProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ente_profile, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        enname = v.findViewById(R.id.enname);
        meet2 = v.findViewById(R.id.meet2);
        MessageFragment = new MessageFragment();

        meet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(MessageFragment);
            }
        });



        recyclerView = v.findViewById(R.id.view3);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("CurrentUser");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                id = snapshot.child("UserID").getValue(String.class);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                databaseReference.child(id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        name = snapshot.child("Name").getValue(String.class);
                        enname.setText(name);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                databaseReference.child(id).child("Posts").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot ds : snapshot.getChildren()){
                            Post post = ds.getValue(Post.class);
                            list.add(post);
                        }
                        postAdapter = new PostAdapter(getContext(),list);
                        recyclerView.setAdapter(postAdapter);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        return v;
    }
    public void loadFragment(Fragment fragment) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



}