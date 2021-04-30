package com.example.quanlycanhan.ui.chi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.quanlycanhan.R;
import com.example.quanlycanhan.adapter.ChiViewPager2Adapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ChiFragment extends Fragment {
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    public ChiFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager2 = view.findViewById(R.id.viewPager2);
        tabLayout = view.findViewById(R.id.tabLayout);


        ChiViewPager2Adapter chiViewPager2Adapter = new ChiViewPager2Adapter(getActivity());
        viewPager2.setAdapter(chiViewPager2Adapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0){
                    tab.setText("Khoản Chi");
                    tab.setIcon(R.drawable.khoanchi);
                }else {
                    tab.setText("Loại Chi");
                    tab.setIcon(R.drawable.loaichi);
                }
            }
        }).attach();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chi, container, false);
        return view;
    }
}