package com.example.chatapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class ChatFragRecViewAdapter extends RecyclerView.Adapter<ChatViewHolder>
{
    List< Users > UserModels =  new LinkedList< Users >();
    Users user;

    Context context ;

    public ChatFragRecViewAdapter(List<Users> userModels, Context context)
    {
        UserModels = userModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        Log.d("LOG:TANYAA","Number of Users." +"UserModels.size()");
        View view = inflater.inflate(R.layout.rec_chat,parent,false);
// another way
        //View view1=LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_chat,parent,false);

        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position)
    {
        Log.d("LOG:TANYAA","onBindViewHolder");
        user = UserModels.get(position);
        Log.d("TAG","Tanya"+user.getDp());
        //holder.civMessengerProPic.setImageResource(R.drawable.tanya);
        holder.txtName.setText(user.userName);
        holder.txtLastMsg.setText(user.lastMsg);
        Picasso.get().load(Uri.parse(user.getDp())).placeholder(R.drawable.profile).error(R.drawable.ic_launcher_background).into(holder.civMessengerProPic);


        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                user = UserModels.get(position);
                Intent i = new Intent(context, ChatBox.class);
             i.putExtra("userId", user.getuID());
             i.putExtra("DP", user.getDp());
             i.putExtra("UserName", user.getUserName());
             Log.d("LOG:TANYAA","UserUserID" + user.getuID());
            context.startActivity(i);
            }
        });
    }
    @Override
    public int getItemCount()
    {
        Log.d("LOG:TANYAA","getItemCount() function "+ UserModels.size());
        return UserModels.size();

    }

}
