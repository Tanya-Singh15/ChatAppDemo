package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.LinkedList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatBox extends AppCompatActivity
{
    TextView UserName;
    ImageView sendBtn , btnBack;
    EditText sendMessage;
    DatabaseReference mReference;
    RecyclerView rcvChatBox;
    CircleImageView profile_pic;
    rcvChatBoxAdapter rcvChatBoxAdapterr ;
    LinkedList <individualMsg> individualMsgs;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        individualMsgs = new LinkedList<>();
        setContentView(R.layout.activity_chat_box);
        mReference = FirebaseDatabase.getInstance().getReference().child("Chats");
        getSupportActionBar().hide();


        String userName = getIntent().getStringExtra("UserName");
        String receiverUID = getIntent().getStringExtra("userId");
        String senderUID = FirebaseAuth.getInstance().getUid();
        String senderChatDB = senderUID.concat(receiverUID);
        String receiverChatDB = receiverUID.concat(senderUID);
        String profilePic = getIntent().getStringExtra("DP");
        Log.d("LOG:TANYAA","UserName" +userName);

        UserName = findViewById(R.id.tvUserName);
        UserName.setText(userName.toString());

        sendBtn = findViewById(R.id.sendBtn);
        btnBack = findViewById(R.id.btnBack);
        sendMessage = findViewById(R.id.sendMessage);
        rcvChatBox = findViewById(R.id.rcvChatBox);
        rcvChatBoxAdapterr = new rcvChatBoxAdapter(this.individualMsgs,this);
        rcvChatBox.setLayoutManager(new LinearLayoutManager(this));
        rcvChatBox.setAdapter(rcvChatBoxAdapterr);
        profile_pic = findViewById(R.id.rec_profile_image);
        Picasso.get().load(Uri.parse(profilePic)).placeholder(R.drawable.profile).error(R.drawable.ic_launcher_background).into(profile_pic);

        FirebaseDatabase.getInstance().getReference().child("Chats").child(senderChatDB).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                individualMsgs.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    individualMsg indvMsg = dataSnapshot.getValue(individualMsg.class);
                    individualMsgs.add(indvMsg);
                }
                rcvChatBoxAdapterr.notifyDataSetChanged();
               rcvChatBox.scrollToPosition(individualMsgs.size() -1 );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(ChatBox.this,ChatFragment.class);
                startActivity(i);

            }
        });

       sendBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                individualMsg msg = new individualMsg(sendMessage.getText().toString(),senderUID, new Date().getTime());
                sendMessage.setText("");
                mReference.child(senderChatDB).push().setValue(msg);
                mReference.child(receiverChatDB).push().setValue(msg);

            }
        });
    }
}