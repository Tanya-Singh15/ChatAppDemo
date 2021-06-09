package com.example.chatapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatViewHolder extends RecyclerView.ViewHolder
{

    CircleImageView civMessengerProPic ;
    TextView txtLastMsg , txtName ;

    public ChatViewHolder(@NonNull View itemView)
    {
        super(itemView);

        civMessengerProPic = itemView.findViewById(R.id.rec_profile_image);
        txtName = itemView.findViewById(R.id.txtName);
        txtLastMsg = itemView.findViewById(R.id.txtLastMsg);
    }
}

