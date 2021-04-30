package com.example.quanlycanhan.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlycanhan.DAO.GiaoDichDAO;
import com.example.quanlycanhan.DAO.ThuChiDAO;
import com.example.quanlycanhan.R;
import com.example.quanlycanhan.model.GiaoDich;
import com.example.quanlycanhan.model.ThuChi;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class KhoanChiView extends RecyclerView.Adapter<KhoanChiView.ViewHolder> {
    private Context context;
    public static ArrayList<GiaoDich> list;
    private GiaoDichDAO daoGiaoDich;
    private ArrayList<ThuChi> listTC = new ArrayList<>();
    private ThuChiDAO daoThuChi;
    private DatePickerDialog datePickerDialog;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public KhoanChiView() {
    }

    public KhoanChiView(Context context, ArrayList<GiaoDich> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_recycleview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text.setText(list.get(position).getTenGD());
        daoGiaoDich = new GiaoDichDAO(context);
        final GiaoDich gd = list.get(position);

        holder.imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_them);
                final TextView moTaGd = dialog.findViewById(R.id.themTenGD);
                final TextView ngayGd = dialog.findViewById(R.id.themNgayGD);
                final TextView tienGd = dialog.findViewById(R.id.themTienGD);
                final Spinner spLoaiGd = dialog.findViewById(R.id.spinnerGD);
                final TextView title = dialog.findViewById(R.id.titleTK);
                final Button xoa = dialog.findViewById(R.id.btnDelete);
                final Button them = dialog.findViewById(R.id.btnInsert);
                daoThuChi = new ThuChiDAO(context);
                listTC = daoThuChi.getThuChi(1);
                title.setText("SỬA KHOẢN CHI");
                them.setText("SỬA");
                moTaGd.setText(gd.getTenGD());
                ngayGd.setText(simpleDateFormat.format(gd.getNgayGD()));
                tienGd.setText(String.valueOf(gd.getTienGD()));
                final ArrayAdapter sp = new ArrayAdapter(context, R.layout.spinner, listTC);
                spLoaiGd.setAdapter(sp);
                int vitri = -1;
                for (int i = 0; i < listTC.size(); i++) {
                    if (listTC.get(i).getTenTC().equalsIgnoreCase(daoThuChi.getTen(gd.getMaTC()))) {
                        vitri = i;
                        break;
                    }
                }
                spLoaiGd.setSelection(vitri);

                ngayGd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar calendar = Calendar.getInstance();
                        int d = calendar.get(Calendar.DAY_OF_MONTH);
                        int m = calendar.get(Calendar.MONTH);
                        int y = calendar.get(Calendar.YEAR);
                        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                final String NgayGD = dayOfMonth + "/" + month + "/" + year;
                                ngayGd.setText(NgayGD);
                            }
                        }, y, m, d);
                        datePickerDialog.show();
                    }
                });
                xoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moTaGd.setText("");
                        ngayGd.setText("");
                        tienGd.setText("");
                        spLoaiGd.setAdapter(sp);
                    }
                });
                them.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mota = moTaGd.getText().toString();
                        String ngay = ngayGd.getText().toString();
                        String tien = tienGd.getText().toString();
                        ThuChi tc = (ThuChi) spLoaiGd.getSelectedItem();
                        int ma = tc.getMaTC();
                        if (mota.trim().isEmpty() && ngay.trim().isEmpty() && tien.trim().isEmpty()) {
                            Toast.makeText(context, "Không được để trống thông tin !!!", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                GiaoDich giaoDich = new GiaoDich(gd.getMaGD(), mota, simpleDateFormat.parse(ngay), Integer.parseInt(tien), ma);
                                if (daoGiaoDich.editGD(giaoDich) == true) {
                                    list.clear();
                                    list.addAll(daoGiaoDich.getGDtheoTC(1));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(context, "Sửa thất bại!", Toast.LENGTH_SHORT).show();
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
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.xoa);
                builder.setMessage("Bạn muốn xóa khoản chi?")
                        .setCancelable(false)
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (daoGiaoDich.deleteGD(gd) == true) {
                                    list.clear();
                                    list.addAll(daoGiaoDich.getGDtheoTC(1));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView text;
        private ImageView imgSua, imgXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textList);
            imgSua = itemView.findViewById(R.id.imgSua);
            imgXoa = itemView.findViewById(R.id.imgXoa);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            GiaoDich gd = list.get(position);
            NumberFormat fm = new DecimalFormat("#,###");
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.thong_tin);
            TextView title, ten, ngay, tien, loai;
            ten = dialog.findViewById(R.id.ten);
            ngay = dialog.findViewById(R.id.ngay);
            tien = dialog.findViewById(R.id.tien);
            loai = dialog.findViewById(R.id.thongTinLoai);
            title = dialog.findViewById(R.id.thongTin);
            title.setText("THÔNG TIN CHI");
            ten.setText(gd.getTenGD());
            ngay.setText(simpleDateFormat.format(gd.getNgayGD()));
            tien.setText(fm.format(gd.getTienGD()) + " VND");
            daoThuChi = new ThuChiDAO(context);
            loai.setText(daoThuChi.getTen(gd.getMaTC()));
            dialog.show();
        }
    }
}
