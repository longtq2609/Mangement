package com.example.quanlycanhan.ui.thu;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlycanhan.DAO.GiaoDichDAO;
import com.example.quanlycanhan.DAO.ThuChiDAO;
import com.example.quanlycanhan.R;
import com.example.quanlycanhan.model.GiaoDich;
import com.example.quanlycanhan.model.ThuChi;
import com.example.quanlycanhan.view.KhoanChiView;
import com.example.quanlycanhan.view.KhoanThuView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class KhoanThuFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton fapThem;
    public static ArrayList<GiaoDich> list = new ArrayList<>();
    ArrayList<ThuChi> listTC = new ArrayList<>();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    GiaoDichDAO daoGiaoDich;
    ThuChiDAO daoThuChi;
    DatePickerDialog datePickerDialog;
    public static KhoanThuView khoanThuView;


    public KhoanThuFragment() {
    }


    public static KhoanThuFragment newInstance() {
        return new KhoanThuFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khoan_thu, container, false);

        recyclerView = view.findViewById(R.id.recyclreViewKC);
        fapThem = view.findViewById(R.id.fapButtonKC);

        daoGiaoDich = new GiaoDichDAO(getActivity());

        list = daoGiaoDich.getGDtheoTC(0);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        khoanThuView = new KhoanThuView(getActivity(), list);
        recyclerView.setAdapter(khoanThuView);

        fapThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_them);
                final TextView moTaGd = dialog.findViewById(R.id.themTenGD);
                final TextView ngayGd = dialog.findViewById(R.id.themNgayGD);
                final TextView tienGd = dialog.findViewById(R.id.themTienGD);
                final Spinner spLoaiGd = dialog.findViewById(R.id.spinnerGD);
                final TextView title = dialog.findViewById(R.id.titleTK);
                final Button delete = dialog.findViewById(R.id.btnDelete);
                final Button insert = dialog.findViewById(R.id.btnInsert);
                daoThuChi = new ThuChiDAO(getActivity());
                listTC = daoThuChi.getThuChi(0);
                title.setText("THÊM KHOẢN THU");

                ngayGd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar calendar = Calendar.getInstance();
                        int d = calendar.get(Calendar.DAY_OF_MONTH);
                        int m = calendar.get(Calendar.MONTH);
                        int y = calendar.get(Calendar.YEAR);
                        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                final String NgayGD = dayOfMonth + "/" + (month + 1) + "/" + year;
                                ngayGd.setText(NgayGD);
                            }
                        }, y, m, d);
                        datePickerDialog.show();
                    }
                });

                final ArrayAdapter sp = new ArrayAdapter(getActivity(), R.layout.spinner, listTC);
                spLoaiGd.setAdapter(sp);

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moTaGd.setText("");
                        ngayGd.setText("");
                        tienGd.setText("");
                        spLoaiGd.setAdapter(sp);
                    }
                });

                insert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mota = moTaGd.getText().toString();
                        String ngay = ngayGd.getText().toString();
                        String tien = tienGd.getText().toString();
                        ThuChi tc = (ThuChi) spLoaiGd.getSelectedItem();
                        int ma = tc.getMaTC();
                        //Check lỗi
                        if (mota.trim().isEmpty() && ngay.isEmpty() && tien.trim().isEmpty()) {
                            Toast.makeText(getActivity(), "Không được để trống thông tin khoản thu !!!", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                GiaoDich gd = new GiaoDich(0, mota, simpleDateFormat.parse(ngay), Integer.parseInt(tien), ma);
                                if (daoGiaoDich.addGD(gd) == true) {
                                    list.clear();
                                    list.addAll(daoGiaoDich.getGDtheoTC(0));
                                    khoanThuView.notifyDataSetChanged();
                                    Toast.makeText(getActivity(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(getActivity(), "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                    }
                });

                dialog.show();
            }
        });


        return view;
    }
}
