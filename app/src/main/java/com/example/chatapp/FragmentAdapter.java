package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.LinkedList;
import java.util.List;

public class FragmentAdapter extends FragmentStateAdapter

{
    List< Fragment > totalFrag ;
    List< String > title ;

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity)
    {
        super(fragmentActivity);
        totalFrag =  new LinkedList< Fragment>();
        title =  new LinkedList<String>();

        totalFrag.add(new ChatFragment());
        totalFrag.add(new StatusFragment());
        totalFrag.add(new CallFragment());

        title.add("CHATS");
        title.add("STATUS");
        title.add("CALLS");
    }

    @NonNull @Override
    public Fragment createFragment(int position)
    {
         return totalFrag.get(position);
    }

    @Override
    public int getItemCount()
    {
        return totalFrag.size();
    }
}
