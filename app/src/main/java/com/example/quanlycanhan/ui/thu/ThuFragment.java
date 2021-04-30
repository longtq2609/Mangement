package com.example.quanlycanhan.ui.thu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.quanlycanhan.R;
import com.example.quanlycanhan.adapter.ThuViewPager2Adapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ThuFragment extends Fragment {
    private ViewPager2 pager2;
    private TabLayout tabLayout;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pager2= view.findViewById(R.id.viewPager2);
        tabLayout = view.findViewById(R.id.tabLayout);
        ThuViewPager2Adapter thuViewPager2Adapter = new ThuViewPager2Adapter(getActivity());
        pager2.setAdapter(thuViewPager2Adapter);
        new TabLayoutMediator(tabLayout, pager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0){
                    tab.setText("Khoản Thu");
                    tab.setIcon(R.drawable.khoanthu);
                }else {
                    tab.setText("Loại Thu");
                    tab.setIcon(R.drawable.loaithu);
                }
            }
        }).attach();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_thu, container, false);

        return root;
    }
}