package com.example.quanlycanhan.ui.thongke;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.quanlycanhan.DAO.GiaoDichDAO;
import com.example.quanlycanhan.R;
import com.example.quanlycanhan.model.GiaoDich;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ThongKeFragment extends Fragment {
    private TextView thu, chi, conlai;
    private GiaoDichDAO daoGiaoDich;

    public ThongKeFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_thong_ke, container, false);
        thu = view.findViewById(R.id.tienThu);
        chi = view.findViewById(R.id.tienChi);
        conlai = view.findViewById(R.id.tienConLai);
        daoGiaoDich = new GiaoDichDAO(getActivity());


        final NumberFormat numberFormat = new DecimalFormat("#,###");
        final ArrayList<GiaoDich> listThu = daoGiaoDich.getGDtheoTC(0);
        final ArrayList<GiaoDich> listChi = daoGiaoDich.getGDtheoTC(1);
        int tongThu = 0, tongChi = 0;
        for (int i = 0; i < listThu.size(); i++) {
            tongThu += listThu.get(i).getTienGD();
        }
        for (int i = 0; i < listChi.size(); i++) {
            tongChi += Math.abs(listChi.get(i).getTienGD());
        }

        thu.setText(numberFormat.format(tongThu) + " VND");
        chi.setText(numberFormat.format(tongChi) + " VND");
        conlai.setText(numberFormat.format(tongThu - tongChi) + " VND");


        return view;
    }
}