package com.example.todoapplication.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.todoapplication.fragments.Notes_Fragment;
import com.example.todoapplication.fragments.Task_Fragment;

import java.util.ArrayList;
import java.util.List;

public class  ViewPgagerAdapter extends FragmentStateAdapter {


    public ViewPgagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }




    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Task_Fragment();
            case 1:
                return new Notes_Fragment();
            default:
                return null;
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
