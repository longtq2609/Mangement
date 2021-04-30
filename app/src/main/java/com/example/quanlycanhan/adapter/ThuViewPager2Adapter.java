package com.example.quanlycanhan.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.quanlycanhan.ui.thu.KhoanThuFragment;
import com.example.quanlycanhan.ui.thu.LoaiThuFragment;

public class ThuViewPager2Adapter extends FragmentStateAdapter {
    public ThuViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = KhoanThuFragment.newInstance();
        } else {
            fragment = LoaiThuFragment.newInstance();
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
