package com.example.letsgrow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    Context context;
    ArrayList<Post> list;
    FirebaseAuth firebaseAuth;

    public PostAdapter(Context context, ArrayList<Post> list) {
        this.context = context;
        this.list = list;
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.postdesignente,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Post post = list.get(position);
        holder.name.setText(post.getName());
        holder.data.setText(post.getData());

        String userid = post.getUserid();
        String username = post.getName();

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("Name",username);
                hashMap.put("UserID",userid);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("CurrentUser");
                databaseReference.setValue(hashMap);
                ((Homepage)context).profile(userid);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,data;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.postname);
            data = itemView.findViewById(R.id.posttext);
        }
    }
}
