package com.example.movieapp.Fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieapp.R;

public class FavoriteFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v;
        v = inflater.inflate(R.layout.fragment_home, container, false);

        return v;

    }
}