package com.example.chatapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.LinkedList;

public class rcvChatBoxAdapter extends RecyclerView.Adapter
{
    final int INCOMING_MSG_VIEWTYPE = 1;
    final int OUTGOING_MSG_VIEWTYPE = 2;
    LinkedList <individualMsg> individualMsgs;
    Context context ;


    public rcvChatBoxAdapter(LinkedList<individualMsg> individualMsgs, Context context)
    {
        this.individualMsgs = individualMsgs;
        this.context = context;
    }
    @Override
    public int getItemViewType(int position)
    {
        Log.d("LOG:TANYAA1",individualMsgs.get(position).getSenderId().toString());
        Log.d("LOG:TANYAA2",(FirebaseAuth.getInstance().getUid()));
        Log.d("LOG:TANYAA3",(position)+ "");
        if(individualMsgs.get(position).getSenderId().equals(FirebaseAuth.getInstance().getUid().toString()))
        {
            return OUTGOING_MSG_VIEWTYPE;
        }
        else
        {
            return INCOMING_MSG_VIEWTYPE;
        }

        //return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(viewType == INCOMING_MSG_VIEWTYPE)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.incoming_msg,parent,false);
            return new IncomingViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.outgoing_msg,parent,false);
            return new OutgoingViewHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
      individualMsg indMsg = individualMsgs.get(position);
      if( holder.getClass() == IncomingViewHolder.class )
      {
          ((IncomingViewHolder) holder).incomingMsg.setText(indMsg.getText());
      }
      else
      {
          ((OutgoingViewHolder) holder).outgoingMsg.setText(indMsg.getText());
      }
    }

    @Override
    public int getItemCount()
    {
        Log.d("TANYAA IndMsgCntRecCht",individualMsgs.size() + "");
        return individualMsgs.size();
    }

     class IncomingViewHolder extends RecyclerView.ViewHolder
    {
        TextView incomingMsg , timeStamp;

        public IncomingViewHolder(@NonNull View itemView)
        {
            super(itemView);
            incomingMsg = itemView.findViewById(R.id.tvMsg);
            timeStamp = itemView.findViewById(R.id.tvTime);
        }
    }
    class OutgoingViewHolder extends RecyclerView.ViewHolder
    {
        TextView outgoingMsg , timeStamp;

        public OutgoingViewHolder(@NonNull View itemView)
        {
            super(itemView);
            outgoingMsg = itemView.findViewById(R.id.tvMsg);
            timeStamp = itemView.findViewById(R.id.tvTime);
        }
    }
}


