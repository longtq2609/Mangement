package com.example.quanlycanhan.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlycanhan.DAO.GiaoDichDAO;
import com.example.quanlycanhan.DAO.ThuChiDAO;
import com.example.quanlycanhan.R;
import com.example.quanlycanhan.model.ThuChi;
import com.example.quanlycanhan.ui.chi.KhoanChiFragment;
import com.example.quanlycanhan.ui.thu.KhoanThuFragment;

import java.util.ArrayList;

public class LoaiThuView extends RecyclerView.Adapter<LoaiThuView.ViewHolder> {

    private Context context;
    private ArrayList<ThuChi> list;
    private ThuChiDAO daoThuChi;
    private GiaoDichDAO daoGiaoDich;

    public LoaiThuView(ArrayList<ThuChi> list) {
    }

    public LoaiThuView(Context context, ArrayList<ThuChi> list) {
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
        holder.text.setText(list.get(position).getTenTC());
        holder.text.setText(list.get(position).getTenTC());
        daoThuChi = new ThuChiDAO(context);
        daoGiaoDich = new GiaoDichDAO(context);
        final ThuChi tc = list.get(position);
        holder.imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_tloai);
                final TextView text = dialog.findViewById(R.id.txtLT);
                Button xoa = dialog.findViewById(R.id.btnDeleteLT);
                final Button them = dialog.findViewById(R.id.btnInsertLT);
                final TextView title = dialog.findViewById(R.id.titleTL);
                title.setText("SỬA LOẠI THU");
                text.setText(tc.getTenTC());
                them.setText("SỬA");

                them.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String themText = text.getText().toString();
                        ThuChi thuchi = new ThuChi(tc.getMaTC(), themText, 0);
                        if (themText.trim().isEmpty()) {
                            Toast.makeText(context, "Không được để trống thông tin !!!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (daoThuChi.editTC(thuchi) == true) {
                                list.clear();
                                list.addAll(daoThuChi.getThuChi(0));
                                notifyDataSetChanged();
                                Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(context, "Sửa thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });

                xoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        text.setText("");
                    }
                });
                dialog.show();
            }
        });


        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.xoa);
                builder.setMessage("Xóa loại thu sẽ mất tất cả thông tin thuộc loại thu !!!")
                        .setCancelable(false)
                        .setPositiveButton("Vẫn Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (daoThuChi.deleteTC(tc)) {
                                    list.clear();
                                    list.addAll(daoThuChi.getThuChi(0));
                                    KhoanThuFragment.list.clear();
                                    KhoanThuFragment.list.addAll(daoGiaoDich.getGDtheoTC(0));
                                    KhoanThuFragment.khoanThuView.notifyDataSetChanged();
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
        if (list == null) return 0;
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private ImageView imgSua, imgXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textList);
            imgSua = itemView.findViewById(R.id.imgSua);
            imgXoa = itemView.findViewById(R.id.imgXoa);

        }
    }
}
