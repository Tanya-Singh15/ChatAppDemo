package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

public class ChatAppHome extends AppCompatActivity
{
   TabLayout tabLayout;
   ViewPager2 viewPager2 ;
   //Button SignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_app_home);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);

        FragmentAdapter fragmentAdapter = new FragmentAdapter(this);
        viewPager2.setAdapter(fragmentAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy()
        {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position)
            {
                tab.setText(fragmentAdapter.title.get(position));
            }
        }).attach();
        ChatFragment.getUsers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.btnSignOut :
                {

                    FirebaseAuth.getInstance().signOut();
                    Intent i = new Intent(ChatAppHome.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }

            case R.id.Settings :
                {
                    Intent i = new Intent(ChatAppHome.this,Settings.class);
                    startActivity(i);
                    finish();
                }

        }
        return super.onOptionsItemSelected(item);
    }
}