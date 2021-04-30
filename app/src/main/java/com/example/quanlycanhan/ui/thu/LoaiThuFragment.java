package com.example.quanlycanhan.ui.thu;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlycanhan.DAO.ThuChiDAO;
import com.example.quanlycanhan.R;
import com.example.quanlycanhan.model.ThuChi;
import com.example.quanlycanhan.view.LoaiThuView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class LoaiThuFragment extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton fapThem;
    private ArrayList<ThuChi> list = new ArrayList<>();
    private ThuChiDAO daoThuChi;

    public LoaiThuFragment() {
    }


    public static LoaiThuFragment newInstance() {

        return new LoaiThuFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_loai_thu, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewLT);
        fapThem = view.findViewById(R.id.fapButtonLT);
        daoThuChi = new ThuChiDAO(getActivity());

        list = daoThuChi.getThuChi(0);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        final LoaiThuView adapter = new LoaiThuView(getActivity(), list);
        recyclerView.setAdapter(adapter);


        fapThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_tloai);
                final TextView text = dialog.findViewById(R.id.txtLT);
                Button delete = dialog.findViewById(R.id.btnDeleteLT);
                final Button insert = dialog.findViewById(R.id.btnInsertLT);


                insert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String themText = text.getText().toString();
                        if (themText.trim().isEmpty()) {
                            Toast.makeText(getActivity(), "Không được để trống thông tin loại thu !!!", Toast.LENGTH_SHORT).show();
                        } else {
                            ThuChi tc = new ThuChi(0, themText, 0);
                            if (daoThuChi.addTC(tc) == true) {
                                list.clear();
                                list.addAll(daoThuChi.getThuChi(0));
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        text.setText("");
                    }
                });
                dialog.show();
            }
        });


        return view;
    }
}