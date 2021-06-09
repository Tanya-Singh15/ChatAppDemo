package com.example.chatapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class ChatFragment extends Fragment
{
    private RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase ;
    static LinkedList<Users> AllUsers;
    ChatFragRecViewAdapter chatFragRecViewAdapter ;
    DatabaseReference mReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ChatFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getUsers();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference().child("User");

        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task)
            {
                if (!task.isSuccessful())
                {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));

                    //Toast.makeText(getContext(), task.getResult().getValue().toString() , Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        AllUsers = new LinkedList<>();
        getUsers();


      //  Log.d("LOG:TANYA","getUsers() successful  "+AllUsers.size());

//        ChatFragRecViewAdapter.setChatModels(AllUsers);
//        Log.d("LOG:TANYA","setChatModels successful");

        View view = inflater.inflate(R.layout.fragment_chat,container, false);
        Log.d("LOG:TANYA","view inflated successfully");

        recyclerView = view.findViewById(R.id.recViewChat);
        recyclerView.setHasFixedSize(true);
        Log.d("LOG:TANYA","Rec view initialized");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.d("LOG:TANYA","layout set rcv");

        Log.d("LOG:TANYA","GET ALL USERS1 ");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");

        chatFragRecViewAdapter = new ChatFragRecViewAdapter(AllUsers, getContext());
        recyclerView.setAdapter(chatFragRecViewAdapter);

        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                AllUsers.clear();
                Log.d("LOG:TANYA","USERS4 On data changed");
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Users users = dataSnapshot.getValue(Users.class);

                    AllUsers.add(users);
                    //UserModels.add(users);

                    Log.d("LOG:TANYAA","WE RECEIVED ALL USERS1  User added: "+users.toString());
                }

                chatFragRecViewAdapter.notifyDataSetChanged();
                Log.d("LOG:TANYA","WE RECEIVED ALL USERS3 Set Adapter ");

                // chatFragRecViewAdapter.UserModels = AllUsers;
                Log.d("LOG:TANYA"," Data Transfer to chat fragment size " +AllUsers.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });


        // Inflate the layout for this fragment
        return view ;
    }
    public static void getUsers()
    {

    }

}