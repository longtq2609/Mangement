package com.example.quanlycanhan.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanlycanhan.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class GioiThieuFragment extends Fragment {
    private FloatingActionButton call;


    public GioiThieuFragment() {
        // Required empty public constructor
    }

    public static GioiThieuFragment newInstance(String param1, String param2) {
        GioiThieuFragment fragment = new GioiThieuFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gioi_thieu, container, false);
    }
}